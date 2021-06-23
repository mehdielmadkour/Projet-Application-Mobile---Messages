package ca.uqac.programmationmobile.messages.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "http://34.152.19.4:80/"

class ApiClient {
    companion object{
        private var retrofit: Retrofit? = null

        fun getApiClient() : Retrofit {
            val gson = GsonBuilder().setLenient().create()
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(1, TimeUnit.SECONDS)
                .connectTimeout(1, TimeUnit.SECONDS)
                .build()
            if(retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit!!
        }
    }
}