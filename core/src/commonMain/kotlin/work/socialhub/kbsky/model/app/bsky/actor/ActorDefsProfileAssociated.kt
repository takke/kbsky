package work.socialhub.kbsky.model.bsky.actor

import kotlinx.serialization.Serializable

@Serializable
class ActorDefsProfileAssociated {

    var lists: Int? = null
    var feedgens: Int? = null
    var labeler: Boolean? = null
    var chat: ActorDefsProfileAssociatedChat? = null
}
