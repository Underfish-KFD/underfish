package ru.underfish.app.entities

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "locations")
data class Location(
    @Id
    @GeneratedValue
    @Column(name = "location_id")
    val locationId: UUID? = null,

    @Column(columnDefinition = "TEXT")
    val address: String? = null,

    @Column(precision = 10, scale = 8)
    val latitude: BigDecimal? = null,

    @Column(precision = 11, scale = 8)
    val longitude: BigDecimal? = null,

    @Column(length = 100)
    val city: String? = null,

    @Column(length = 100)
    val district: String? = null,

    @Column(name = "place_name")
    val placeName: String? = null,

    @Column(length = 50)
    val timezone: String? = null
)