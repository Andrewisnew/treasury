package tl.azkom.treasury.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import tl.azkom.treasury.controllers.dtos.ExpenditureDTO
import tl.azkom.treasury.entities.Expenditure
import tl.azkom.treasury.services.ExpendituresService
import tl.azkom.treasury.services.TagsService
import java.util.stream.Collectors


@Controller
class ExpendituresController(
    @Autowired val expendituresService: ExpendituresService,
    @Autowired val tagsService: TagsService
) {
    @GetMapping("/expenditures")
    fun getExpenditures(
        model: Model
    ): String? {
        val allExpenditures = this.expendituresService.fetchAllExpenditures()
        val allTags = this.tagsService.fetchAllTags()
        model.addAttribute("expenditure", ExpenditureDTO())
        model.addAttribute("expenditures", allExpenditures)
        model.addAttribute("tags", allTags)
        return "expenditures"
    }

    @PostMapping("/expenditures/create")
    fun post(
        @ModelAttribute expenditure: ExpenditureDTO,
        model: Model
    ): String? {
        val allTags = this.tagsService.fetchAllTags().stream()
            .filter { t -> expenditure.tags.contains(t.id) }
            .collect(Collectors.toList())
        expendituresService.createExpenditure(
            Expenditure(
                expenditure.id, expenditure.name, expenditure.quantity,
                expenditure.unit!!, allTags
            )
        )
        return "redirect:/expenditures"
    }

    @GetMapping("/expenditures/delete/{id}")
    fun delete(
        @PathVariable id: Long,
        model: Model
    ): String? {
        expendituresService.deleteExpenditure(id)
        return "redirect:/expenditures"
    }
}