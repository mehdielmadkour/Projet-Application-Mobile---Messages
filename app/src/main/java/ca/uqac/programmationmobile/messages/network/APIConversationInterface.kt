package ca.uqac.programmationmobile.messages.network

import ca.uqac.programmationmobile.messages.models.Conversation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIConversationInterface {

    @POST("createConversation/{uid}/{title}/{photoUrl}")
    fun createConversation(
        @Path("uid") uid : String,
        @Path("title") username : String,
        @Path("photoUrl") photoUrl : String
    ) : Call<String>

    @GET("conversation/{uid}")
    fun getConversation(
        @Path("uid") uid : String
    ) : Call<Conversation>

    @POST("postMessage/{conversationId}/{authorId}/{text}")
    fun postMessage(
        @Path("conversationId") conversationId : String,
        @Path("authorId") authorId : String,
        @Path("text") text : String
    ) : Call<Unit>

    @GET("conversationList/{id}")
    fun getConversations(
        @Path("id") id : String
    ) : Call<List<Conversation>>

    @POST("inviteFriend/{conversationId}/{friendId}")
    fun inviteFriend(
        @Path("conversationId") conversationId: String,
        @Path("friendId") friendId: String
    ) : Call<Unit>
}