package com.wgoweb.kokaibywgo.ui.activities.lessons

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wgoweb.kokaibywgo.databinding.ItemListSentenceLayoutBinding
import com.wgoweb.kokaibywgo.models.SentenceModel
import com.wgoweb.kokaibywgo.utils.Constants


class SentenceActivityAdapter(val context: Context, val items: ArrayList<SentenceModel>)
    : RecyclerView.Adapter<SentenceActivityAdapter.GetViewBindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetViewBindingHolder {
        val itemBinding = ItemListSentenceLayoutBinding.inflate(
            LayoutInflater.from(parent.context)
            , parent, false)
        return GetViewBindingHolder(context, itemBinding)
    }

    override fun onBindViewHolder(holder: GetViewBindingHolder, position: Int) {
        val rowBinding: SentenceModel = items[position]
        holder.bind(rowBinding)
    }

    override fun getItemCount(): Int = items.size

    class GetViewBindingHolder(val context: Context, private val itemBinding: ItemListSentenceLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(rowData: SentenceModel) {

           itemBinding.tvSentenceMenu.text  = rowData.sentence
            /*  itemBinding.tvSentenceMenu.setOnClickListener {
                  // Intent to the other Activity
                  val intent = Intent(context, SentenceActivity::class.java)
                  intent.putExtra(Constants.INTENT_SENTENCE_ID, rowData.sentence_id.toString())

                  context.startActivity(intent)
              }
              */
        }
    }

}