package work.socialhub.kbsky.api.entity.app.bsky.graph

import kotlinx.serialization.Serializable
import work.socialhub.kbsky.model.app.bsky.actor.ActorDefsProfileView

@Serializable
class GraphGetKnownFollowersResponse {
    var cursor: String? = null
    lateinit var subject: ActorDefsProfileView
    lateinit var followers: List<ActorDefsProfileView>
}
