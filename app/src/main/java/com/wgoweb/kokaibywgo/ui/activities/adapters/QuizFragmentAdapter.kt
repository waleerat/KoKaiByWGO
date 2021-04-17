package com.wgoweb.kokaibywgo.ui.activities.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.ItemListQuizLayoutBinding
import com.wgoweb.kokaibywgo.models.LevelModel
import com.wgoweb.kokaibywgo.ui.activities.lessons.ChapterActivity
import com.wgoweb.kokaibywgo.ui.activities.quiz.QuizLessonActivity
import com.wgoweb.kokaibywgo.ui.fragments.QuizFragment
import com.wgoweb.kokaibywgo.utils.Constants

 
class QuizFragmentAdapter(
    val context: Context,
    val models: ArrayList<LevelModel>,
    menuKey: String,
    private val listener: QuizFragment.OnClickListener
): RecyclerView.Adapter<QuizFragmentAdapter.GetViewBindingHolder>() {

    var  mMenuKey: String = menuKey


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetViewBindingHolder {
        val itemBinding = ItemListQuizLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GetViewBindingHolder(context, itemBinding, mMenuKey , listener)
    }

    override fun onBindViewHolder(holder: GetViewBindingHolder, position: Int) {

        val rowBinding: LevelModel = models[position]
        holder.bind(rowBinding)
    }

    override fun getItemCount(): Int = models.size

    class GetViewBindingHolder(
        val context: Context,
        private val itemBinding: ItemListQuizLayoutBinding,
        menuKey: String,
        private val listener: QuizFragment.OnClickListener
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private val mListener = listener

        // A global variable for OnClickListener interface.
        private var onClickListener: View.OnClickListener? = null
        var  mMenuKey: String = menuKey
        fun bind(rowData: LevelModel) {
            // GlobalFunctions(context).loadPictureFromDrawableId(rowData.image, itemBinding.ivItemImage)
            itemBinding.tvName.text = Constants.QUIZ_TEXT+rowData.level_name

            if (rowData.background != "") {
                //itemBinding.btnLevelMenu.setBackgroundColor(Color.parseColor(rowData.background));
            }

            itemBinding.btnQuizMenu.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onClick(itemBinding.root)
                } else {

                    val intent = Intent(context, QuizLessonActivity::class.java)
                    intent.putExtra(Constants.INTENT_LEVEL_ID, rowData.level_id)
                    intent.putExtra(Constants.INTENT_LEVEL_NAME, rowData.level_name)
                    context.startActivity(intent)

                }
            }

            itemBinding.btnPlayTts.setOnClickListener{
                Constants.TEXT_TO_SPEECH =  Constants.QUIZ_TEXT+rowData.level_name
                //call to fire event on Activity
                mListener.onClick(Constants.TEXT_TO_SPEECH)
            }

        }

    }

}