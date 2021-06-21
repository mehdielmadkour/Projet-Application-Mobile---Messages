package ca.uqac.programmationmobile.messages.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.programmationmobile.messages.R
import ca.uqac.programmationmobile.messages.models.Conversation

class ConversationsAdapter(private var dataset: List<Conversation>? = null, private val callback : (Conversation) -> Unit) : RecyclerView.Adapter<ConversationsAdapter.ConversationViewHolder>() {
    class ConversationViewHolder(private val view: View, private val callback : (Conversation) -> Unit) : RecyclerView.ViewHolder(view) {
        val title_text_view : TextView = view.findViewById(R.id.title)
        val last_user_text_view : TextView = view.findViewById(R.id.last_user)
        val last_message_text_view : TextView = view.findViewById(R.id.last_message)
        var conversation : Conversation? = null

        init {
            view.findViewById<RelativeLayout>(R.id.holder).setOnClickListener {
                conversation?.let(callback)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.conversation_item_layout, parent, false)

        return ConversationViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        if (dataset == null) return
        val conversation = dataset!![position]
        holder.conversation = conversation
        holder.title_text_view.text = conversation.title
        conversation.lastMessage?.let { message ->
            holder.last_user_text_view.text = message.user
            holder.last_message_text_view.text = message.text
        }
    }

    override fun getItemCount(): Int {
        if (dataset == null) return 0
        return dataset!!.size
    }

    fun updateData(data: List<Conversation>) {
        dataset = data
        notifyDataSetChanged()
    }
}