package com.sipmat.sipmat.admin.apar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sipmat.sipmat.R
import kotlinx.android.synthetic.main.activity_apar.*
import org.jetbrains.anko.startActivity

class AparActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apar)

        btnitemapar.setOnClickListener {
            startActivity<ItemAparActivity>()
        }

        btnscheduleapar.setOnClickListener {
            startActivity<ScheduleAparActivity>()
        }

        btnhasilapar.setOnClickListener {
            startActivity<HasilAparActivity>()
        }


    }
}