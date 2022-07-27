package com.sipmat.sipmat.pelaksana.aparpelaksana

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.databinding.ActivityCekAparKadaluarsaBinding
import com.sipmat.sipmat.model.AparModel
import com.sipmat.sipmat.pelaksana.aparpelaksana.QrCoderCekAparKadaluarsaActivity.Companion.jeniskadaluarsa
import com.sipmat.sipmat.pelaksana.aparpelaksana.QrCoderCekAparKadaluarsaActivity.Companion.kadaluarsakadaluarsa
import com.sipmat.sipmat.pelaksana.aparpelaksana.QrCoderCekAparKadaluarsaActivity.Companion.kodeaparkadaluarsa
import com.sipmat.sipmat.pelaksana.aparpelaksana.QrCoderCekAparKadaluarsaActivity.Companion.lokasikadaluarsa
import com.sipmat.sipmat.session.SessionManager
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*

class CekAparKadaluarsaActivity : AppCompatActivity() {
    lateinit var binding: ActivityCekAparKadaluarsaBinding
    var api = ApiClient.instance()
    lateinit var sessionManager: SessionManager
    lateinit var progressDialog: ProgressDialog
    var currentDate: String? = null

    companion object {
        var cekapar: AparModel? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_cek_apar_kadaluarsa)
        val gson = Gson()
        cekapar =
            gson.fromJson(intent.getStringExtra("cekapar"), AparModel::class.java)
        val sdf = SimpleDateFormat("yyyy-M-dd")
        currentDate = sdf.format(Date())
        binding.txltglPemeriksa.text = "Tgl Pemeriksa : $currentDate"
        sessionManager = SessionManager(this)
        progressDialog = ProgressDialog(this)
        binding.txtshift.text = "Shift : ${sessionManager.getNama()}"
        binding.btnscan.setOnClickListener {
            startActivity<QrCoderCekAparKadaluarsaActivity>()
        }
    }

    override fun onStart() {
        super.onStart()
        //CEK APAR
        if (kodeaparkadaluarsa != null && jeniskadaluarsa != null && lokasikadaluarsa != null && kadaluarsakadaluarsa != null) {
            binding.txtkodeapar.text = "Kode APAR : ${kodeaparkadaluarsa.toString()}"
            binding.txtjenisapar.text = "Jenis APAR : ${jeniskadaluarsa.toString()}"
            binding.txtlokasi.text = "Lokasi Penempatan APAR : ${lokasikadaluarsa.toString()}"
            binding.txtkadaluarsa.text = "Tanggal Kadaluarsa : ${kadaluarsakadaluarsa.toString()}"
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        QrCoderCekAparKadaluarsaActivity.kodeaparkadaluarsa = null
        QrCoderCekAparKadaluarsaActivity.jeniskadaluarsa = null
        QrCoderCekAparKadaluarsaActivity.lokasikadaluarsa = null
        QrCoderCekAparKadaluarsaActivity.kadaluarsakadaluarsa = null
    }
}