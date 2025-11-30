package ru.underfish.app.database.entities

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@MappedSuperclass
abstract class AbstractEntity {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    @Column(name = "created_at")
    @CreationTimestamp
    var createdAt: LocalDateTime = LocalDateTime.now()
}