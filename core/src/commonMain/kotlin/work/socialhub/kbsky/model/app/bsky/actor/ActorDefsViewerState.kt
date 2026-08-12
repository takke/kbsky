package work.socialhub.kbsky.model.app.bsky.actor


import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
@JsExport
data class ActorDefsViewerState(
    var muted: Boolean? = null,
    /**
     * Whether the account's reposts are muted.
     * Scoped mutes are exclusive with [muted]: this can be true while [muted] is false.
     * If [muted] is true, this will be false.
     */
    var mutedOnlyReposts: Boolean? = null,
    /**
     * Whether the account's quote posts are muted. See [mutedOnlyReposts].
     */
    var mutedOnlyQuoteposts: Boolean? = null,
    var blockedBy: Boolean? = null,
    /** at-uri  */
    var blocking: String? = null,
    /** at-uri  */
    var following: String? = null,
    /** at-uri  */
    var followedBy: String? = null,
    val knownFollowers: ActorDefsKnownFollowers? = null,
)
