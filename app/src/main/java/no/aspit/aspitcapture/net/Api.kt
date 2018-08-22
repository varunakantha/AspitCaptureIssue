package no.aspit.aspitcapture.net

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {

    //this is sample code
    @POST("authenticate")
    fun authenticate(
            @Query("email") email: String,
            @Query("password") password: String
    ): Call<Token>
}