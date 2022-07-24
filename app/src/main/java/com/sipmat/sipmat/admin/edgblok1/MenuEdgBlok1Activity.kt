package com.sipmat.sipmat.admin.edgblok1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.edgblok1.ScheduleEdgblok1Adapter
import com.sipmat.sipmat.admin.edgblok1.hasil.HasilEdgblok1Activity
import com.sipmat.sipmat.admin.edgblok1.schedule.ScheduleEdgBlok1Activity
import com.sipmat.sipmat.admin.seawater.hasil.HasilSeaWaterActivity
import com.sipmat.sipmat.admin.seawater.schedule.ScheduleSeaWaterActivity
import com.sipmat.sipmat.databinding.ActivityMenuEdgblok1Binding
import org.jetbrains.anko.startActivity

class MenuEdgBlok1Activity : AppCompatActivity() {
    lateinit var binding : ActivityMenuEdgblok1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_menu_edgblok1)
        binding.lifecycleOwner = this


        binding.btnscheduleffblok.setOnClickListener {
            startActivity<ScheduleEdgBlok1Activity>()
        }

        binding.btnhasilffblok.setOnClickListener {
            startActivity<HasilEdgblok1Activity>()
        }

    }

}