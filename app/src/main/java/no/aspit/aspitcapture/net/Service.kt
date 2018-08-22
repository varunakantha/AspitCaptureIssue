package no.aspit.aspitcapture.net

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import no.aspit.aspitcapture.BuildConfig
import no.aspit.aspitcapture.common.Utils
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory.create

class Service(context: Context) {
    private val cacheSize: Long = 10 * 1024 * 1024

    private var retrofit: Retrofit

    private var service: Api

    init {
        val accessToken = Utils().getAccessToken(context)
        val client: OkHttpClient
        if (accessToken?.isNotEmpty()!!) {
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
        val converterFactory: Converter.Factory =
            create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            )
        retrofit =
            Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.API_BASE_URL)
                .addConverterFactory(converterFactory)
                .build()
        service = retrofit.create(Api::class.java)
    }


    //region api functions

    fun authenticate(
        email: String,
        password: String,
        callBack: Callback<Token>
    ) {
        service.authenticate(email, password).enqueue(callBack)
    }

    //endregion
}