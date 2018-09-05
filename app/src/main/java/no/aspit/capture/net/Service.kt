package no.aspit.capture.net

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import no.aspit.capture.BuildConfig
import no.aspit.capture.common.BaseActivity
import okhttp3.Authenticator
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory.create

class Service(var context: Context, var accessToken: String = "") {
    private val cacheSize: Long = 10 * 1024 * 1024

    private var converterFactory: Converter.Factory

    private var client: OkHttpClient

    init {
        if (accessToken.isNotEmpty()) {
            client = OkHttpClient.Builder()
                    .cache(Cache(context.cacheDir, cacheSize))
                    .addInterceptor { chain ->
                        val original = chain.request()
                        val requestBuilder = original.newBuilder()
                                .header("Accept", "application/json")
                                .header("Content-Type", "application/json")
                                .header(
                                        "Authorization", "Bearer $accessToken"
                                )
                                .method(original.method(), original.body())

                        val request = requestBuilder.build()
                        chain.proceed(request)
                    }
                    .authenticator(Authenticator { route, response ->
                        if (response.code() == 401) {
                            val baseActivity = context as BaseActivity
                            baseActivity.logout()
                            return@Authenticator null
                        } else response.request()
                    })
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
        } else {
            client = OkHttpClient.Builder()
                    .cache(Cache(context.cacheDir, cacheSize))
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
        }
        converterFactory =
                create(
                        Moshi.Builder()
                                .add(KotlinJsonAdapterFactory())
                                .build()
                )

    }

    private fun generateService(baseUrl: String): Api? {
        val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .build()
        return retrofit.create(Api::class.java)
    }

    //region api functions
    fun authenticationToken(
            authCode: String,
            clientId: String,
            redirectUri: String,
            callback: Callback<Token>
    ) {
        generateService(BuildConfig.AUTHORIZATION_BASE_URL)?.authenticationToken(
                authCode,
                "authorization_code",
                clientId,
                redirectUri
        )!!.enqueue(callback)
    }

    fun getUser(callback: Callback<String>) {
        generateService(BuildConfig.API_BASE_URL)?.userApi()!!
                .enqueue(callback)
    }

    fun getPatientBySSN(ssn: String, callback: Callback<Patient>){
        generateService(BuildConfig.API_BASE_URL)?.getPatientBySSN(ssn)!!.enqueue(callback)
    }
    //endregion

    fun uploadFile(
            nin: String,
            data: String,
            assetType: String,
            title: String,
            note: String,
            comment: String,
            callback: Callback<String>
    ) {
        generateService(BuildConfig.API_BASE_URL)?.uploadFile(
                nin,
                data,
                assetType,
                title,
                note,
                comment
        )!!.enqueue(callback)
    }
}