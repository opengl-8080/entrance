package entrance.application.categorization

import entrance.domain.categorization.TaggedImage
import entrance.domain.categorization.TaggedImageRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class CategorizeImageService (
    private val taggedImageRepository: TaggedImageRepository
) {
    
    fun categorize(modifiedImages: List<TaggedImage>) {
        modifiedImages.forEach { taggedImage ->
            taggedImageRepository.save(taggedImage)
        }
    }
    
//    fun categorize(imageUnit: CategorizationImageUnit, selectedTagList: Set<Tag>) {
//        val newSelectedTagSet = imageUnit.commonAssignedTags.filterNewSelectedTagSet(selectedTagList)
//        val releasedTagSet = imageUnit.commonAssignedTags.filterReleasedTagSet(selectedTagList)
//        
//        imageUnit.imageList.forEach { image ->
//            val newAssignedTagSet = image.filterNewAssignedTagSet(newSelectedTagSet)
//            taggedImageRepository.save(image, newAssignedTagSet, releasedTagSet)
//        }
//    }
}