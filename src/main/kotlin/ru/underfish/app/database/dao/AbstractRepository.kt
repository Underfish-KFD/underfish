package ru.underfish.app.database.dao

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import ru.underfish.app.database.entities.AbstractEntity

@NoRepositoryBean
interface AbstractRepository<T : AbstractEntity> : JpaRepository<T, Long> {

}
