package ca.uqac.programmationmobile.messages.adapters

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.programmationmobile.messages.R
import ca.uqac.programmationmobile.messages.data.UserDataSource
import ca.uqac.programmationmobile.messages.models.Conversation
import com.squareup.picasso.Picasso

class ConversationsAdapter(private var dataset: List<Conversation>? = null, private val callback : (Conversation) -> Unit, private val viewLifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<ConversationsAdapter.ConversationViewHolder>() {
    class ConversationViewHolder(private val view: View, private val callback : (Conversation) -> Unit) : RecyclerView.ViewHolder(view) {
        val title_text_view : TextView = view.findViewById(R.id.title)
        val last_user_text_view : TextView = view.findViewById(R.id.last_user)
        val last_message_text_view : TextView = view.findViewById(R.id.last_message)
        val separator : TextView = view.findViewById(R.id.separator)
        val thumbnail : ImageView = view.findViewById(R.id.image)
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
        if (conversation.messages?.isEmpty() == true) {
            holder.last_user_text_view.visibility = TextView.GONE
            holder.last_message_text_view.visibility = TextView.GONE
            holder.separator.visibility = TextView.GONE
        }
        else {
            holder.conversation!!.lastMessage = conversation.messages?.get(conversation.messages.size-1)
            UserDataSource().getUser(conversation.lastMessage?.user!!).observe(viewLifecycleOwner, { userHolder ->
                holder.last_user_text_view.text = userHolder.user?.username
                holder.last_message_text_view.text = conversation.lastMessage?.text
            })
        }

        Picasso.get()
            .load(R.drawable.ic_message)
            .error(R.drawable.ic_message)
            .into(holder.thumbnail);

        conversation.photoUrl?.let { uri ->
            val photoUrl = uri.replace("http:", "https:")
            Picasso.get()
                .load(photoUrl)
                .error(R.drawable.ic_message)
                .into(holder.thumbnail);
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