package com.sipmat.sipmat.pelaksana.aparpelaksana

import android.app.ProgressDialog
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.sipmat.sipmat.R
import com.sipmat.sipmat.databinding.ActivityQrCoderCekAparBinding
import com.sipmat.sipmat.model.apar.CekAparModel
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QrCoderCekAparActivity : AppCompatActivity(),AnkoLogger {
    private lateinit var codeScanner: CodeScanner
    lateinit var binding : ActivityQrCoderCekAparBinding

    var api = ApiClient.instance()
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_qr_coder_cek_apar)
        binding.lifecycleOwner  =this
        progressDialog = ProgressDialog(this)

        setupPermissions()
        codeScanner()
    }

    private fun codeScanner() {
        codeScanner = CodeScanner(this, binding.scn)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread {
                    loading(true)
                    hasilqrcode = it.text
                    api.cekapar(hasilqrcode!!).enqueue(object :Callback<CekAparModel>{
                        override fun onResponse(
                            call: Call<CekAparModel>,
                            response: Response<CekAparModel>
                        ) {
                            if (response.isSuccessful){
                                if (CekAparActivity.cekapar!!.kodeApar == response.body()!!.data!!.kode){
                                    jenis = response.body()!!.data!!.jenis
                                    lokasi = response.body()!!.data!!.lokasi
                                    kadaluarsa = response.body()!!.data!!.tglKadaluarsa
                                    kodeapar = response.body()!!.data!!.kode
                                    finish()
                                }else{
                                    toast("kode tidak sama")
                                    finish()
                                }
                            }else{
                                toast("Kesalahan response")
                                finish()
                            }
                        }

                        override fun onFailure(call: Call<CekAparModel>, t: Throwable) {
                            loading(true)
                            toast("Kesalhan jaringan silahkan scan ulang")
                            finish()
                        }

                    })
                }
            }

            errorCallback = ErrorCallback {
                runOnUiThread {
                    info { "dinda error codescanner ${it.message}" }
                }
            }

            binding.scn.setOnClickListener {
                codeScanner.startPreview()
            }

        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this, arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQ
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQ -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        "You need the camera permission to use this app",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun loading(status : Boolean){
        if (status){
            progressDialog.setTitle("Loading...")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
        }else{
            progressDialog.dismiss()
        }
    }

    companion object {
        private const val CAMERA_REQ = 101
        var hasilqrcode : String? = null
        var jenis : String? = null
        var kodeapar : String? = null
        var lokasi : String? = null
        var kadaluarsa : String? = null

    }
}