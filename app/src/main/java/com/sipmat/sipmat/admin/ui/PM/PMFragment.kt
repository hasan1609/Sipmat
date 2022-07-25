package com.sipmat.sipmat.admin.ui.PM

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sipmat.sipmat.admin.apar.AparActivity
import com.sipmat.sipmat.R
import com.sipmat.sipmat.admin.apat.ApatActivity
import com.sipmat.sipmat.admin.damkar.MenuDamkarActivity
import com.sipmat.sipmat.admin.edgblok1.MenuEdgBlok1Activity
import com.sipmat.sipmat.admin.edgblok2.MenuEdgblok2Activity
import com.sipmat.sipmat.admin.ffblok1.MenuFFblokActivity
import com.sipmat.sipmat.admin.ffblok2.MenuFFblok2Activity
import com.sipmat.sipmat.admin.hydrant.item.MenuHydrantActivity
import com.sipmat.sipmat.admin.kebisingan.MenuKebisinganActivity
import com.sipmat.sipmat.admin.pencahayaan.MenuPencahayaanActivity
import com.sipmat.sipmat.admin.seawater.MenuSeaWaterActivity
import com.sipmat.sipmat.databinding.FragmentPmBinding
import com.sipmat.sipmat.pelaksana.ffblokpelaksana.CekFFblokActivity
import org.jetbrains.anko.support.v4.startActivity

class PMFragment : Fragment() {
    //copy 1
    lateinit var binding : FragmentPmBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pm,container,false)
        binding.lifecycleOwner

        binding.txtapar.setOnClickListener {
            startActivity<AparActivity>()
        }
        binding.txtapat.setOnClickListener {
            startActivity<ApatActivity>()
        }

        binding.txthydrant.setOnClickListener {
            startActivity<MenuHydrantActivity>()
        }

        binding.txtkebisingan.setOnClickListener {
            startActivity<MenuKebisinganActivity>()
        }

        binding.txtpencahayaan.setOnClickListener {
            startActivity<MenuPencahayaanActivity>()
        }


        binding.txtseawater.setOnClickListener {
            startActivity<MenuSeaWaterActivity>()
        }

        binding.txtffblok1.setOnClickListener {
            startActivity<MenuFFblokActivity>()
        }

        binding.txtfirefightingblok2.setOnClickListener {
            startActivity<MenuFFblok2Activity>()
        }

        binding.txtedgblok1.setOnClickListener {
            startActivity<MenuEdgBlok1Activity>()
        }
        binding.txtedgblok2.setOnClickListener {
            startActivity<MenuEdgblok2Activity>()
        }
//        binding.txtedgblok3.setOnClickListener {
//            startActivity<MenuEdgBlok2Activity>()
//        }
        binding.txtmobildamkar.setOnClickListener {
            startActivity<MenuDamkarActivity>()
        }

        //copy3
        return  binding.root
    }
}