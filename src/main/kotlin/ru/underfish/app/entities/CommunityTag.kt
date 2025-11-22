package ru.underfish.app.entities


import jakarta.persistence.*
import java.io.Serializable
import java.util.*


@Embeddable
data class CommunityTagId(
    @Column(name = "community_id")
    val communityId: UUID? = null,

    @Column(name = "tag_id")
    val tagId: UUID? = null
) : Serializable

@Entity
@Table(name = "CommunityTags")
data class CommunityTag(
    @EmbeddedId
    val id: CommunityTagId = CommunityTagId(),

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("communityId")
    @JoinColumn(name = "community_id", nullable = false)
    var community: Community,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId")
    @JoinColumn(name = "tag_id", nullable = false)
    var tag: Tag
)

