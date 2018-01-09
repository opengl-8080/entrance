package entrance.domain.entry

interface AddedEntryImageEventSubscriber {
    
    fun subscribe(addedEntryImage: EntryImage)
}