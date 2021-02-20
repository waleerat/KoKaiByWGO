package com.wgoweb.kokaibywgo.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.FragmentQuizBinding
import com.wgoweb.kokaibywgo.firebase.LevelListener
import com.wgoweb.kokaibywgo.models.LevelModel
import com.wgoweb.kokaibywgo.ui.activities.adapters.QuizFragmentAdapter
import com.wgoweb.kokaibywgo.ui.activities.quiz.QuizAlphabetActivity
import com.wgoweb.kokaibywgo.ui.activities.quiz.QuizVowelActivity
import com.wgoweb.kokaibywgo.utils.Constants
import com.wgoweb.kokaibywgo.utils.SharePreferenceHelper
import java.util.ArrayList

class QuizFragment : BaseFragment(), View.OnClickListener {

    private lateinit var binding : FragmentQuizBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        super.onViewCreated(view, savedInstanceState)
        binding = FragmentQuizBinding.bind(view)

        binding.btnMenuQuizAlphabets.setOnClickListener(this)
        binding.btnMenuQuizVowels.setOnClickListener(this)

        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        LevelListener().getLevelItemsListForQuizFragment(this@QuizFragment, this.requireActivity())

    }


    // call this function from LevelListener
    fun saveLevelToPreference(itemsList: ArrayList<LevelModel>) {
        //LevelListener().setLevelPreference(this.requireActivity(), itemsList)
        if (itemsList.size > 0) {
            val jsonString = Gson().toJson(itemsList)
            SharePreferenceHelper().setSharePreference(this.requireActivity(), Constants.REF_LEVEL_PREFERENCE,jsonString )
        }
    }

    // call this function from LevelListener
    fun successItemsList(itemsList: ArrayList<LevelModel>){
        // Hide the progress dialog.
        hideProgressDialog()

        if (itemsList.size > 0) {
            binding.rvViewItems.visibility = View.VISIBLE
            binding.tvNoItemsFound.visibility = View.GONE

            binding.rvViewItems.layoutManager = LinearLayoutManager(activity)
            binding.rvViewItems.setHasFixedSize(true)

            val itemAdapter = QuizFragmentAdapter(requireActivity(), itemsList, "QUIZ", object:
                OnClickListener {
                override fun onClick(currentText: String) {
                    speakOut(currentText)
                }
            })
            // adapter instance is set to the recyclerview to inflate the items.
            binding.rvViewItems.adapter = itemAdapter
        } else {
            binding.rvViewItems.visibility = View.GONE
            binding.tvNoItemsFound.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.btn_menu_quiz_alphabets -> {
                val intent = Intent(context, QuizAlphabetActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_menu_quiz_vowels -> {
                val intent = Intent(context, QuizVowelActivity::class.java)
                startActivity(intent)
            }
        }
    }

    public interface OnClickListener {
        fun onClick(currentText: String)
    }
}
