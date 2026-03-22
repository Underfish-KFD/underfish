package ru.underfish.app.database.entities

import jakarta.persistence.*
import java.math.BigDecimal
import lombok.Data

@Entity
@Data
@Table(name = "locations")
data class Location(

    @Column(
        name = "latitude", precision = 10, scale = 8, nullable = false, columnDefinition = "DECIMAL(11,8) DEFAULT 0.0"
    ) var latitude: BigDecimal = BigDecimal("0.0"),

    @Column(
        name = "longitude", precision = 11, scale = 8, nullable = false, columnDefinition = "DECIMAL(11,8) DEFAULT 0.0"
    ) var longitude: BigDecimal = BigDecimal("0.0")
) : AbstractEntity() {

    @Column(name = "address", columnDefinition = "TEXT")
    var address: String? = null

    @Column(name = "city", length = 100)
    var city: String? = null

    @Column(name = "district", length = 100)
    var district: String? = null

    @Column(name = "place_name", length = 250)
    var placeName: String? = null

    @Column(name = "timezone", length = 20)
    var timezone: String = "UTC+3"
}