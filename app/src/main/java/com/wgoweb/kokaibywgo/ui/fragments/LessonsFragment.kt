package com.wgoweb.kokaibywgo.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.FragmentLessonsBinding
import com.wgoweb.kokaibywgo.databinding.FragmentQuizBinding
import com.wgoweb.kokaibywgo.firebase.LevelListener
import com.wgoweb.kokaibywgo.models.LevelModel
import com.wgoweb.kokaibywgo.ui.activities.learn.LearnAlphabetsActivity
import com.wgoweb.kokaibywgo.ui.activities.adapters.LevelsFragmentAdapter
import com.wgoweb.kokaibywgo.ui.activities.learn.AlphabetAndSoundActivity
import com.wgoweb.kokaibywgo.ui.activities.learn.LearnVowelsActivity
import com.wgoweb.kokaibywgo.utils.Constants
import com.wgoweb.kokaibywgo.utils.SharePreferenceHelper
import java.util.*

class LessonsFragment : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentLessonsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLessonsBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvNoItemsFound.visibility = View.GONE
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        LevelListener().getLevelItemsListForLessonFragment(this@LessonsFragment , this.requireActivity())

        binding.btnSoundTitle.setOnClickListener(this)
        binding.btnMenuLearnAlphabets.setOnClickListener(this)
        binding.btnMenuLearnVowels.setOnClickListener(this)
        binding.btnMenuLearnVowelsAndAlphabets.setOnClickListener(this)

        binding.btnLearnAlphabetPlay.setOnClickListener(this)
        binding.btnLearnVowelPlay.setOnClickListener(this)
        binding.btnLearnVowelAndAlphabetPlay.setOnClickListener(this)


    }
    // call this function from LevelListener
    fun saveLevelToPreference(itemsList: ArrayList<LevelModel>) {
        val jsonString = Gson().toJson(itemsList)
        if (itemsList.size > 0) {
            SharePreferenceHelper.setSharePreference(this.requireActivity(), Constants.REF_LEVEL_PREFERENCE,jsonString)
        }
    }

    // call this function from LevelListener
    fun successItemsList(itemsList: ArrayList<LevelModel>){
        // Hide the progress dialog.
        hideProgressDialog()

        if (itemsList.size > 0) {
            binding.rvViewItems.visibility = View.VISIBLE
            binding.tvNoItemsFound.visibility = View.GONE
            //binding.rvViewItems.layoutManager = GridLayoutManager(activity, 2)
            binding.rvViewItems.layoutManager = LinearLayoutManager(activity)
            binding.rvViewItems.setHasFixedSize(true)

            val itemAdapter = LevelsFragmentAdapter(requireActivity(), itemsList, "LESSON" , object:
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
            R.id.btn_menu_learn_alphabets -> {
                val intent = Intent(context, LearnAlphabetsActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_menu_learn_vowels -> {
                val intent = Intent(context, LearnVowelsActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_menu_learn_vowels_and_alphabets -> {
                val intent = Intent(context, AlphabetAndSoundActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_sound_title -> speakOut(resources.getString(R.string.menu_main_lesson))

            R.id.btn_learn_alphabet_play -> speakOut(resources.getString(R.string.menu_learn_alphabet))
            R.id.btn_learn_vowel_play -> speakOut(resources.getString(R.string.menu_learn_vowel))
            R.id.btn_learn_vowel_and_alphabet_play -> speakOut(resources.getString(R.string.menu_alphabet_and_vowel))


        }
    }

    public interface OnClickListener {
        fun onClick(currentText: String)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
