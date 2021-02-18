package com.wgoweb.kokaibywgo.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wgoweb.kokaibywgo.databinding.ActivityResultBinding
import com.wgoweb.kokaibywgo.utils.Constants

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // val username = intent.getStringExtra(Constants.USER_NAME)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        val totalQuestion = intent.getIntExtra(Constants.TOTAL_QUESTION, 0)

        //binding.tvName.text = username
        binding.tvScore.text = "Your Score is ${correctAnswers.toString()} out of ${totalQuestion.toString()}"

        binding.bntGoHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        binding.bntPlayAgain.setOnClickListener {
            onBackPressed()
        }
    }
}