package ru.underfish.app.dto.request

import java.math.BigDecimal

data class LocationCreateRequest(
    val address: String?,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
    val city: String?,
    val district: String?,
    val placeName: String?,
    val timezone: String?,
)
