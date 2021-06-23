package ca.uqac.programmationmobile.messages.network

import ca.uqac.programmationmobile.messages.models.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIUserInterface {

    @POST("createUser/{uid}/{username}/{photoUrl}")
    fun createUser(
        @Path("uid") uid : String,
        @Path("username") username : String,
        @Path("photoUrl") photoUrl : String
    ) : Call<Unit>

    @GET("user/{uid}")
    fun getUser(
        @Path("uid") uid : String
    ) : Call<User>

    @POST("addFriend/{uid}/{friendId}")
    fun addFriend(
        @Path("uid") uid : String,
        @Path("friendId") friendId : String
    ) : Call<Unit>

    @GET("friendList/{uid}")
    fun getFriends(
        @Path("uid") uid : String
    ) : Call<List<User>>
}