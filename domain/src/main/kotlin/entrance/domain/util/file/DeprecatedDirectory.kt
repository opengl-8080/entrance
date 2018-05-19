package entrance.domain.util.file

import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.toList

/**
 * ディレクトリを操作するためのクラス.
 * 
 * @param path ディレクトリのパス
 */
data class DeprecatedDirectory (
    val path: Path
) {

    /**
     * このディレクトリから見て指定した相対パスを指す新しいディレクトリオブジェクトを生成する.
     * 
     * @param relativePath ディレクトリへの相対パス
     * @return 相対パスで指定した位置を指す新しいディレクトリオブジェクト
     */
    fun resolveDir(relativePath: DeprecatedRelativePath): DeprecatedDirectory {
        val resolvedPath = path.resolve(relativePath.path)
        return DeprecatedDirectory(resolvedPath)
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
    fun resolveFile(relativePath: DeprecatedRelativePath): DeprecatedLocalFile {
        val resolvedPath = path.resolve(relativePath.path)
        return DeprecatedLocalFile(resolvedPath)
    }

    /**
     * このディレクトリから指定したファイルへ辿る相対パスを生成する.
     * 
     * @param file 相対パスを求めたいファイルオブジェクト
     * @return このディレクトリから指定したファイルへの相対パス
     */
    fun relativize(file: DeprecatedLocalFile): DeprecatedRelativePath {
        val relativePath = path.relativize(file.path)
        return DeprecatedRelativePath(relativePath)
    }

    /**
     * このディレクトリから指定したディレクトリへ辿る相対パスを生成する.
     *
     * @param dir 相対パスを求めたいファイルオブジェクト
     * @return このディレクトリから指定したファイルへの相対パス
     */
    fun relativize(dir: DeprecatedDirectory): DeprecatedRelativePath {
        val relativePath = path.relativize(dir.path)
        return DeprecatedRelativePath(relativePath)
    }

    /**
     * このディレクトリの直下に存在する全てのファイルを取得する.
     * 
     * @return ディレクトリ直下に存在するファイルのリスト
     */
    fun files(): List<DeprecatedLocalFile> = Files.list(path).map(::DeprecatedLocalFile).toList()
}