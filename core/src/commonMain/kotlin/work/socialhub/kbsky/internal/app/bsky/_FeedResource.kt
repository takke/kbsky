package work.socialhub.kbsky.internal.app.bsky

import kotlinx.coroutines.runBlocking
import work.socialhub.kbsky.ATProtocolTypes.RepoCreateRecord
import work.socialhub.kbsky.ATProtocolTypes.RepoDeleteRecord
import work.socialhub.kbsky.BlueskyConfig
import work.socialhub.kbsky.BlueskyTypes
import work.socialhub.kbsky.BlueskyTypes.FeedGetActorFeeds
import work.socialhub.kbsky.BlueskyTypes.FeedGetActorLikes
import work.socialhub.kbsky.BlueskyTypes.FeedGetAuthorFeed
import work.socialhub.kbsky.BlueskyTypes.FeedGetFeed
import work.socialhub.kbsky.BlueskyTypes.FeedGetFeedGenerator
import work.socialhub.kbsky.BlueskyTypes.FeedGetFeedGenerators
import work.socialhub.kbsky.BlueskyTypes.FeedGetFeedSearchPosts
import work.socialhub.kbsky.BlueskyTypes.FeedGetLikes
import work.socialhub.kbsky.BlueskyTypes.FeedGetListFeed
import work.socialhub.kbsky.BlueskyTypes.FeedGetPostThread
import work.socialhub.kbsky.BlueskyTypes.FeedGetPosts
import work.socialhub.kbsky.BlueskyTypes.FeedGetQuotes
import work.socialhub.kbsky.BlueskyTypes.FeedGetRepostedBy
import work.socialhub.kbsky.BlueskyTypes.FeedGetTimeline
import work.socialhub.kbsky.BlueskyTypes.FeedLike
import work.socialhub.kbsky.BlueskyTypes.FeedPost
import work.socialhub.kbsky.BlueskyTypes.FeedRepost
import work.socialhub.kbsky.api.app.bsky.FeedResource
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedDeleteLikeRequest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedDeletePostRequest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedDeleteRepostRequest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetActorFeedsRequest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetActorFeedsResponse
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetActorLikesRequest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetActorLikesResponse
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetAuthorFeedRequest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetAuthorFeedResponse
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetFeedGeneratorRequest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetFeedGeneratorResponse
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetFeedGeneratorsRequest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetFeedGeneratorsResponse
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetFeedRequest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetFeedResponse
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetLikesRequest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetLikesResponse
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetListFeedRequest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetListFeedResponse
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetPostThreadRequest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetPostThreadResponse
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetPostsRequest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetPostsResponse
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetQuotesRequest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetQuotesResponse
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetRepostedByRequest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetRepostedByResponse
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetTimelineRequest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedGetTimelineResponse
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedLikeRequest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedLikeResponse
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedPostRequest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedPostResponse
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedPostgateRequest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedPostgateResponse
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedRepostRequest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedRepostResponse
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedSearchPostsRequest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedSearchPostsResponse
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedThreadgateRequest
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedThreadgateResponse
import work.socialhub.kbsky.api.entity.com.atproto.repo.RepoCreateRecordRequest
import work.socialhub.kbsky.api.entity.com.atproto.repo.RepoDeleteRecordRequest
import work.socialhub.kbsky.api.entity.share.Response
import work.socialhub.kbsky.internal.share._InternalUtility.proceed
import work.socialhub.kbsky.internal.share._InternalUtility.proceedUnit
import work.socialhub.kbsky.internal.share._InternalUtility.xrpc
import work.socialhub.kbsky.util.ATUriParser
import work.socialhub.kbsky.util.MediaType
import work.socialhub.khttpclient.HttpRequest

