package work.socialhub.kbsky.api.entity.app.bsky.feed


import kotlinx.serialization.Serializable
import work.socialhub.kbsky.model.app.bsky.feed.FeedDefsPostView
import kotlin.js.JsExport

@Serializable
@JsExport
data class FeedSearchPostsV2Response(
    /** Cursor for the next page of results. */
    var cursor: String? = null,

    /** Estimated total number of matching hits. May be rounded or truncated. */
    var hitsTotal: Int? = null,

    /** Hydrated views of matching posts. */
    var posts: List<FeedDefsPostView> = emptyList(),

    /** Query languages detected for CJK, Thai, or Arabic text ("ja", "zh", "ko", "th", "ar"). Empty or omitted for other scripts. */
    var detectedQueryLanguages: List<String>? = null,
)
