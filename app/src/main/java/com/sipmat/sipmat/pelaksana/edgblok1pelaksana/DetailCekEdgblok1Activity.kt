package com.sipmat.sipmat.pelaksana.edgblok1pelaksana

import android.app.ProgressDialog
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.github.gcacace.signaturepad.views.SignaturePad
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.databinding.ActivityDetailCekEdgblok1Binding
import com.sipmat.sipmat.databinding.ActivityDetailCekFfblok2Binding
import com.sipmat.sipmat.databinding.ActivityDetailCekFfblokBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.edgblok1.EdgBlokModel
import com.sipmat.sipmat.model.ffblok.FFBlokModel
import com.sipmat.sipmat.model.ffblok2.FFBlok2Model
import com.sipmat.sipmat.pelaksana.ffblok2pelaksana.DetailCekFFblok2Activity
import com.sipmat.sipmat.session.SessionManager
import com.sipmat.sipmat.webservice.ApiClient
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
import java.text.SimpleDateFormat
import java.util.*

class DetailCekEdgblok1Activity : AppCompatActivity(), AnkoLogger {
    lateinit var binding: ActivityDetailCekEdgblok1Binding
    var api = ApiClient.instance()
    lateinit var sessionManager: SessionManager
    lateinit var progressDialog: ProgressDialog

    var ttd_k3: MultipartBody.Part? = null
    var signaturePadSupervisor: MultipartBody.Part? = null
    var signaturePadOperator: MultipartBody.Part? = null

    companion object {
        var edgblok1: EdgBlokModel? = null
    }

    var currentDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_cek_edgblok1)
        binding.lifecycleOwner = this
        sessionManager = SessionManager(this)
        progressDialog = ProgressDialog(this)
        val gson = Gson()
        edgblok1 =
            gson.fromJson(
                intent.getStringExtra("cekedgblok1"),
                EdgBlokModel::class.java
            )

        val sdf = SimpleDateFormat("yyyy-M-dd")
        currentDate = sdf.format(Date())
        binding.txltglPemeriksa.text = "Tgl Pemeriksa : $currentDate"
        binding.txtshift.text = "Shift : ${sessionManager.getNama()}"

        binding.signaturePadK3.setOnSignedListener(object : SignaturePad.OnSignedListener {
            override fun onStartSigning() {
            }
            override fun onSigned() {
                //create a file to write bitmap data
                //create a file to write bitmap data
                val f: File = File(cacheDir, "foto")
                f.createNewFile()
                //Convert bitmap
                //ini bitmapnya
                val bitmap = binding.signaturePadK3.transparentSignatureBitmap
                val bos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 0/*ignored for PNG*/, bos)
                val bitmapdata = bos.toByteArray()
                val foto = bitmapToBytes(bitmap)

                val reqFile = RequestBody.create(MediaType.parse("image/*"), foto)
                ttd_k3 = MultipartBody.Part.createFormData("k3_ttd", f.name, reqFile)
            }
            override fun onClear() {
            }
        })
        binding.signaturePadOperator.setOnSignedListener(object : SignaturePad.OnSignedListener {
            override fun onStartSigning() {
            }
            override fun onSigned() {
                //create a file to write bitmap data
                //create a file to write bitmap data
                val f: File = File(cacheDir, "foto")
                f.createNewFile()
                //Convert bitmap
                //ini bitmapnya
                val bitmap = binding.signaturePadOperator.transparentSignatureBitmap
                val bos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 0/*ignored for PNG*/, bos)
                val bitmapdata = bos.toByteArray()
                val foto = bitmapToBytes(bitmap)
                val reqFile = RequestBody.create(MediaType.parse("image/*"), foto)
                signaturePadOperator =
                    MultipartBody.Part.createFormData("operator_ttd", f.name, reqFile)
            }
            override fun onClear() {
            }
        })
        binding.signaturePadSupervisor.setOnSignedListener(object : SignaturePad.OnSignedListener {
            override fun onStartSigning() {
            }
            override fun onSigned() {
                //create a file to write bitmap data
                //create a file to write bitmap data
                val f: File = File(cacheDir, "foto")
                f.createNewFile()
                //Convert bitmap
                //ini bitmapnya
                val bitmap = binding.signaturePadSupervisor.transparentSignatureBitmap
                val bos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 0/*ignored for PNG*/, bos)
                val bitmapdata = bos.toByteArray()
                val foto = bitmapToBytes(bitmap)

                val reqFile = RequestBody.create(MediaType.parse("image/*"), foto)
                signaturePadSupervisor =
                    MultipartBody.Part.createFormData("supervisor_ttd", f.name, reqFile)
            }

            override fun onClear() {

            }

        })
        binding.btnsubmit.setOnClickListener {
            val body_tw: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                edgblok1!!.tw.toString()
            )

            val body_shift: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                sessionManager.getNama().toString()
            )

            val body_is_status: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"), "1"
            )

            val body_tahun: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                edgblok1!!.tahun
            )

            val body_id: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                "${edgblok1!!.id}"
            )

            val sdf = SimpleDateFormat("yyyy-M-dd ")
            val tanggal_cek = sdf.format(Date())

            val body_tanggal_cek: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                "${tanggal_cek}"
            )
            //=================parameter===================
            //waktu pencatatan
            val txtpWpSbs = binding.txtpWppSbs.text.toString().trim()
            val txtpWpSds = binding.txtpWpSds.text.toString().trim()
            val body_txtpWpSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpWpSbs
            )
            val body_txtpWpSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpWpSds
            )
            //oil preasure
            val txtpOpSbs = binding.txtpOpSbs.text.toString().trim()
            val txtpOpSds = binding.txtspOpSds.text.toString().trim()
            val body_txtpOpSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpOpSbs
            )
            val body_txtpOpSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpOpSds
            )
            //Oil temperature
            val txtpOtSbs = binding.txtpOtSbs.text.toString().trim()
            val txtpOtSds = binding.txtpOtSds.text.toString().trim()
            val body_txtpOtSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpOtSbs
            )
            val body_txtpOtSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpOtSds
            )
