package work.socialhub.kbsky.api.entity.app.bsky.feed

import kotlinx.serialization.Serializable
import work.socialhub.kbsky.model.app.bsky.feed.FeedDefsPostView

@Serializable
class FeedGetPostsResponse {
    lateinit var posts: List<FeedDefsPostView>
}
