package com.sipmat.sipmat.admin.damkar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sipmat.sipmat.R
import com.sipmat.sipmat.admin.seawater.hasil.HasilSeaWaterActivity
import com.sipmat.sipmat.admin.seawater.schedule.ScheduleSeaWaterActivity
import com.sipmat.sipmat.databinding.ActivityMenuFfblokBinding
import org.jetbrains.anko.startActivity

class MenuDamkarActivity : AppCompatActivity() {
    lateinit var binding : ActivityMenuFfblokBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_menu_ffblok2)
        binding.lifecycleOwner = this


        binding.btnscheduleffblok.setOnClickListener {
            startActivity<ScheduleSeaWaterActivity>()
        }

        binding.btnhasilffblok.setOnClickListener {
            startActivity<HasilSeaWaterActivity>()
        }

    }

}