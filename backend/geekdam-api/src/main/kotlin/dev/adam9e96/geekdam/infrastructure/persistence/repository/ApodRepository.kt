package dev.adam9e96.geekdam.infrastructure.persistence.repository

import dev.adam9e96.geekdam.domain.apod.ApodItem
import dev.adam9e96.geekdam.infrastructure.persistence.table.ApodTable
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class ApodRepository {

    fun findByDate(dateValue: String): ApodItem? =
        transaction {
            val row = ApodTable
                .selectAll()
                .where { ApodTable.date eq dateValue }
                .singleOrNull()

            row?.let {
                // row -> ApodItem 매핑
                null
            }
        }

    fun save(apodItem: ApodItem) {
        transaction {
            // insert 또는 update
        }
    }

}