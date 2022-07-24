package com.sipmat.sipmat.admin.pencahayaan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sipmat.sipmat.R
import com.sipmat.sipmat.admin.pencahayaan.hasil.HasilPencahayaanActivity
import com.sipmat.sipmat.admin.pencahayaan.item.ItemPencahayaanActivity
import com.sipmat.sipmat.admin.pencahayaan.schedule.SchedulePencahayaanActivity
import com.sipmat.sipmat.databinding.ActivityMenuPencahayaanBinding

import org.jetbrains.anko.startActivity

class MenuPencahayaanActivity : AppCompatActivity() {
    lateinit var binding : ActivityMenuPencahayaanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_menu_pencahayaan)
        binding.lifecycleOwner = this

        binding.btnitempencahayaan.setOnClickListener {
            startActivity<ItemPencahayaanActivity>()
        }

        binding.btnschedulepencahayaan.setOnClickListener {
            startActivity<SchedulePencahayaanActivity>()
        }

        binding.btnhasilpencahayaan.setOnClickListener {
            startActivity<HasilPencahayaanActivity>()
        }

    }

}