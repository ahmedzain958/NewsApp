package com.zainco.newsapp.ui.list

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import com.zainco.newsapp.R
import com.zainco.newsapp.data.network.response.Article
import com.zainco.newsapp.internal.DateUtils.getDateFormatted
import com.zainco.newsapp.internal.DrawableUtils
import kotlinx.android.synthetic.main.item.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle


class NewsItem(
    val article: Article
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            titleTextView.text = article.title
            descriptionTextView.text = article.description
            sourceTextView.text = article.source.name
            timeTextView.text = getDateFormatted(article.publishedAt)
            publishedAtTextView.text = getDateFormatted(article.publishedAt)
            updateValues()
        }
    }

    override fun getLayout() = R.layout.item

    private fun ViewHolder.updateValues() {
        val requestOptions = RequestOptions()
        requestOptions.placeholder(DrawableUtils.randomDrawbleColor)
        requestOptions.error(DrawableUtils.randomDrawbleColor)
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
        requestOptions.centerCrop()

        article.urlToImage?.let { urlToImage ->
            Glide.with(this.containerView)
                .load(urlToImage)
                .apply(requestOptions)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        @Nullable e: GlideException?, model: Any,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
        }

    }
}