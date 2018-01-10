package entrance.domain.file

import java.nio.file.Path


class LocalFile (
    val path: Path
) {
    
    val extension = path.apply { fileName.toString().substringAfterLast(".") }
}