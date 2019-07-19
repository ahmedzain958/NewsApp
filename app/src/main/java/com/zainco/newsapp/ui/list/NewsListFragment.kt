package com.zainco.newsapp.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import com.zainco.newsapp.R
import com.zainco.newsapp.data.network.response.Article
import com.zainco.newsapp.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.news_list_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class NewsListFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: NewsListViewModelFactory by instance()


    private lateinit var viewModel: NewsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.news)
        return inflater.inflate(R.layout.news_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewsListViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val newsEntries = viewModel.newsEntries.await()

        newsEntries.observe(this@NewsListFragment, Observer { newsEntries ->
            if (newsEntries == null) return@Observer

            group_loading.visibility = View.GONE
            initRecyclerView(newsEntries)
        })
    }

    private fun initRecyclerView(newsEntries: List<Article>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(newsEntries.toNewsItems())
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@NewsListFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, view ->
            (item as? NewsItem)?.let {
                showNewsDetail(it.article.id, view)
            }
        }
    }

    private fun showNewsDetail(articleId: Int, view: View) {
        val actionDetail = NewsListFragmentDirections.actionDetail(articleId)
        Navigation.findNavController(view).navigate(actionDetail)
    }

    private fun List<Article>.toNewsItems(): List<NewsItem> {
        return this.map {
            NewsItem(it)
        }
    }
}
