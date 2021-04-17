package com.wgoweb.kokaibywgo.ui.activities.sounds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.ActivitySoundFromSentenceBinding
import com.wgoweb.kokaibywgo.ui.activities.BaseActivity
import com.wgoweb.kokaibywgo.utils.Constants

class SoundFromSentenceActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySoundFromSentenceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySoundFromSentenceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSpeak.setOnClickListener(this)
        binding.btnLearnAlphabetPlay.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.btnSpeak -> {
                if (binding.etEnteredText.text.isEmpty()) {
                    Toast.makeText(this, "Enter a text to speak.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    speakOut(binding.etEnteredText.text.toString())
                }
            }

            R.id.btn_learn_alphabet_play -> {
                speakOut(resources.getString(R.string.speech_title))
            }
        }
    }


}
