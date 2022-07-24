package com.sipmat.sipmat.pelaksana.aparpelaksana

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.model.AparModel

class CekAparKadaluarsaActivity : AppCompatActivity() {
    var cekapar: AparModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cek_apar_kadaluarsa)
        val gson = Gson()
        cekapar =
            gson.fromJson(intent.getStringExtra("cekapar"), AparModel::class.java)
    }
}