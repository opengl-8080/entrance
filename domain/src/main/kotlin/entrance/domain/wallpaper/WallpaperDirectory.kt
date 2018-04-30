package entrance.domain.wallpaper

import entrance.domain.util.config.EntranceHome
import entrance.domain.util.file.RelativePath
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Component
class WallpaperDirectory (
    entranceHome: EntranceHome
) {
    private val logger = LoggerFactory.getLogger(WallpaperDirectory::class.java)
    private val dir = entranceHome.initDir(RelativePath("wallpaper"))
    
    fun replace(wallpapers: List<Wallpaper>) {
        clean()
        set(wallpapers)
    }
    
    private fun clean() {
        Files.list(dir.path).forEach { wallpaper -> 
            Files.delete(wallpaper)
        }
    }

    private fun set(wallpapers: List<Wallpaper>) {
        wallpapers
            .map { Paths.get(it.uri) }
            .forEach { wallpaper ->
                logger.debug("wallpapper=${wallpaper.fileName}")
                val toFile = dir.resolveFile(RelativePath(wallpaper.fileName))
                Files.copy(wallpaper, toFile.path, StandardCopyOption.REPLACE_EXISTING)
            }
    }
}