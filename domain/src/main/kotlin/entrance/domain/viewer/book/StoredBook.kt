package entrance.domain.viewer.book

import entrance.domain.ItemId
import entrance.domain.Rank
import entrance.domain.base.file.LocalDirectory
import entrance.domain.base.file.RelativePath
import entrance.domain.book.BaseBook
import entrance.domain.book.BookName
import entrance.domain.sort.BaseSortableImage
import entrance.domain.sort.SortableItem
import entrance.domain.tag.Tag
import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import java.time.Instant


class StoredBook (
    val itemId: ItemId,
    val name: BookName,
    val relativePath: RelativePath,
    directory: LocalDirectory,
    tags: List<Tag>,
    rank: Rank
): BaseBook(directory), SortableItem by BaseSortableImage(tags, rank) {
    
    fun delete() {
        Files.walkFileTree(directory.javaPath, object: SimpleFileVisitor<Path>() {
            override fun visitFile(file: Path?, attrs: BasicFileAttributes?): FileVisitResult {
                Files.delete(file)
                return super.visitFile(file, attrs)
            }
            
            override fun postVisitDirectory(dir: Path, exc: IOException?): FileVisitResult {
                /*
                 * Files.delete(dir) で単純にディレクトリを削除しようとすると、
                 * なぜかディレクトリは削除されず、アクセスできない状態になる.
                 * 
                 * JVM が終了すると、ディレクトリは完全に削除されるが、
                 * それまではそのディレクトリに一切アクセスできない.
                 * 
                 * 結果、同日に同名のブックを登録しようとすると、
                 * ディレクトリにアクセスできないため登録ができなくなる.
                 * 
                 * そこで、一旦重複しない別名でフォルダを移動しておき、
                 * 移動後の一時フォルダに対して Files.delete() を実行することで、
                 * 上記問題をひとまず回避している.
                 */
                val now = Instant.now()
                val name = "${dir.fileName}_${now.toEpochMilli()}_${now.nano}"
                val removedDir = dir.parent.resolve(name)
                Files.move(dir, removedDir)
                Files.delete(removedDir)
                return super.postVisitDirectory(dir, exc)
            }
        })
    }
}