package dev.adam9e96.geekdam.infrastructure.nasa

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.LocalDate

/**
 * NASA APOD API 전용 클라이언트.
 * 외부 API 호출 세부사항을 feature/service 계층에서 분리.
 */
class NasaApodClient(
    private val httpClient: HttpClient,
    private val apiKey: String,
    private val baseUrl: String = "https://api.nasa.gov",
) {

    /**
     * 오늘 날짜 기준 APOD를 가져오기.
     */
    suspend fun getTodayApod(hd: Boolean = true): NasaApodResponse =
        httpClient.get("$baseUrl/planetary/apod") {
            parameter("api_key", apiKey)
            parameter("hd", hd)
        }.body()

    /**
     * 특정 날짜의 APOD를 가져옴.
     */
    suspend fun getApodByDate(date: LocalDate, hd: Boolean = true): NasaApodResponse =
        httpClient.get("$baseUrl/planetary/apod") {
            parameter("api_key", apiKey)
            parameter("date", date.toString())
            parameter("hd", hd)
        }.body()
}
