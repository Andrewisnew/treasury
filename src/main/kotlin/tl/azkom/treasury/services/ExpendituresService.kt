package tl.azkom.treasury.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tl.azkom.treasury.dao.ExpendituresDAO
import tl.azkom.treasury.entities.Expenditure

@Service
class ExpendituresService(@Autowired val expendituresDAO: ExpendituresDAO) {
    fun fetchAllExpenditures() = expendituresDAO.readAll()
    fun createExpenditure(expenditure : Expenditure) = expendituresDAO.create(expenditure)
    fun deleteExpenditure(expenditureId : Long) = expendituresDAO.delete(expenditureId)
}
