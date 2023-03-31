package tl.azkom.treasury.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import tl.azkom.treasury.controllers.dtos.ProviderDTO
import tl.azkom.treasury.entities.Provider
import tl.azkom.treasury.services.ProviderService

@Controller
class ProviderController(@Autowired val providerService: ProviderService) {
    @GetMapping("/providers")
    fun getProviders(
        model: Model
    ): String? {
        val allProviders = this.providerService.fetchAllProviders()
        model.addAttribute("provider", ProviderDTO())
        model.addAttribute("providers", allProviders)
        return "providers"
    }

    @PostMapping("/providers/create")
    fun post(
        @ModelAttribute provider: ProviderDTO,
        model: Model
    ): String? {
        providerService.createProvider(Provider(provider.id, provider.name, provider.address))
        return "redirect:/providers"
    }

    @GetMapping("/providers/delete/{id}")
    fun delete(@PathVariable id: Long,
        model: Model
    ): String? {
        providerService.deleteProvider(id)
        return "redirect:/providers"
    }
}