package dev.adam9e96.geekdam.feature.dashboard.dto

/**
 * 프론트엔드에 내려줄 Geekdam 대시보드 응답 DTO.
 * 도메인 모델을 그대로 노출하지 않고, 화면에 필요한 형태로 정리해서 전달.
 */
data class DashboardResponse(
    val featuredCard: SpaceCardResponse, // 오늘의 대표 카드 응답.
    val playlist: PlaylistResponse?, // 오늘의 추천 플레이리스트 응답.
    val reflection: ReflectionResponse?, // 화면에 함께 노출할 짧은 문구 응답.
    val recentCards: List<SpaceCardResponse>, // 최근 카드 목록 응답.
)

/**
 * 프론트엔드 카드 렌더링에 맞춘 우주 카드 응답 DTO.
 */
data class SpaceCardResponse(
    val id: String, // 카드 식별자.
    val source: String, // 카드 출처를 문자열로 내려줌. 예: APOD
    val title: String, // 카드 제목.
    val summary: String, // 요약 설명.
    val date: String, // 프론트가 바로 쓰기 쉬운 문자열 날짜.
    val mediaType: String, // 렌더링 분기를 위한 미디어 타입 문자열.
    val mediaUrl: String, // 기본 표시용 미디어 URL.
    val hdMediaUrl: String?, // 고해상도 원본 URL.
    val thumbnailUrl: String?, // 썸네일 URL.
    val copyright: String?, // 저작권 표기 정보.
    val moodTag: String?, // 분위기 태그.
)

/**
 * 프론트엔드에 전달할 플레이리스트 응답 DTO.
 */
data class PlaylistResponse(
    val title: String, // 플레이리스트 제목.
    val description: String, // 추천 이유 또는 분위기 설명.
    val playlistUrl: String, // 플레이리스트 링크.
    val thumbnailUrl: String?, // 플레이리스트 썸네일 URL.
)

/**
 * 프론트엔드에 전달할 짧은 문구 응답 DTO.
 */
data class ReflectionResponse(
    val title: String, // 문구 섹션 제목.
    val body: String, // 문구 본문.
)
