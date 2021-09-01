package com.elattaoui.moviedb.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.airbnb.epoxy.ModelView
import com.elattaoui.moviedb.databinding.LoaderViewBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class LoaderView : LinearLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        LoaderViewBinding.inflate(LayoutInflater.from(context), this)
        gravity = Gravity.CENTER_HORIZONTAL
    }
}
