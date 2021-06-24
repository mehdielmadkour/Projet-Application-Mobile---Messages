package ca.uqac.programmationmobile.messages.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.programmationmobile.messages.R
import ca.uqac.programmationmobile.messages.data.UserDataSource
import ca.uqac.programmationmobile.messages.models.Message
import ca.uqac.programmationmobile.messages.utils.Time
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.squareup.picasso.Picasso

class MessagesAdapter(private var dataset: List<Message>? = null, private val context: Context, private val viewLifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {
    abstract class MessageViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val text_message : TextView = view.findViewById(R.id.text_message)
        val thumbnail : ImageView = view.findViewById(R.id.thumbnail)
        val cardUser : CardView = view.findViewById(R.id.card_user)
        val timestamp : TextView = view.findViewById(R.id.timestamp)
        val date : TextView = view.findViewById(R.id.date)
    }

    class MessageViewHolderRight(view: View) : MessageViewHolder(view)
    class MessageViewHolderLeft(view: View) : MessageViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

        return when (viewType) {
            0 -> MessageViewHolderRight(LayoutInflater.from(parent.context)
                .inflate(R.layout.message_item_layout_right, parent, false))
            else -> MessageViewHolderLeft(LayoutInflater.from(parent.context)
                .inflate(R.layout.message_item_layout_left, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = dataset!![position]
        val account = GoogleSignIn.getLastSignedInAccount(context)

        if (account?.id == message.user) return 0
        else return 1
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        if (dataset == null) return
        val message = dataset!![position]
        holder.text_message.text = message.text
        if (position > 0){
            val nextMessage = dataset!![position-1]

            if (Time().getDate(message.timestamp) == Time().getDate(nextMessage.timestamp)) holder.date.visibility = TextView.GONE
            else holder.date.text = Time().getDate(message.timestamp)

            if (Time().getTime(message.timestamp) == Time().getTime(nextMessage.timestamp)) holder.timestamp.visibility = TextView.GONE
            else holder.timestamp.text = Time().getTime(message.timestamp)

            if (message.user == nextMessage.user) {
                holder.cardUser.visibility = TextView.INVISIBLE
                holder.thumbnail.visibility = TextView.INVISIBLE
            }
            else updateThumbnail(holder, message)

            return
        }
        holder.date.text = Time().getDate(message.timestamp)
        holder.timestamp.text = Time().getTime(message.timestamp)
        updateThumbnail(holder, message)
    }

    fun updateThumbnail(holder: MessageViewHolder, message: Message) {
        UserDataSource().getUser(message.user).observe(viewLifecycleOwner, { userHolder ->
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