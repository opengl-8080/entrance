package entrance.view.javafx.management

import entrance.domain.management.ManagedImage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope("prototype")
class ManagementImageTagController {
    lateinit var imageList: List<ManagedImage>
    
    
}