package entrance.domain.entry

/**
 * 画像ファイルのエントリポイントを表すインターフェース.
 */
interface EntryPoint {

    /**
     * エントリポイントを監視し、画像ファイルが追加されたイベントを配信するサブスクライバを登録する.
     */
    fun watch(subscriber: AddedEntryImageEventSubscriber)
}