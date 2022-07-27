package com.sipmat.sipmat.admin.damkar.schedule

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.github.gcacace.signaturepad.views.SignaturePad
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sipmat.sipmat.R
import com.sipmat.sipmat.constant.Constant
import com.sipmat.sipmat.databinding.ActivityCekEdgblok1AdminBinding
import com.sipmat.sipmat.databinding.ActivityDetailCekDamkarAdminBinding
import com.sipmat.sipmat.databinding.FragmentSinkronHasil2Binding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.damkar.DamkarModel
import com.sipmat.sipmat.webservice.ApiClient
import com.squareup.picasso.Picasso
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File


class CekDamkarAdminActivity : AppCompatActivity(), AnkoLogger {
    lateinit var cekedgblok1: DamkarModel
    lateinit var binding: ActivityDetailCekDamkarAdminBinding

    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_cek_damkar_admin)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)
        val gson = Gson()
        cekedgblok1 =
            gson.fromJson(intent.getStringExtra("damkar"), DamkarModel::class.java)

        if (cekedgblok1.isStatus == 0 || cekedgblok1.isStatus ==3){
            binding.btnApprove.visibility = View.GONE
        }
        if (cekedgblok1.isStatus ==1){
            binding.btnReturn.visibility = View.VISIBLE
        }
        if (cekedgblok1.isStatus ==2 ){
            binding.btnApprove.visibility = View.GONE
            binding.btnReturn.visibility = View.GONE
            binding.btncetakpdf.visibility = View.VISIBLE

        }
        //================Motor Driven==============
        binding.txts.text = "${cekedgblok1.start.toString()}"
        binding.txtst.text = "${cekedgblok1.stop.toString()}"
        binding.txtac.text = "${cekedgblok1.airAccu.toString()}"
        binding.txtlar.text = "${cekedgblok1.levelAirRadiator.toString()}"
        binding.txttm.text = "${cekedgblok1.tempraturMesin.toString()}"
        binding.txtlo.text = "${cekedgblok1.levelOil.toString()}"
        binding.txtfs.text = "${cekedgblok1.filterSolar.toString()}"
        binding.txtlmr.text = "${cekedgblok1.levelMinyakRem.toString()}"
        binding.txtsm.text = "${cekedgblok1.suaraMesin.toString()}"
        binding.txtld.text = "${cekedgblok1.lampuDepan.toString()}"
        binding.txtlb.text = "${cekedgblok1.lampuBelakang.toString()}"
        binding.txtlr.text = "${cekedgblok1.lampuRem.toString()}"
        binding.txtlsknd.text = "${cekedgblok1.lampuSeinKananDepan.toString()}"
        binding.txtlskrd.text = "${cekedgblok1.lampuSeinKiriDepan.toString()}"
        binding.txtlsknb.text = "${cekedgblok1.lampuSeinKananBelakang.toString()}"
        binding.txtlskrb.text = "${cekedgblok1.lampuSeinKiriBelakang.toString()}"
        binding.txtlh.text = "${cekedgblok1.lampuHazard.toString()}"
        binding.txtlsr.text = "${cekedgblok1.lampuSorot.toString()}"
        binding.txtldp.text = "${cekedgblok1.lampuDalamDepan.toString()}"
        binding.txtldt.text = "${cekedgblok1.lampuDalamTengah.toString()}"
        binding.txtldb.text = "${cekedgblok1.lampuDalamBelakang.toString()}"
        binding.txtw.text = "${cekedgblok1.wiper.toString()}"
        binding.txtsp.text = "${cekedgblok1.spion.toString()}"
        binding.txtsr.text = "${cekedgblok1.sirine.toString()}"
        binding.txtcatatan.text = "${cekedgblok1.catatan.toString()}"




        binding.txltglPemeriksa.text = "Tgl Pemeriksa : ${cekedgblok1.tanggalCek.toString()}"
        binding.txtshift.text = "Shift : ${cekedgblok1.shift.toString()}"

        binding.btnReturn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Mobil Damkar")
            builder.setMessage("Return Mobil Damkar ? ")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                loading(true)
                api.return_damkar(cekedgblok1.id!!).enqueue(object :
                    Callback<PostDataResponse> {
                    override fun onResponse(
                        call: Call<PostDataResponse>,
                        response: Response<PostDataResponse>
                    ) {
                        try {
                            if (response.body()!!.sukses == 1) {
                                loading(false)
                                toast("return schedule berhasil")
                                finish()
                            } else {
                                loading(false)
                                Snackbar.make(it, "return   gagal", Snackbar.LENGTH_SHORT)
                                    .show()

                            }


                        } catch (e: Exception) {
                            progressDialog.dismiss()
                            info { "dinda ${e.message}${response.code()} " }
                        }

                    }

                    override fun onFailure(call: Call<PostDataResponse>, t: Throwable) {
                        loading(false)
                        Snackbar.make(it, "Kesalahan jaringan", Snackbar.LENGTH_SHORT).show()

                    }

                })
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->

            }

            builder.show()
        }

        binding.btnApprove.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Mobil Damkar")
            builder.setMessage("ACC Mobil Damkar ? ")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                loading(true)
                api.acc_damkar(cekedgblok1.id!!).enqueue(object :
                    Callback<PostDataResponse> {
                    override fun onResponse(
                        call: Call<PostDataResponse>,
                        response: Response<PostDataResponse>
                    ) {
                        try {
                            if (response.body()!!.sukses == 1) {
                                loading(false)
                                toast("acc schedule berhasil")
                                finish()
                            } else {
                                loading(false)
                                Snackbar.make(it, "acc  gagal", Snackbar.LENGTH_SHORT)
                                    .show()

                            }


                        } catch (e: Exception) {
                            progressDialog.dismiss()
                            info { "dinda ${e.message}${response.code()} " }
                        }

                    }

                    override fun onFailure(call: Call<PostDataResponse>, t: Throwable) {
                        loading(false)
                        Snackbar.make(it, "Kesalahan jaringan", Snackbar.LENGTH_SHORT).show()

                    }

                })
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->

            }

            builder.show()

        }

        binding.btncetakpdf.setOnClickListener {
            showcustomdialog()
        }

    }


    fun showcustomdialog() {
        val dialogBinding: FragmentSinkronHasil2Binding =
            DataBindingUtil.inflate(
                LayoutInflater.from(this),
                R.layout.fragment_sinkron_hasil2,
                null,
                false
            )

        val customDialog =
            androidx.appcompat.app.AlertDialog.Builder(this, 0).create()

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
                    val jabatan = dialogBinding.edtjabatan.text.toString().trim()
                    val nama = dialogBinding.edtnama.text.toString().trim()
                    loading(true)

//                    val requestid: RequestBody = RequestBody.create(
//                        MediaType.parse("text/plain"),
//                        cekedgblok1!!.id
//                    )
                    val requestjabatan: RequestBody = RequestBody.create(
                        MediaType.parse("text/plain"),
                        jabatan
                    )
                    val requestnama: RequestBody = RequestBody.create(
                        MediaType.parse("text/plain"),
                        nama
                    )
                    if (jabatan.isNotEmpty() && nama.isNotEmpty()){
                        api.damkarku_pdf(cekedgblok1.id!!,body,requestjabatan,requestnama).enqueue(object : Callback<PostDataResponse>{
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