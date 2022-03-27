package com.eurosp0rt.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.eurosp0rt.R
import com.squareup.picasso.Picasso
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

@BindingAdapter("imgUrl")
fun ImageView.setImg(url: String?) {
    url?.let {
        val imgUri = (url).toUri().buildUpon().scheme("https").build()
        Picasso.get()
            .load(imgUri)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(this)
    }
}

@BindingAdapter("date")
fun TextView.setFormatDate(date: Double?) {
    date?.let {
        val p = PrettyTime(Locale.getDefault())
        this.text = p.format(Date(date.toLong()*1000))
    }
}