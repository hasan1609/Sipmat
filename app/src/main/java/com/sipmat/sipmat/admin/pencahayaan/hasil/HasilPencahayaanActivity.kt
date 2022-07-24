package com.sipmat.sipmat.admin.pencahayaan.hasil

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
import com.sipmat.sipmat.adapter.pencahayaan.HasilPencahayaanAdapter
import com.sipmat.sipmat.admin.pencahayaan.schedule.CekPencahayaanAdminActivity
import com.sipmat.sipmat.databinding.ActivityHasilPencahayaanBinding
import com.sipmat.sipmat.databinding.FragmentSinkronHasilBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.pencahayaan.HasilPencahayaanModel
import com.sipmat.sipmat.model.pencahayaan.HasilPencahayaanResponse
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

class HasilPencahayaanActivity : AppCompatActivity(), AnkoLogger {
    private lateinit var mAdapter: HasilPencahayaanAdapter
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    var triwulan: String? = null
    lateinit var  binding : ActivityHasilPencahayaanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_hasil_pencahayaan)
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
        binding.rvhasilpencahayaan.layoutManager = LinearLayoutManager(this)
        binding.rvhasilpencahayaan.setHasFixedSize(true)
        (binding.rvhasilpencahayaan.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.gethasil_pencahayaan(tw, tahun)
            .enqueue(object : Callback<HasilPencahayaanResponse> {
                override fun onResponse(
                    call: Call<HasilPencahayaanResponse>,
                    response: Response<HasilPencahayaanResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<HasilPencahayaanModel>()
                            val data = response.body()
                            if (data!!.data!!.isEmpty()) {
                                toast("data kosong")
                            } else {
                                binding.btnsinkron.visibility = View.VISIBLE
                                binding.btnsinkron.setOnClickListener {
                                    showcustomdialog()
                                }
                                for (hasil in data.data!!) {
                                    notesList.add(hasil)
                                    mAdapter = HasilPencahayaanAdapter(notesList, this@HasilPencahayaanActivity)
                                    binding.rvhasilpencahayaan.adapter = mAdapter
                                    mAdapter.setDialog(object : HasilPencahayaanAdapter.Dialog {

                                        override fun onClick(position: Int, note: HasilPencahayaanModel) {
                                            val builder =
                                                AlertDialog.Builder(this@HasilPencahayaanActivity)
                                            builder.setMessage("Cek pencahayaan ? ")
                                            builder.setPositiveButton("Cek pencahayaan") { dialog, which ->
                                                val gson = Gson()
                                                val noteJson = gson.toJson(note)
                                                startActivity<CekPencahayaanAdminActivity>("cekpencahayaan" to noteJson)
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

                override fun onFailure(call: Call<HasilPencahayaanResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })


    }

    fun showcustomdialog() {
        val dialogBinding: FragmentSinkronHasilBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(this),
                R.layout.fragment_sinkron_hasil,
                null,
                false
            )

        val customDialog =
            AlertDialog.Builder(this, 0).create()

        customDialog.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setView(dialogBinding.root)
            setCancelable(true)
        }.show()

        dialogBinding.signaturePad.setOnSignedListener(object : SignaturePad.OnSignedListener {
            override fun onStartSigning() {
            }

            override fun onSigned() {

                //create a file to write bitmap data
                //create a file to write bitmap data
                val f: File = File(cacheDir,"foto")
                f.createNewFile()

                //Convert bitmap
                //ini bitmapnya
                val bitmap = dialogBinding.signaturePad.transparentSignatureBitmap
                val bos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG,0/*ignored for PNG*/, bos)
                val bitmapdata = bos.toByteArray()
                val foto = bitmapToBytes(bitmap)

                val reqFile = RequestBody.create(MediaType.parse("image/*"),foto)
                val body = MultipartBody.Part.createFormData("foto", f.name, reqFile)



                dialogBinding.btnsynchrone.setOnClickListener {
                    //data
                    val tw = dialogBinding.spntriwulan.selectedItem.toString()
                    val tahun = dialogBinding.edttahun.text.toString().trim()
                    val jabatan = dialogBinding.edtjabatan.text.toString().trim()
                    val nama = dialogBinding.edtnama.text.toString().trim()
                    loading(true)

                    val requesttw: RequestBody = RequestBody.create(
                        MediaType.parse("text/plain"),
                        tw
                    )
                    val requesttahun: RequestBody = RequestBody.create(
                        MediaType.parse("text/plain"),
                        tahun
                    )
                    val requestjabatan: RequestBody = RequestBody.create(
                        MediaType.parse("text/plain"),
                        jabatan
                    )
                    val requestnama: RequestBody = RequestBody.create(
                        MediaType.parse("text/plain"),
                        nama
                    )

                    if (tw.isNotEmpty() && tahun.isNotEmpty() && jabatan.isNotEmpty() && nama.isNotEmpty()){
                        api.pencahayaan_pdf(body,requesttw,requesttahun,requestjabatan,requestnama).enqueue(object :
                            Callback<PostDataResponse> {
                            override fun onResponse(
                                call: Call<PostDataResponse>,
                                response: Response<PostDataResponse>
                            ) {
                                try {
                                    if (response.isSuccessful){
                                        loading(false)
                                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(response.body()!!.data.toString()))
                                        startActivity(browserIntent)


                                    }else{
                                        val gson = Gson()
                                        val type = object : TypeToken<PostDataResponse>() {}.type
                                        var errorResponse: PostDataResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                                        info { "dinda ${errorResponse}" }

                                        loading(false)
                                        toast("kesalahan response")
                                    }

                                }catch (e :Exception){
                                    info { "dinda e ${e.message}" }
                                }
                            }

                            override fun onFailure(call: Call<PostDataResponse>, t: Throwable) {
                                info { "dinda failure ${t.message}" }
                                loading(false)
                            }

                        })
                    }else{
                        toast("jangan kosommgi kolom")
                        loading(false)
                    }
                }

            }

            override fun onClear() {

            }

        })



    }

    fun bitmapToBytes(photo: Bitmap): ByteArray? {
        val stream = ByteArrayOutputStream()
        photo.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
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