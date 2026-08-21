package work.socialhub.kbsky.app.bsky.graph

import kotlinx.coroutines.test.runTest
import work.socialhub.kbsky.AbstractTest
import work.socialhub.kbsky.api.entity.app.bsky.actor.ActorGetProfileRequest
import work.socialhub.kbsky.api.entity.app.bsky.graph.GraphMuteActorRequest
import work.socialhub.kbsky.api.entity.app.bsky.graph.GraphUnmuteActorRequest
import work.socialhub.kbsky.internal.share.InternalUtility.fromJson
import work.socialhub.kbsky.model.app.bsky.actor.ActorDefsViewerState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class MuteTest : AbstractTest() {

    @Test
    fun testMute() = runTest {
        client()
            .graph()
            .muteActor(
                GraphMuteActorRequest(auth()).also {
                    it.actor = "bsky.app"
                }
            )
    }

    @Test
    fun testMuteByDID() = runTest {
        client()
            .graph()
            .muteActor(
                GraphMuteActorRequest(auth()).also {
                    it.actor = "did:plc:oc6vwdlmk2kqyida5i74d3p5"
                }
            )
    }

    @Test
    fun testMuteOnlyReposts() = runTest {
        val actor = "bsky.app"

        try {
            client()
                .graph()
                .muteActor(
                    GraphMuteActorRequest(auth()).also {
                        it.actor = actor
                        it.onlyReposts = true
                    }
                )

            val viewer = getViewerState(actor)
            println("viewer: $viewer")

            // スコープ付きミュートは muted と排他であること
            assertEquals(true, viewer.mutedOnlyReposts)
            assertFalse(viewer.muted == true, "muted: ${viewer.muted}")
            assertFalse(viewer.mutedOnlyQuoteposts == true)

        } finally {
            unmute(actor)
        }
    }

    @Test
    fun testMuteOnlyQuoteposts() = runTest {
        val actor = "bsky.app"

        try {
            client()
                .graph()
                .muteActor(
                    GraphMuteActorRequest(auth()).also {
                        it.actor = actor
                        it.onlyQuoteposts = true
                    }
                )

            val viewer = getViewerState(actor)
            println("viewer: $viewer")

            assertEquals(true, viewer.mutedOnlyQuoteposts)
            assertFalse(viewer.muted == true, "muted: ${viewer.muted}")
            assertFalse(viewer.mutedOnlyReposts == true)

        } finally {
            unmute(actor)
        }
    }

    @Test
    fun testMuteScopeIsReplacedByRepeatCall() = runTest {
        val actor = "bsky.app"

        try {
            // 1回目: リポストのみをミュート
            client()
                .graph()
                .muteActor(
                    GraphMuteActorRequest(auth()).also {
                        it.actor = actor
                        it.onlyReposts = true
                    }
                )

            // 2回目: 引用ポストのみをミュート (スコープは追加ではなく置換される)
            client()
                .graph()
                .muteActor(
                    GraphMuteActorRequest(auth()).also {
                        it.actor = actor
                        it.onlyQuoteposts = true
                    }
                )

            val viewer = getViewerState(actor)
            println("viewer: $viewer")

            assertEquals(true, viewer.mutedOnlyQuoteposts)
            assertFalse(viewer.mutedOnlyReposts == true, "スコープが置換されていません。")

        } finally {
            unmute(actor)
        }
    }

    @Test
    fun testRequestParameterMapping() {
        // スコープ指定がリクエストボディへ正しく変換されること (ネットワーク不要)
        val request = GraphMuteActorRequest(auth()).also {
            it.actor = "bsky.app"
            it.onlyReposts = true
            it.onlyQuoteposts = false
        }

        val params = request.toMap()
        assertEquals("bsky.app", params["actor"])
        assertEquals(true, params["onlyReposts"])
        assertEquals(false, params["onlyQuoteposts"])

        // Boolean が JSON のリテラルとして出力されること
        val json = request.toMappedJson()
        assertTrue(json.contains("\"onlyReposts\":true"), "json: $json")
        assertTrue(json.contains("\"onlyQuoteposts\":false"), "json: $json")
    }

    @Test
    fun testRequestParameterMappingWithoutScope() {
        // スコープ未指定の場合はアカウント全体のミュートとしてキーが含まれないこと
        val request = GraphMuteActorRequest(auth()).also {
            it.actor = "bsky.app"
        }

        val params = request.toMap()
        assertEquals("bsky.app", params["actor"])
        assertTrue("onlyReposts" !in params)
        assertTrue("onlyQuoteposts" !in params)
    }

    @Test
    fun testViewerStateDeserialize() {
        // スコープ付きミュートの状態がデシリアライズされること (ネットワーク不要)
        val json = """
            {
              "muted": false,
              "mutedOnlyReposts": true,
              "mutedOnlyQuoteposts": false,
              "blockedBy": false
            }
        """.trimIndent()

        val viewer = fromJson<ActorDefsViewerState>(json)
        assertEquals(false, viewer.muted)
        assertEquals(true, viewer.mutedOnlyReposts)
        assertEquals(false, viewer.mutedOnlyQuoteposts)
        assertEquals(false, viewer.blockedBy)
    }

    @Test
    fun testViewerStateDeserializeWithoutScope() {
        // スコープ関連のフィールドが無いレスポンスでも壊れないこと
        val viewer = fromJson<ActorDefsViewerState>("""{"muted": true}""")
        assertEquals(true, viewer.muted)
        assertEquals(null, viewer.mutedOnlyReposts)
        assertEquals(null, viewer.mutedOnlyQuoteposts)
    }

    private suspend fun getViewerState(
        actor: String
    ): ActorDefsViewerState {
        val profile = client()
            .actor()
            .getProfile(
                ActorGetProfileRequest(auth()).also {
                    it.actor = actor
                }
            )
        return assertNotNull(profile.data.viewer, "viewer がありません。")
    }

    private suspend fun unmute(
        actor: String
    ) {
        client()
            .graph()
            .unmuteActor(
                GraphUnmuteActorRequest(auth()).also {
                    it.actor = actor
                }
            )
    }
}
