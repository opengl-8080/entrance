package entrance.view.javafx.util

import javafx.application.Platform
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.control.TextArea
import javafx.stage.Stage
import java.io.PrintWriter
import java.io.StringWriter

class Dialog {
    
    companion object {
        fun warn(message: String) {
            Platform.runLater {
                val alert = Alert(Alert.AlertType.WARNING, message, ButtonType.OK)
                alert.headerText = null
                alert.title = "警告"

                val stage = alert.dialogPane.scene.window as Stage
                stage.isAlwaysOnTop = true

                alert.showAndWait()
            }
        }
        
        fun error(th: Throwable) {
            th.printStackTrace(System.err)
            Platform.runLater {
                val message = if (th.message == null) "Unknown error" else th.message
                val alert = Alert(Alert.AlertType.ERROR, message, ButtonType.OK)
                alert.headerText = null
                alert.title = "エラー"
                val textArea = TextArea()
                textArea.isEditable = false

                val stringWriter = StringWriter()
                val writer = PrintWriter(stringWriter)
                th.printStackTrace(writer)
                textArea.text = stringWriter.toString()

                alert.dialogPane.expandableContent = textArea

                val stage = alert.dialogPane.scene.window as Stage
                stage.isAlwaysOnTop = true

                alert.showAndWait()
            }
        }
    }
}