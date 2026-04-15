package dev.adam9e96.geekdam.domain.dashboard

import java.time.LocalDate

/**
 * Geekdam 대시보드에서 공통적으로 보여줄 수 있는 우주 카드 모델.
 * APOD뿐 아니라 이후 다른 NASA 데이터도 같은 카드 형태로 확장할 수 있도록 설계
 */
data class SpaceCard(
    val id: String, // 카드의 고유 식별자. 날짜나 소스 정보를 조합해 만들 수 있다.
    val source: SpaceSource, // 이 카드가 어떤 데이터 소스에서 왔는지 나타냄.
    val title: String, // 사용자에게 보여줄 카드 제목.
    val summary: String, // 메인 화면에 노출할 요약 설명.
    val date: LocalDate, // 이 카드가 기준으로 삼는 날짜.
    val mediaType: SpaceMediaType, // 이미지인지 영상인지 등 화면 렌더링 방식에 필요한 타입.
    val mediaUrl: String, // 기본 표시용 미디어 URL.
    val hdMediaUrl: String?, // 고해상도 원본 URL.
    val thumbnailUrl: String?, // 썸네일 전용 URL. 없으면 mediaUrl 을 대체로 사용할 수 있다.
    val copyright: String?, // 저작권 표기 정보.
    val moodTag: String?, // 분위기 추천이나 플레이리스트 연결에 활용할 태그.
)

/**
 * 카드의 원본 데이터 출처를 구분하기 위한 타입.
 */
enum class SpaceSource {
    APOD,
    UNKNOWN,
}

/**
 * 대시보드에서 카드 렌더링 방식을 결정하기 위한 미디어 타입.
 */
enum class SpaceMediaType {
    IMAGE,
    VIDEO,
    UNKNOWN,
}
