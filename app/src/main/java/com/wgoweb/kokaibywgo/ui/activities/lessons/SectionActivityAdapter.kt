package com.wgoweb.kokaibywgo.ui.activities.lessons

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wgoweb.kokaibywgo.databinding.ItemListSectionLayoutBinding
import com.wgoweb.kokaibywgo.models.SectionModel
import com.wgoweb.kokaibywgo.utils.Constants


class SectionActivityAdapter(val context: Context, val items: ArrayList<SectionModel>)
    : RecyclerView.Adapter<SectionActivityAdapter.GetViewBindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetViewBindingHolder {
        val itemBinding = ItemListSectionLayoutBinding.inflate(
            LayoutInflater.from(parent.context)
            , parent, false)
        return GetViewBindingHolder(context, itemBinding)
    }

    override fun onBindViewHolder(holder: GetViewBindingHolder, position: Int) {
        val rowBinding: SectionModel = items[position]
        holder.bind(rowBinding)
    }

    override fun getItemCount(): Int = items.size

    class GetViewBindingHolder(val context: Context, private val itemBinding: ItemListSectionLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(rowData: SectionModel) {
            itemBinding.btnSectionMenu.text  = rowData.section_name
            //itemBinding.btnLevelMenu.text  = rowData.section_title
            itemBinding.btnSectionMenu.setOnClickListener {
                // Intent to the other Activity
                val intent = Intent(context, SentenceActivity::class.java)
                intent.putExtra(Constants.INTENT_SECTION_ID, rowData.section_id.toString())
                intent.putExtra(Constants.INTENT_SECTION_NAME, rowData.section_name)
                context.startActivity(intent)
            }
        }
    }

}