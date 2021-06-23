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
import ca.uqac.programmationmobile.messages.adapters.MessagesAdapter
import ca.uqac.programmationmobile.messages.data.MessagesDataSource
import ca.uqac.programmationmobile.messages.data.UserDataSource
import com.google.android.gms.auth.api.signin.GoogleSignIn

class Friends : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_friends, container, false)

        view.findViewById<RecyclerView>(R.id.friend_list).adapter = FriendsAdapter(null)

        val account = GoogleSignIn.getLastSignedInAccount(context)

        UserDataSource(requireContext()).getFriends(account!!.id).observe(viewLifecycleOwner, { holder ->
            if (holder.users != null) {
                (view.findViewById<RecyclerView>(R.id.friend_list).adapter as FriendsAdapter).updateData(holder.users)
            }
            else {
                Toast.makeText(activity, holder.errorMsg, Toast.LENGTH_SHORT).show()
            }
        })

        view.findViewById<View>(R.id.new_friend_button).setOnClickListener {
            val action = FriendsDirections.actionFriendsToQrScanner()
            findNavController().navigate(action)
        }

        return view
    }
}