//            water temp
            val txtpWtSbs = binding.txtpWtSbs.text.toString().trim()
            val txtpWtSds = binding.txtpWtSds.text.toString().trim()
            val body_txtpWtSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpWtSbs
            )
            val body_txtpWtSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpWtSds
            )
//            speed
            val txtpsSbs = binding.txtpSSbs.text.toString().trim()
            val txtpssSds = binding.txtspSSds.text.toString().trim()
            val body_txtpsSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpsSbs
            )
            val body_txtpsSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpssSds
            )
//            hor meter 1
            val txtphm1Sbs = binding.txtpH1Sbs.text.toString().trim()
            val txtphm1Sds = binding.txtspH1Sds.text.toString().trim()
            val body_txthm1Sbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtphm1Sbs
            )
            val body_txthm1Sds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtphm1Sds
            )
//            hour meter 2
            val txtphm2Sbs = binding.txtpH2Sbs.text.toString().trim()
            val txtphm2Sds = binding.txtspH2Sds.text.toString().trim()
            val body_txtphm2Sbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtphm2Sbs
            )
            val body_txtphm2Sds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtphm2Sds
            )
//            main line vol
            val txtpMlvSbs = binding.txtpMlvSbs.text.toString().trim()
            val txtpMlvSds = binding.txtspMlvSds.text.toString().trim()
            val body_txtpMlvSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpMlvSbs
            )
            val body_txtpMlvSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpMlvSds
            )
//            main line freq
            val txtpMlfSbs = binding.txtpMlfSbs.text.toString().trim()
            val txtspMlfSds = binding.txtspMlfSds.text.toString().trim()
            val body_txtpMlfSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpMlfSbs
            )
            val body_txtspMlfSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspMlfSds
            )
