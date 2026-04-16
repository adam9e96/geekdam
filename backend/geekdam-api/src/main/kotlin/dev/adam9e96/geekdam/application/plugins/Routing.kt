package dev.adam9e96.geekdam.application.plugins

import dev.adam9e96.geekdam.feature.apod.ApodService
import dev.adam9e96.geekdam.feature.apod.apodRoutes
import dev.adam9e96.geekdam.feature.health.healthRoutes
import dev.adam9e96.geekdam.infrastructure.nasa.NasaApiException
import dev.adam9e96.geekdam.shared.dto.ApiErrorResponse
import dev.adam9e96.geekdam.shared.dto.nowIsoString
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.path
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * 애플리케이션 전체 라우팅 진입점.
 * 공통 루트와 feature 라우트를 함께 등록.
 */
fun Application.configureRouting(
    apodService: ApodService,
) {
    install(StatusPages) {
        exception<NasaApiException> { call, cause ->
            val status = cause.toHttpStatusCode()

            call.respond(
                status = status,
                message = ApiErrorResponse(
                    code = cause.toApiErrorCode(),
                    message = cause.toClientMessage(),
                    status = status.value,
                    path = call.request.path(),
                    timestamp = nowIsoString(),
                ),
            )
        }

        exception<Throwable> { call, cause ->
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = ApiErrorResponse(
                    code = "INTERNAL_SERVER_ERROR",
                    message = "서버 내부 오류가 발생했습니다.",
                    status = HttpStatusCode.InternalServerError.value,
                    path = call.request.path(),
                    timestamp = nowIsoString(),
                ),
            )
        }
    }

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        route("/api/v1") {
            healthRoutes()
            apodRoutes(apodService)
        }
    }
}

/**
 * NASA upstream 상태코드를 현재 API의 HTTP 상태코드로 변환합니다.
 * 5xx는 외부 장애로 보고 503으로 통일하고, 4xx는 가능한 한 의미를 유지합니다.
 */
private fun NasaApiException.toHttpStatusCode(): HttpStatusCode =
    when (statusCode) {
        400 -> HttpStatusCode.BadRequest
        401 -> HttpStatusCode.Unauthorized
        403 -> HttpStatusCode.Forbidden
        404 -> HttpStatusCode.NotFound
        429 -> HttpStatusCode.TooManyRequests
        in 500..599 -> HttpStatusCode.ServiceUnavailable
        else -> HttpStatusCode.BadGateway
    }

/**
 * 프론트나 로그에서 분기하기 쉬운 NASA 관련 에러 코드를 만듭니다.
 */
private fun NasaApiException.toApiErrorCode(): String =
    when (statusCode) {
        400 -> "NASA_APOD_BAD_REQUEST"
        401 -> "NASA_APOD_UNAUTHORIZED"
        403 -> "NASA_APOD_FORBIDDEN"
        404 -> "NASA_APOD_NOT_FOUND"
        429 -> "NASA_APOD_RATE_LIMITED"
        else -> "NASA_APOD_UNAVAILABLE"
    }

/**
 * 사용자에게 보여줄 NASA 관련 오류 메시지를 구성합니다.
 */
private fun NasaApiException.toClientMessage(): String =
    when (statusCode) {
        400 -> "NASA APOD 요청 형식이 올바르지 않습니다."
        401 -> "NASA API 키가 올바르지 않거나 인증에 실패했습니다."
        403 -> "NASA APOD API 접근이 거부되었습니다."
        404 -> "요청한 APOD 데이터를 찾을 수 없습니다."
        429 -> "NASA APOD API 요청 한도를 초과했습니다."
        else -> "NASA APOD API가 일시적으로 응답하지 않습니다."
    }
