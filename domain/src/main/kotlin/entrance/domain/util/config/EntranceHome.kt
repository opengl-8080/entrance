package entrance.domain.util.config

import entrance.domain.base.file.LocalDirectory
import entrance.domain.base.file.RelativePath
import entrance.domain.util.file.DeprecatedDirectory
import entrance.domain.util.file.DeprecatedLocalFile
import entrance.domain.util.file.DeprecatedRelativePath

interface EntranceHome {

    /**
     * ホームディレクトリから指定した相対パスの場所を指すディレクトリを解決する.
     * 
     * @param relativePath 相対パス
     * @return 解決したディレクトリ
     */
    fun resolveDirectory(relativePath: RelativePath): LocalDirectory
    
    /**
     * ホームからの相対ディレクトリを取得する.
     * 
     * ディレクトリが存在しない場合、自動的に作成されます.
     */
    fun initDir(relativePath: DeprecatedRelativePath): DeprecatedDirectory

    /**
     * ホームからの相対パスでファイルを解決する.
     */
    fun resolveFile(relativePath: DeprecatedRelativePath): DeprecatedLocalFile

    /**
     * データベースに接続するための JDBC URL を取得する
     */
    fun jdbcUrl(): String
}
