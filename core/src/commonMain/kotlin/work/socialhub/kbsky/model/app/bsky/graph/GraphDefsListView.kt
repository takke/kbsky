package work.socialhub.kbsky.model.app.bsky.graph

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import work.socialhub.kbsky.BlueskyTypes
import work.socialhub.kbsky.model.app.bsky.actor.ActorDefsProfileView
import work.socialhub.kbsky.model.app.bsky.embed.EmbedRecordViewUnion
import work.socialhub.kbsky.model.app.bsky.richtext.RichtextFacet

/**
 * List
 */
@Serializable
class GraphDefsListView : EmbedRecordViewUnion() {

    companion object {
        const val TYPE = BlueskyTypes.GraphDefs + "#listView"
    }

    @SerialName("\$type")
    override var type = TYPE

    lateinit var uri: String
    lateinit var cid: String
    lateinit var creator: ActorDefsProfileView
    lateinit var name: String
    lateinit var purpose: String
    var description: String? = null
    var descriptionFacets: List<RichtextFacet> = emptyList()
    var avatar: String? = null
    var viewer: GraphDefsListViewerState? = null
    var indexedAt: String? = null
}
