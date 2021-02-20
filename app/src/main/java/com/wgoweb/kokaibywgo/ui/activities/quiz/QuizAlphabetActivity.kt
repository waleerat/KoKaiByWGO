package com.wgoweb.kokaibywgo.ui.activities.quiz

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.ActivityQuizAlphabetBinding
import com.wgoweb.kokaibywgo.models.AlphabetModel
import com.wgoweb.kokaibywgo.ui.activities.BaseActivity
import com.wgoweb.kokaibywgo.ui.activities.ResultActivity
import com.wgoweb.kokaibywgo.utils.Constants

class QuizAlphabetActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityQuizAlphabetBinding

    private lateinit var mAlphabetItems: ArrayList<AlphabetModel>
    private lateinit var mQuizChoices : ArrayList<Int>
    private  var mQuizAnswer: Int = 0

    private val mAmountOfRows: Int = 44
    private var mMaxQuiz: Int = 5
    private var mCurrentPostition: Int = 1
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswer: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuizAlphabetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        mAlphabetItems = Constants.getAlphabetItems(this)  // Get All Items

        binding.choiceOne.setOnClickListener(this)
        binding.choiceTwo.setOnClickListener(this)
        binding.choiceThree.setOnClickListener(this)
        binding.choiceFour.setOnClickListener(this)
        binding.replayAnswer.setOnClickListener(this)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        mMaxQuiz = Constants.MAX_QUIZ_FOR_ALPHABETS
        mCurrentPostition = 1
        mSelectedOptionPosition = 0
        mCorrectAnswer = 0
        if (mAlphabetItems.size > 0) {
            loadQuiz()
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    @Suppress("DEPRECATION")
    private fun loadQuiz(){
        binding.progressBar.progress = mCurrentPostition

        binding.progressBar.max = mMaxQuiz
        binding.textViewProgressBar.text = "$mCurrentPostition" + "/" + binding.progressBar.max

        mQuizChoices = Constants.generateChoices(mAmountOfRows)  // Get Choices
        mQuizAnswer = Constants.getAnswer()  // Int 1 to 4
        setQuizChoiceToLayout()

        if (mCurrentPostition == 1 ) {
            Handler().postDelayed(
                {
                    playSound( mAlphabetItems[mQuizChoices[mQuizAnswer]].sound)
                },
                1000 )
        } else {
            playSound( mAlphabetItems[mQuizChoices[mQuizAnswer]].sound)
        }

    }



    @RequiresApi(Build.VERSION_CODES.M)
    private fun setQuizChoiceToLayout() {
        //Log.i("Choices Log >>",  mAlphabetItems[mQuizChoices[mQuizAnswer]].vowelThai + " " + mQuizAnswer)

        for ((choiceNumber, row) in mQuizChoices.withIndex()) {

            borderDefaultView()

            when(choiceNumber) {
                0 -> {
                    binding.choiceOne.setImageResource(mAlphabetItems[row].image)
                    setImageSize(binding.choiceOne, true)
                }
                1 -> {
                    binding.choiceTwo.setImageResource(mAlphabetItems[row].image)
                    setImageSize(binding.choiceTwo, true)
                }
                2 -> {
                    binding.choiceThree.setImageResource(mAlphabetItems[row].image)
                    setImageSize(binding.choiceThree, true)
                }
                3 -> {
                    binding.choiceFour.setImageResource(mAlphabetItems[row].image)
                    setImageSize(binding.choiceFour, true)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.choice_one -> {
                checkAnswer(0)
            }
            R.id.choice_two -> {
                checkAnswer(1)
            }
            R.id.choice_three -> {
                checkAnswer(2)
            }
            R.id.choice_four -> {
                checkAnswer(3)
            }

            R.id.replay_answer -> {
                playSound( mAlphabetItems[mQuizChoices[mQuizAnswer]].sound)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @Suppress("DEPRECATION")
    private fun checkAnswer(selectedChoice: Int){
        borderCorrectAnswerView(mQuizAnswer)

        if (selectedChoice == mQuizAnswer ) {
            mCorrectAnswer++
            playSound( "sound_correct")
            // showErrorSnackBar("You have Correct" + mCorrectAnswer.toString() + " Answer" + mAlphabetItems[mQuizChoices[mQuizAnswer]].vowelThai, false)
        } else {
            playSound( "sound_incorrect")
            borderIncorrectAnswerView(selectedChoice)
            //showErrorSnackBar("You have Correct" + mCorrectAnswer.toString() + " Answer" + mAlphabetItems[mQuizChoices[mQuizAnswer]].vowelThai, true)
        }

        mCurrentPostition++

        Handler().postDelayed(
            {

                if (mCurrentPostition <= mMaxQuiz) {
                    loadQuiz()
                } else {
                    val intent = Intent(this, ResultActivity::class.java)
                    //intent.putExtra(Constants.USER_NAME, mUserName)
                    intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswer)
                    intent.putExtra(Constants.TOTAL_QUESTION, mMaxQuiz)
                    startActivity(intent)
                }
            },
            2500
        )

    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun borderCorrectAnswerView(answer: Int) {
        when(answer) {
            0 -> {
                binding.choiceOne.foreground = ContextCompat.getDrawable(this, R.drawable.option_border_correct_bg)
            }
            1 -> {
                binding.choiceTwo.foreground = ContextCompat.getDrawable(this, R.drawable.option_border_correct_bg)
            }
            2 -> {
                binding.choiceThree.foreground = ContextCompat.getDrawable(this, R.drawable.option_border_correct_bg)
            }
            3 -> {
                binding.choiceFour.foreground = ContextCompat.getDrawable(this, R.drawable.option_border_correct_bg)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun borderIncorrectAnswerView(selectedChoice: Int) {
        when(selectedChoice) {
            0 -> {
                binding.choiceOne.foreground = ContextCompat.getDrawable(this, R.drawable.option_border_wrong_bg)
            }
            1 -> {
                binding.choiceTwo.foreground = ContextCompat.getDrawable(this, R.drawable.option_border_wrong_bg)
            }
            2 -> {
                binding.choiceThree.foreground = ContextCompat.getDrawable(this, R.drawable.option_border_wrong_bg)
            }
            3 -> {
                binding.choiceFour.foreground = ContextCompat.getDrawable(this, R.drawable.option_border_wrong_bg)
            }
        }
    }




    @RequiresApi(Build.VERSION_CODES.M)
    private fun borderDefaultView(){
        val options = ArrayList<ImageView>()
        options.add(0, binding.choiceOne)
        options.add(1, binding.choiceTwo)
        options.add(2, binding.choiceThree)
        options.add(3, binding.choiceFour)

        for (option in options){
            option.foreground = ContextCompat.getDrawable(this, R.drawable.option_border_default_bg)
        }
    }


    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarCustom)
        binding.tvTitle.text = "Alphabet Quiz"

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_topbar_back_arrow)
        }
        binding.toolbarCustom.setNavigationOnClickListener {
            customDialogForBackButton()
        }
    }
}