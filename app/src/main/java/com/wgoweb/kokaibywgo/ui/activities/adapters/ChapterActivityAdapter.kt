package com.wgoweb.kokaibywgo.ui.activities.adapters


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wgoweb.kokaibywgo.databinding.ItemListChapterLayoutBinding
import com.wgoweb.kokaibywgo.models.ChapterModel
import com.wgoweb.kokaibywgo.ui.activities.lessons.ChapterActivity
import com.wgoweb.kokaibywgo.ui.activities.lessons.SectionActivity
import com.wgoweb.kokaibywgo.utils.Constants

class ChapterActivityAdapter(val context: Context,
                             val items: ArrayList<ChapterModel>,
                             private val listener: ChapterActivity.OnClickListener
                             ) : RecyclerView.Adapter<ChapterActivityAdapter.GetViewBindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetViewBindingHolder {
        val itemBinding = ItemListChapterLayoutBinding.inflate(LayoutInflater.from(parent.context)
            , parent, false)
        return GetViewBindingHolder(context, itemBinding, listener)
    }

    override fun onBindViewHolder(holder: GetViewBindingHolder, position: Int) {
        val rowBinding: ChapterModel = items[position]
        holder.bind(rowBinding)
    }


   override fun getItemCount(): Int {
       return items.size
   }

    class GetViewBindingHolder(val context: Context,
                               private val itemBinding: ItemListChapterLayoutBinding,
                               private val listener: ChapterActivity.OnClickListener
                               ): RecyclerView.ViewHolder(itemBinding.root) {

        private val mListener = listener

        fun bind(rowData: ChapterModel) {
            //Log.i("Get rowBinding* >>", rowData.chapter_desctiption)
            itemBinding.tvName.text = rowData.chapter_name
            itemBinding.tvDescription.text = rowData.chapter_desctiption

            if (rowData.background != "") {
                //itemBinding.btnChapterMenu.setBackgroundColor(Color.parseColor(rowData.background));
            }

            itemBinding.btnChapterMenu.setOnClickListener {
                // Intent to the other Activity
                val intent = Intent(context, SectionActivity::class.java)
                intent.putExtra(Constants.INTENT_CHAPTER_ID, rowData.chapter_id)
                intent.putExtra(Constants.INTENT_CHAPTER_NAME, rowData.chapter_name +" "+rowData.chapter_desctiption)
                context.startActivity(intent)
            }

            itemBinding.btnPlayTts.setOnClickListener{
                Constants.TEXT_TO_SPEECH =  rowData.chapter_name +" "+rowData.chapter_desctiption
                //call to fire event on Activity
                mListener.onClick(Constants.TEXT_TO_SPEECH)
            }
        }
    }



}


/*
* public interface OnClickListener {
    fun onClick()
}



In adapter,


* */