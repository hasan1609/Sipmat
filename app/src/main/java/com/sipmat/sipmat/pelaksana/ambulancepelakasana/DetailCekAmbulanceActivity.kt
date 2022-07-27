package com.sipmat.sipmat.pelaksana.ambulancepelakasana

import android.app.ProgressDialog
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import com.github.gcacace.signaturepad.views.SignaturePad
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.databinding.*
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.ambulance.AmbulanceModel
import com.sipmat.sipmat.model.damkar.DamkarModel
import com.sipmat.sipmat.model.edgblok2.EdgBlok2Model
import com.sipmat.sipmat.model.edgblok3.EdgBlok3Model
import com.sipmat.sipmat.model.ffblok.FFBlokModel
import com.sipmat.sipmat.model.ffblok2.FFBlok2Model
import com.sipmat.sipmat.model.postdata.UpdateAmbulance
import com.sipmat.sipmat.model.postdata.UpdateDamkar
import com.sipmat.sipmat.pelaksana.ffblok2pelaksana.DetailCekFFblok2Activity
import com.sipmat.sipmat.session.SessionManager
import com.sipmat.sipmat.webservice.ApiClient
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.find
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class DetailCekAmbulanceActivity : AppCompatActivity(), AnkoLogger {
    lateinit var binding: ActivityDetailCekAmbulanceBinding
    var api = ApiClient.instance()
    lateinit var sessionManager: SessionManager
    lateinit var progressDialog: ProgressDialog

    companion object {
        var damkar: AmbulanceModel? = null
    }

    var currentDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_cek_ambulance)
        binding.lifecycleOwner = this
        sessionManager = SessionManager(this)
        progressDialog = ProgressDialog(this)
        val gson = Gson()
        damkar =
            gson.fromJson(
                intent.getStringExtra("ambulance"),
                AmbulanceModel::class.java
            )
        val sdf = SimpleDateFormat("yyyy-M-dd ")

        currentDate = sdf.format(Date())
        binding.txltglPemeriksa.text = "Tgl Pemeriksa : $currentDate"
        binding.txtshift.text = "Shift : ${sessionManager.getNama()}"

        binding.btnsubmit.setOnClickListener {
            val txtto = binding.txtto.text.toString()
            val txttoT = binding.txttoT.text.toString()
            val txta = binding.txta.text.toString()
            val txtaT = binding.txtaT.text.toString()
            val txtaKs = binding.txtaKs.text.toString()
            val txtci = binding.txtci.text.toString()
            val txtpa = binding.txtpa.text.toString()
            val txttd = binding.txttd.text.toString()
            val txttdK = binding.txttdK.text.toString()
            val txttdR = binding.txttdR.text.toString()
            val txttdSk = binding.txttdSk.text.toString()
            val txttl = binding.txttl.text.toString()
            val txtaw = binding.txtaw.text.toString()
            val txtag = binding.txtag.text.toString()
            val txttk = binding.txttk.text.toString()
            val txto = binding.txto.text.toString()
            val txttp = binding.txttp.text.toString()
            val txtc = binding.txtcatatan.text.toString()

            if (
                txtto.isNotEmpty() &&
                txttoT.isNotEmpty() &&
                txta.isNotEmpty() &&
                txtaT.isNotEmpty() &&
                txtaKs.isNotEmpty() &&
                txtci.isNotEmpty() &&
                txtpa.isNotEmpty() &&
                txttd.isNotEmpty() &&
                txttdK.isNotEmpty() &&
                txttdR.isNotEmpty() &&
                txttdSk.isNotEmpty() &&
                txttl.isNotEmpty() &&
                txtaw.isNotEmpty() &&
                txtag.isNotEmpty() &&
                txttk.isNotEmpty() &&
                txto.isNotEmpty() &&
                txttp.isNotEmpty() &&
                txtc.isNotEmpty()) {
                loading(true)
                val updateAmbulance = UpdateAmbulance(
                    txttk,
                    currentDate,
                    sessionManager.getNama(),
                    txtaKs,
                    txta,
                    txttdR,
                    1,
                    txttdSk,
                    txtto,
                    damkar!!.id,
                    txto,
                    txtaw,
                    txttdK,
                    txtag,
                    txtc,
                    txtaT,
                    txttd,
                    txttoT,
                    txttl,
                    txttp,
                    txtpa,
                    txtci
                )
                api.update_ambulanceku(updateAmbulance).enqueue(object :
                    Callback<PostDataResponse> {
                    override fun onResponse(
                        call: Call<PostDataResponse>,
                        response: Response<PostDataResponse>
                    ) {
                        if (response.isSuccessful) {
                            loading(false)
                            if (response.body()!!.sukses == 1) {
                                finish()
                                toast("Tunggu approve admin")
                            } else {
                                finish()
                                toast("silahkan coba lagi")
                            }
                        } else {
                            loading(false)
                            toast("Response gagal")
                        }
                    }

                    override fun onFailure(call: Call<PostDataResponse>, t: Throwable) {
                        loading(false)
                        toast("Jaringan error")
                        info { "dinda errror ${t.message}" }
                    }

                })

            } else {
                toast("Jangan kosongi kolom")
            }

        }

    }

    override fun onStart() {
        super.onStart()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        damkar = null
    }

    fun loading(status: Boolean) {
        if (status) {
            progressDialog.setTitle("Loading...")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
        } else {
            progressDialog.dismiss()
        }
    }


    fun bitmapToBytes(photo: Bitmap): ByteArray? {
        val stream = ByteArrayOutputStream()
        photo.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

}