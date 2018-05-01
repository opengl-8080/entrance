package entrance

import entrance.application.wallpaper.WallpaperService
import entrance.domain.util.config.EntranceHome
import entrance.view.javafx.EntranceApplication
import javafx.application.Application
import org.h2.tools.Shell
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

fun main(args: Array<String>) {
    val context = SpringApplication.run(Main::class.java, *args)

    val entranceHome = context.getBean(EntranceHome::class.java)
    
    if (args.contains("--database")) {
        Shell.main("-url", entranceHome.jdbcUrl(), "-user", "SA", "-password", "")
        return
    }

    val wallpaperService = context.getBean(WallpaperService::class.java)
    wallpaperService.doService()

    EntranceApplication.context = context
    Application.launch(EntranceApplication::class.java, *args)
}

@SpringBootApplication
class Main
