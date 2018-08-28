package no.aspit.aspitcapture.common

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.custom_action_bar.view.*
import no.aspit.aspitcapture.R

class CustomActionBar : LinearLayout {

    lateinit var listenerConnector:ActionBarListener

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        listenerConnector = context as ActionBarListener
        var mainTitleText: String? = ""
        var subTitleText: String? = ""
        var mainBgColor = Color.DKGRAY
        var pipeVisibility:Int = View.GONE

        val inflater = LayoutInflater.from(getContext())
        inflater.inflate(R.layout.custom_action_bar, this)

        if (attrs != null) {
            val array = context.obtainStyledAttributes(attrs, R.styleable.CustomActionBar)
            mainTitleText = array.getString(R.styleable.CustomActionBar_main_text)
            subTitleText = array.getString(R.styleable.CustomActionBar_sub_title)
            mainBgColor = array.getColor(R.styleable.CustomActionBar_bar_bg_color, mainBgColor)
            pipeVisibility = array.getInt(R.styleable.CustomActionBar_pipe_visibility, pipeVisibility)
            array.recycle()
        }

        mainTitle.text = mainTitleText
        subTitle.text = subTitleText
        constraint_layout.setBackgroundColor(mainBgColor)
        pipeBar.visibility = pipeVisibility
        closeView.setOnClickListener {
            listenerConnector.onClose()
        }

    }

    interface ActionBarListener{
        fun onClose()
    }
}
