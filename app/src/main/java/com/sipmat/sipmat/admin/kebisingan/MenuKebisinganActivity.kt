package com.sipmat.sipmat.admin.kebisingan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sipmat.sipmat.R
import com.sipmat.sipmat.admin.hydrant.hasil.HasilHydrantActivity
import com.sipmat.sipmat.admin.hydrant.item.ItemHydrantActivity
import com.sipmat.sipmat.admin.hydrant.schedule.ScheduleHydrantActivity
import com.sipmat.sipmat.admin.kebisingan.hasil.HasilKebisinganActivity
import com.sipmat.sipmat.admin.kebisingan.item.ItemKebisinganActivity
import com.sipmat.sipmat.admin.kebisingan.schedule.ScheduleKebisinganActivity
import com.sipmat.sipmat.databinding.ActivityMenuHydrantBinding
import com.sipmat.sipmat.databinding.ActivityMenuKebisinganBinding
import org.jetbrains.anko.startActivity


class MenuKebisinganActivity : AppCompatActivity() {
    lateinit var binding : ActivityMenuKebisinganBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_menu_kebisingan)
        binding.lifecycleOwner = this

        binding.btnitemkebisingan.setOnClickListener {
            startActivity<ItemKebisinganActivity>()
        }

        binding.btnschedulekebisingan.setOnClickListener {
            startActivity<ScheduleKebisinganActivity>()
        }

        binding.btnhasilkebisingan.setOnClickListener {
            startActivity<HasilKebisinganActivity>()
        }

    }

}