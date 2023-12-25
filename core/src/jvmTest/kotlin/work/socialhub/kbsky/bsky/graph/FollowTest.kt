package work.socialhub.kbsky.bsky.graph

import work.socialhub.kbsky.AbstractTest
import work.socialhub.kbsky.BlueskyFactory
import work.socialhub.kbsky.api.entity.bsky.graph.GraphDeleteFollowRequest
import work.socialhub.kbsky.api.entity.bsky.graph.GraphFollowRequest
import work.socialhub.kbsky.domain.Service.BSKY_SOCIAL
import kotlin.test.Test

class FollowTest : AbstractTest() {

    @Test
    fun testFollow() {
        val did = "did:plc:oc6vwdlmk2kqyida5i74d3p5"
        var uri: String

        run {

            // Follow
            val response = BlueskyFactory
                .instance(BSKY_SOCIAL.uri)
                .graph().follow(
                    GraphFollowRequest(accessJwt).also {
                        it.subject = did
                    }
                )

            uri = checkNotNull(response.data.uri)
            println(uri)
        }

        run { // DeleteFollow
            BlueskyFactory
                .instance(BSKY_SOCIAL.uri)
                .graph()
                .deleteFollow(
                    GraphDeleteFollowRequest(accessJwt).also {
                        it.uri = uri
                    }
                )
        }
    }
}