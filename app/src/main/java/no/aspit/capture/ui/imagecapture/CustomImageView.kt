package no.aspit.capture.ui.imagecapture

import android.content.Context
import android.graphics.Canvas
import android.widget.ImageView

class CustomImageView(var c: Context) : ImageView(c) {

    init {

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }


}