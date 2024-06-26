package work.socialhub.kbsky.model.app.bsky.embed

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import work.socialhub.kbsky.BlueskyTypes

/**
 * A set of images embedded in some other form of content
 */
@Serializable
class EmbedImages : EmbedUnion() {

    companion object {
        const val TYPE = BlueskyTypes.EmbedImages
    }

    @SerialName("\$type")
    override var type = TYPE

    var images: List<EmbedImagesImage>? = null
}
