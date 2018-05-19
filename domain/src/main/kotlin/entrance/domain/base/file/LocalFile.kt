package entrance.domain.base.file

import java.io.File
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption


class LocalFile (
    val absolutePath: AbsolutePath,
    /**このファイルの名前*/
    val name: String = absolutePath.name,
    /**このファイルの拡張子を除いたベース名*/
    val baseName: String = name.substringBeforeLast("."),
    /**このファイルの拡張子*/
    val extension: String = name.substringAfterLast("."),
    /**このファイルを指す [java.nio.file.Path] オブジェクト*/
    val javaPath: Path = absolutePath.javaPath,
    /**このファイルのURI表現*/
    val uri: URI = javaPath.toUri(),
    /**このファイルを指す [java.io.File] オブジェクト*/
    val javaFile: File = javaPath.toFile(),
    /**このファイルが存在するディレクトリ*/
    val parentDir: LocalDirectory = LocalDirectory(AbsolutePath(javaPath.parent))
): Comparable<LocalFile> {

    /**
     * このファイルをローカルから削除する.
     */
    fun delete() {
        Files.delete(javaPath)
    }

    /**
     * このファイルを指定したファイルに移動させる.
     * 
     * @param outputFile 移動先のファイル
     */
    fun moveTo(outputFile: LocalFile) {
        Files.move(javaPath, outputFile.javaPath, StandardCopyOption.ATOMIC_MOVE)
    }

    override fun compareTo(other: LocalFile): Int {
        return javaPath.compareTo(other.javaPath)
    }
}
