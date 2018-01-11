package entrance.domain.config

import entrance.domain.file.Directory
import entrance.domain.file.RelativePath

interface EntranceHome {

    /**
     * ホームからの相対ディレクトリを取得する.
     * 
     * ディレクトリが存在しない場合、自動的に作成されます.
     */
    fun initDir(relativePath: RelativePath): Directory
}
