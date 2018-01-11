package entrance.domain.config

import entrance.domain.file.Directory
import entrance.domain.file.RelativePath

abstract class EntranceHomeBase: EntranceHome {
    
    abstract val home: Directory
    
    override fun initDir(relativePath: RelativePath): Directory {
        val resolveDir = home.resolveDir(relativePath)
        resolveDir.createIfNotExists()
        return resolveDir
    }

    override fun toString(): String {
        return "EntranceHomeBase(home=$home)"
    }
}