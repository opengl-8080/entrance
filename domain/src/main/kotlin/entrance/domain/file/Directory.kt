package entrance.domain.file

import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.toList

/**
 * ディレクトリを操作するためのクラス.
 * 
 * @param path ディレクトリのパス
 */
data class Directory (
    val path: Path
) {

    /**
     * このディレクトリから見て指定した相対パスを指す新しいディレクトリオブジェクトを生成する.
     * 
     * @param relativePath ディレクトリへの相対パス
     * @return 相対パスで指定した位置を指す新しいディレクトリオブジェクト
     */
    fun resolveDir(relativePath: RelativePath): Directory {
        val resolvedPath = path.resolve(relativePath.path)
        return Directory(resolvedPath)
    }

    /**
     * このディレクトリが存在しない場合は、そこまでの全てのディレクトリを全て生成する.
     */
    fun createIfNotExists() {
        if (Files.notExists(path)) {
            Files.createDirectories(path)
        }
    }

    /**
     * このディレクトリから見て指定した相対パスを指す新しいファイルオブジェクトを生成する.
     * 
     * @param relativePath ファイルへの相対パス
     * @return 相対パスで指定した位置を指す新しいファイルオブジェクト
     */
    fun resolveFile(relativePath: RelativePath): LocalFile {
        val resolvedPath = path.resolve(relativePath.path)
        return LocalFile(resolvedPath)
    }

    /**
     * このディレクトリから指定したファイルへ辿る相対パスを生成する.
     * 
     * @param file 相対パスを求めたいファイルオブジェクト
     * @return このディレクトリから指定したファイルへの相対パス
     */
    fun relativize(file: LocalFile): RelativePath {
        val relativePath = path.relativize(file.path)
        return RelativePath(relativePath)
    }

    /**
     * このディレクトリの直下に存在する全てのファイルを取得する.
     * 
     * @return ディレクトリ直下に存在するファイルのリスト
     */
    fun files(): List<LocalFile> = Files.list(path).map(::LocalFile).toList()
}