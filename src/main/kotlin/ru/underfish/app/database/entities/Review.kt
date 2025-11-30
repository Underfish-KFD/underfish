package ru.underfish.app.database.entities


import jakarta.persistence.*
import lombok.Data


@Entity
@Data
@Table(name = "reviews")
data class Review(

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false) var user: User,

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "event_id", nullable = false) var event: Event
) : AbstractEntity() {

    @Column(name = "rating")
    var rating: Int? = null //Может не должен быть nullable? (Тогда нужно поменять и в БД)
    //Может стоить добавить проверку? Аналог CHECK (rating BETWEEN 1 AND 5) в БД

    @Column(name = "comment", columnDefinition = "TEXT")
    var comment: String? = null

}
