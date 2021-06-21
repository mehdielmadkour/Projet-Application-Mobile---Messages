package ca.uqac.programmationmobile.messages.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.programmationmobile.messages.R
import ca.uqac.programmationmobile.messages.models.Message

class MessagesAdapter(private var dataset: List<Message>? = null) : RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {
    class MessageViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val text_message : TextView = view.findViewById(R.id.text_message)
        val thumbnail : ImageView = view.findViewById(R.id.thumbnail)
        val timestamp : TextView = view.findViewById(R.id.timestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_item_layout, parent, false)

        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        if (dataset == null) return
        val message = dataset!![position]
        holder.text_message.text = message.text
    }

    override fun getItemCount(): Int {
        if (dataset == null) return 0
        return dataset!!.size
    }

    fun updateData(data: List<Message>) {
        dataset = data
        notifyDataSetChanged()
    }
}