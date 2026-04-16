package dev.adam9e96.geekdam.infrastructure.persistence.database

import dev.adam9e96.geekdam.infrastructure.persistence.table.ApodTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init(dbPath: String){
        // DB 접속 설정 등록
        Database.connect(
            url = "jdbc:sqlite:$dbPath",
            driver = "org.sqlite.JDBC",
        )
        // 실제 SQL 실행 범위
        transaction {
            SchemaUtils.create(ApodTable) // 테이블이 없으면 생성
        }
        println("데이터베이스 초기화 완료")

    }
}