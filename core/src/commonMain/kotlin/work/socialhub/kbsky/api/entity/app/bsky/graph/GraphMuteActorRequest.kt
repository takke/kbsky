package work.socialhub.kbsky.api.entity.app.bsky.graph


import work.socialhub.kbsky.api.entity.share.AuthRequest
import work.socialhub.kbsky.api.entity.share.MapRequest
import work.socialhub.kbsky.auth.AuthProvider
import kotlin.js.JsExport

@JsExport
data class GraphMuteActorRequest(
    override val auth: AuthProvider,
    var actor: String? = null,
    /**
     * Restrict the mute to the account's reposts.
     * When any 'only' scope is set, just the scoped content is muted;
     * when none are set, the account is fully muted.
     * Repeat calls replace the stored scope rather than adding to it.
     */
    var onlyReposts: Boolean? = null,
    /**
     * Restrict the mute to the account's quote posts. See [onlyReposts].
     */
    var onlyQuoteposts: Boolean? = null,
) : AuthRequest(auth), MapRequest {

    override fun toMap(): Map<String, Any> {
        return mutableMapOf<String, Any>().also {
            it.addParam("actor", actor)
            it.addParam("onlyReposts", onlyReposts)
            it.addParam("onlyQuoteposts", onlyQuoteposts)
        }
    }
}
