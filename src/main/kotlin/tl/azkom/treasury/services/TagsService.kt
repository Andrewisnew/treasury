package tl.azkom.treasury.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tl.azkom.treasury.dao.TagsDAO
import tl.azkom.treasury.entities.Tag

@Service
class TagsService(@Autowired val tagsDAO: TagsDAO) {
    fun fetchAllTags() = tagsDAO.readAll()
    fun createTag(tag : Tag) = tagsDAO.create(tag)
    fun deleteTag(tagId : Long) = tagsDAO.delete(tagId)
}
