package com.wgoweb.kokaibywgo.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.FragmentHomeBinding
import com.wgoweb.kokaibywgo.ui.activities.sounds.AlphabetBySoundActivity
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


        binding.btnSoundLowPlay.setOnClickListener(this)
        binding.btnSoundHighPlay.setOnClickListener(this)
        binding.btnSoundMiddlePlay.setOnClickListener(this)

        binding.btnShowHighSoundAlphabet.setOnClickListener(this)
        binding.btnShowMiddleSoundAlphabet.setOnClickListener(this)
        binding.btnShowLowSoundAlphabet.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.btn_sound_high_play -> speakOut(Constants.SOUND_HIGH_TEXT)
            R.id.btn_sound_middle_play -> speakOut(Constants.SOUND_MIDDLE_TEXT)
            R.id.btn_sound_low_play -> speakOut(Constants.SOUND_LOW_TEXT)

            R.id.btn_show_high_sound_alphabet -> {
                val intent = Intent(context, AlphabetBySoundActivity::class.java)
                intent.putExtra(Constants.INTENT_SOUND_LEVEL, "high")  //HIGH
                intent.putExtra(Constants.INTENT_SOUND_LEVEL_NAME, Constants.SOUND_HIGH_TEXT)
                startActivity(intent)
            }

            R.id.btn_show_middle_sound_alphabet -> {
                val intent = Intent(context, AlphabetBySoundActivity::class.java)
                intent.putExtra(Constants.INTENT_SOUND_LEVEL, "mid") // MIDDLE
                intent.putExtra(Constants.INTENT_SOUND_LEVEL_NAME, Constants.SOUND_MIDDLE_TEXT)
                startActivity(intent)
            }

            R.id.btn_show_low_sound_alphabet -> {
                val intent = Intent(context, AlphabetBySoundActivity::class.java)
                intent.putExtra(Constants.INTENT_SOUND_LEVEL, "low") //LOW
                intent.putExtra(Constants.INTENT_SOUND_LEVEL_NAME, Constants.SOUND_LOW_TEXT)
                startActivity(intent)
            }
        }
    }


}
