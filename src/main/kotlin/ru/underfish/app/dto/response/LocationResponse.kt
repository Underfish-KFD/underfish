package ru.underfish.app.dto.response

import ru.underfish.app.database.entities.Location
import java.math.BigDecimal

data class LocationResponse(
    val locationId: String,
    val address: String?,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
    val city: String?,
    val district: String?,
    val placeName: String?,
    val timezone: String?,
) {
    constructor(location: Location) : this(
        locationId = location.id.toString(),
        address = location.address,
        latitude = location.latitude,
        longitude = location.longitude,
        city = location.city,
        district = location.district,
        placeName = location.placeName,
        timezone = location.timezone,
    )
}
