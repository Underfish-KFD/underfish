package ru.underfish.app.entities


import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(name = "Reviews")
data class Review(
    @Id
    @GeneratedValue
    @Column(name = "review_id")
    val reviewId: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    var event: Event,

    @Column(name = "rating")
    var rating: Int? = null, //Может не должен быть nullable? (Тогда нужно поменять и в БД)
                             //Может стоить добавить проверку? Аналог CHECK (rating BETWEEN 1 AND 5) в БД

    @Column(name = "comment")
    var comment: String? = null,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)
