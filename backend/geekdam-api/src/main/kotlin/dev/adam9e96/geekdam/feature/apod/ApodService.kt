package dev.adam9e96.geekdam.feature.apod

import dev.adam9e96.geekdam.domain.apod.ApodItem
import dev.adam9e96.geekdam.infrastructure.nasa.NasaApodClient
import dev.adam9e96.geekdam.infrastructure.nasa.toDomain
import java.time.LocalDate

/**
 * APOD 기능의 비즈니스 로직을 담당하는 서비스.
 * 현재는 NASA API 호출과 도메인 변환을 우선 담당.
 */
class ApodService(
    private val nasaApodClient: NasaApodClient,
) {

    /**
     * 오늘 날짜 기준 APOD를 가져옴.
     */
    suspend fun getTodayApod(): ApodItem =
        nasaApodClient.getTodayApod().toDomain()

    /**
     * 특정 날짜 기준 APOD를 가져옴.
     */
    suspend fun getApodByDate(date: LocalDate): ApodItem =
        nasaApodClient.getApodByDate(date).toDomain()
}
