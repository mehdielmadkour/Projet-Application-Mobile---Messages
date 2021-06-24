package ca.uqac.programmationmobile.messages.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ca.uqac.programmationmobile.messages.models.User
import ca.uqac.programmationmobile.messages.network.APIUserInterface
import ca.uqac.programmationmobile.messages.network.ApiClient
import retrofit2.Call
import retrofit2.Response

class UserDataSource(val context: Context) {
    data class UserHolder(val users: User?, val errorMsg: String?)
    data class UsersHolder(val users: List<User>?, val errorMsg: String?)

    private var apiInterface: APIUserInterface? = null

    init {
        apiInterface = ApiClient.getApiClient().create(APIUserInterface::class.java)
    }

    fun createUser(uid : String, username : String, photoUrl : String) {
        apiInterface?.createUser(uid, username, photoUrl)?.enqueue(object : retrofit2.Callback<Unit>{
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {}
            override fun onFailure(call: Call<Unit>, t: Throwable) {}
        })
    }

    fun getUser(uid : String) : LiveData<UserHolder> {
        val data = MutableLiveData<UserHolder>()

        apiInterface?.getUser(uid)?.enqueue(object : retrofit2.Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val res = response.body()
                if(response.code() == 200) {

                    if (res != null) data.value = UserHolder(res, null)
                    else data.value = UserHolder(null, "User not found")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                data.value = UserHolder(null, "Something went wrong")
            }
        })

        return data
    }

    fun addFriend(uid: String, friendId: String) {
        apiInterface?.addFriend(uid, friendId)?.enqueue(object : retrofit2.Callback<Unit>{
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {}
            override fun onFailure(call: Call<Unit>, t: Throwable) {}
        })
    }

    fun getFriends(uid: String) : LiveData<UsersHolder> {
        val data = MutableLiveData<UsersHolder>()

        apiInterface?.getFriends(uid)?.enqueue(object :retrofit2.Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                val res = response.body()
                if(response.code() == 200) {

                    if (res != null) data.value = UsersHolder(res, null)
                    else data.value = UsersHolder(null, "Friends not found")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                data.value = UsersHolder(null, "Something went wrong")
            }
        })

        return data
    }
}