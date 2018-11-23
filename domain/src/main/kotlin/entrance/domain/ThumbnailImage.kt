package entrance.domain

import java.net.URI

/**
 * サムネイル画像.
 */
interface ThumbnailImage {
    /**
     * サムネイル画像の URI.
     */
    val thumbnailUri: URI

    /**
     * サムネイル画像が選択されたときにステータスバーに表示するテキスト.
     */
    val statusText: String
}