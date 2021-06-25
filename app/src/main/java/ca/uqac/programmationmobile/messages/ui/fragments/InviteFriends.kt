package ca.uqac.programmationmobile.messages.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.programmationmobile.messages.R
import ca.uqac.programmationmobile.messages.adapters.FriendsAdapter
import ca.uqac.programmationmobile.messages.data.ConversationsDataSource
import ca.uqac.programmationmobile.messages.data.UserDataSource
import com.google.android.gms.auth.api.signin.GoogleSignIn

class InviteFriends : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_invite_friends, container, false)

        view.findViewById<RecyclerView>(R.id.friend_list).adapter = FriendsAdapter(null, invite = true)

        val account = GoogleSignIn.getLastSignedInAccount(context)

        UserDataSource(requireContext()).getFriends(account!!.id).observe(viewLifecycleOwner, { holder ->
            if (holder.users != null) {
                (view.findViewById<RecyclerView>(R.id.friend_list).adapter as FriendsAdapter).updateData(holder.users)
            }
            else {
                Toast.makeText(activity, holder.errorMsg, Toast.LENGTH_SHORT).show()
            }
        })

        view.findViewById<View>(R.id.invite_button).setOnClickListener {
            val friendsToInvite = (view.findViewById<RecyclerView>(R.id.friend_list).adapter as FriendsAdapter).friendsToInvite.toList()
            for (friendId in friendsToInvite) {
                ConversationsDataSource.inviteFriend(conversationId = arguments?.get("conversationId") as String, friendId)
            }
            val action = InviteFriendsDirections.actionInviteFriendsToConversation3(conversationId = arguments?.get("conversationId") as String)
            findNavController().navigate(action)
        }

        return view
    }
}