package com.wgoweb.kokaibywgo.ui.activities.adapters

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wgoweb.kokaibywgo.databinding.ItemListVowelLayoutBinding
import com.wgoweb.kokaibywgo.models.VowelModel
import com.wgoweb.kokaibywgo.utils.GlobalFunctions
import java.io.IOException

class AllVowelsAdapter (val context: Context,
                        val items: ArrayList<VowelModel>
                        ): RecyclerView.Adapter<AllVowelsAdapter.GetViewBindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetViewBindingHolder {
        val itemBinding = ItemListVowelLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GetViewBindingHolder(context, itemBinding)
    }

    override fun onBindViewHolder(holder: GetViewBindingHolder, position: Int) {
        val rowBinding: VowelModel = items[position]
        holder.bind(rowBinding)
    }

    override fun getItemCount(): Int = items.size

    class GetViewBindingHolder(val context: Context,
                               private val itemBinding: ItemListVowelLayoutBinding
                               ) : RecyclerView.ViewHolder(itemBinding.root) {
        // A global variable for OnClickListener interface.
        private var onClickListener: View.OnClickListener? = null


        fun bind(rowData: VowelModel) {

            itemBinding.tvVowelEnglish.text  = rowData.vowelEnglish
            itemBinding.tvWritingPattern.text  = rowData.writing_pattern

            GlobalFunctions(context).loadPictureFromDrawableId(rowData.image, itemBinding.ivItemImage)

            itemBinding.itemRowLayout.setOnClickListener {
                playSound(rowData.sound)
                if (onClickListener != null) {
                    onClickListener!!.onClick(itemBinding.root)
                }
            }
        }

        private lateinit var mediaPlayer: MediaPlayer
        private fun playSound(imageName: String){
            try {
                val imageResource = context.resources.getIdentifier(imageName, "raw", context.packageName)
                mediaPlayer = MediaPlayer.create(context, imageResource )
                mediaPlayer.start()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

    }

}