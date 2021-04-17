package com.wgoweb.kokaibywgo.ui.activities.learn

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.ActivityAllAlphabetsBinding
import com.wgoweb.kokaibywgo.models.AlphabetModel
import com.wgoweb.kokaibywgo.ui.activities.BaseActivity
import com.wgoweb.kokaibywgo.ui.activities.adapters.AllAlphabetsAdapter
import com.wgoweb.kokaibywgo.utils.Constants
import java.util.*

class AllAlphabetsActivity : BaseActivity() {
    private lateinit var binding: ActivityAllAlphabetsBinding
    private lateinit var mAlphabetItems: ArrayList<AlphabetModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAllAlphabetsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        getRowsData()
    }


    private fun getRowsData() {
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        mAlphabetItems = Constants.getAlphabetItems(this)  // Get All Items
        // Hide Progress dialog.
        hideProgressDialog()

        if (mAlphabetItems.size > 0) {
            binding.rvAlphabetDataItems.visibility = View.VISIBLE
            binding.tvAlphabetNoItemsFound.visibility = View.GONE

           // binding!!.rvDataItems.layoutManager = LinearLayoutManager(this)
           // binding!!.rvDataItems.setHasFixedSize(true)

            val orientation = resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // In landscape
                binding.rvAlphabetDataItems.layoutManager = GridLayoutManager(this, 5)
            } else {
                // In portrait
                binding.rvAlphabetDataItems.layoutManager = GridLayoutManager(this, 3)
            }

            val itemAdapter = AllAlphabetsAdapter(this@AllAlphabetsActivity, mAlphabetItems)
            // adapter instance is set to the recyclerview to inflate the items.
            binding.rvAlphabetDataItems.adapter = itemAdapter
        } else {
            binding.rvAlphabetDataItems.visibility = View.GONE
            binding.tvAlphabetNoItemsFound.visibility = View.VISIBLE
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarCustom)
        binding.tvAlphabetTitle.text =  resources.getString(R.string.menu_learn_alphabet)

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