package dev.adam9e96.geekdam.feature.health

import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.Route

/**
 * 서버 기본 상태를 확인하기 위한 health 라우트.
 */
fun Route.healthRoutes() {
    get("/health") {
        call.respondText("ok")
    }
}
