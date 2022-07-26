package com.sipmat.sipmat.pelaksana.damkarpelaksana

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
import com.sipmat.sipmat.model.damkar.DamkarModel
import com.sipmat.sipmat.model.edgblok2.EdgBlok2Model
import com.sipmat.sipmat.model.edgblok3.EdgBlok3Model
import com.sipmat.sipmat.model.ffblok.FFBlokModel
import com.sipmat.sipmat.model.ffblok2.FFBlok2Model
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

class DetailCekDamkarActivity : AppCompatActivity(), AnkoLogger {
    lateinit var binding: ActivityDetailCekDamkarBinding
    var api = ApiClient.instance()
    lateinit var sessionManager: SessionManager
    lateinit var progressDialog: ProgressDialog

    companion object {
        var damkar: DamkarModel? = null
    }

    var currentDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_cek_damkar)
        binding.lifecycleOwner = this
        sessionManager = SessionManager(this)
        progressDialog = ProgressDialog(this)
        val gson = Gson()
        damkar =
            gson.fromJson(
                intent.getStringExtra("cekdamkar"),
                DamkarModel::class.java
            )
        val sdf = SimpleDateFormat("yyyy-M-dd ")

        currentDate = sdf.format(Date())
        binding.txltglPemeriksa.text = "Tgl Pemeriksa : $currentDate"
        binding.txtshift.text = "Shift : ${sessionManager.getNama()}"

        val start = binding.txts.text.toString().trim()
        val stop = binding.txtst.text.toString().trim()
        val catatan = binding.txtcatatan.text.toString().trim()
        val accu = binding.txtac.selectedItem
        val air_radiator = binding.txtlar.selectedItem
        val temp_mesin = binding.txttm.selectedItem
        val lvl_oil = binding.txtlo.selectedItem
        val filter_solar = binding.txtfs.selectedItem
        val lvl_minyak_rem = binding.txtlmr.selectedItem
        val suara_mesin = binding.txtsm.selectedItem
        val lamp_depan = binding.txtld.selectedItem
        val lamp_belakang = binding.txtlb.selectedItem
        val lamp_rem = binding.txtlr.selectedItem
        val lsknd = binding.txtlsknd.selectedItem
        val lskrd = binding.txtlskrd.selectedItem
        val lsknb = binding.txtlsknb.selectedItem
        val lskrb = binding.txtlskrd.selectedItem
        val hazard = binding.txtlh.selectedItem
        val sorot = binding.txtlsr.selectedItem
        val ldd = binding.txtldp.selectedItem
        val ldt = binding.txtldt.selectedItem
        val ldb = binding.txtldb.selectedItem
        val wiper = binding.txtw.selectedItem
        val spion = binding.txtsp.selectedItem
        val sirine = binding.txtsr.selectedItem

        binding.btnsubmit.setOnClickListener {
            val start = binding.txts.text.toString()
            val stop = binding.txtst.text.toString()
            val catatan = binding.txtcatatan.text.toString().trim()
            val accu = binding.txtac.selectedItem
            val air_radiator = binding.txtlar.selectedItem
            val temp_mesin = binding.txttm.selectedItem
            val lvl_oil = binding.txtlo.selectedItem
            val filter_solar = binding.txtfs.selectedItem
            val lvl_minyak_rem = binding.txtlmr.selectedItem
            val suara_mesin = binding.txtsm.selectedItem
            val lamp_depan = binding.txtld.selectedItem
            val lamp_belakang = binding.txtlb.selectedItem
            val lamp_rem = binding.txtlr.selectedItem
            val lsknd = binding.txtlsknd.selectedItem
            val lskrd = binding.txtlskrd.selectedItem
            val lsknb = binding.txtlsknb.selectedItem
            val lskrb = binding.txtlskrd.selectedItem
            val hazard = binding.txtlh.selectedItem
            val sorot = binding.txtlsr.selectedItem
            val ldd = binding.txtldp.selectedItem
            val ldt = binding.txtldt.selectedItem
            val ldb = binding.txtldb.selectedItem
            val wiper = binding.txtw.selectedItem
            val spion = binding.txtsp.selectedItem
            val sirine = binding.txtsr.selectedItem
            if (start.isNotEmpty() && stop.isNotEmpty() && catatan.isNotEmpty()) {
                loading(true)
                val updateDamkar = UpdateDamkar(
                    spion.toString(),
                    hazard.toString(),
                    currentDate,
                    sessionManager.getNama(),
                    lamp_rem.toString(),
                    sorot.toString(),
                    ldt.toString(),
                    lvl_minyak_rem.toString(),
                    1,
                    lamp_belakang.toString(),
                    accu.toString(),
                    air_radiator.toString(),
                    lvl_oil.toString(),
                    damkar!!.id,
                    lskrb.toString(),
                    suara_mesin.toString(),
                    lamp_depan.toString(),
                    start,
                    catatan,
                    ldb.toString(),
                    filter_solar.toString(),
                    lsknd.toString(),
                    stop,
                    lsknb.toString(),
                    wiper.toString(),
                    ldd.toString(),
                    temp_mesin.toString(),
                    lskrd.toString(),
                    sirine.toString()
                )
                api.update_damkarku(updateDamkar).enqueue(object :
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