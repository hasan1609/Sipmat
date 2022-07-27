package com.sipmat.sipmat.admin.ambulance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sipmat.sipmat.R
import com.sipmat.sipmat.admin.ambulance.hasil.HasilAmbulanceActivity
import com.sipmat.sipmat.admin.ambulance.schedule.ScheduleAmbulanceActivity
import com.sipmat.sipmat.admin.damkar.hasil.HasilDamkarActivity
import com.sipmat.sipmat.admin.damkar.schedule.ScheduleDamkarActivity
import com.sipmat.sipmat.admin.seawater.hasil.HasilSeaWaterActivity
import com.sipmat.sipmat.admin.seawater.schedule.ScheduleSeaWaterActivity
import com.sipmat.sipmat.databinding.ActivityMenuAmbulanceBinding
import com.sipmat.sipmat.databinding.ActivityMenuDamkarBinding
import com.sipmat.sipmat.databinding.ActivityMenuFfblokBinding
import org.jetbrains.anko.startActivity

class MenuAmbulanceActivity : AppCompatActivity() {
    lateinit var binding : ActivityMenuAmbulanceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_menu_ambulance)
        binding.lifecycleOwner = this


        binding.btnscheduleffblok.setOnClickListener {
            startActivity<ScheduleAmbulanceActivity>()
        }

        binding.btnhasilffblok.setOnClickListener {
            startActivity<HasilAmbulanceActivity>()
        }

    }

}