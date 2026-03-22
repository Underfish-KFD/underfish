package ru.underfish.app.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.underfish.app.dto.request.LocationCreateRequest
import ru.underfish.app.dto.response.LocationResponse
import ru.underfish.app.service.LocationService

@RestController
@RequestMapping("/api/v1/locations")
class LocationController(
    private val locationService: LocationService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createLocation(@RequestBody request: LocationCreateRequest): LocationResponse {
        return locationService.createLocation(request)
    }

    @GetMapping
    fun getLocations(): List<LocationResponse> {
        return locationService.getLocations()
    }

    @GetMapping("/{location_id}")
    fun getLocation(@PathVariable("location_id") locationId: Long): LocationResponse {
        return locationService.getLocationById(locationId)
    }

    @PutMapping("/{location_id}")
    fun updateLocation(
        @PathVariable("location_id") locationId: Long,
        @RequestBody request: LocationCreateRequest
    ): LocationResponse {
        return locationService.updateLocation(locationId, request)
    }

    @DeleteMapping("/{location_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteLocation(@PathVariable("location_id") locationId: Long) {
        locationService.deleteLocation(locationId)
    }
}
