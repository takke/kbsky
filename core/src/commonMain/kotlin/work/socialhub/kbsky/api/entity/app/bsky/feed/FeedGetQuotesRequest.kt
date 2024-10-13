package work.socialhub.kbsky.api.entity.app.bsky.feed

import work.socialhub.kbsky.api.entity.share.AuthRequest
import work.socialhub.kbsky.api.entity.share.MapRequest
import work.socialhub.kbsky.auth.AuthProvider

class FeedGetQuotesRequest(
    auth: AuthProvider
) : AuthRequest(auth), MapRequest {

    lateinit var uri: String

    var cid: String? = null
    var limit: Int? = null
    var cursor: String? = null

    override fun toMap(): Map<String, Any> {
        return mutableMapOf<String, Any>().also {
            it.addParam("uri", uri)
            it.addParam("cid", cid)
            it.addParam("limit", limit)
            it.addParam("cursor", cursor)
        }
    }
}