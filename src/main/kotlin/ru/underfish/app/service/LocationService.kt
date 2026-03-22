package ru.underfish.app.service

import org.springframework.stereotype.Service
import ru.underfish.app.database.dao.LocationRepository
import ru.underfish.app.database.entities.Location
import ru.underfish.app.dto.request.LocationCreateRequest
import ru.underfish.app.dto.response.LocationResponse
import ru.underfish.app.exception.NotFoundException

@Service
class LocationService(
    private val locationRepository: LocationRepository,
) {
    fun createLocation(request: LocationCreateRequest): LocationResponse {
        val location =
            Location(
                latitude = request.latitude,
                longitude = request.longitude,
            ).apply {
                address = request.address
                city = request.city
                district = request.district
                placeName = request.placeName
                timezone = request.timezone ?: "UTC+3"
            }
        val savedLocation = locationRepository.save(location)
        return LocationResponse(savedLocation)
    }

    fun getLocations(): List<LocationResponse> = locationRepository.findAll().map { LocationResponse(it) }

    fun getLocationById(locationId: Long): LocationResponse {
        val location =
            locationRepository.findById(locationId).orElseThrow {
                NotFoundException("Location not found")
            }
        return LocationResponse(location)
    }

    fun updateLocation(
        locationId: Long,
        request: LocationCreateRequest,
    ): LocationResponse {
        val existingLocation =
            locationRepository.findById(locationId).orElseThrow {
                NotFoundException("Location not found")
            }

        val updatedLocation =
            Location(
                latitude = request.latitude,
                longitude = request.longitude,
            ).apply {
                id = existingLocation.id
                createdAt = existingLocation.createdAt
                address = request.address
                city = request.city
                district = request.district
                placeName = request.placeName
                timezone = request.timezone ?: existingLocation.timezone
            }

        val savedLocation = locationRepository.save(updatedLocation)
        return LocationResponse(savedLocation)
    }

    fun deleteLocation(locationId: Long) {
        if (!locationRepository.existsById(locationId)) {
            throw NotFoundException("Location not found")
        }
        locationRepository.deleteById(locationId)
    }
}
