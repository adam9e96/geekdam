package dev.adam9e96.geekdam.infrastructure.persistence.table

import org.jetbrains.exposed.sql.Table

object ApodTable : Table("apod") {
    val date = varchar("date", 10)
    val title = varchar("title", 255)
    val description = text("description")
    val mediaType = varchar("media_type", 20)
    val mediaUrl = text("media_url")
    val hdMediaUrl = text("hd_media_url").nullable()
    val copyright = text("copyright").nullable()
    val fetchedAt = varchar("fetched_at", 40)

    override val primaryKey = PrimaryKey(date)
}