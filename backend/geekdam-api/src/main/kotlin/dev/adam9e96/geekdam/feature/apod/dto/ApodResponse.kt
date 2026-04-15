package dev.adam9e96.geekdam.feature.apod.dto

import dev.adam9e96.geekdam.domain.apod.ApodItem
import kotlinx.serialization.Serializable

/**
 * 프론트엔드에 내려줄 APOD 응답 DTO.
 * 내부 도메인 모델을 화면 친화적인 형태로 변환해 전달.
 */
@Serializable
data class ApodResponse(
    val title: String, // 오늘의 우주 사진 제목.
    val description: String, // 화면에 보여줄 설명문.
    val date: String, // 프론트가 바로 쓰기 쉬운 문자열 날짜.
    val mediaType: String, // 렌더링 분기용 미디어 타입.
    val mediaUrl: String, // 기본 표시용 이미지 또는 영상 URL.
    val hdMediaUrl: String? = null, // 고해상도 원본 URL.
    val copyright: String? = null, // 저작권 표기 정보.
)

/**
 * APOD 도메인 모델을 프론트 응답 DTO로 변환.
 */
fun ApodItem.toResponse(): ApodResponse =
    ApodResponse(
        title = title,
        description = description,
        date = date.toString(),
        mediaType = mediaType.name,
        mediaUrl = mediaUrl,
        hdMediaUrl = hdMediaUrl,
        copyright = copyright,
    )
