package com.newyorktimes.app.features.main

import android.graphics.Bitmap
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.newyorktimes.app.BuildConfig
import com.newyorktimes.app.R
import com.newyorktimes.app.data.model.mostpopular.Medium
import com.newyorktimes.app.data.model.mostpopular.Result
import com.newyorktimes.app.data.model.searcharticle.Doc
import com.newyorktimes.app.data.model.searcharticle.Multimedium
import com.newyorktimes.app.util.DateUtil
import javax.inject.Inject

class ItemAdapter @Inject
constructor() : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var mItems: MutableList<Any> = ArrayList()

    fun setItems(items: MutableList<Any>) {
        mItems = items
    }

    fun addItems(result: MutableList<Doc>) {
        mItems.addAll(result)
    }

    fun clear() {
        mItems.clear()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_news, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = mItems[position]

        if (item is Result) {

            holder.mTextTitle.text = item.title
            holder.mTextAbstract.text = item.abstract
            holder.mTextPublishDate.text = DateUtil.formatDate(item.publishedDate!!)
            holder.mTextPublishSource.text = item.source

            if (item.media is List<*>) {
                val mMediumItem = item.media as List<Medium>

                Glide.with(holder.mImageView.context)
                        .load(mMediumItem[0].mediaMetadata?.get(0)?.url)
                        .asBitmap().centerCrop()
                        .into(object : BitmapImageViewTarget(holder.mImageView) {
                            override fun setResource(resource: Bitmap) {
                                val bitmapDrawable = RoundedBitmapDrawableFactory.create(
                                        holder.itemView.resources,
                                        resource)
                                holder.mImageView.setImageDrawable(bitmapDrawable)
                                holder.mProgressBar.visibility = View.INVISIBLE
                            }
                        })
            } else {
                holder.mImageView.setImageDrawable(ContextCompat.getDrawable(holder.mImageView.context, R.mipmap.poweredby_nytimes))
                holder.mProgressBar.visibility = View.INVISIBLE
            }
        } else if (item is Doc) {

            holder.mTextTitle.text = item.headline?.main
            if (item.abstract != null)
                holder.mTextAbstract.text = item.abstract.toString()
            else
                holder.mTextAbstract.text = ""
            holder.mTextPublishDate.text = DateUtil.formatDate(item.pubDate!!)
            holder.mTextPublishSource.text = item.source

            if (item.multimedia?.isNotEmpty() as Boolean) {

                Log.i("URL", " = " + (item.multimedia as List<Multimedium>)[0].url)
                Glide.with(holder.mImageView.context)
                        .load(BuildConfig.NYTIMES_IMAGE_URL + (item.multimedia as List<Multimedium>)[0].url)
                        .asBitmap().centerCrop()
                        .into(object : BitmapImageViewTarget(holder.mImageView) {
                            override fun setResource(resource: Bitmap) {
                                val bitmapDrawable = RoundedBitmapDrawableFactory.create(
                                        holder.itemView.resources,
                                        resource)
                                holder.mImageView.setImageDrawable(bitmapDrawable)
                                holder.mProgressBar.visibility = View.INVISIBLE
                            }
                        })
            } else {
                holder.mImageView.setImageDrawable(ContextCompat.getDrawable(holder.mImageView.context, R.mipmap.poweredby_nytimes))
                holder.mProgressBar.visibility = View.INVISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }


    open inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val mTextTitle: TextView = itemView.findViewById(R.id.text_title)
        val mTextAbstract: TextView = itemView.findViewById(R.id.text_abstract)
        val mTextPublishDate: TextView = itemView.findViewById(R.id.text_publish_date)
        val mTextPublishSource: TextView = itemView.findViewById(R.id.text_source)
        val mImageView: ImageView = itemView.findViewById(R.id.image_view)
        val mProgressBar: ProgressBar = itemView.findViewById(R.id.progress_bar_loading_item)
    }


}
