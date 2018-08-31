package no.aspit.aspitcapture.net

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {

    @FormUrlEncoded
    @POST("connect/token")
    fun authenticationToken(
            @Field("code") code: String,
            @Field("grant_type") grantType: String,
            @Field("client_id") clientId: String,
            @Field("redirect_uri") redirectUri: String
    ): Call<Token>
}