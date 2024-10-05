package work.socialhub.kbsky.internal.app.bsky

import kotlinx.coroutines.runBlocking
import work.socialhub.kbsky.BlueskyConfig
import work.socialhub.kbsky.BlueskyTypes.NotificationGetUnreadCount
import work.socialhub.kbsky.BlueskyTypes.NotificationListNotifications
import work.socialhub.kbsky.BlueskyTypes.NotificationUpdateSeen
import work.socialhub.kbsky.api.app.bsky.NotificationResource
import work.socialhub.kbsky.api.entity.app.bsky.notification.NotificationGetUnreadCountRequest
import work.socialhub.kbsky.api.entity.app.bsky.notification.NotificationGetUnreadCountResponse
import work.socialhub.kbsky.api.entity.app.bsky.notification.NotificationListNotificationsRequest
import work.socialhub.kbsky.api.entity.app.bsky.notification.NotificationListNotificationsResponse
import work.socialhub.kbsky.api.entity.app.bsky.notification.NotificationUpdateSeenRequest
import work.socialhub.kbsky.api.entity.share.Response
import work.socialhub.kbsky.internal.share._InternalUtility.proceed
import work.socialhub.kbsky.internal.share._InternalUtility.proceedUnit
import work.socialhub.kbsky.internal.share._InternalUtility.xrpc
import work.socialhub.kbsky.util.MediaType
import work.socialhub.khttpclient.HttpRequest

class _NotificationResource(
    private val config: BlueskyConfig
) : NotificationResource {

    override fun getUnreadCount(
        request: NotificationGetUnreadCountRequest
    ): Response<NotificationGetUnreadCountResponse> {

        return proceed {
            runBlocking {
                HttpRequest()
                    .url(xrpc(config, NotificationGetUnreadCount))
                    .header("Authorization", request.authorizationHeader)
                    .accept(MediaType.JSON)
                    .get()
            }
        }
    }

    override fun listNotifications(
        request: NotificationListNotificationsRequest
    ): Response<NotificationListNotificationsResponse> {

        return proceed {
            runBlocking {
                HttpRequest()
                    .url(xrpc(config, NotificationListNotifications))
                    .header("Authorization", request.authorizationHeader)
                    .accept(MediaType.JSON)
                    .queries(request.toMap())
                    .get()
            }
        }
    }

    override fun updateSeen(
        request: NotificationUpdateSeenRequest
    ): Response<Unit> {

        return proceedUnit {
            runBlocking {
                HttpRequest()
                    .url(xrpc(config, NotificationUpdateSeen))
                    .header("Authorization", request.authorizationHeader)
                    .accept(MediaType.JSON)
                    .json(request.toMappedJson())
                    .post()
            }
        }
    }
}
