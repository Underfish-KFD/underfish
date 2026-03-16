package ru.underfish.app.dto.request

data class CommunityCreateRequest (
    val name: String,
    val description: String,
    val isPrivate: Boolean,
    val coverUrl: String,
)