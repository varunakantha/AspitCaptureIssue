package no.aspit.capture

import android.app.Application
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient


class AspitCaptureApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG){
            Stetho.initializeWithDefaults(this)
            OkHttpClient.Builder()
                    .addNetworkInterceptor(StethoInterceptor())
                    .build()
        }
    }
}