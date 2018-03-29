package entrance.domain.config

import entrance.domain.file.Directory
import entrance.domain.file.LocalFile
import entrance.domain.file.RelativePath

abstract class EntranceHomeBase: EntranceHome {
    
    abstract val home: Directory
    
    override fun initDir(relativePath: RelativePath): Directory {
        val resolveDir = home.resolveDir(relativePath)
        resolveDir.createIfNotExists()
        return resolveDir
    }

    override fun resolveFile(relativePath: RelativePath): LocalFile {
        return home.resolveFile(relativePath)
    }

    override fun toString(): String {
        return "EntranceHomeBase(home=$home)"
    }
}