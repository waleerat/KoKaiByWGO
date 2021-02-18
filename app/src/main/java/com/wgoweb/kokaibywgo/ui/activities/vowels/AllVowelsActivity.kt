package com.wgoweb.kokaibywgo.ui.activities.vowels

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.ActivityAllVowelsBinding
import com.wgoweb.kokaibywgo.models.VowelModel
import com.wgoweb.kokaibywgo.ui.activities.BaseActivity
import com.wgoweb.kokaibywgo.utils.Constants
import java.util.ArrayList

class AllVowelsActivity : BaseActivity() {
    private lateinit var binding: ActivityAllVowelsBinding

    private lateinit var mVowelItems: ArrayList<VowelModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAllVowelsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        getRowsData()
    }


    private fun getRowsData() {
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        mVowelItems = Constants.getVowelItems(this)  // Get All Items
        // Hide Progress dialog.
        hideProgressDialog()

        if (mVowelItems.size > 0) {
            binding.rvDataItems.visibility = View.VISIBLE
            binding.tvNoItemsFound.visibility = View.GONE

            // binding!!.rvDataItems.layoutManager = LinearLayoutManager(this)
            // binding!!.rvDataItems.setHasFixedSize(true)

            val orientation = resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // In landscape
                binding.rvDataItems.layoutManager = GridLayoutManager(this, 5)
            } else {
                // In portrait
                binding.rvDataItems.layoutManager = GridLayoutManager(this, 3)
            }

            val itemAdapter = AllVowelsAdapter(this@AllVowelsActivity, mVowelItems)
            // adapter instance is set to the recyclerview to inflate the items.
            binding.rvDataItems.adapter = itemAdapter


        } else {
            binding.rvDataItems.visibility = View.GONE
            binding.tvNoItemsFound.visibility = View.VISIBLE
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarCustom)
        binding.tvTitle.text = "All Vowels"

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_topbar_back_arrow)
        }
        binding.toolbarCustom.setNavigationOnClickListener {
            onBackPressed()
        }
    }

}