package com.wgoweb.kokaibywgo.ui.activities.adapters

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wgoweb.kokaibywgo.databinding.ItemListAlphabetBySoundBinding
import com.wgoweb.kokaibywgo.models.VowelModel
import com.wgoweb.kokaibywgo.ui.activities.lessons.ChapterActivity
import com.wgoweb.kokaibywgo.ui.activities.sounds.AlphabetBySoundActivity
import com.wgoweb.kokaibywgo.utils.Constants
import com.wgoweb.kokaibywgo.utils.GlobalFunctions
import java.io.IOException

class AlphabetBySoundActivityAdapter (val context: Context,
                                      val items: ArrayList<VowelModel>,
                                      private val listener: AlphabetBySoundActivity.OnClickListener
                                      ): RecyclerView.Adapter<AlphabetBySoundActivityAdapter.GetViewBindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetViewBindingHolder {
        val itemBinding = ItemListAlphabetBySoundBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GetViewBindingHolder(context, itemBinding, listener)
    }

    override fun onBindViewHolder(holder: GetViewBindingHolder, position: Int) {
        val rowBinding: VowelModel = items[position]
            holder.bind(rowBinding)
    }

    override fun getItemCount(): Int = items.size

    class GetViewBindingHolder(val context: Context,
                               private val itemBinding: ItemListAlphabetBySoundBinding,
                               private val listener: AlphabetBySoundActivity.OnClickListener
                               ) : RecyclerView.ViewHolder(itemBinding.root) {
        // A global variable for OnClickListener interface.
        private val mListener = listener

        private var onClickListener: View.OnClickListener? = null


        fun bind(rowData: VowelModel) {


            when (Constants.SELECTED_SOUND_LEVEL) {
                "high" -> {
                    itemBinding.llHighSound.visibility = View.VISIBLE
                    itemBinding.llMiddleSound.visibility = View.GONE
                    itemBinding.llLowSound.visibility = View.GONE

                    itemBinding.tvHighSoundFive.text  = rowData.soundLevelOne.replace("ก", Constants.SELECTED_ALPHABET)
                    itemBinding.tvHighSoundTwo.text  = rowData.soundLevelTwo.replace("ก", Constants.SELECTED_ALPHABET)
                    itemBinding.tvHighSoundThree.text  = rowData.soundLevelThree.replace("ก", Constants.SELECTED_ALPHABET)
                    GlobalFunctions(context).loadPictureFromDrawableId(rowData.image, itemBinding.ivVowelHighSound)
                    itemBinding.ivVowelHighSound.setOnClickListener{
                        //call to fire event on Activity
                        mListener.onVowelSoundClick(rowData.image)
                    }
                }

                "mid" -> {
                    itemBinding.llHighSound.visibility = View.GONE
                    itemBinding.llMiddleSound.visibility = View.VISIBLE
                    itemBinding.llLowSound.visibility = View.GONE

                    itemBinding.tvMiddleSoundOne.text  = rowData.soundLevelOne.replace("ก", Constants.SELECTED_ALPHABET)
                    itemBinding.tvMiddleSoundTwo.text  = rowData.soundLevelTwo.replace("ก", Constants.SELECTED_ALPHABET)
                    itemBinding.tvMiddleSoundThree.text  = rowData.soundLevelThree.replace("ก", Constants.SELECTED_ALPHABET)
                    itemBinding.tvMiddleSoundFour.text  = rowData.soundLevelFour.replace("ก", Constants.SELECTED_ALPHABET)
                    itemBinding.tvMiddleSoundFive.text  = rowData.soundLevelFive.replace("ก", Constants.SELECTED_ALPHABET)

                    GlobalFunctions(context).loadPictureFromDrawableId(rowData.image, itemBinding.ivVowelMiddleSound)
                    itemBinding.ivVowelMiddleSound.setOnClickListener{
                        //call to fire event on Activity
                        mListener.onVowelSoundClick(rowData.image)
                    }
                }
                "low" -> {
                    itemBinding.llHighSound.visibility = View.GONE
                    itemBinding.llMiddleSound.visibility = View.GONE
                    itemBinding.llLowSound.visibility = View.VISIBLE

                    itemBinding.tvLowSoundOne.text  = rowData.soundLevelOne.replace("ก", Constants.SELECTED_ALPHABET)
                    itemBinding.tvLowSoundThree.text  = rowData.soundLevelTwo.replace("ก", Constants.SELECTED_ALPHABET)
                    itemBinding.tvLowSoundFour.text  = rowData.soundLevelThree.replace("ก", Constants.SELECTED_ALPHABET)

                    GlobalFunctions(context).loadPictureFromDrawableId(rowData.image, itemBinding.ivVowelLowSound)
                    itemBinding.ivVowelLowSound.setOnClickListener{
                        //call to fire event on Activity
                        mListener.onVowelSoundClick(rowData.image)
                    }
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