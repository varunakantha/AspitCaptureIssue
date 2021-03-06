package no.aspit.capture.net

import retrofit2.Call
import retrofit2.http.*

interface Api {

    @FormUrlEncoded
    @POST("connect/token")
    fun authenticationToken(
            @Field("code") code: String,
            @Field("grant_type") grantType: String,
            @Field("client_id") clientId: String,
            @Field("redirect_uri") redirectUri: String
    ): Call<Token>

    @GET("api/person/lookup")
    fun getPatientBySSN(@Query("ssn") ssn: String): Call<Patient>

    @GET("api/user")
    fun userApi(
    ): Call<String>

    @PUT("api/upload")
    fun uploadFile(@Body upload: Upload): Call<String>
}