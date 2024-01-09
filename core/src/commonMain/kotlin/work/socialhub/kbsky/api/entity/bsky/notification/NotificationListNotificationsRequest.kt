package work.socialhub.kbsky.api.entity.bsky.notification

import work.socialhub.kbsky.api.entity.share.AuthRequest
import work.socialhub.kbsky.api.entity.share.MapRequest

class NotificationListNotificationsRequest(
    accessJwt: String
) : AuthRequest(accessJwt), MapRequest {

    var limit: Int? = null
    var cursor: String? = null

    override fun toMap(): Map<String, Any> {
        return mutableMapOf<String, Any>().also {
            it.addParam("limit", limit)
            it.addParam("cursor", cursor)
        }
    }
}
