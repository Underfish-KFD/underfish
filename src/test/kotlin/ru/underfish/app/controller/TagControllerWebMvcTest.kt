package ru.underfish.app.controller

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.underfish.app.dto.request.TagRequest
import ru.underfish.app.dto.response.TagResponse
import ru.underfish.app.exception.GlobalExceptionHandler
import ru.underfish.app.exception.NotFoundException
import ru.underfish.app.security.JwtAuthenticationFilter
import ru.underfish.app.service.TagService

@WebMvcTest(TagController::class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler::class)
class TagControllerWebMvcTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var tagService: TagService

    @MockitoBean
    private lateinit var jwtAuthenticationFilter: JwtAuthenticationFilter

    @Test
    fun `create tag returns 201 and body`() {
        val request = TagRequest(name = "music")
        val response = TagResponse(tagId = "1", name = "music")

        Mockito.`when`(tagService.createTag(request)).thenReturn(response)

        mockMvc.perform(
            post("/api/v1/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"name":"music"}""")
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.tag_id").value("1"))
            .andExpect(jsonPath("$.name").value("music"))
    }

    @Test
    fun `get tags returns 200 and list`() {
        val response = listOf(
            TagResponse(tagId = "1", name = "music"),
            TagResponse(tagId = "2", name = "sport")
        )

        Mockito.`when`(tagService.getTags()).thenReturn(response)

        mockMvc.perform(get("/api/v1/tags"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].name").value("music"))
            .andExpect(jsonPath("$[1].name").value("sport"))
    }

    @Test
    fun `get tag returns 404 when tag not found`() {
        Mockito.`when`(tagService.getTagById(999L)).thenThrow(NotFoundException("Tag not found"))

        mockMvc.perform(get("/api/v1/tags/999"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.error").value("Not Found"))
            .andExpect(jsonPath("$.message").value("Tag not found"))
    }
}

