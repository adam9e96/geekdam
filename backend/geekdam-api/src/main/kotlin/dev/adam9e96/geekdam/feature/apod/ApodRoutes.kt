package dev.adam9e96.geekdam.feature.apod

import dev.adam9e96.geekdam.feature.apod.dto.toResponse
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

/**
 * APOD 관련 엔드포인트를 모아두는 라우트.
 */
fun Route.apodRoutes(
    apodService: ApodService,
) {
    get("/apod/today") {
        val apodItem = apodService.getTodayApod()
        call.respond(apodItem.toResponse())
    }
}
