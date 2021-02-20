package com.wgoweb.kokaibywgo.ui.activities.adapters

import android.content.Context
import android.media.MediaPlayer
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.wgoweb.kokaibywgo.databinding.ItemListAlphabetLayoutBinding
import com.wgoweb.kokaibywgo.models.AlphabetModel
import com.wgoweb.kokaibywgo.utils.GlobalFunctions
import java.io.IOException
import kotlin.math.roundToInt

class AllAlphabetsAdapter(val context: Context,
                          val items: ArrayList<AlphabetModel>
                          ): RecyclerView.Adapter<AllAlphabetsAdapter.GetViewBindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetViewBindingHolder {
        val itemBinding = ItemListAlphabetLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GetViewBindingHolder(context, itemBinding)
    }

    override fun onBindViewHolder(holder: GetViewBindingHolder, position: Int) {
        val rowBinding: AlphabetModel = items[position]
        holder.bind(rowBinding)
    }

    override fun getItemCount(): Int = items.size

    class GetViewBindingHolder(val context: Context,
                               private val itemBinding: ItemListAlphabetLayoutBinding
                               ) : RecyclerView.ViewHolder(itemBinding.root) {
        // A global variable for OnClickListener interface.
        private var onClickListener: View.OnClickListener? = null


        fun bind(rowData: AlphabetModel) {

            itemBinding.tvAlphabetEnglish.text  = rowData.vowelEnglish
            itemBinding.tvMeaning.text  = rowData.meaning

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



//GlideLoader(context).loadPictureFromDrawableId(rowData.image, itemBinding.ivItemImage)

/*if (position % 2 == 0) {
itemBinding.itemRow.setBackgroundColor(
ContextCompat.getColor(context,
R.color.colorThemeOrange
)
)
} else {
itemBinding.itemRow.setBackgroundColor(ContextCompat.getColor(context, R.color.colorThemePink))
}*/