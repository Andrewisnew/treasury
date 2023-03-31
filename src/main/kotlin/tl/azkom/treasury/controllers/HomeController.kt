package tl.azkom.treasury.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import tl.azkom.treasury.controllers.dtos.TagDTO
import tl.azkom.treasury.entities.Tag
import tl.azkom.treasury.services.TagsService


@Controller
class HomeController(@Autowired val tagsService: TagsService) {
    @GetMapping("/home")
    fun getHome(
    ): String? {
        return "home"
    }
}