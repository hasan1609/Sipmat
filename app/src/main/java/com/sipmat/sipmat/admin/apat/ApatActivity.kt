package com.sipmat.sipmat.admin.apat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sipmat.sipmat.R
import com.sipmat.sipmat.databinding.ActivityApatBinding
import org.jetbrains.anko.startActivity

class ApatActivity : AppCompatActivity() {
    lateinit var binding : ActivityApatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_apat)
        binding.lifecycleOwner = this

        binding.btnitemapat.setOnClickListener {
            startActivity<ItemApatActivity>()
        }

        binding.btnscheduleapat.setOnClickListener {
            startActivity<ScheduleApatActivity>()
        }

        binding.btnhasilapat.setOnClickListener {
            startActivity<HasilApatActivity>()
        }

    }
}