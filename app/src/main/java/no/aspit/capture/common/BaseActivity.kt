package no.aspit.capture.common

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.appcenter.analytics.Analytics
import no.aspit.capture.R
import no.aspit.capture.extention.deleteString

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    companion object {
        var TAG = this::class.java.simpleName
    }


    private var loadingView: View? = null
    lateinit var authenticator: Authenticator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Analytics.trackEvent(this::class.java.simpleName)
        authenticator = Authenticator(this)
    }

    private fun getLoadingView(): View {
        return View.inflate(this, R.layout.loading, null)
    }

    protected fun showProgress() {
        loadingView = getLoadingView()
        hideProgress()

        window.addContentView(
                loadingView, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams
                        .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        )
    }

    protected fun hideProgress() {
        val viewParent = loadingView?.parent

        if (viewParent != null) {
            (viewParent as ViewGroup).removeView(loadingView)
        }
    }

    fun logout() {
        deleteString(Constant.KEY_AUTH_TOKEN)
        authenticator.redirectToAuthentication()
        finish()
    }
}