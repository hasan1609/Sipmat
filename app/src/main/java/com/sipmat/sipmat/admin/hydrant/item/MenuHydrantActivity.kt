package com.sipmat.sipmat.admin.hydrant.item

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sipmat.sipmat.R
import com.sipmat.sipmat.admin.apat.HasilApatActivity
import com.sipmat.sipmat.admin.apat.ScheduleApatActivity
import com.sipmat.sipmat.admin.hydrant.hasil.HasilHydrantActivity
import com.sipmat.sipmat.admin.hydrant.schedule.ScheduleHydrantActivity
import com.sipmat.sipmat.databinding.ActivityMenuHydrantBinding
import org.jetbrains.anko.startActivity

class MenuHydrantActivity : AppCompatActivity() {
    lateinit var binding : ActivityMenuHydrantBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_menu_hydrant)
        binding.lifecycleOwner = this

        binding.btnitemhydrant.setOnClickListener {
            startActivity<ItemHydrantActivity>()
        }

        binding.btnschedulehydrant.setOnClickListener {
            startActivity<ScheduleHydrantActivity>()
        }

        binding.btnhasilhydrant.setOnClickListener {
            startActivity<HasilHydrantActivity>()
        }

    }

}