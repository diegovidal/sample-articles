package com.dvidal.samplearticles.features.articles.presentation.review

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import dagger.Reusable
import kotlinx.android.synthetic.main.fragment_articles_selection.*
import kotlinx.android.synthetic.main.recycler_item_article_review.view.*
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-25.
 */

@Reusable
class ArticlesReviewAdapter @Inject constructor(): RecyclerView.Adapter<ArticlesReviewAdapter.ArticleReviewViewHolder>() {

    private var dataSet = emptyList<ArticleView>()

    fun updateDataSet(list: List<ArticleView>) {

        this.dataSet = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleReviewViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_article_review, parent, false)

        return ArticleReviewViewHolder(itemView)
    }

    override fun getItemCount(): Int {

        return dataSet.size
    }

    override fun onBindViewHolder(holder: ArticleReviewViewHolder, position: Int) {

        val articleView = dataSet[position]
        holder.bind(articleView)
    }

    inner class ArticleReviewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(articleView: ArticleView) {

            Glide
                .with(itemView.context)
                .load(articleView.imageUrl)
                .centerCrop()
                .into(itemView.iv_article_review)

            itemView.tv_article_title.text = articleView.title
        }
    }
}