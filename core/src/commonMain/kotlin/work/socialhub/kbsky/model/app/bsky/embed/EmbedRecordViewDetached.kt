package work.socialhub.kbsky.model.app.bsky.embed

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import work.socialhub.kbsky.BlueskyTypes

@Serializable
class EmbedRecordViewDetached : EmbedRecordViewUnion() {

    companion object {
        const val TYPE = BlueskyTypes.EmbedRecord + "#viewDetached"
    }

    @SerialName("\$type")
    override var type = TYPE

    var uri: String? = null

    var detached: Boolean = true
}
