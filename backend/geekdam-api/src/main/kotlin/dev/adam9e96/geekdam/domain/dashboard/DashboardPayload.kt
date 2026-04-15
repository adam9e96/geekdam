package dev.adam9e96.geekdam.domain.dashboard

/**
 * Geekdam 메인 대시보드를 구성하는 내부 도메인 모델.
 * 여러 데이터를 한 번에 묶어 프론트에 전달하기 전의 조합 결과.
 */
data class DashboardPayload(
    val featuredCard: SpaceCard, // 오늘의 메인 우주 카드.
    val playlist: DashboardPlaylist?, // 오늘의 추천 플레이리스트 정보.
    val reflection: DashboardReflection?, // 화면에 함께 보여줄 짧은 문장 또는 프롬프트.
    val recentCards: List<SpaceCard>, // 최근에 함께 보여줄 보조 카드 목록.
)

/**
 * 대시보드에서 노출할 플레이리스트 요약 정보.
 */
data class DashboardPlaylist(
    val title: String, // 플레이리스트 제목.
    val description: String, // 분위기 설명 또는 추천 이유.
    val playlistUrl: String, // 실제 플레이리스트로 이동할 URL.
    val thumbnailUrl: String?, // 카드에 노출할 썸네일 이미지 URL.
)

/**
 * 대시보드에서 보여줄 짧은 문구나 성찰 프롬프트입니다.
 */
data class DashboardReflection(
    val title: String, // 짧은 섹션 제목.
    val body: String, // 실제 문구 또는 프롬프트 본문.
)
