package work.socialhub.kbsky.api.entity.chat.bsky.convo

import kotlinx.serialization.Serializable
import work.socialhub.kbsky.model.chat.convo.ChatConvoDefsConvoView

@Serializable
class ConvoGetConvoForMembersResponse {
    lateinit var convo: ChatConvoDefsConvoView
}
