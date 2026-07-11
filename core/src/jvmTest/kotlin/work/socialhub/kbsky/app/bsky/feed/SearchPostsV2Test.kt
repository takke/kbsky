package work.socialhub.kbsky.app.bsky.feed

import kotlinx.coroutines.test.runTest
import work.socialhub.kbsky.AbstractTest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedSearchPostsV2Request
import kotlin.test.Test

class SearchPostsV2Test : AbstractTest() {

    @Test
    fun testSearchPostsV2() = runTest {
        val feeds = client()
            .feed()
            .searchPostsV2(
                FeedSearchPostsV2Request(
                    auth = auth(),
                    query = "SocialHub",
                    sort = "recent",
                )
            )

        feeds.data.posts
            .forEach { print(it) }
    }

    @Test
    fun testSearchPostsV2WithFilters() = runTest {
        val feeds = client()
            .feed()
            .searchPostsV2(
                FeedSearchPostsV2Request(
                    auth = auth(),
                    query = "kotlin",
                    sort = "top",
                    languages = listOf("ja", "en"),
                    hasMedia = true,
                    allTime = true,
                )
            )

        println("hitsTotal: ${feeds.data.hitsTotal}")
        println("detectedQueryLanguages: ${feeds.data.detectedQueryLanguages}")
        feeds.data.posts
            .forEach { print(it) }
    }
}
