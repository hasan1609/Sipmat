package com.sipmat.sipmat.admin.ffblok2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sipmat.sipmat.R
import com.sipmat.sipmat.admin.ffblok2.hasil.HasilFFblok2Activity
import com.sipmat.sipmat.admin.ffblok2.schedule.ScheduleFFBlok2Activity
import com.sipmat.sipmat.admin.seawater.hasil.HasilSeaWaterActivity
import com.sipmat.sipmat.admin.seawater.schedule.ScheduleSeaWaterActivity
import com.sipmat.sipmat.databinding.ActivityMenuFfblok2Binding
import com.sipmat.sipmat.databinding.ActivityMenuFfblokBinding
import org.jetbrains.anko.startActivity

class MenuFFblok2Activity : AppCompatActivity() {
    lateinit var binding : ActivityMenuFfblok2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_menu_ffblok2)
        binding.lifecycleOwner = this


        binding.btnscheduleffblok.setOnClickListener {
            startActivity<ScheduleFFBlok2Activity>()
        }

        binding.btnhasilffblok.setOnClickListener {
            startActivity<HasilFFblok2Activity>()
        }

    }

}