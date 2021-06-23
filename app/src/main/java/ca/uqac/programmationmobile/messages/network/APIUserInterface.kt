package ca.uqac.programmationmobile.messages.network

import ca.uqac.programmationmobile.messages.models.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface APIUserInterface {

    @POST("createUser")
    fun createUser(uid : String, username : String, photoUrl : String)

    @GET("user")
    fun getUser(uid : String) : Call<User>

    @POST("addFriend")
    fun addFriend(uid : String, friendId : String)

    @GET("friendList")
    fun getFriends(uid : String) : Call<List<User>>

}