package work.socialhub.kbsky.bsky.feed

import work.socialhub.kbsky.AbstractTest
import work.socialhub.kbsky.BlueskyFactory
import work.socialhub.kbsky.api.entity.bsky.feed.FeedDeleteLikeRequest
import work.socialhub.kbsky.api.entity.bsky.feed.FeedLikeRequest
import work.socialhub.kbsky.domain.Service
import work.socialhub.kbsky.model.atproto.repo.RepoStrongRef
import kotlin.test.Test

class LikeTest : AbstractTest() {

    @Test
    fun testLike() {
        val uri = "at://did:plc:bwdof2anluuf5wmfy2upgulw/app.bsky.feed.post/3jsmlerb7m22b"
        val cid = "bafyreied4repnphzjbz5lu5syibtnp72omdecpyde2yykpfjppgp32zopu"

        val ref = RepoStrongRef(uri, cid)
        val response = BlueskyFactory
            .instance(Service.BSKY_SOCIAL.uri)
            .feed()
            .like(
                FeedLikeRequest(accessJwt).also {
                    it.subject = ref
                }
            )

        println(response.data.uri)
    }

    @Test
    fun deleteLike() {
        val uri = "at://did:plc:bwdof2anluuf5wmfy2upgulw/app.bsky.feed.post/3jsmlerb7m22b"
        val cid = "bafyreied4repnphzjbz5lu5syibtnp72omdecpyde2yykpfjppgp32zopu"

        val ref = RepoStrongRef(uri, cid)
        val response = BlueskyFactory
            .instance(Service.BSKY_SOCIAL.uri)
            .feed()
            .like(
                FeedLikeRequest(accessJwt).also {
                    it.subject = ref
                }
            )

        println(response.data.uri)

        BlueskyFactory
            .instance(Service.BSKY_SOCIAL.uri)
            .feed()
            .deleteLike(
                FeedDeleteLikeRequest(accessJwt).also {
                    it.uri = response.data.uri
                }
            )
    }
}