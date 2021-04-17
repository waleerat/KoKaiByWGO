package com.wgoweb.kokaibywgo.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.FragmentHomeBinding
import com.wgoweb.kokaibywgo.databinding.FragmentQuizBinding
import com.wgoweb.kokaibywgo.ui.activities.sounds.AlphabetBySoundActivity
import com.wgoweb.kokaibywgo.utils.Constants

class HomeFragment : BaseFragment(), View.OnClickListener {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSoundTitle.setOnClickListener(this)
        binding.btnSoundLowPlay.setOnClickListener(this)
        binding.btnSoundHighPlay.setOnClickListener(this)
        binding.btnSoundMiddlePlay.setOnClickListener(this)

        binding.btnShowHighSoundAlphabet.setOnClickListener(this)
        binding.btnShowMiddleSoundAlphabet.setOnClickListener(this)
        binding.btnShowLowSoundAlphabet.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.btn_sound_title -> speakOut(resources.getString(R.string.menu_main_home))
            R.id.btn_sound_high_play -> speakOut(resources.getString(R.string.menu_sound_height))
            R.id.btn_sound_middle_play -> speakOut(resources.getString(R.string.menu_sound_middle))
            R.id.btn_sound_low_play -> speakOut(resources.getString(R.string.menu_sound_low))

            R.id.btn_show_high_sound_alphabet -> {
                val intent = Intent(context, AlphabetBySoundActivity::class.java)
                intent.putExtra(Constants.INTENT_SOUND_LEVEL, "high")  //HIGH
                intent.putExtra(Constants.INTENT_SOUND_LEVEL_NAME, resources.getString(R.string.menu_sound_height))
                startActivity(intent)
            }

            R.id.btn_show_middle_sound_alphabet -> {
                val intent = Intent(context, AlphabetBySoundActivity::class.java)
                intent.putExtra(Constants.INTENT_SOUND_LEVEL, "mid") // MIDDLE
                intent.putExtra(Constants.INTENT_SOUND_LEVEL_NAME, resources.getString(R.string.menu_sound_middle))
                startActivity(intent)
            }

            R.id.btn_show_low_sound_alphabet -> {
                val intent = Intent(context, AlphabetBySoundActivity::class.java)
                intent.putExtra(Constants.INTENT_SOUND_LEVEL, "low") //LOW
                intent.putExtra(Constants.INTENT_SOUND_LEVEL_NAME, resources.getString(R.string.menu_sound_low))
                startActivity(intent)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
