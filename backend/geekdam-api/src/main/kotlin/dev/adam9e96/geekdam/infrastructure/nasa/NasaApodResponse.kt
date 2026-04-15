package dev.adam9e96.geekdam.infrastructure.nasa

import kotlinx.serialization.Serializable

/**
 * NASA APOD 원본 응답을 그대로 받기 위한 DTO.
 * 필드명은 NASA JSON 구조를 우선해서 유지.
 *
 * request: https://api.nasa.gov/planetary/apod?api_key=ABC
 */
@Serializable
data class NasaApodResponse(
    val copyright: String? = null, // 저작권 정보. 없는 경우도 있어 nullable.
    val date: String, // APOD 기준 날짜. NASA 응답은 문자열이므로 우선 String.
    val explanation: String, // 사진 또는 영상에 대한 NASA의 원본 설명문.
    val hdurl: String? = null, // 고해상도 이미지 URL. 영상이거나 미제공일 수 있다.
    val media_type: String, // NASA가 주는 미디어 타입 문자열. 예: image, video
    val service_version: String, // NASA APOD API 버전 정보.
    val title: String, // 오늘의 우주 사진 또는 영상 제목.
    val url: String, // 기본 표시용 미디어 URL.
)
