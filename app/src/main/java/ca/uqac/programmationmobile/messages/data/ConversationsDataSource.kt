package ca.uqac.programmationmobile.messages.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ca.uqac.programmationmobile.messages.models.Conversation
import ca.uqac.programmationmobile.messages.network.APIConversationInterface
import ca.uqac.programmationmobile.messages.network.ApiClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import retrofit2.Call
import retrofit2.Response

class ConversationsDataSource(val context : Context) {
    data class ConversationHolder(val conversations: Conversation?, val errorMsg: String?)
    data class ConversationsHolder(val conversations: List<Conversation>?, val errorMsg: String?)

    private var apiInterface: APIConversationInterface? = null

    init {
        apiInterface = ApiClient.getApiClient().create(APIConversationInterface::class.java)
    }

    fun getUserConversations(uid: String) : LiveData<ConversationsHolder> {
        val data = MutableLiveData<ConversationsHolder>()

        apiInterface?.getConversations(uid)?.enqueue(object : retrofit2.Callback<List<Conversation>>{
            override fun onResponse(
                call: Call<List<Conversation>>,
                response: Response<List<Conversation>>
            ) {
                val res = response.body()
                if(response.code() == 200) {

                    if (res != null) data.value = ConversationsHolder(res, null)
                    else data.value = ConversationsHolder(null, "Conversations not found")
                }
            }

            override fun onFailure(call: Call<List<Conversation>>, t: Throwable) {
                data.value = ConversationsHolder(null, "Something went wrong")
            }
        })

        return data
    }

    fun getConversation(id: String) : LiveData<ConversationHolder> {
        val data = MutableLiveData<ConversationHolder>()

        apiInterface?.getConversation(id)?.enqueue(object : retrofit2.Callback<Conversation>{
            override fun onResponse(
                call: Call<Conversation>,
                response: Response<Conversation>
            ) {
                val res = response.body()
                if(response.code() == 200) {

                    if (res != null) data.value = ConversationHolder(res, null)
                    else data.value = ConversationHolder(null, "Conversation not found")
                }
            }

            override fun onFailure(call: Call<Conversation>, t: Throwable) {
                data.value = ConversationHolder(null, "Something went wrong")
            }
        })

        return data
    }

    fun createConversation(title: String) : LiveData<String> {

        val data = MutableLiveData<String>()
        val account = GoogleSignIn.getLastSignedInAccount(context)

        apiInterface?.createConversation(account!!.id, title, "EMPTY")?.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val res = response.body()
                data.value = res!!
                Log.e("ID", res.toString())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("ID", t.toString())
            }
        })

        return data
    }
}