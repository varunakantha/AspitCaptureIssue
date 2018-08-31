package no.aspit.capture.common

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import no.aspit.capture.R

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    companion object {
        var TAG = this::class.java.simpleName
    }

    private var loadingView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
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
}