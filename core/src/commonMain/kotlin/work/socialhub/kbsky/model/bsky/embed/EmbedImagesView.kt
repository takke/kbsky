package work.socialhub.kbsky.model.bsky.embed

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import work.socialhub.kbsky.BlueskyTypes

@Serializable
class EmbedImagesView : EmbedViewUnion() {

    companion object {
        const val TYPE = BlueskyTypes.EmbedImages + "#view"
    }

    @SerialName("\$type")
    override var type = TYPE

    var images: List<EmbedImagesViewImage>? = null
}
