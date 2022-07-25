package com.sipmat.sipmat.admin.edgblok2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sipmat.sipmat.R
import com.sipmat.sipmat.admin.edgblok2.hasil.HasilEdgblok2Activity
import com.sipmat.sipmat.admin.edgblok2.schedule.ScheduleEdgBlok2Activity
import com.sipmat.sipmat.admin.seawater.hasil.HasilSeaWaterActivity
import com.sipmat.sipmat.admin.seawater.schedule.ScheduleSeaWaterActivity
import com.sipmat.sipmat.databinding.ActivityMenuEdgblok2Binding
import com.sipmat.sipmat.databinding.ActivityMenuFfblokBinding
import org.jetbrains.anko.startActivity

class MenuEdgblok2Activity : AppCompatActivity() {
    lateinit var binding : ActivityMenuEdgblok2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_menu_edgblok2)
        binding.lifecycleOwner = this


        binding.btnscheduleffblok.setOnClickListener {
            startActivity<ScheduleEdgBlok2Activity>()
        }

        binding.btnhasilffblok.setOnClickListener {
            startActivity<HasilEdgblok2Activity>()
        }

    }

}