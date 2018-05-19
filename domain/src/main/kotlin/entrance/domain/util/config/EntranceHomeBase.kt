package entrance.domain.util.config

import entrance.domain.base.file.LocalDirectory
import entrance.domain.base.file.RelativePath
import entrance.domain.util.file.DeprecatedDirectory
import entrance.domain.util.file.DeprecatedLocalFile
import entrance.domain.util.file.DeprecatedRelativePath

abstract class EntranceHomeBase: EntranceHome {
    
    abstract val homeDirectory: LocalDirectory
    abstract val home: DeprecatedDirectory

    override fun resolveDirectory(relativePath: RelativePath): LocalDirectory {
        return homeDirectory.resolveDirectory(relativePath)
    }
    
    override fun initDir(relativePath: DeprecatedRelativePath): DeprecatedDirectory {
        val resolveDir = home.resolveDir(relativePath)
        resolveDir.createIfNotExists()
        return resolveDir
    }

    override fun resolveFile(relativePath: DeprecatedRelativePath): DeprecatedLocalFile {
        return home.resolveFile(relativePath)
    }

    override fun toString(): String {
        return "EntranceHomeBase(home=$home)"
    }
}