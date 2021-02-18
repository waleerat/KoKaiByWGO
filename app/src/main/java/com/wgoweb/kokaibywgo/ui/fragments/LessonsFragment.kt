package com.wgoweb.kokaibywgo.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.FragmentLessonsBinding
import com.wgoweb.kokaibywgo.firebase.LevelListener
import com.wgoweb.kokaibywgo.models.LevelModel
import com.wgoweb.kokaibywgo.ui.activities.alphabets.LearnAlphabetsActivity
import com.wgoweb.kokaibywgo.ui.activities.lessons.LevelsFragmentAdapter
import com.wgoweb.kokaibywgo.ui.activities.vowels.LearnVowelsActivity
import com.wgoweb.kokaibywgo.utils.Constants
import com.wgoweb.kokaibywgo.utils.SharePreferenceHelper
import java.util.*

class LessonsFragment : BaseFragment(), View.OnClickListener {

    private lateinit var binding : FragmentLessonsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lessons, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLessonsBinding.bind(view)

        binding.btnMenuLearnAlphabets.setOnClickListener(this)
        binding.btnMenuLearnVowels.setOnClickListener(this)

        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        LevelListener().getLevelItemsListForLessonFragment(this@LessonsFragment , this.requireActivity())


    }



    // call this function from LevelListener
    fun saveLevelToPreference(itemsList: ArrayList<LevelModel>) {
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

            val itemAdapter = LevelsFragmentAdapter(requireActivity(), itemsList, "LESSON")
            // adapter instance is set to the recyclerview to inflate the items.
            binding.rvViewItems.adapter = itemAdapter
        } else {
            binding.rvViewItems.visibility = View.GONE
            binding.tvNoItemsFound.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.btn_menu_learn_alphabets -> {
                val intent = Intent(context, LearnAlphabetsActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_menu_learn_vowels -> {
                val intent = Intent(context, LearnVowelsActivity::class.java)
                startActivity(intent)
            }
        }
    }


}
