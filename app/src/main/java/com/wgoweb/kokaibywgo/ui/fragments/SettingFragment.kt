package com.wgoweb.kokaibywgo.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.SupportMapFragment
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private var _fragmentBinding: FragmentSettingBinding? = null
    private val fragmentBinding get() = _fragmentBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _fragmentBinding = FragmentSettingBinding.inflate(inflater, container, false)
        return  fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Code here
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentBinding = null
    }

}