package com.sipmat.sipmat.admin.seawater.hasil

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.gcacace.signaturepad.views.SignaturePad
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.seawater.HasilSeaWaterAdapter
import com.sipmat.sipmat.admin.seawater.schedule.CekSeaWaterAdminActivity
import com.sipmat.sipmat.databinding.ActivityHasilSeaWaterBinding
import com.sipmat.sipmat.databinding.FragmentSinkronHasilBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.seawater.HasilSeaWaterResponse
import com.sipmat.sipmat.model.seawater.SeaWaterModel
import com.sipmat.sipmat.webservice.ApiClient
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File


class HasilSeaWaterActivity : AppCompatActivity(), AnkoLogger {
    private lateinit var mAdapter: HasilSeaWaterAdapter
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    var triwulan: String? = null
    lateinit var binding: ActivityHasilSeaWaterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hasil_sea_water)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)

        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ), PackageManager.PERMISSION_GRANTED
        )

        binding.btncari.setOnClickListener {
            val tahun = binding.edttahun.text.toString().trim()
            if (tahun.isNotEmpty()) {
                gethasil(binding.spntw.selectedItem.toString(), tahun)
            }
        }
    }

    fun gethasil(tw: String, tahun: String) {
        binding.rvhasilseawater.layoutManager = LinearLayoutManager(this)
        binding.rvhasilseawater.setHasFixedSize(true)
        (binding.rvhasilseawater.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.gethasil_seawater(tw, tahun)
            .enqueue(object : Callback<HasilSeaWaterResponse> {
                override fun onResponse(
                    call: Call<HasilSeaWaterResponse>,
                    response: Response<HasilSeaWaterResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<SeaWaterModel>()
                            val data = response.body()
                            if (data!!.data!!.isEmpty()) {
                                toast("data kosong")
                            } else {
                                for (hasil in data.data!!) {
                                    notesList.add(hasil)
                                    mAdapter =
                                        HasilSeaWaterAdapter(notesList, this@HasilSeaWaterActivity)
                                    binding.rvhasilseawater.adapter = mAdapter
                                    mAdapter.setDialog(object : HasilSeaWaterAdapter.Dialog {

                                        override fun onClick(position: Int, note: SeaWaterModel) {
                                            val builder =
                                                AlertDialog.Builder(this@HasilSeaWaterActivity)
                                            builder.setMessage("Cek seawater ? ")
                                            builder.setPositiveButton("Cek seawater") { dialog, which ->
                                                val gson = Gson()
                                                val noteJson = gson.toJson(note)
                                                startActivity<CekSeaWaterAdminActivity>("cekseawater" to noteJson)
                                            }

                                            builder.setNegativeButton("Cancel ?") { dialog, which ->

                                            }

                                            builder.show()

                                        }

                                    })
                                    mAdapter.notifyDataSetChanged()
                                }

                            }
                        } else {
                            toast("gagal mendapatkan response")
                        }
                    } catch (e: Exception) {
                        info { "dinda ${e.message}" }
                    }
                }

                override fun onFailure(call: Call<HasilSeaWaterResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })


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

}