package com.sipmat.sipmat.admin.ffblok1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sipmat.sipmat.R
import com.sipmat.sipmat.admin.ffblok1.hasil.HasilFFblokActivity
import com.sipmat.sipmat.admin.ffblok1.schedule.ScheduleFFBlokActivity
import com.sipmat.sipmat.admin.seawater.hasil.HasilSeaWaterActivity
import com.sipmat.sipmat.admin.seawater.schedule.ScheduleSeaWaterActivity
import com.sipmat.sipmat.databinding.ActivityMenuFfblokBinding
import org.jetbrains.anko.startActivity

class MenuFFblokActivity : AppCompatActivity() {
    lateinit var binding : ActivityMenuFfblokBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_menu_ffblok)
        binding.lifecycleOwner = this


        binding.btnscheduleffblok.setOnClickListener {
            startActivity<ScheduleFFBlokActivity>()
        }

        binding.btnhasilffblok.setOnClickListener {
            startActivity<HasilFFblokActivity>()
        }

    }

}