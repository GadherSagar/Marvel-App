package com.example.marvelapp.util

import android.graphics.Paint
import android.graphics.Typeface
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.marvelapp.R
import com.example.marvelapp.database.entity.Comic

@BindingAdapter("android:setImageUrl")
fun setImageUrl(view: ImageView, imageUrl: String?) {
    if (imageUrl == null) {
        view.setImageBitmap(null)
    } else {
        view.load(imageUrl) {

        }
        view.load(imageUrl)
    }
}

@Suppress("MagicNumber")
@BindingAdapter("android:inflateComics")
fun inflateComics(layout: LinearLayoutCompat, comics: List<Comic>?) {
    val context = layout.context
    layout.removeAllViews()
    val tvTitle = TextView(context)
    tvTitle.text = context.getString(R.string.lbl_comics)
    val sp16 = context.resources.getDimension(R.dimen.sp16)
    tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp16)
    tvTitle.setTypeface(tvTitle.typeface, Typeface.BOLD)
    tvTitle.paintFlags = tvTitle.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    tvTitle.setTextColor(context.getColor(R.color.black))
    layout.addView(tvTitle)
    if (!comics.isNullOrEmpty()) {
        val limit = 5
        var count = 0
        for (comic in comics) {
            if (count < limit) {
                val tv = TextView(context)
                tv.text = comic.name
                layout.addView(tv)
                count++
            } else {
                break
            }
        }
    } else {
        val tv = TextView(context)
        tv.text = "NA"
        layout.addView(tv)
    }
}
