package work.socialhub.kbsky.app.bsky.feed

import kotlinx.coroutines.test.runTest
import work.socialhub.kbsky.AbstractTest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedSearchPostsV2Request
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedSearchPostsV2Response
import work.socialhub.kbsky.internal.share.InternalUtility.fromJson
import work.socialhub.kbsky.internal.share.InternalUtility.toJson
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

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

        val data = assertNotNull(feeds.data)
        // 検索結果は posts か次ページの cursor のいずれかを含むこと
        assertTrue(
            data.posts.isNotEmpty() || data.cursor != null,
            "posts と cursor の両方が空です。"
        )
        data.posts.forEach { post ->
            assertTrue(assertNotNull(post.uri).startsWith("at://"), "uri: ${post.uri}")
            assertTrue(assertNotNull(post.cid).isNotEmpty(), "cid が空です。")
            assertTrue(assertNotNull(post.author?.did).isNotEmpty(), "author.did が空です。")
            assertNotNull(post.record, "record がありません。")
        }

        // レスポンス全体がシリアライズ/デシリアライズで往復できること
        val restored = fromJson<FeedSearchPostsV2Response>(toJson(data))
        assertEquals(data.cursor, restored.cursor)
        assertEquals(data.hitsTotal, restored.hitsTotal)
        assertEquals(data.detectedQueryLanguages, restored.detectedQueryLanguages)
        assertEquals(data.posts.map { it.uri }, restored.posts.map { it.uri })
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

        val data = assertNotNull(feeds.data)
        assertTrue(
            data.posts.isNotEmpty() || data.cursor != null,
            "posts と cursor の両方が空です。"
        )
        data.hitsTotal?.let { assertTrue(it >= 0, "hitsTotal: $it") }
        data.posts.forEach { post ->
            assertNotNull(post.uri)
            assertNotNull(post.cid)
        }

        println("hitsTotal: ${data.hitsTotal}")
        println("detectedQueryLanguages: ${data.detectedQueryLanguages}")
    }

    @Test
    fun testRequestParameterMapping() {
        // テストで使用するリクエストフィールドがクエリパラメータへ正しく変換されること
        val request = FeedSearchPostsV2Request(
            auth = auth(),
            query = "kotlin",
            sort = "top",
            languages = listOf("ja", "en"),
            hasMedia = true,
            allTime = true,
        )

        val params = request.toMap()
        assertEquals("kotlin", params["query"])
        assertEquals("top", params["sort"])
        assertEquals(true, params["hasMedia"])
        assertEquals(true, params["allTime"])
        // 未指定のスカラーパラメータは含まれないこと
        assertTrue("limit" !in params)
        assertTrue("cursor" !in params)
        // 配列パラメータは toMap ではなく toListParams 側で展開されること
        assertTrue("languages" !in params)

        val listParams = request.toListParams()
        assertEquals(listOf("ja", "en"), listParams["languages"])
        // 未指定の配列パラメータは含まれないこと
        assertTrue("authors" !in listParams)
    }

    @Test
    fun testResponseDeserialize() {
        // レスポンスの全フィールドがデシリアライズされること(ネットワーク不要)
        val json = """
            {
              "cursor": "25",
              "hitsTotal": 100,
              "detectedQueryLanguages": ["ja"],
              "posts": [
                {
                  "uri": "at://did:plc:example/app.bsky.feed.post/abc123",
                  "cid": "bafyreiexample",
                  "author": {
                    "did": "did:plc:example",
                    "handle": "example.bsky.social"
                  },
                  "record": {
                    "${'$'}type": "app.bsky.feed.post",
                    "text": "hello",
                    "createdAt": "2024-01-01T00:00:00.000Z"
                  },
                  "replyCount": 1,
                  "repostCount": 2,
                  "likeCount": 3,
                  "indexedAt": "2024-01-01T00:00:00.000Z"
                }
              ]
            }
        """.trimIndent()

        val response = fromJson<FeedSearchPostsV2Response>(json)
        assertEquals("25", response.cursor)
        assertEquals(100, response.hitsTotal)
        assertEquals(listOf("ja"), response.detectedQueryLanguages)
        assertEquals(1, response.posts.size)

        val post = response.posts[0]
        assertEquals("at://did:plc:example/app.bsky.feed.post/abc123", post.uri)
        assertEquals("bafyreiexample", post.cid)
        assertEquals("did:plc:example", post.author?.did)
        assertEquals("example.bsky.social", post.author?.handle)
        assertEquals("hello", post.record?.asFeedPost?.text)
        assertEquals(1, post.replyCount)
        assertEquals(2, post.repostCount)
        assertEquals(3, post.likeCount)
        assertEquals("2024-01-01T00:00:00.000Z", post.indexedAt)
    }
}
