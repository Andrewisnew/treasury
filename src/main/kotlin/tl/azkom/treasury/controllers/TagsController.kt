package tl.azkom.treasury.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import tl.azkom.treasury.controllers.dtos.TagDTO
import tl.azkom.treasury.entities.Tag
import tl.azkom.treasury.services.TagsService


@Controller
class TagsController(@Autowired val tagsService: TagsService) {
    @GetMapping("/tags")
    fun getTags(
        model: Model
    ): String? {
        val allTags = this.tagsService.fetchAllTags()
        model.addAttribute("tag", TagDTO())
        model.addAttribute("tags", allTags)
        return "tags"
    }

    @PostMapping("/tags/create")
    fun post(
        @ModelAttribute tag: TagDTO,
        model: Model
    ): String? {
        tagsService.createTag(Tag(tag.id, tag.name))
        return "redirect:/tags"
    }

    @GetMapping("/tags/delete/{id}")
    fun delete(@PathVariable id: Long,
        model: Model
    ): String? {
        tagsService.deleteTag(id)
        return "redirect:/tags"
    }
}