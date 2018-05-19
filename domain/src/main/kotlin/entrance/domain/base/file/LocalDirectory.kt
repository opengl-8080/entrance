package entrance.domain.base.file

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import kotlin.streams.toList


class LocalDirectory(
    /**このディレクトリを指す絶対パス*/
    val absolutePath: AbsolutePath,
    /**このディレクトリの名前*/
    val name: String = absolutePath.name,
    /**このディレクトリを指す [java.nio.file.Path] オブジェクト*/
    val javaPath: Path = absolutePath.javaPath
) {

    /**
     * 指定したファイルをこのディレクトリに移動させる.
     * 
     * @param file 移動するファイル
     * @return 移動後のファイル
     */
    fun move(file: LocalFile): LocalFile {
        val distPath = resolve(file.name)
        Files.move(file.javaPath, distPath.javaPath, StandardCopyOption.ATOMIC_MOVE)
        return LocalFile(distPath)
    }

    /**
     * 指定したディレクトリをこのディレクトリの下に移動させる.
     * 
     * @param directory 移動するディレクトリ
     * @return 移動後のディレクトリ
     */
    fun move(directory: LocalDirectory): LocalDirectory {
        val distPath = resolve(directory.name)
        Files.move(directory.javaPath, distPath.javaPath, StandardCopyOption.ATOMIC_MOVE)
        return LocalDirectory(distPath)
    }

    /**
     * このディレクトリから指定した相対パスに存在するディレクトリのパスを解決する.
     *
     * @param relativePath 相対パス
     * @return 解決結果
     */
    fun resolveDirectory(relativePath: RelativePath): LocalDirectory {
        val absolutePath = AbsolutePath(javaPath.resolve(relativePath.javaPath))
        return LocalDirectory(absolutePath)
    }

    /**
     * このディレクトリから指定した相対パスに存在するファイルのパスを解決する.
     *
     * @param relativePath 相対パス
     * @return 解決結果
     */
    fun resolveFile(relativePath: RelativePath): LocalFile {
        val absolutePath = AbsolutePath(javaPath.resolve(relativePath.javaPath))
        return LocalFile(absolutePath)
    }

    /**
     * このディレクトリから指定した相対パスを解決した結果を絶対パスで取得する.
     * 
     * @param relativePath 相対パス
     * @return 解決結果
     */
    private fun resolve(relativePath: String): AbsolutePath {
        return AbsolutePath(javaPath.resolve(relativePath))
    }

    /**
     * このディレクトリが存在しない場合はディレクトリを作成する.
     * 
     * @return このインスタンス
     */
    fun createIfNotExists(): LocalDirectory {
        if (!Files.exists(javaPath)) {
            Files.createDirectories(javaPath)
        }
        return this
    }

    /**
     * このディレクトリ直下に存在する、指定した条件に一致するファイルだけを取得する.
     * 
     * @param filter ファイルの絞り込み条件
     * @return 絞り込まれたファイルのリスト
     */
    fun getFiles(filter: (LocalFile) -> Boolean = { true }): List<LocalFile> {
        return Files.list(javaPath)
                .map { AbsolutePath(it) }
                .map { LocalFile(it) }
                .filter(filter)
                .toList()
    }

    /**
     * このディレクトリ直下に存在するディレクトリを取得する.
     * 
     * @return 直下に存在するディレクトリ
     */
    fun getDirectories(): List<LocalDirectory> {
        return Files.list(javaPath)
                .filter { Files.isDirectory(it) }
                .map { AbsolutePath(it) }
                .map { LocalDirectory(it) }
                .toList()
    }

    /**
     * このディレクトリから指定したファイルまでの相対パスを取得する.
     * 
     * @param distPath 相対パスを求めるパス
     * @return 解決した相対パス
     */
    fun relativize(distPath: AbsolutePath): RelativePath {
        val relativePath = javaPath.relativize(distPath.javaPath)
        return RelativePath(relativePath)
    }

    /**
     * このディレクトリ直下に存在する最初のファイルを取得する.
     * 
     * @return ディレクトリ直下の最初のファイル
     */
    fun getFirstFile(): LocalFile {
        val path = Files.list(javaPath).sorted().findFirst().get()
        return LocalFile(AbsolutePath(path))
    }
}
