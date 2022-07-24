package com.sipmat.sipmat.admin.seawater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sipmat.sipmat.R
import com.sipmat.sipmat.admin.seawater.hasil.HasilSeaWaterActivity
import com.sipmat.sipmat.admin.seawater.schedule.ScheduleSeaWaterActivity
import com.sipmat.sipmat.databinding.ActivityMenuSeaWaterBinding

import org.jetbrains.anko.startActivity

class MenuSeaWaterActivity : AppCompatActivity() {
    lateinit var binding : ActivityMenuSeaWaterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_menu_sea_water)
        binding.lifecycleOwner = this

        binding.btnscheduleseawater.setOnClickListener {
            startActivity<ScheduleSeaWaterActivity>()
        }

        binding.btnhasilseawater.setOnClickListener {
            startActivity<HasilSeaWaterActivity>()
        }

    }

}