//            gen break
            val txtpGbSbs = binding.txtpGbSbs.text.toString().trim()
            val txtspGbSds = binding.txtspGbSds.text.toString().trim()
            val body_txtpGbSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpGbSbs
            )
            val body_txtspGbSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspGbSds
            )
//            gen vol
            val txtpGvSbs = binding.txtpGvSbs.text.toString().trim()
            val txtspGvSds = binding.txtspGvSds.text.toString().trim()
            val body_txtpGvSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpGvSbs
            )
            val body_txtspGvSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspGvSds
            )
//            gen freq
            val txtpGfSbs = binding.txtpGfSbs.text.toString().trim()
            val txtspGfSds = binding.txtspGfSds.text.toString().trim()
            val body_txtpGfSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpGfSbs
            )
            val body_txtspGfSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspGfSds
            )
//            load cur
            val txtpLcSbs = binding.txtpLcSbs.text.toString().trim()
            val txtspLcSds = binding.txtspLcSds.text.toString().trim()
            val body_txtpLcSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpLcSbs
            )
            val body_txtspLcSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspLcSds
            )
//            daya
            val txtpDSbs = binding.txtpDSbs.text.toString().trim()
            val txtspDSds = binding.txtspDSds.text.toString().trim()
            val body_txtpDSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpDSbs
            )
            val body_txtspDSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspDSds
            )
//            COS
            val txtpCSbs = binding.txtpCSbs.text.toString().trim()
            val txtspCSds = binding.txtspCSds.text.toString().trim()
            val body_txtpCSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpCSbs
            )
            val body_txtspCSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspCSds
            )
//            bat charg vol
            val txtpBcvSbs = binding.txtpBcvSbs.text.toString().trim()
            val txtspBcvSds = binding.txtspBcvSds.text.toString().trim()
            val body_txtpBcvSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpBcvSbs
            )
            val body_txtspBcvSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspBcvSds
            )
//            hour
            val txtpHSbs = binding.txtpHSbs.text.toString().trim()
            val txtspHSds = binding.txtspHSds.text.toString().trim()
            val body_txtpHSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpHSbs
            )
            val body_txtspHSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspHSds
            )
//            lube oil
            val txtpLoSbs = binding.txtpLoSbs.text.toString().trim()
            val txtspLoSds = binding.txtspLoSds.text.toString().trim()
            val body_txtpLoSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpLoSbs
            )
            val body_txtspLoSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspLoSds
            )
//            accu lev
            val txtpAlSbs = binding.txtpAlSbs.text.toString().trim()
            val txtspAlSds = binding.txtspAlSds.text.toString().trim()
            val body_txtpAlSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpAlSbs
            )
            val body_txtspAlSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspAlSds
            )
//            radiator lev
            val txtpRlSbs = binding.txtpRlSbs.text.toString().trim()
            val txtspRlSds = binding.txtspRlSds.text.toString().trim()
            val body_txtpRlSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpRlSbs
            )
            val body_txtspRlSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspRlSds
            )
