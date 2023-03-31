package tl.azkom.treasury.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tl.azkom.treasury.dao.ProviderDAO
import tl.azkom.treasury.entities.Provider

@Service
class ProviderService(@Autowired val providerDAO: ProviderDAO) {
    fun fetchAllProviders() = providerDAO.readAll()
    fun createProvider(provider : Provider) = providerDAO.create(provider)
    fun deleteProvider(providerId : Long) = providerDAO.delete(providerId)
}
