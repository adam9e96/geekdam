package dev.adam9e96.geekdam.application.plugins

import dev.adam9e96.geekdam.feature.apod.ApodService
import dev.adam9e96.geekdam.feature.apod.apodRoutes
import dev.adam9e96.geekdam.feature.health.healthRoutes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
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
        exception<Throwable> { call, cause ->
            call.respondText(
                text = "500: $cause",
                status = HttpStatusCode.InternalServerError,
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
