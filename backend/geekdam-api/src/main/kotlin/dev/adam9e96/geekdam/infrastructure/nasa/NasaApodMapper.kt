package dev.adam9e96.geekdam.infrastructure.nasa

import dev.adam9e96.geekdam.domain.apod.ApodItem
import dev.adam9e96.geekdam.domain.apod.ApodMediaType
import java.time.LocalDate

/**
 * NASA 원본 DTO를 Geekdam 내부 도메인 모델로 변환하는 매퍼.
 * 외부 필드명과 내부 모델을 분리하기 위한 첫 번째 변환 지점.
 */
fun NasaApodResponse.toDomain(): ApodItem =
    ApodItem(
        title = title,
        description = explanation,
        date = LocalDate.parse(date), // 도메인 모델로 변환시 타입변환
        mediaType = media_type.toApodMediaType(),
        mediaUrl = url,
        hdMediaUrl = hdurl,
        copyright = copyright,
    )

/**
 * NASA의 문자열 media_type을 내부 enum으로 안전하게 변환.
 */
private fun String.toApodMediaType(): ApodMediaType =
    when (lowercase()) {
        "image" -> ApodMediaType.IMAGE
        "video" -> ApodMediaType.VIDEO
        else -> ApodMediaType.UNKNOWN
    }
