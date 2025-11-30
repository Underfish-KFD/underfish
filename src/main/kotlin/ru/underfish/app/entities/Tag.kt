package ru.underfish.app.entities


import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "Tags")
data class Tag(
    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    val tagId: UUID? = null,

    @Column(name = "name", nullable = false, unique = true, length = 100)
    var name: String
)
