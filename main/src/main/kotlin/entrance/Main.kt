package entrance

import entrance.domain.util.config.EntranceHome
import entrance.infrastructure.entry.WatchingEntryDirectoryTask
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

    val task = context.getBean(WatchingEntryDirectoryTask::class.java)
    task.begin()

    EntranceApplication.context = context
    Application.launch(EntranceApplication::class.java, *args)
}

@SpringBootApplication
class Main
