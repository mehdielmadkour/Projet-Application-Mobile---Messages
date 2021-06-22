package ca.uqac.programmationmobile.messages.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.programmationmobile.messages.R
import ca.uqac.programmationmobile.messages.models.User
import com.squareup.picasso.Picasso

class FriendsAdapter(private var dataset: List<User>? = null) : RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {
    class FriendViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val username : TextView = view.findViewById(R.id.username)
        val thumbnail : ImageView = view.findViewById(R.id.profile_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.friend_item_layout, parent, false)

        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        if (dataset == null) return
        val user = dataset!![position]
        holder.username.text = user.username
        Picasso.get()
            .load(user.photoUrl.toString().replace("http:", "https:"))
            .placeholder(R.drawable.ic_profile)
            .error(R.drawable.ic_profile)
            .into(holder.thumbnail);
    }

    override fun getItemCount(): Int {
        if (dataset == null) return 0
        return dataset!!.size
    }

    fun updateData(data: List<User>) {
        dataset = data
        notifyDataSetChanged()
    }
}