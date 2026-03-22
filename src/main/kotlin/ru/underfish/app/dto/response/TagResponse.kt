package ru.underfish.app.dto.response

import ru.underfish.app.database.entities.Tag

data class TagResponse(
    val tagId: String,
    val name: String,
) {
    constructor(tag: Tag) : this(
        tagId = tag.id.toString(),
        name = tag.name,
    )
}
