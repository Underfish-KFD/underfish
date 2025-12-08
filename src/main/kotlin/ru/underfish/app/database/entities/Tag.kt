package ru.underfish.app.database.entities


import jakarta.persistence.*
import lombok.Data

@Entity
@Data
@Table(name = "tags")
data class Tag(
    @Column(name = "name", nullable = false, unique = true, length = 100) var name: String
) : AbstractEntity()