//            fuel oil level
            val txtpFolSbs = binding.txtpFolSbs.text.toString().trim()
            val txtspFolSds = binding.txtspFolSds.text.toString().trim()
            val body_txtpFolSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtpFolSbs
            )
            val body_txtspFolSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspFolSds
            )

            val catatan = binding.txtcatatan.text.toString().trim()
            val body_catatan: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                catatan
            )
            val namak3 = binding.txtnamak3.text.toString().trim()
            val body_namak3: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                namak3
            )
            val namaoperator = binding.txtnamaoperator.text.toString().trim()
            val namasupervisor = binding.txtnamasupervisor.text.toString().trim()
            val body_namasupervisor: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                namasupervisor
            )
            val body_namaoperator: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                namaoperator
            )
            if (txtpWpSbs.isNotEmpty() &&
                txtpWpSds.isNotEmpty() &&
                txtpOpSbs.isNotEmpty() &&
                txtpOpSds.isNotEmpty() &&
                txtpOtSbs.isNotEmpty() &&
                txtpOtSds.isNotEmpty() &&
                txtpWtSbs.isNotEmpty() &&
                txtpWtSds.isNotEmpty() &&
                txtpsSbs.isNotEmpty() &&
                txtpssSds.isNotEmpty() &&
                txtphm1Sbs.isNotEmpty() &&
                txtphm1Sds.isNotEmpty() &&
                txtphm2Sbs.isNotEmpty() &&
                txtphm2Sds.isNotEmpty() &&
                txtpMlvSbs.isNotEmpty() &&
                txtpMlvSds.isNotEmpty() &&
                txtpMlfSbs.isNotEmpty() &&
                txtspMlfSds.isNotEmpty() &&
                txtpGbSbs.isNotEmpty() &&
                txtspGbSds.isNotEmpty() &&
                txtpGvSbs.isNotEmpty() &&
                txtspGvSds.isNotEmpty() &&
                txtpGfSbs.isNotEmpty() &&
                txtspGfSds.isNotEmpty() &&
                txtpLcSbs.isNotEmpty() &&
                txtspLcSds.isNotEmpty() &&
                txtpDSbs.isNotEmpty() &&
                txtspDSds.isNotEmpty() &&
                txtpBcvSbs.isNotEmpty() &&
                txtspBcvSds.isNotEmpty() &&
                txtpHSbs.isNotEmpty() &&
                txtspHSds.isNotEmpty() &&
                txtpLoSbs.isNotEmpty() &&
                txtspLoSds.isNotEmpty() &&
                txtpAlSbs.isNotEmpty() &&
                txtspAlSds.isNotEmpty() &&
                txtpRlSbs.isNotEmpty() &&
                txtspRlSds.isNotEmpty() &&
                txtpFolSbs.isNotEmpty() &&
                txtspFolSds.isNotEmpty() &&
                catatan.isNotEmpty() &&
                namak3.isNotEmpty() &&
                namaoperator.isNotEmpty() &&
                namasupervisor.isNotEmpty() &&
                signaturePadSupervisor != null &&
                signaturePadOperator != null &&
                ttd_k3 != null
            ) {
                loading(true)
                api.update_edgblok1(
                    body_id,
                    body_tw,
                    body_tahun,
                    body_tanggal_cek,
                    body_shift,
                    body_is_status,
                    body_txtpWpSbs,
                    body_txtpWpSds,
                    body_txtpOpSbs,
                    body_txtpOpSds,
                    body_txtpOtSbs,
                    body_txtpOtSds,
                    body_txtpWtSbs,
                    body_txtpWtSds,
                    body_txtpsSbs,
                    body_txtpsSds,
                    body_txthm1Sbs,
                    body_txthm1Sds,
                    body_txtphm2Sbs,
                    body_txtphm2Sds,
                    body_txtpMlvSbs,
                    body_txtpMlvSds,
                    body_txtpMlfSbs,
                    body_txtspMlfSds,
                    body_txtpGbSbs,
                    body_txtspGbSds,
                    body_txtpGvSbs,
                    body_txtspGvSds,
                    body_txtpGfSbs,
                    body_txtspGfSds,
                    body_txtpLcSbs,
                    body_txtspLcSds,
                    body_txtpDSbs,
                    body_txtspDSds,
                    body_txtpCSbs,
                    body_txtspCSds,
                    body_txtpBcvSbs,
                    body_txtspBcvSds,
                    body_txtpHSbs,
                    body_txtspHSds,
                    body_txtpLoSbs,
                    body_txtspLoSds,
                    body_txtpAlSbs,
                    body_txtspAlSds,
                    body_txtpRlSbs,
                    body_txtspRlSds,
                    body_txtpFolSbs,
                    body_txtspFolSds,
                    body_catatan,
                    body_namak3,
                    ttd_k3,
                    body_namaoperator,
                    signaturePadOperator,
                    body_namasupervisor,
                    signaturePadSupervisor,
                ).enqueue(object :
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
        edgblok1 = null
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