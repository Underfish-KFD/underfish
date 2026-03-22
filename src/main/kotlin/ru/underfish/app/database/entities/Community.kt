package ru.underfish.app.database.entities

import jakarta.persistence.*
import lombok.Data

@Entity
@Data
@Table(name = "communities")
data class Community(

    @Column(name = "name", length = 255, nullable = false) var name: String = "community"

) : AbstractEntity() {

    @Column(columnDefinition = "TEXT")
    var description: String? = null

    @Column(name = "is_private", nullable = false)
    var isPrivate: Boolean = false

    @Column(name = "cover_url", columnDefinition = "TEXT")
    var coverUrl: String? = null

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "CommunityTags",
        joinColumns = [JoinColumn(name = "community_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    var tags: MutableSet<Tag> = mutableSetOf()
}
