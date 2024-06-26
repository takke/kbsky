package work.socialhub.kbsky.model.app.bsky.actor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import work.socialhub.kbsky.BlueskyTypes

@Serializable
class ActorDefsSavedFeedsPref : ActorDefsPreferencesUnion() {

    companion object {
        const val TYPE = BlueskyTypes.ActorDefs + "#savedFeedsPref"
    }

    @SerialName("\$type")
    override var type = TYPE

    /** at-url  */
    lateinit var pinned: List<String>

    /** at-url  */
    lateinit var saved: List<String>
}
