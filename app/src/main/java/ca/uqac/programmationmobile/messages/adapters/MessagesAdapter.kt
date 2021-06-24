package ca.uqac.programmationmobile.messages.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.programmationmobile.messages.R
import ca.uqac.programmationmobile.messages.data.UserDataSource
import ca.uqac.programmationmobile.messages.models.Message
import ca.uqac.programmationmobile.messages.utils.Time
import com.squareup.picasso.Picasso

class MessagesAdapter(private var dataset: List<Message>? = null, private val context : Context, private val viewLifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {
    class MessageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val text_message : TextView = view.findViewById(R.id.text_message)
        val thumbnail : ImageView = view.findViewById(R.id.thumbnail)
        val cardUser : CardView = view.findViewById(R.id.card_user)
        val timestamp : TextView = view.findViewById(R.id.timestamp)
        val date : TextView = view.findViewById(R.id.date)
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
        if (position > 0){
            val previousMessage = dataset!![position-1]

            if (Time().getDate(message.timestamp) == Time().getDate(previousMessage.timestamp)) holder.date.visibility = TextView.GONE
            else holder.date.text = Time().getDate(message.timestamp)

            if (Time().getTime(message.timestamp) == Time().getTime(previousMessage.timestamp)) holder.timestamp.visibility = TextView.GONE
            else holder.timestamp.text = Time().getTime(message.timestamp)

            if (message.user == previousMessage.user) {
                holder.cardUser.visibility = TextView.INVISIBLE
                holder.thumbnail.visibility = TextView.INVISIBLE
            }

            return
        }
        holder.date.text = Time().getDate(message.timestamp)
        holder.timestamp.text = Time().getTime(message.timestamp)

        UserDataSource(context).getUser(message.user).observe(viewLifecycleOwner, { userHolder ->
            if (userHolder.user != null) {
                Picasso.get()
                    .load(R.drawable.ic_profile)
                    .error(R.drawable.ic_profile)
                    .into(holder.thumbnail);

                userHolder.user.photoUrl?.let { uri ->
                    val photoUrl = uri.replace("http:", "https:")
                    Picasso.get()
                        .load(photoUrl)
                        .error(R.drawable.ic_profile)
                        .into(holder.thumbnail);
                }
            }
        })
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