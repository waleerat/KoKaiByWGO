package com.wgoweb.kokaibywgo.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.FragmentHomeBinding
import com.wgoweb.kokaibywgo.ui.activities.learn.AlphabetAndSoundActivity
import com.wgoweb.kokaibywgo.ui.activities.learn.LearnVowelsActivity
import com.wgoweb.kokaibywgo.ui.activities.quiz.QuizAlphabetActivity
import com.wgoweb.kokaibywgo.utils.Constants

class HomeFragment : BaseFragment(), View.OnClickListener {

    private lateinit var binding : FragmentHomeBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)


        binding.btnSpeak.setOnClickListener(this)
        binding.btnLearnAlphabetPlay.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.btnSpeak -> {
                if (binding.etEnteredText.text.isEmpty()) {
                    Toast.makeText(this.requireActivity(), "Enter a text to speak.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    speakOut(binding.etEnteredText.text.toString())
                }
            }

            R.id.btn_learn_alphabet_play -> {
                speakOut(Constants.SUGGEST_TO_TYPE_SENTENCE)
            }
        }
    }


}
