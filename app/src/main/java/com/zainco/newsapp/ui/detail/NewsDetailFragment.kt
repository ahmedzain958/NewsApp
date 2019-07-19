package com.zainco.newsapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.zainco.newsapp.R
import com.zainco.newsapp.data.network.response.Article
import com.zainco.newsapp.internal.DateUtils.getDateFormatted
import com.zainco.newsapp.internal.DrawableUtils
import com.zainco.newsapp.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.news_detail_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory

class NewsDetailFragment : ScopedFragment(), KodeinAware, AppBarLayout.OnOffsetChangedListener {


    override val kodein by closestKodein()
    private var isHideToolbarView = false

    private lateinit var viewModel: NewsDetailViewModel
    private val viewModelDetailInstanceFactory
            : ((Int) -> NewsDetailViewModelFactory) by factory()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.news_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let { NewsDetailFragmentArgs.fromBundle(it) }
        val newsId = safeArgs?.newsId
        viewModel =
            ViewModelProviders.of(this, viewModelDetailInstanceFactory(newsId!!)).get(NewsDetailViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val detailsNews = viewModel.detailsNews.await()
        detailsNews.observe(this@NewsDetailFragment, Observer { detailsNews ->
            if (detailsNews == null) return@Observer

            bindViews(detailsNews)

        })

    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        val maxScroll = appBarLayout!!.totalScrollRange
        val percentage = Math.abs(verticalOffset).toFloat() / maxScroll.toFloat()

        if (percentage == 1f && isHideToolbarView) {
            date_behavior.visibility = View.GONE
            title_appbar.visibility = View.VISIBLE
            isHideToolbarView = !isHideToolbarView

        } else if (percentage < 1f && !isHideToolbarView) {
            date_behavior.visibility = View.VISIBLE
            title_appbar.visibility = View.GONE
            isHideToolbarView = !isHideToolbarView
        }
    }

    private fun bindViews(article: Article) {
        appBarLayout.addOnOffsetChangedListener(this)
        val requestOptions = RequestOptions()
        requestOptions.error(DrawableUtils.randomDrawbleColor)
        Glide.with(this)
            .load(article.urlToImage)
            .apply(requestOptions)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(backdropImageView)

        titleonappbarTextView.text = article.source.name
        subtitleonappbarTextView.text = article.url
        dateTextView.text = getDateFormatted(article.publishedAt)
        titleTextView.text = article.title
        val author: String
        if (article.author != null) {
            author = " \u2022 ${article.author}"
        } else {
            author = ""
        }
        timeTextView.text = article.source.name + author + " \u2022 " + getDateFormatted(article.publishedAt)
        initWebView(article.url)
    }

    private fun initWebView(url: String) {
        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
    }

}
