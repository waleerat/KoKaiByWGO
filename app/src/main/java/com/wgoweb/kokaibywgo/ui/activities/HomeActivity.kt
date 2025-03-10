package com.wgoweb.kokaibywgo.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.ActivityHomeBinding
import com.wgoweb.kokaibywgo.ui.activities.learn.AllAlphabetsActivity
import com.wgoweb.kokaibywgo.ui.activities.learn.LearnAlphabetsActivity
import com.wgoweb.kokaibywgo.ui.activities.quiz.QuizAlphabetActivity
import com.wgoweb.kokaibywgo.ui.activities.learn.AllVowelsActivity
import com.wgoweb.kokaibywgo.ui.activities.learn.LearnVowelsActivity
import com.wgoweb.kokaibywgo.ui.activities.quiz.QuizVowelActivity

class HomeActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMenuLearnAlphabets.setOnClickListener(this)
        binding.btnMenuAllAlphabets.setOnClickListener(this)
        binding.btnMenuQuizAlphabets.setOnClickListener(this)

        binding.btnMenuLearnVowels.setOnClickListener(this)
        binding.btnMenuAllVowels.setOnClickListener(this)
        binding.btnMenuQuizVowels.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            /**--Alphabets --*/
            R.id.btn_menu_learn_alphabets -> {
                val intent = Intent(this@HomeActivity, LearnAlphabetsActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_menu_all_alphabets -> {
                val intent = Intent(this@HomeActivity, AllAlphabetsActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_menu_quiz_alphabets -> {
                val intent = Intent(this@HomeActivity, QuizAlphabetActivity::class.java)
                startActivity(intent)
            }
            /**--Vowels --*/
            R.id.btn_menu_learn_vowels -> {
                val intent = Intent(this@HomeActivity, LearnVowelsActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_menu_quiz_vowels -> {
                val intent = Intent(this@HomeActivity, QuizVowelActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_menu_all_vowels -> {
                val intent = Intent(this@HomeActivity, AllVowelsActivity::class.java)
                startActivity(intent)
            }

        }
    }

    override fun onBackPressed() {
        doubleBackToExit()
    }

}