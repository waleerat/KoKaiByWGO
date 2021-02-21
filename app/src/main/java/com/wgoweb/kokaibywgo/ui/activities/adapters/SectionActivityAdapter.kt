package com.wgoweb.kokaibywgo.ui.activities.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wgoweb.kokaibywgo.databinding.ItemListSectionLayoutBinding
import com.wgoweb.kokaibywgo.models.SectionModel
import com.wgoweb.kokaibywgo.ui.activities.lessons.SectionActivity
import com.wgoweb.kokaibywgo.ui.activities.lessons.SentenceActivity
import com.wgoweb.kokaibywgo.utils.Constants


class SectionActivityAdapter(val context: Context,
                             val items: ArrayList<SectionModel>,
                             private val listener: SectionActivity.OnClickListener
                             ): RecyclerView.Adapter<SectionActivityAdapter.GetViewBindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetViewBindingHolder {
        val itemBinding = ItemListSectionLayoutBinding.inflate(
            LayoutInflater.from(parent.context)
            , parent, false)
        return GetViewBindingHolder(context, itemBinding, listener)
    }

    override fun onBindViewHolder(holder: GetViewBindingHolder, position: Int) {
        val rowBinding: SectionModel = items[position]
        holder.bind(rowBinding)
    }

    override fun getItemCount(): Int = items.size

    class GetViewBindingHolder(val context: Context,
                               private val itemBinding: ItemListSectionLayoutBinding,
                               private val listener: SectionActivity.OnClickListener
                               ) : RecyclerView.ViewHolder(itemBinding.root) {

        private val mListener = listener

        fun bind(rowData: SectionModel) {

            itemBinding.tvName.text  = rowData.section_name
            itemBinding.tvDescription.text  = rowData.section_descritpion
            Constants.LESSON_TEXT = rowData.section_name

            itemBinding.btnSectionMenu.setOnClickListener {
                // Intent to the other Activity
                val intent = Intent(context, SentenceActivity::class.java)
                intent.putExtra(Constants.INTENT_SECTION_ID, rowData.section_id)
                intent.putExtra(Constants.INTENT_SECTION_NAME, rowData.section_descritpion)
                context.startActivity(intent)
            }

            itemBinding.btnPlayTts.setOnClickListener{
                Constants.TEXT_TO_SPEECH =  rowData.section_name +" "+rowData.section_descritpion
                //call to fire event on Activity
                mListener.onClick(Constants.TEXT_TO_SPEECH)
            }

        }
    }

}