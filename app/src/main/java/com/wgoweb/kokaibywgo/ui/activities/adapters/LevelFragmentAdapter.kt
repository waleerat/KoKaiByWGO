package com.wgoweb.kokaibywgo.ui.activities.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wgoweb.kokaibywgo.databinding.ItemListLevelLayoutBinding
import com.wgoweb.kokaibywgo.models.LevelModel
import com.wgoweb.kokaibywgo.ui.activities.lessons.ChapterActivity
import com.wgoweb.kokaibywgo.ui.fragments.LessonsFragment
import com.wgoweb.kokaibywgo.utils.Constants

class LevelsFragmentAdapter(
    val context: Context,
    val models: ArrayList<LevelModel>,
    menuKey: String,
    private val listener: LessonsFragment.OnClickListener
    ): RecyclerView.Adapter<LevelsFragmentAdapter.GetViewBindingHolder>() {

    var  mMenuKey: String = menuKey


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetViewBindingHolder {
        val itemBinding = ItemListLevelLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GetViewBindingHolder(context, itemBinding, mMenuKey , listener)
    }

    override fun onBindViewHolder(holder: GetViewBindingHolder, position: Int) {

        val rowBinding: LevelModel = models[position]
        holder.bind(rowBinding)
    }

    override fun getItemCount(): Int = models.size

    class GetViewBindingHolder(
        val context: Context,
        private val itemBinding: ItemListLevelLayoutBinding,
        menuKey: String,
        private val listener: LessonsFragment.OnClickListener
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private val mListener = listener

        // A global variable for OnClickListener interface.
        private var onClickListener: View.OnClickListener? = null
        var  mMenuKey: String = menuKey
        fun bind(rowData: LevelModel) {
           // GlobalFunctions(context).loadPictureFromDrawableId(rowData.image, itemBinding.ivItemImage)

            when (mMenuKey) {
                "QUIZ" -> {
                    itemBinding.tvName.text = Constants.QUIZ_TEXT + rowData.level_name
                }
                "LESSON" ->  {
                    itemBinding.tvName.text = rowData.level_name
                }
            }

            if (rowData.background != "") {
                //itemBinding.btnLevelMenu.setBackgroundColor(Color.parseColor(rowData.background));
            }

            itemBinding.btnLevelMenu.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onClick(itemBinding.root)
                } else {

                    Constants.LESSON_TEXT = rowData.level_name
                    val intent = Intent(context, ChapterActivity::class.java)
                    intent.putExtra(Constants.INTENT_LEVEL_ID, rowData.level_id.toString())
                    intent.putExtra(Constants.INTENT_LEVEL_NAME, rowData.level_name)
                    context.startActivity(intent)

                }
            }

            itemBinding.btnPlayTts.setOnClickListener{
                Constants.TEXT_TO_SPEECH =  rowData.level_name
                //call to fire event on Activity
                mListener.onClick(Constants.TEXT_TO_SPEECH)
            }

        }

    }

}