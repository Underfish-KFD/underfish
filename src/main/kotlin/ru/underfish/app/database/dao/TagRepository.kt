package ru.underfish.app.database.dao

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.underfish.app.database.entities.Tag

interface TagRepository : AbstractRepository<Tag> {
    fun existsByName(name: String): Boolean

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Tag t WHERE t.name = :name AND t.id != :id")
    fun existsByNameAndIdNot(@Param("name") name: String, @Param("id") id: Long): Boolean
}
