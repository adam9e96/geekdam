package dev.adam9e96.geekdam.domain.dashboard

import dev.adam9e96.geekdam.domain.apod.ApodItem
import dev.adam9e96.geekdam.domain.apod.ApodMediaType

/**
 * APOD 도메인 모델을 대시보드 공통 카드 모델로 변환.
 * 이후 다른 NASA 데이터도 같은 방식으로 SpaceCard 로 수렴시키기 위한 시작점.
 */
fun ApodItem.toSpaceCard(): SpaceCard =
    SpaceCard(
        id = "apod-$date", // APOD는 날짜가 사실상 고유하므로 source + date 조합으로 식별자를 만듭니다.
        source = SpaceSource.APOD, // 현재 데이터 소스는 APOD 입니다.
        title = title, // APOD 제목을 카드 제목으로 그대로 사용합니다.
        summary = description, // 현재는 원본 설명문 전체를 사용하고, 이후 요약 로직을 붙일 수 있습니다.
        date = date, // APOD 기준 날짜를 그대로 사용합니다.
        mediaType = mediaType.toSpaceMediaType(), // APOD 미디어 타입을 대시보드 공통 미디어 타입으로 변환합니다.
        mediaUrl = mediaUrl, // 기본 표시용 URL 입니다.
        hdMediaUrl = hdMediaUrl, // 고해상도 원본 URL 입니다.
        thumbnailUrl = null, // 아직 별도 썸네일 생성 로직이 없어 null 로 둡니다.
        copyright = copyright, // 저작권 정보를 그대로 전달합니다.
        moodTag = null, // 아직 분위기 태그 계산 로직이 없어 null 로 둡니다.
    )

/**
 * APOD 전용 미디어 타입을 대시보드 공통 미디어 타입으로 변환합니다.
 */
private fun ApodMediaType.toSpaceMediaType(): SpaceMediaType =
    when (this) {
        ApodMediaType.IMAGE -> SpaceMediaType.IMAGE
        ApodMediaType.VIDEO -> SpaceMediaType.VIDEO
        ApodMediaType.UNKNOWN -> SpaceMediaType.UNKNOWN
    }
