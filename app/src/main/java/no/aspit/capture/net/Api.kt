package no.aspit.capture.net

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.PUT

interface Api {

    @FormUrlEncoded
    @POST("connect/token")
    fun authenticationToken(
            @Field("code") code: String,
            @Field("grant_type") grantType: String,
            @Field("client_id") clientId: String,
            @Field("redirect_uri") redirectUri: String
    ): Call<Token>

    @FormUrlEncoded
    @PUT("api/upload")
    fun uploadFile(
            @Field("nin") nin: String,
            @Field("data") data: String,
            @Field("assetType") assetType: String,
            @Field("title") title: String,
            @Field("note") note: String,
            @Field("comment") comment: String): Call<String>
}