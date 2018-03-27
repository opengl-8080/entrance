package entrance.domain.file

import java.nio.file.Path

/**
 * ファイルを操作するためのクラス.
 * 
 * @param path ファイルのパス
 */
class LocalFile (
    val path: Path
) {

    /**
     * このファイルの拡張子.
     */
    val extension: String = path.fileName.toString().substringAfterLast(".")

    /**
     * このファイルが画像ファイル化どうかを確認する.
     * 
     * @return 画像ファイルの場合は true
     */
    fun isImage() = listOf("jpg", "jpeg", "png", "gif", "bmp").any { it.equals(extension, ignoreCase = true) }
}