package com.wgoweb.kokaibywgo.ui.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.util.DisplayMetrics
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.DialogCustomBackConfirmationBinding
import com.wgoweb.kokaibywgo.databinding.DialogProgressBinding
import java.io.IOException
import java.util.*
import kotlin.math.roundToInt

open class BaseActivity : AppCompatActivity() , TextToSpeech.OnInitListener{
    // A global variable for double back press feature.
    private var doubleBackToExitPressedOnce = false

    private var mShowingDialog:Boolean = false

    /**
     * Toolbar Activity
     */

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_go_to_dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_go_to_home -> {
            finish()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    /**
     * Set full screen
     */
    @Suppress("DEPRECATION")
    fun setFullScreen(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    /**
     * SnackBar : show bottom of screen
     */
    fun showErrorSnackBar(message: String, errorMessage: Boolean) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view


        if (errorMessage) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.colorSnackBarError
                )
            )
        }else{
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.colorSnackBarSuccess
                )
            )
        }
        snackBar.show()
    }

    /**
     * mProgressDialog  : Pleast wait
     */
    private lateinit var mProgressDialog: Dialog
    private lateinit var dialogBinding : DialogProgressBinding
    fun showProgressDialog(text: String) {
        mShowingDialog = true
        mProgressDialog = Dialog(this)

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/

        dialogBinding = DialogProgressBinding.inflate(layoutInflater)
        mProgressDialog.setContentView(dialogBinding.root)

        dialogBinding.tvProgressText.text = text

        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)

        //Start the dialog and display it on screen.
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        if (mShowingDialog) {
            mProgressDialog.dismiss()
        }
    }

    /**
     * Function is used to launch the custom confirmation dialog.
     */
    private lateinit var confirmBinding : DialogCustomBackConfirmationBinding
    fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        /*Set the screen content from a layout resource.
         The resource will be inflated, adding all top-level views to the screen.*/

        confirmBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(confirmBinding.root)
        //Start the dialog and display it on screen.
        customDialog.show()

        confirmBinding.tvYes.setOnClickListener {
            finish()
            customDialog.dismiss() // Dialog will be dismissed
        }
        confirmBinding.tvNo.setOnClickListener {
            customDialog.dismiss()
        }

    }

    /**
     * A function to implement the double back press feature to exit the app.
     */
    fun doubleBackToExit() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true

        Toast.makeText(
            this,
            resources.getString(R.string.please_click_back_again_to_exit),
            Toast.LENGTH_SHORT
        ).show()

        @Suppress("DEPRECATION")
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }



    /**
     * For Quiz
     */
    @Suppress("DEPRECATION")
    fun setImageSize(image: ImageView, isQuiz: Boolean){
        var imgHeight: Int
        var imgWidth: Int
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels
        val screenWidth = displayMetrics.widthPixels
        if (isQuiz) {
            imgHeight = (screenHeight * 0.30).roundToInt()
            imgWidth = (screenWidth * 0.40).roundToInt()
        } else {
            imgHeight = (screenHeight * 0.80).roundToInt()
            imgWidth = screenWidth
        }

        image.layoutParams.height = imgHeight
        image.layoutParams.width = imgWidth
    }

    /**
     * MediaPlayer
     */
    private lateinit var mediaPlayer: MediaPlayer
    fun playSound(imageName: String){
        try {
            val imageResource = resources.getIdentifier(imageName, "raw", packageName)
            mediaPlayer = MediaPlayer.create(this, imageResource )
            mediaPlayer.start()
        } catch (e: IOException) {
            //Log.i("Error >>", ">> Error read file")
            e.printStackTrace()
        }

    }

     fun stopSound() {
         if (mediaPlayer.isPlaying != null) {
             mediaPlayer.stop(); // or mp.pause();
             mediaPlayer.release();
         }
    }


    /**
     * This the TextToSpeech override function
     *
     * Called to signal the completion of the TextToSpeech engine initialization.
     */

    private var tts: TextToSpeech? = null // Variable for TextToSpeech
    @Suppress("DEPRECATION")

    override fun onResume() {
        super.onResume()
        // Initialize the Text To Speech
        tts = TextToSpeech(this, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            //val result = tts!!.setLanguage(Locale.US)
            val result = tts!!.setLanguage(Locale.forLanguageTag("TH"))
            tts!!.setSpeechRate(0.8f)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }
        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    /**
     * Here is Destroy function we will stop and shutdown the tts which is initialized above.
     */
    public override fun onDestroy() {
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

    /**
     * Function is used to speak the text what we pass to it.
     */
     fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

}