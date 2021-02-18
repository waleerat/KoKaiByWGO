package com.wgoweb.kokaibywgo.ui.activities.lessons

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wgoweb.kokaibywgo.databinding.ItemListChapterLayoutBinding
import com.wgoweb.kokaibywgo.models.ChapterModel
import com.wgoweb.kokaibywgo.utils.Constants

class ChapterActivityAdapter(val context: Context, val items: ArrayList<ChapterModel>)
    : RecyclerView.Adapter<ChapterActivityAdapter.GetViewBindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetViewBindingHolder {
        val itemBinding = ItemListChapterLayoutBinding.inflate(LayoutInflater.from(parent.context)
            , parent, false)
        return GetViewBindingHolder(context, itemBinding)
    }

    override fun onBindViewHolder(holder: GetViewBindingHolder, position: Int) {
        val rowBinding: ChapterModel = items[position]
        holder.bind(rowBinding)
    }

    override fun getItemCount(): Int = items.size

    class GetViewBindingHolder(val context: Context, private val itemBinding: ItemListChapterLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(rowData: ChapterModel) {
            itemBinding.btnLevelMenu.text  = rowData.chapter_name
            itemBinding.btnLevelMenu.setOnClickListener {
                // Intent to the other Activity
                val intent = Intent(context, SectionActivity::class.java)
                intent.putExtra(Constants.INTENT_CHAPTER_ID, rowData.chapter_id.toString())
                intent.putExtra(Constants.INTENT_CHAPTER_NAME, rowData.chapter_name)
                context.startActivity(intent)
            }
        }
    }

}