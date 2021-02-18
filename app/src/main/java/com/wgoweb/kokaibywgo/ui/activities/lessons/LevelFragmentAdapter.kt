package com.wgoweb.kokaibywgo.ui.activities.lessons

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.ItemListLevelLayoutBinding
import com.wgoweb.kokaibywgo.models.LevelModel
import com.wgoweb.kokaibywgo.utils.Constants

class LevelsFragmentAdapter(val context: Context, val models: ArrayList<LevelModel>,  menuKey: String):
    RecyclerView.Adapter<LevelsFragmentAdapter.GetViewBindingHolder>() {

    var  mMenuKey: String = menuKey


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetViewBindingHolder {
        val itemBinding = ItemListLevelLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GetViewBindingHolder(context, itemBinding, mMenuKey)
    }

    override fun onBindViewHolder(holder: GetViewBindingHolder, position: Int) {

        val rowBinding: LevelModel = models[position]
        holder.bind(rowBinding)
    }

    override fun getItemCount(): Int = models.size

    class GetViewBindingHolder(val context: Context,private val itemBinding: ItemListLevelLayoutBinding, menuKey: String) : RecyclerView.ViewHolder(itemBinding.root) {
        // A global variable for OnClickListener interface.
        private var onClickListener: View.OnClickListener? = null
        var  mMenuKey: String = menuKey
        fun bind(rowData: LevelModel) {
            itemBinding.btnLevelMenu.text  = rowData.level_name


           // GlobalFunctions(context).loadPictureFromDrawableId(rowData.image, itemBinding.ivItemImage)

            itemBinding.btnLevelMenu.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onClick(itemBinding.root)
                } else {

                    when (mMenuKey) {
                        "QUIZ" -> {
                            // Go to Quiz
                        }
                        "LESSON" ->  {
                            val intent = Intent(context, ChapterActivity::class.java)
                            intent.putExtra(Constants.INTENT_LEVEL_ID, rowData.level_id.toString())
                            intent.putExtra(Constants.INTENT_LEVEL_NAME, rowData.level_name)
                            context.startActivity(intent)
                        }
                    }

                }
            }
        }

    }

}