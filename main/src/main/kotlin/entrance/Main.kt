package entrance

import entrance.view.javafx.EntranceApplication
import javafx.application.Application
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

fun main(args: Array<String>) {
    val context = SpringApplication.run(Main::class.java, *args)
    EntranceApplication.context = context
    Application.launch(EntranceApplication::class.java, *args)
}

@SpringBootApplication
class Main
