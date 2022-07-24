package com.sipmat.sipmat.admin.ui.ARSIP

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sipmat.sipmat.databinding.FragmentArsipBinding
import com.sipmat.sipmat.R
class ArsipFragment : Fragment() {

    //copy 1
    lateinit var binding : FragmentArsipBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_arsip,container,false)
        binding.lifecycleOwner


        //copy3
        return  binding.root
    }


}