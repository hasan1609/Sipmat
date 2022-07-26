package com.sipmat.sipmat.admin.edgblok3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.edgblok3.ScheduleEdgblok3Adapter
import com.sipmat.sipmat.admin.edgblok3.hasil.HasilEdgblok3Activity
import com.sipmat.sipmat.admin.edgblok3.schedule.ScheduleEdgBlok3Activity
import com.sipmat.sipmat.admin.seawater.hasil.HasilSeaWaterActivity
import com.sipmat.sipmat.admin.seawater.schedule.ScheduleSeaWaterActivity
import com.sipmat.sipmat.databinding.ActivityMenuEdgblok3Binding
import com.sipmat.sipmat.databinding.ActivityMenuFfblokBinding
import org.jetbrains.anko.startActivity

class MenuEdgblok3Activity : AppCompatActivity() {
    lateinit var binding : ActivityMenuEdgblok3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_menu_edgblok3)
        binding.lifecycleOwner = this


        binding.btnscheduleffblok.setOnClickListener {
            startActivity<ScheduleEdgBlok3Activity>()
        }

        binding.btnhasilffblok.setOnClickListener {
            startActivity<HasilEdgblok3Activity>()
        }

    }

}