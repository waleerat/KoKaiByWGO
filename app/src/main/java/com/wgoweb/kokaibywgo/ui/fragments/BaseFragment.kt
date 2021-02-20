package com.wgoweb.kokaibywgo.ui.fragments

import android.app.Dialog
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.DialogProgressBinding
import java.util.*


open class BaseFragment : Fragment() , TextToSpeech.OnInitListener{

    private lateinit var dialogBinding : DialogProgressBinding

    private lateinit var mProgressDialog: Dialog

    private var mShowingDialog:Boolean = false

    fun showProgressDialog(text: String) {
        mShowingDialog = true
        mProgressDialog = Dialog(requireActivity())

        dialogBinding = DialogProgressBinding.inflate(layoutInflater)
        mProgressDialog.setContentView(dialogBinding.root)

        dialogBinding.tvProgressText.text = text

        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        if (mShowingDialog) {
            mProgressDialog.dismiss()
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
        tts = TextToSpeech(requireActivity(), this)
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