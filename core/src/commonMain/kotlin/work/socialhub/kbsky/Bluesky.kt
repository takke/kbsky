package work.socialhub.kbsky

import work.socialhub.kbsky.api.app.bsky.ActorResource
import work.socialhub.kbsky.api.app.bsky.FeedResource
import work.socialhub.kbsky.api.app.bsky.GraphResource
import work.socialhub.kbsky.api.app.bsky.LabelerResource
import work.socialhub.kbsky.api.app.bsky.NotificationResource
import work.socialhub.kbsky.api.app.bsky.UnspeccedResource
import work.socialhub.kbsky.api.app.bsky.VideoResource
import work.socialhub.kbsky.api.chat.bsky.ConvoResource

interface Bluesky : ATProtocol {
    fun actor(): ActorResource
    fun feed(): FeedResource
    fun graph(): GraphResource
    fun labeler(): LabelerResource
    fun notification(): NotificationResource
    fun unspecced(): UnspeccedResource
    fun video(): VideoResource
    fun convo(): ConvoResource
}
