package no.aspit.aspitcapture.common

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.custom_action_bar.view.*
import no.aspit.aspitcapture.R

class CustomActionBar : LinearLayout {

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        var mainTitleText: String? = "Main title"
        var subTitleText: String? = "Sub title"
        var mainBgColor = Color.DKGRAY

        val inflater = LayoutInflater.from(getContext())
        inflater.inflate(R.layout.custom_action_bar, this)

        if (attrs != null) {
            val array = context.obtainStyledAttributes(attrs, R.styleable.CustomActionBar)
            mainTitleText = array.getString(R.styleable.CustomActionBar_main_text)
            subTitleText = array.getString(R.styleable.CustomActionBar_sub_title)
            mainBgColor = array.getColor(R.styleable.CustomActionBar_bar_bg_color, mainBgColor)
            array.recycle()
        }

        main_title.text = mainTitleText
        sub_title.text = subTitleText
        constraint_layout.setBackgroundColor(mainBgColor)
        close_view.setOnClickListener {
            Toast.makeText(getContext(), "Button clicked", Toast.LENGTH_LONG).show()
            //                ((Activity)context).finish();
        }
    }
}
