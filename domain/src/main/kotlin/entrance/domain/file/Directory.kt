package entrance.domain.file

import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.toList

data class Directory (
    val path: Path
) {
    
    fun resolveDir(relativePath: RelativePath): Directory {
        val resolvedPath = path.resolve(relativePath.path)
        return Directory(resolvedPath)
    }
    
    fun createIfNotExists() {
        if (Files.notExists(path)) {
            Files.createDirectories(path)
        }
    }
    
    fun resolveFile(relativePath: RelativePath): LocalFile {
        val resolvedPath = path.resolve(relativePath.path)
        return LocalFile(resolvedPath)
    }
    
    fun relativize(file: LocalFile): RelativePath {
        val relativePath = path.relativize(file.path)
        return RelativePath(relativePath)
    }
    
    fun files(): List<LocalFile> = Files.list(path).map(::LocalFile).toList()
}