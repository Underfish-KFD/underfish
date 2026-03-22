package ru.underfish.app.service

import org.springframework.stereotype.Service
import ru.underfish.app.database.dao.TagRepository
import ru.underfish.app.database.entities.Tag
import ru.underfish.app.dto.request.TagRequest
import ru.underfish.app.dto.response.TagResponse
import ru.underfish.app.exception.BadRequestException
import ru.underfish.app.exception.NotFoundException

@Service
class TagService(
    private val tagRepository: TagRepository
) {
    fun createTag(request: TagRequest): TagResponse {
        if (tagRepository.existsByName(request.name)) {
            throw BadRequestException("Tag with name '${request.name}' already exists")
        }
        val tag = Tag(name = request.name)
        val savedTag = tagRepository.save(tag)
        return TagResponse(savedTag)
    }

    fun getTags(): List<TagResponse> {
        return tagRepository.findAll().map { TagResponse(it) }
    }

    fun getTagById(tagId: Long): TagResponse {
        val tag = tagRepository.findById(tagId).orElseThrow {
            NotFoundException("Tag not found")
        }
        return TagResponse(tag)
    }

    fun updateTag(tagId: Long, request: TagRequest): TagResponse {
        val tag = tagRepository.findById(tagId).orElseThrow {
            NotFoundException("Tag not found")
        }
        if (tagRepository.existsByNameAndIdNot(request.name, tagId)) {
            throw BadRequestException("Tag with name '${request.name}' already exists")
        }
        tag.name = request.name
        val savedTag = tagRepository.save(tag)
        return TagResponse(savedTag)
    }

    fun deleteTag(tagId: Long) {
        if (!tagRepository.existsById(tagId)) {
            throw NotFoundException("Tag not found")
        }
        tagRepository.deleteById(tagId)
    }
}
