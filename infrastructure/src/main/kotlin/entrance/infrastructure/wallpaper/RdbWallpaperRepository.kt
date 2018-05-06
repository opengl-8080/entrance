package entrance.infrastructure.wallpaper

import entrance.domain.RankCondition
import entrance.domain.entry.LibraryDirectory
import entrance.domain.util.file.RelativePath
import entrance.domain.wallpaper.Wallpaper
import entrance.domain.wallpaper.WallpaperRepository
import entrance.infrastructure.database.image.ImageTableDao
import org.springframework.stereotype.Component

@Component
class RdbWallpaperRepository (
        private val imageTableDao: ImageTableDao,
        private val libraryDirectory: LibraryDirectory
): WallpaperRepository {
    
    override fun findWallpapers(): List<Wallpaper> {
        val rankCondition = RankCondition.DEFAULT
        
        return imageTableDao.findWallpapers(rankCondition.min.value, rankCondition.max.value)
                .map { it.path }
                .map { RelativePath(it) }
                .map { libraryDirectory.resolveFile(it) }
                .map { Wallpaper(it) }
    }
}