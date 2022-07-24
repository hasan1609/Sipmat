package com.sipmat.sipmat.admin.apar

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.gcacace.signaturepad.views.SignaturePad
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.HasilAparAdapter
import com.sipmat.sipmat.databinding.FragmentSinkronHasilBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.ScheduleModel
import com.sipmat.sipmat.model.ScheduleResponse
import com.sipmat.sipmat.webservice.ApiClient
import kotlinx.android.synthetic.main.activity_hasil_apar.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File


class HasilAparActivity : AppCompatActivity(), AnkoLogger {
    private lateinit var mAdapter: HasilAparAdapter
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    var triwulan: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hasil_apar)
        progressDialog = ProgressDialog(this)
        tw()
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ), PackageManager.PERMISSION_GRANTED
        )

        btncari.setOnClickListener {
            val tahun = edttahun.text.toString().trim()
            if (triwulan != null && tahun.isNotEmpty()) {
                gethasil(triwulan!!, tahun)
            }
        }
    }

    private fun tw() {
        val jenis_tw = arrayOf("I", "II", "III", "IV")
        val spinner = find<Spinner>(R.id.spntw)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, jenis_tw
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    triwulan = jenis_tw[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }
    }

    fun gethasil(tw: String, tahun: String) {
        rvhasilapar.layoutManager = LinearLayoutManager(this)
        rvhasilapar.setHasFixedSize(true)
        (rvhasilapar.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.getapar_hasil(tw, tahun)
            .enqueue(object : Callback<ScheduleResponse> {
                override fun onResponse(
                    call: Call<ScheduleResponse>,
                    response: Response<ScheduleResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<ScheduleModel>()
                            val data = response.body()
                            if (data!!.data!!.isEmpty()) {
                                toast("data kosong")
                            } else {
                                btnsinkron.visibility = View.VISIBLE
                                btnsinkron.setOnClickListener {
                                    showcustomdialog()
                                }
                                for (hasil in data.data!!) {
                                    notesList.add(hasil)
                                    mAdapter = HasilAparAdapter(notesList, this@HasilAparActivity)
                                    rvhasilapar.adapter = mAdapter
                                    mAdapter.setDialog(object : HasilAparAdapter.Dialog {
                                        override fun onClick(position: Int, note: ScheduleModel) {
                                            val builder =
                                                AlertDialog.Builder(this@HasilAparActivity)
                                            builder.setMessage("Cek apar ? ")
                                            builder.setPositiveButton("Cek APAR") { dialog, which ->
                                                val gson = Gson()
                                                val noteJson = gson.toJson(note)
                                                startActivity<CekAparAdminActivity>("cekapar" to noteJson)
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

                override fun onFailure(call: Call<ScheduleResponse>, t: Throwable) {
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
                        api.create_pdf(body,requesttw,requesttahun,requestjabatan,requestnama).enqueue(object : Callback<PostDataResponse>{
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