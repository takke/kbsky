package work.socialhub.kbsky.util.json

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import work.socialhub.kbsky.model.app.bsky.embed.EmbedExternalView
import work.socialhub.kbsky.model.app.bsky.embed.EmbedImagesView
import work.socialhub.kbsky.model.app.bsky.embed.EmbedRecordView
import work.socialhub.kbsky.model.app.bsky.embed.EmbedRecordWithMediaView
import work.socialhub.kbsky.model.app.bsky.embed.EmbedVideoView
import work.socialhub.kbsky.model.app.bsky.embed.EmbedViewUnion
import work.socialhub.kbsky.util.json.JsonElementUtil.type

object EmbedViewPolymorphicSerializer :
    JsonContentPolymorphicSerializer<EmbedViewUnion>(
        EmbedViewUnion::class
    ) {

    override fun selectDeserializer(
        element: JsonElement
    ): DeserializationStrategy<EmbedViewUnion> {
        return when (val type = element.type()) {
            EmbedExternalView.TYPE -> EmbedExternalView.serializer()
            EmbedImagesView.TYPE -> EmbedImagesView.serializer()
            EmbedVideoView.TYPE -> EmbedVideoView.serializer()
            EmbedRecordView.TYPE -> EmbedRecordView.serializer()
            EmbedRecordWithMediaView.TYPE -> EmbedRecordWithMediaView.serializer()
            else -> {
                println("[Warning] Unknown Item type: $type (EmbedViewUnion)")
                Unknown.serializer()
            }
        }
    }

    @Serializable
    class Unknown : EmbedViewUnion() {
        override var type: String = "unknown"
    }
}