class _FeedResource(
    private val config: BlueskyConfig
) : FeedResource {

    override fun getAuthorFeed(
        request: FeedGetAuthorFeedRequest
    ): Response<FeedGetAuthorFeedResponse> {

        return proceed {
            runBlocking {
                HttpRequest()
                    .url(xrpc(config, FeedGetAuthorFeed))
                    .header("Authorization", request.authorizationHeader)
                    .accept(MediaType.JSON)
                    .queries(request.toMap())
                    .get()
            }
        }
    }

    override fun getLikes(
        request: FeedGetLikesRequest
    ): Response<FeedGetLikesResponse> {

        return proceed {
            runBlocking {
                HttpRequest()
                    .url(xrpc(config, FeedGetLikes))
                    .header("Authorization", request.authorizationHeader)
                    .accept(MediaType.JSON)
                    .queries(request.toMap())
                    .get()
            }
        }
    }

    override fun getPostThread(
        request: FeedGetPostThreadRequest
    ): Response<FeedGetPostThreadResponse> {

        return proceed {
            runBlocking {
                HttpRequest()
                    .url(xrpc(config, FeedGetPostThread))
                    .header("Authorization", request.authorizationHeader)
                    .accept(MediaType.JSON)
                    .queries(request.toMap())
                    .get()
            }
        }
    }

    override fun getPosts(
        request: FeedGetPostsRequest
    ): Response<FeedGetPostsResponse> {

        return proceed {
            runBlocking {
                HttpRequest()
                    .url(xrpc(config, FeedGetPosts))
                    .header("Authorization", request.authorizationHeader)
                    .accept(MediaType.JSON)
                    .also { req ->
                        request.uris?.forEach {
                            req.query("uris", it)
                        }
                    }
                    .get()
            }
        }
    }

    override fun getQuotes(
        request: FeedGetQuotesRequest
    ): Response<FeedGetQuotesResponse> {

        return proceed {
            runBlocking {
                HttpRequest()
                    .url(xrpc(config, FeedGetQuotes))
                    .header("Authorization", request.authorizationHeader)
                    .accept(MediaType.JSON)
                    .queries(request.toMap())
                    .get()
            }
        }
    }

    override fun getRepostedBy(
        request: FeedGetRepostedByRequest
    ): Response<FeedGetRepostedByResponse> {

        return proceed {
            runBlocking {
                HttpRequest()
                    .url(xrpc(config, FeedGetRepostedBy))
                    .header("Authorization", request.authorizationHeader)
                    .accept(MediaType.JSON)
                    .queries(request.toMap())
                    .get()
            }
        }
    }

    override fun getTimeline(
        request: FeedGetTimelineRequest
    ): Response<FeedGetTimelineResponse> {

        return proceed {
            runBlocking {
                HttpRequest()
                    .url(xrpc(config, FeedGetTimeline))
                    .header("Authorization", request.authorizationHeader)
                    .accept(MediaType.JSON)
                    .queries(request.toMap())
                    .get()
            }
        }
    }

    override fun getFeed(
        request: FeedGetFeedRequest
    ): Response<FeedGetFeedResponse> {

        return proceed {
            runBlocking {
                HttpRequest()
                    .url(xrpc(config, FeedGetFeed))
                    .header("Authorization", request.authorizationHeader)
                    .accept(MediaType.JSON)
                    .queries(request.toMap())
                    .get()
            }
        }
    }

    override fun getListFeed(
        request: FeedGetListFeedRequest
    ): Response<FeedGetListFeedResponse> {

        return proceed {
            runBlocking {
                HttpRequest()
                    .url(xrpc(config, FeedGetListFeed))
                    .header("Authorization", request.authorizationHeader)
                    .accept(MediaType.JSON)
                    .queries(request.toMap())
                    .get()
            }
        }
    }

    override fun getActorFeeds(
        request: FeedGetActorFeedsRequest
    ): Response<FeedGetActorFeedsResponse> {

        return proceed {
            runBlocking {
                HttpRequest()
                    .url(xrpc(config, FeedGetActorFeeds))
                    .header("Authorization", request.authorizationHeader)
                    .accept(MediaType.JSON)
                    .queries(request.toMap())
                    .get()
            }
        }
    }

    override fun getActorLikes(
        request: FeedGetActorLikesRequest
    ): Response<FeedGetActorLikesResponse> {

        return proceed {
            runBlocking {
                HttpRequest()
                    .url(xrpc(config, FeedGetActorLikes))
                    .header("Authorization", request.authorizationHeader)
                    .accept(MediaType.JSON)
                    .queries(request.toMap())
                    .get()
            }
        }
    }

    override fun searchPosts(
        request: FeedSearchPostsRequest
    ): Response<FeedSearchPostsResponse> {

        return proceed {
            runBlocking {
                HttpRequest()
                    .url(xrpc(config, FeedGetFeedSearchPosts))
                    .header("Authorization", request.authorizationHeader)
                    .accept(MediaType.JSON)
                    .queries(request.toMap())
                    .get()
            }
        }
    }

    override fun getFeedGenerator(
        request: FeedGetFeedGeneratorRequest
    ): Response<FeedGetFeedGeneratorResponse> {

        return proceed {
            runBlocking {
                HttpRequest()
                    .url(xrpc(config, FeedGetFeedGenerator))
                    .header("Authorization", request.authorizationHeader)
                    .accept(MediaType.JSON)
                    .queries(request.toMap())
                    .get()
            }
        }
    }

    override fun getFeedGenerators(
        request: FeedGetFeedGeneratorsRequest
    ): Response<FeedGetFeedGeneratorsResponse> {

        return proceed {
            runBlocking {
                HttpRequest()
                    .url(xrpc(config, FeedGetFeedGenerators))
                    .header("Authorization", request.authorizationHeader)
                    .accept(MediaType.JSON)
                    .also { req ->
                        request.feeds?.forEach {
                            req.query("feeds", it)
                        }
                    }
                    .get()
            }
        }
    }

    override fun like(
        request: FeedLikeRequest
    ): Response<FeedLikeResponse> {

        return proceed {
            runBlocking {
                val record = RepoCreateRecordRequest(
                    accessJwt = request.accessJwt,
                    repo = request.did!!,
                    collection = FeedLike,
                    record = request.toLike(),
                )

                HttpRequest()
                    .url(xrpc(config, RepoCreateRecord))
                    .header("Authorization", request.authorizationHeader)
                    .json(record.toMappedJson())
                    .accept(MediaType.JSON)
                    .post()
            }
        }
    }

    override fun deleteLike(
        request: FeedDeleteLikeRequest
    ): Response<Unit> {

        return proceedUnit {
            runBlocking {
                val record = RepoDeleteRecordRequest(
                    accessJwt = request.accessJwt,
                    repo = request.did!!,
                    collection = FeedLike,
                    rkey = request.rkey()!!,
                )

                HttpRequest()
                    .url(xrpc(config, RepoDeleteRecord))
                    .header("Authorization", request.authorizationHeader)
                    .json(record.toMappedJson())
                    .accept(MediaType.JSON)
                    .post()
            }
        }
    }

    override fun post(
        request: FeedPostRequest
    ): Response<FeedPostResponse> {

        return proceed {
            runBlocking {
                val record = RepoCreateRecordRequest(
                    accessJwt = request.accessJwt,
                    repo = request.did!!,
                    collection = FeedPost,
                    record = request.toPost(),
                )

                HttpRequest()
                    .url(xrpc(config, RepoCreateRecord))
                    .header("Authorization", request.authorizationHeader)
                    .json(record.toMappedJson())
                    .accept(MediaType.JSON)
                    .post()
            }
        }
    }

    override fun deletePost(
        request: FeedDeletePostRequest
    ): Response<Unit> {

        return proceedUnit {
            runBlocking {
                val record = RepoDeleteRecordRequest(
                    accessJwt = request.accessJwt,
                    repo = request.did!!,
                    collection = FeedPost,
                    rkey = request.rkey()!!,
                )

                HttpRequest()
                    .url(xrpc(config, RepoDeleteRecord))
                    .header("Authorization", request.authorizationHeader)
                    .json(record.toMappedJson())
                    .accept(MediaType.JSON)
                    .post()
            }
        }
    }

    override fun repost(
        request: FeedRepostRequest
    ): Response<FeedRepostResponse> {

        return proceed {
            runBlocking {
                val record = RepoCreateRecordRequest(
                    accessJwt = request.accessJwt,
                    repo = request.did!!,
                    collection = FeedRepost,
                    record = request.toRepost(),
                )

                HttpRequest()
                    .url(xrpc(config, RepoCreateRecord))
                    .header("Authorization", request.authorizationHeader)
                    .json(record.toMappedJson())
                    .accept(MediaType.JSON)
                    .post()
            }
        }
    }

    override fun deleteRepost(
        request: FeedDeleteRepostRequest
    ): Response<Unit> {

        return proceedUnit {
            runBlocking {
                val record = RepoDeleteRecordRequest(
                    accessJwt = request.accessJwt,
                    repo = request.did!!,
                    collection = FeedRepost,
                    rkey = request.rkey()!!,
                )

                HttpRequest()
                    .url(xrpc(config, RepoDeleteRecord))
                    .header("Authorization", request.authorizationHeader)
                    .json(record.toMappedJson())
                    .accept(MediaType.JSON)
                    .post()
            }
        }
    }

    override fun threadgate(
        request: FeedThreadgateRequest
    ): Response<FeedThreadgateResponse> {

        return proceed {
            runBlocking {
                val record = RepoCreateRecordRequest(
                    accessJwt = request.accessJwt,
                    repo = request.did!!,
                    collection = BlueskyTypes.FeedThreadgate,
                    record = request.toThreadgate(),
                ).also {
                    // get rkey from uri of post
                    it.rkey = ATUriParser.getRKey(request.post)
                }

                HttpRequest()
                    .url(xrpc(config, RepoCreateRecord))
                    .header("Authorization", request.authorizationHeader)
                    .json(record.toMappedJson())
                    .accept(MediaType.JSON)
                    .post()
            }
        }
    }

    override fun postgate(
        request: FeedPostgateRequest
    ): Response<FeedPostgateResponse> {

        return proceed {
            runBlocking {
                val record = RepoCreateRecordRequest(
                    accessJwt = request.accessJwt,
                    repo = request.did!!,
                    collection = BlueskyTypes.FeedPostgate,
                    record = request.toPostgate(),
                ).also {
                    // get rkey from uri of post
                    it.rkey = ATUriParser.getRKey(request.post)
                }

                HttpRequest()
                    .url(xrpc(config, RepoCreateRecord))
                    .header("Authorization", request.authorizationHeader)
                    .json(record.toMappedJson())
                    .accept(MediaType.JSON)
                    .post()
            }
        }
    }
}
