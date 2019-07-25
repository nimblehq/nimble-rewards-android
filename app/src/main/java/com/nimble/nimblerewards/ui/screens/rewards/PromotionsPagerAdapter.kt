package com.nimble.nimblerewards.ui.screens.rewards

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.nimble.nimblerewards.R
import com.nimble.nimblerewards.data.models.Promotion


class PromotionsPagerAdapter(
    private val context: Context
) : PagerAdapter() {

    var promotions = emptyList<Promotion>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount() = promotions.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return ImageView(context).apply {
            loadImage(promotions[position].imageUrl, this)
            container.addView(this)
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    private fun loadImage(imageUrl: String, imageView: ImageView) {
        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.color.light_gray)
            .centerCrop()
            .into(imageView)
    }
}
