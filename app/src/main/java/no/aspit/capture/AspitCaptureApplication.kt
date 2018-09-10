package no.aspit.capture

import android.app.Application
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.AppCenter




class AspitCaptureApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG){
            Stetho.initializeWithDefaults(this)
            OkHttpClient.Builder()
                    .addNetworkInterceptor(StethoInterceptor())
                    .build()
        }

        AppCenter.start(this, "bf935cf6-4a9d-4f8f-90ca-25308f9cb9b3",
                Analytics::class.java, Crashes::class.java)
    }
}