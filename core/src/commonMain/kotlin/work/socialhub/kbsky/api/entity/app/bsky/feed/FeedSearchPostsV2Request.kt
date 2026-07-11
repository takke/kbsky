package work.socialhub.kbsky.api.entity.app.bsky.feed


import work.socialhub.kbsky.api.entity.share.AuthRequest
import work.socialhub.kbsky.api.entity.share.MapRequest
import work.socialhub.kbsky.auth.AuthProvider
import kotlin.js.JsExport

@JsExport
data class FeedSearchPostsV2Request(
    override val auth: AuthProvider,
    /** Search query string. A query or at least one filter is required. */
    var query: String? = null,
    /** Ranking order for results. 'recent' sorts by recency; 'top' uses search ranking. */
    var sort: String? = null,
    /** Include posts by any of these authors (at-identifier). Handles are resolved to DIDs before searching. */
    var authors: List<String>? = null,
    /** Include posts that mention any of these accounts (at-identifier). */
    var mentions: List<String>? = null,
    /** Include posts that link to any of these domains. */
    var domains: List<String>? = null,
    /** Include posts that link to any of these URLs. */
    var urls: List<String>? = null,
    /** Include posts that embed any of these AT URIs. */
    var embeddedAtUris: List<String>? = null,
    /** Include posts tagged with any of these hashtags. Do not include the hash (#) prefix. */
    var hashtags: List<String>? = null,
    /** Exclude posts by any of these authors (at-identifier). */
    var excludeAuthors: List<String>? = null,
    /** Exclude posts that mention any of these accounts (at-identifier). */
    var excludeMentions: List<String>? = null,
    /** Exclude posts that link to any of these domains. */
    var excludeDomains: List<String>? = null,
    /** Exclude posts that link to any of these URLs. */
    var excludeUrls: List<String>? = null,
    /** Exclude posts that embed any of these AT URIs. */
    var excludeEmbeddedAtUris: List<String>? = null,
    /** Exclude posts tagged with any of these hashtags. Do not include the hash (#) prefix. */
    var excludeHashtags: List<String>? = null,
    /** Include posts indexed at or after this timestamp. Can be a datetime, or just an ISO date (YYYY-MM-DD). */
    var since: String? = null,
    /** Include posts indexed before this timestamp. Defaults to the current time. */
    var until: String? = null,
    /** Search the full index instead of the recent-post window. */
    var allTime: Boolean? = null,
    /** Include posts whose language matches any of these language codes. */
    var languages: List<String>? = null,
    /** Exclude posts whose language matches any of these language codes. */
    var excludeLanguages: List<String>? = null,
    /** Include only posts with media. */
    var hasMedia: Boolean? = null,
    /** Include only posts with video. */
    var hasVideo: Boolean? = null,
    /** Include only direct replies to this parent post URI. */
    var replyParentUri: String? = null,
    /** Include only posts in the thread rooted at this post URI. */
    var threadRootUri: String? = null,
    /** Exclude replies from results. Mutually exclusive with repliesOnly. */
    var excludeReplies: Boolean? = null,
    /** Include only replies. Mutually exclusive with excludeReplies. */
    var repliesOnly: Boolean? = null,
    /** Include only posts from accounts followed by the viewer. */
    var following: Boolean? = null,
    /** Language analyzer hint for the query text ("ja", "zh", "ko", "th", "ar"). If unset, the server auto-detects when possible. */
    var queryLanguage: String? = null,
    // [1-100] default: 25
    var limit: Int? = null,
    /** Optional pagination cursor. */
    var cursor: String? = null,
) : AuthRequest(auth), MapRequest {

    /** Scalar parameters only. Array parameters are provided by [toListParams]. */
    override fun toMap(): Map<String, Any> {
        return mutableMapOf<String, Any>().also {
            it.addParam("query", query)
            it.addParam("sort", sort)
            it.addParam("since", since)
            it.addParam("until", until)
            it.addParam("allTime", allTime)
            it.addParam("hasMedia", hasMedia)
            it.addParam("hasVideo", hasVideo)
            it.addParam("replyParentUri", replyParentUri)
            it.addParam("threadRootUri", threadRootUri)
            it.addParam("excludeReplies", excludeReplies)
            it.addParam("repliesOnly", repliesOnly)
            it.addParam("following", following)
            it.addParam("queryLanguage", queryLanguage)
            it.addParam("limit", limit)
            it.addParam("cursor", cursor)
        }
    }

    /**
     * Array parameters. These must be expanded as `key=v1&key=v2` on the query string,
     * so they are kept out of [toMap] (khttpclient's `queries(Map)` calls `toString()`
     * on List values, producing `"[v1, v2]"`).
     */
    fun toListParams(): Map<String, List<String>> {
        return mutableMapOf<String, List<String>>().also {
            it.addListParam("authors", authors)
            it.addListParam("mentions", mentions)
            it.addListParam("domains", domains)
            it.addListParam("urls", urls)
            it.addListParam("embeddedAtUris", embeddedAtUris)
            it.addListParam("hashtags", hashtags)
            it.addListParam("excludeAuthors", excludeAuthors)
            it.addListParam("excludeMentions", excludeMentions)
            it.addListParam("excludeDomains", excludeDomains)
            it.addListParam("excludeUrls", excludeUrls)
            it.addListParam("excludeEmbeddedAtUris", excludeEmbeddedAtUris)
            it.addListParam("excludeHashtags", excludeHashtags)
            it.addListParam("languages", languages)
            it.addListParam("excludeLanguages", excludeLanguages)
        }
    }

    private fun MutableMap<String, List<String>>.addListParam(
        key: String,
        values: List<String>?
    ) {
        if (values.isNullOrEmpty()) {
            return
        }
        this[key] = values
    }
}
