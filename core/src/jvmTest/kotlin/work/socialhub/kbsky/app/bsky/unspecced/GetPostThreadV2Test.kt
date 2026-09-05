package work.socialhub.kbsky.app.bsky.unspecced

import kotlinx.coroutines.test.runTest
import work.socialhub.kbsky.AbstractTest
import work.socialhub.kbsky.api.entity.app.bsky.unspecced.UnspeccedGetPostThreadV2Request
import kotlin.test.Test

class GetPostThreadV2Test : AbstractTest() {

    @Test
    fun testGetPostThreadV2() = runTest {
        val anchor = "at://did:plc:z72i7hdynmk6r22z27h6tvur/app.bsky.feed.post/3mtwf7gxkwc2r"

        val response = client()
            .unspecced()
            .getPostThreadV2(
                UnspeccedGetPostThreadV2Request(auth()).also {
                    it.anchor = anchor
                }
            )

        println("hasOtherReplies: " + response.data.hasOtherReplies)

        response.data.thread.forEach { item ->
            val post = item.value?.asPost
            println(
                "depth=" + item.depth +
                        ", uri=" + item.uri +
                        ", opThread=" + post?.opThread +
                        ", opThreadPostIndex=" + post?.opThreadPostIndex +
                        ", opThreadPostCount=" + post?.opThreadPostCount
            )
        }
    }
}
