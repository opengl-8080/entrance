package entrance.domain.entry.entrance

import entrance.domain.base.file.LocalDirectory
import entrance.domain.base.item.book.BookName


class EntryBook (
    val localDirectory: LocalDirectory,
    val name: BookName = BookName(localDirectory.name)
)
