package com.wgoweb.kokaibywgo.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.FragmentHomeBinding
import com.wgoweb.kokaibywgo.ui.activities.learn.AlphabetAndSoundActivity

class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentHomeBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        binding.btnAlphabetAndSound.setOnClickListener(this)
        // Code here
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.btn_alphabet_and_sound -> {
                val intent = Intent(context, AlphabetAndSoundActivity::class.java)
                startActivity(intent)
            }
        }
    }


}
