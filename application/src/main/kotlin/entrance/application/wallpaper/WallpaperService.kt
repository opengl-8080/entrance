package entrance.application.wallpaper

import entrance.domain.wallpaper.WallpaperDirectory
import entrance.domain.wallpaper.WallpaperRepository
import org.springframework.stereotype.Service

@Service
class WallpaperService (
    private val wallpaperDirectory: WallpaperDirectory,
    private val wallpaperRepository: WallpaperRepository
) {
    
    fun doService() {
        val wallpapers = wallpaperRepository.findWallpapers()
        wallpaperDirectory.replace(wallpapers)
    }
}