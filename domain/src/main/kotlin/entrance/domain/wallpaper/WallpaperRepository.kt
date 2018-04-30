package entrance.domain.wallpaper

interface WallpaperRepository {
    
    fun findWallpapers(): List<Wallpaper>
}