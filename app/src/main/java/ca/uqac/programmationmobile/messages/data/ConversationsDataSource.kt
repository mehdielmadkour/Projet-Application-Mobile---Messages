package ca.uqac.programmationmobile.messages.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ca.uqac.programmationmobile.messages.models.Conversation
import ca.uqac.programmationmobile.messages.network.APIConversationInterface
import ca.uqac.programmationmobile.messages.network.ApiClient
import retrofit2.Call
import retrofit2.Response

class ConversationsDataSource {
    companion object {
        data class ConversationHolder(val conversation: Conversation?, val errorMsg: String?)
        data class ConversationsHolder(val conversations: List<Conversation>?, val errorMsg: String?)

        private var apiInterface: APIConversationInterface? = null

        val conversationsHolder = MutableLiveData<ConversationsHolder>()
        val conversationHolder = MutableLiveData<ConversationHolder>()


        init {
            apiInterface = ApiClient.getApiClient().create(APIConversationInterface::class.java)
        }

        fun getUserConversations(uid: String) : LiveData<ConversationsHolder> {
            updateConversations(uid)
            return conversationsHolder
        }

        fun updateConversations(uid: String) {
            apiInterface?.getConversations(uid)?.enqueue(object : retrofit2.Callback<List<Conversation>>{
                override fun onResponse(
                    call: Call<List<Conversation>>,
                    response: Response<List<Conversation>>
                ) {
                    val res = response.body()
                    if(response.code() == 200) {

                        if (res != null) conversationsHolder.value = ConversationsHolder(res, null)
                        else conversationsHolder.value = ConversationsHolder(null, "Conversations not found")
                    }
                }

                override fun onFailure(call: Call<List<Conversation>>, t: Throwable) {
                    conversationsHolder.value = ConversationsHolder(null, "Something went wrong")
                }
            })
        }

        fun getConversation(id: String) : LiveData<ConversationHolder> {
            updateConversation(id)
            return conversationHolder
        }

        fun updateConversation(id: String) {
            apiInterface?.getConversation(id)?.enqueue(object : retrofit2.Callback<Conversation>{
                override fun onResponse(
                    call: Call<Conversation>,
                    response: Response<Conversation>
                ) {
                    val res = response.body()
                    if(response.code() == 200) {

                        if (res != null) conversationHolder.value = ConversationHolder(res, null)
                        else conversationHolder.value = ConversationHolder(null, "Conversation not found")
                    }
                }

                override fun onFailure(call: Call<Conversation>, t: Throwable) {
                    conversationHolder.value = ConversationHolder(null, "Something went wrong")
                }
            })
        }

        fun createConversation(title: String, uid: String) : LiveData<String> {

            val data = MutableLiveData<String>()

            apiInterface?.createConversation(uid, title, "EMPTY")?.enqueue(object : retrofit2.Callback<String>{
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
}