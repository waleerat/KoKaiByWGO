package com.wgoweb.kokaibywgo.ui.activities.adapters

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wgoweb.kokaibywgo.databinding.ItemListGridAlphabetsBinding
import com.wgoweb.kokaibywgo.databinding.ItemListGridVowelsBinding
import com.wgoweb.kokaibywgo.models.AlphabetModel
import com.wgoweb.kokaibywgo.models.VowelModel
import com.wgoweb.kokaibywgo.ui.activities.learn.AlphabetAndSoundActivity
import com.wgoweb.kokaibywgo.utils.GlobalFunctions
import java.io.IOException


class HorizontalAlphabetsAdapter(val context: Context,
                                val items: ArrayList<AlphabetModel>,
                                 private val listener: AlphabetAndSoundActivity.OnClickListener
                                 ): RecyclerView.Adapter<HorizontalAlphabetsAdapter.GetViewBindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetViewBindingHolder {
        val itemBinding = ItemListGridAlphabetsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GetViewBindingHolder(context, itemBinding, listener)
    }

    override fun onBindViewHolder(holder: GetViewBindingHolder, position: Int) {
        val rowBinding: AlphabetModel = items[position]
        holder.bind(rowBinding)
    }

    override fun getItemCount(): Int = items.size

    class GetViewBindingHolder(
        val context: Context,
        private val itemBinding: ItemListGridAlphabetsBinding,
        private val listener: AlphabetAndSoundActivity.OnClickListener
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private val mListener = listener
        // A global variable for OnClickListener interface.
        private var onClickListener: View.OnClickListener? = null


        fun bind(rowData: AlphabetModel) {

            itemBinding.tvAlphabetEnglish.text  = rowData.vowelEnglish
            itemBinding.tvMeaning.text  = rowData.meaning

            GlobalFunctions(context).loadPictureFromDrawableId(rowData.image, itemBinding.ivItemImage)

            itemBinding.itemRowLayout.setOnClickListener {
                mListener.onAlphabetClick(rowData)
            }
        }
    }

}


class HorizontalVowelsAdapter (val context: Context,
                               val items: ArrayList<VowelModel>,
                               private val listener: AlphabetAndSoundActivity.OnClickListener
                               ): RecyclerView.Adapter<HorizontalVowelsAdapter.GetViewBindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetViewBindingHolder {
        val itemBinding = ItemListGridVowelsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GetViewBindingHolder(context, itemBinding,listener)
    }

    override fun onBindViewHolder(holder: GetViewBindingHolder, position: Int) {
        val rowBinding: VowelModel = items[position]
        holder.bind(rowBinding)
    }

    override fun getItemCount(): Int = items.size

    class GetViewBindingHolder(val context: Context,
                               private val itemBinding: ItemListGridVowelsBinding,
                               private val listener: AlphabetAndSoundActivity.OnClickListener
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        // A global variable for OnClickListener interface.
        private var onClickListener: View.OnClickListener? = null
        private val mListener = listener

        fun bind(rowData: VowelModel) {

            itemBinding.tvVowelEnglish.text  = rowData.vowelEnglish
            itemBinding.tvWritingPattern.text  = rowData.writing_pattern

            GlobalFunctions(context).loadPictureFromDrawableId(rowData.image, itemBinding.ivItemImage)

            itemBinding.itemRowLayout.setOnClickListener {
                mListener.onVowelClick(rowData)
            }
        }

    }

}