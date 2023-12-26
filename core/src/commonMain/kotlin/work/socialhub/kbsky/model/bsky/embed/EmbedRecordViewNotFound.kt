package work.socialhub.kbsky.model.bsky.embed

import kotlinx.serialization.Serializable
import work.socialhub.kbsky.BlueskyTypes

@Serializable
class EmbedRecordViewNotFound : EmbedRecordViewUnion() {

    companion object {
        const val TYPE = BlueskyTypes.EmbedRecord + "#viewNotFound"
    }

    override var type = TYPE

    var uri: String? = null
}
