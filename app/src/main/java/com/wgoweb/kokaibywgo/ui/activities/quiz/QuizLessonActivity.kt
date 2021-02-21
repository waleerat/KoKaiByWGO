package com.wgoweb.kokaibywgo.ui.activities.quiz

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.ActivityQuizLessonBinding
import com.wgoweb.kokaibywgo.firebase.SentenceListener
import com.wgoweb.kokaibywgo.models.AlphabetModel
import com.wgoweb.kokaibywgo.models.SentenceModel
import com.wgoweb.kokaibywgo.ui.activities.BaseActivity
import com.wgoweb.kokaibywgo.ui.activities.ResultActivity
import com.wgoweb.kokaibywgo.utils.Constants
import com.wgoweb.kokaibywgo.utils.SharePreferenceHelper

class QuizLessonActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityQuizLessonBinding

    private lateinit var mSentenceItems: ArrayList<SentenceModel>
    private lateinit var mQuizChoices : ArrayList<Int>
    private  var mQuizAnswer: Int = 0
    private var isEmptyQuiz: Boolean = true

    private val mAmountOfRows: Int = 44
    private var mMaxQuiz: Int = 5
    private var mCurrentPostition: Int = 1
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswer: Int = 0

    var mLevelId: String = ""
    var mLevelName: String = ""

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuizLessonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        if (intent.hasExtra(Constants.INTENT_LEVEL_ID)) {
            mLevelId = intent.getStringExtra(Constants.INTENT_LEVEL_ID)!!
            Constants.LEVEL_ID = mLevelId
        }
        if (intent.hasExtra(Constants.INTENT_LEVEL_NAME)) {
            mLevelName = intent.getStringExtra(Constants.INTENT_LEVEL_NAME)!!
            //binding.activityTitle.text = mLevelName
        }

        mSentenceItems = ArrayList<SentenceModel>() // Hide the progress dialog.

        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        SentenceListener().getDataListItemForQuizActivity(this@QuizLessonActivity)
        // Hide the progress dialog.
        hideProgressDialog()
        binding.choiceOne.setOnClickListener(this)
        binding.choiceTwo.setOnClickListener(this)
        binding.choiceThree.setOnClickListener(this)
        binding.choiceFour.setOnClickListener(this)
        binding.playAnswer.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        mMaxQuiz = Constants.MAX_QUIZ_FOR_ALPHABETS
        mCurrentPostition = 1
        mSelectedOptionPosition = 0
        mCorrectAnswer = 0
        if (mSentenceItems.size > 0) {
            isEmptyQuiz = false
            loadQuiz()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun successItemsList(itemsList: ArrayList<SentenceModel>){
        mSentenceItems = itemsList

        if (mSentenceItems.size == 0 ) {
            isEmptyQuiz = true
            showErrorSnackBar(Constants.ERROR_EMPTY_QUIZ, true)
            binding.parentQuizLesson.visibility = View.GONE
            finish()
        } else {
            isEmptyQuiz = false
            loadQuiz()
            binding.parentQuizLesson.visibility = View.VISIBLE
        }
    }

    // call this function from SectionListener
    fun saveSentenceToPreference(itemsList: java.util.ArrayList<SentenceModel>) {
        if (itemsList.size > 0) {
            val jsonString = Gson().toJson(itemsList)
            SharePreferenceHelper().setSharePreference(this@QuizLessonActivity, Constants.REF_SENTENCE_PREFERENCE,jsonString )
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    @Suppress("DEPRECATION")
    private fun loadQuiz(){
        if (!isEmptyQuiz) {
            binding.progressBar.progress = mCurrentPostition

            binding.progressBar.max = mMaxQuiz
            binding.textViewProgressBar.text = "$mCurrentPostition" + "/" + binding.progressBar.max

            mQuizChoices = Constants.generateChoices(mAmountOfRows)  // Get Choices
            mQuizAnswer = Constants.getAnswer()  // Int 1 to 4
            setQuizChoiceToLayout()

            Handler().postDelayed(
                {
                    speakOut(mSentenceItems[mQuizChoices[mQuizAnswer]].sentence_text)

                },
                1000 )
        }
    }



    @RequiresApi(Build.VERSION_CODES.M)
    private fun setQuizChoiceToLayout() {
        //Log.i("Choices Log >>",  mSentenceItems[mQuizChoices[mQuizAnswer]].vowelThai + " " + mQuizAnswer)

        for ((choiceNumber, row) in mQuizChoices.withIndex()) {

            borderDefaultView()

            when(choiceNumber) {
                0 -> {
                    binding.choiceOne.text = mSentenceItems[row].sentence_text
                }
                1 -> {
                    binding.choiceTwo.text = mSentenceItems[row].sentence_text
                }
                2 -> {
                    binding.choiceThree.text = mSentenceItems[row].sentence_text
                }
                3 -> {
                    binding.choiceFour.text = mSentenceItems[row].sentence_text
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

            R.id.playAnswer -> {
                speakOut(mSentenceItems[mQuizChoices[mQuizAnswer]].sentence_text)
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
            // showErrorSnackBar("You have Correct" + mCorrectAnswer.toString() + " Answer" + mSentenceItems[mQuizChoices[mQuizAnswer]].vowelThai, false)
        } else {
            playSound( "sound_incorrect")
            borderIncorrectAnswerView(selectedChoice)
            //showErrorSnackBar("You have Correct" + mCorrectAnswer.toString() + " Answer" + mSentenceItems[mQuizChoices[mQuizAnswer]].vowelThai, true)
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
        val options = ArrayList<TextView>()
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