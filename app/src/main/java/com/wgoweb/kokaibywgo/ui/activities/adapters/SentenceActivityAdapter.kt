package com.wgoweb.kokaibywgo.ui.activities.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.ItemListSentenceLayoutBinding
import com.wgoweb.kokaibywgo.models.SentenceModel
import com.wgoweb.kokaibywgo.ui.activities.lessons.SentenceActivity
import com.wgoweb.kokaibywgo.utils.Constants


class SentenceActivityAdapter(val context: Context,
                              val items: ArrayList<SentenceModel>,
                              private val listener: SentenceActivity.OnClickListener
)
    : RecyclerView.Adapter<SentenceActivityAdapter.GetViewBindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetViewBindingHolder {
        val itemBinding = ItemListSentenceLayoutBinding.inflate(
            LayoutInflater.from(parent.context)
            , parent, false)
        return GetViewBindingHolder(context, itemBinding, listener)
    }

    override fun onBindViewHolder(holder: GetViewBindingHolder, position: Int) {
        val rowBinding: SentenceModel = items[position]
        Constants.CURRENT_ROW_BACKGROUND = position
        holder.bind(rowBinding)
    }

    override fun getItemCount(): Int = items.size

    class GetViewBindingHolder(val context: Context,
                               private val itemBinding: ItemListSentenceLayoutBinding,
                               private val listener: SentenceActivity.OnClickListener
    )
        : RecyclerView.ViewHolder(itemBinding.root) {

        private val mListener = listener

        fun bind(rowData: SentenceModel) {

            if (rowData.sentence_text.length > Constants.LONGEST_SENTENCE.length) {
                Constants.LONGEST_SENTENCE = rowData.sentence_text
            }

           itemBinding.tvName.text  = rowData.sentence_text

            itemBinding.btnPlayTts.setOnClickListener{
                Constants.TEXT_TO_SPEECH =  rowData.sentence_text
                //call to fire event on Activity
                mListener.onClick(Constants.TEXT_TO_SPEECH)
            }

            // Updating the background color according to the odd/even positions in list.
            if (Constants.CURRENT_ROW_BACKGROUND % 2 == 0) {
                itemBinding.llRow.setBackgroundColor(
                    ContextCompat.getColor(context, R.color.background_row_odd)
                )
            } else {
                itemBinding.llRow.setBackgroundColor(
                    ContextCompat.getColor(context, R.color.background_row_even)
                )
            }
        }
    }

}