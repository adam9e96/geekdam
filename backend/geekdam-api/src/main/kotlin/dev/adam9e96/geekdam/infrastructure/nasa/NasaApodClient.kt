package dev.adam9e96.geekdam.infrastructure.nasa

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.bodyAsText
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.delay
import java.io.IOException
import java.time.LocalDate
import kotlin.time.Duration.Companion.milliseconds

/**
 * NASA APOD API 전용 클라이언트.
 * 외부 API 호출 세부사항을 feature/service 계층에서 분리.
 */
class NasaApodClient(
    private val httpClient: HttpClient,
    private val apiKey: String,
    private val baseUrl: String = "https://api.nasa.gov",
) {

    private val maxAttempts: Int = 2
    private val retryDelayMillis: Long = 500

    /**
     * 오늘 날짜 기준 APOD를 가져오기.
     */
    suspend fun getTodayApod(hd: Boolean = true): NasaApodResponse =
        executeWithRetry {
            val response: HttpResponse = httpClient.get("$baseUrl/planetary/apod") {
                parameter("api_key", apiKey)
                parameter("hd", hd)
            }

            response.toNasaApodResponse()
        }

    /**
     * 특정 날짜의 APOD를 가져옴.
     */
    suspend fun getApodByDate(date: LocalDate, hd: Boolean = true): NasaApodResponse =
        executeWithRetry {
            val response: HttpResponse = httpClient.get("$baseUrl/planetary/apod") {
                parameter("api_key", apiKey)
                parameter("date", date.toString())
                parameter("hd", hd)
            }

            response.toNasaApodResponse()
        }

    /**
     * NASA 응답을 APOD DTO로 변환.
     * 먼저 상태코드를 확인한 뒤 정상 응답일 때만 DTO 역직렬화를 시도.
     */
    private suspend fun HttpResponse.toNasaApodResponse(): NasaApodResponse {
        if (!status.isSuccess()) {
            val errorBody: String = bodyAsText()
            val summarizedBody = errorBody.take(500)

            throw NasaApiException(
                statusCode = status.value,
                responseBody = summarizedBody,
                message = "NASA APOD 요청 실패: status=${status.value}, body=$summarizedBody",
            )
        }

        return body()
    }

    /**
     * NASA API 호출이 첫 시도에서 실패할 때를 대비해 짧은 재시도를 수행.
     */
    private suspend fun <T> executeWithRetry(block: suspend () -> T): T {
        repeat(maxAttempts - 1) { attempt ->
            try {
                return block()
            } catch (exception: Exception) {
                if (!exception.isRetryable()) {
                    throw exception
                }

                // 첫 시도 실패 시 짧게 기다렸다가 한 번 더 시도합니다.
                delay(retryDelayMillis.milliseconds)
            }
        }

        return block()
    }

    /**
     * 재시도할 가치가 있는 예외인지 판단합니다.
     * 일시적인 외부 장애나 타임아웃 계열만 재시도 대상으로 봅니다.
     */
    private fun Exception.isRetryable(): Boolean =
        when (this) {
            is NasaApiException -> statusCode == 502 || statusCode == 503 || statusCode == 504
            is HttpRequestTimeoutException -> true
            is ConnectTimeoutException -> true
            is SocketTimeoutException -> true
            is IOException -> true
            else -> false
        }
}
