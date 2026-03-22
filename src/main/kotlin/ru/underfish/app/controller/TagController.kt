package ru.underfish.app.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.underfish.app.dto.request.TagRequest
import ru.underfish.app.dto.response.TagResponse
import ru.underfish.app.service.TagService

@RestController
@RequestMapping("/api/v1/tags")
class TagController(
    private val tagService: TagService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTag(@RequestBody request: TagRequest): TagResponse {
        return tagService.createTag(request)
    }

    @GetMapping
    fun getTags(): List<TagResponse> {
        return tagService.getTags()
    }

    @GetMapping("/{tag_id}")
    fun getTag(@PathVariable("tag_id") tagId: Long): TagResponse {
        return tagService.getTagById(tagId)
    }

    @PutMapping("/{tag_id}")
    fun updateTag(@PathVariable("tag_id") tagId: Long, @RequestBody request: TagRequest): TagResponse {
        return tagService.updateTag(tagId, request)
    }

    @DeleteMapping("/{tag_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTag(@PathVariable("tag_id") tagId: Long) {
        tagService.deleteTag(tagId)
    }
}
