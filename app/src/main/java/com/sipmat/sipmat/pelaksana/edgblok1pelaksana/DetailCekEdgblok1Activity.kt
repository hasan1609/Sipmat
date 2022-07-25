package com.sipmat.sipmat.pelaksana.edgblok1pelaksana

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

class DetailCekEdgblok1Activity : AppCompatActivity(), AnkoLogger {
    lateinit var binding: ActivityDetailCekEdgblok1Binding
    var api = ApiClient.instance()
    lateinit var sessionManager: SessionManager
    lateinit var progressDialog: ProgressDialog

    var ttd_k3: MultipartBody.Part? = null
    var signaturePadSupervisor: MultipartBody.Part? = null
    var signaturePadOperator: MultipartBody.Part? = null
    var gb_sebelum: String? = null
    fun gb_sebelum() {
        val datakelamin = arrayOf("Open", "Close")
        val spinner = find<Spinner>(R.id.txtp_gb_sbs)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, datakelamin
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    gb_sebelum = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var gb_sesudah: String? = null
    fun gb_sesudah() {
        val datakelamin = arrayOf("Open", "Close")
        val spinner = find<Spinner>(R.id.txtsp_gb_sds)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, datakelamin
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    gb_sesudah = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var lube_oil_sebelum: String? = null
    fun lube_oil_sebelum() {
        val datakelamin = arrayOf("Low", "Normal", "Full")
        val spinner = find<Spinner>(R.id.txtp_lo_sbs)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, datakelamin
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    lube_oil_sebelum = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var lube_oil_sesudah: String? = null
    fun lube_oil_sesudah() {
        val datakelamin = arrayOf("Low", "Normal", "Full")
        val spinner = find<Spinner>(R.id.txtsp_lo_sds)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, datakelamin
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    lube_oil_sesudah = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var accu_level_sebelum: String? = null
    fun accu_level_sebelum() {
        val datakelamin = arrayOf("Low", "Normal", "Full")
        val spinner = find<Spinner>(R.id.txtp_al_sbs)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, datakelamin
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    accu_level_sebelum = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var accu_level_sesudah: String? = null
    fun accu_level_sesudah() {
        val datakelamin = arrayOf("Low", "Normal", "Full")
        val spinner = find<Spinner>(R.id.txtsp_al_sds)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, datakelamin
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    accu_level_sesudah = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var radiator_sebelum: String? = null
    fun radiator_sebelum() {
        val datakelamin = arrayOf("Low", "Normal", "Full")
        val spinner = find<Spinner>(R.id.txtp_rl_sbs)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, datakelamin
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    radiator_sebelum = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var radiator_sesudah: String? = null
    fun radiator_sesudah() {
        val datakelamin = arrayOf("Low", "Normal", "Full")
        val spinner = find<Spinner>(R.id.txtsp_rl_sds)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, datakelamin
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    radiator_sesudah = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var fuel_oil_sebelum: String? = null
    fun fuel_oil_sebelum() {
        val datakelamin = arrayOf("Low", "Normal", "Full")
        val spinner = find<Spinner>(R.id.txtp_fol_sbs)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, datakelamin
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    fuel_oil_sebelum = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var fuel_oil_sesudah: String? = null
    fun fuel_oil_sesudah() {
        val datakelamin = arrayOf("Low", "Normal", "Full")
        val spinner = find<Spinner>(R.id.txtsp_fol_sds)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, datakelamin
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    fuel_oil_sesudah = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }

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
        gb_sebelum()
        gb_sesudah()
        lube_oil_sebelum()
        lube_oil_sesudah()
        accu_level_sebelum()
        accu_level_sesudah()
        radiator_sebelum()
        radiator_sesudah()
        fuel_oil_sebelum()
        fuel_oil_sesudah()
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
            val body_txtpGbSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                gb_sebelum
            )
            val body_txtspGbSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                gb_sesudah
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
            val body_txtpLoSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                lube_oil_sebelum
            )
            val body_txtspLoSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                lube_oil_sesudah
            )
//            accu lev
            val body_txtpAlSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                accu_level_sebelum
            )
            val body_txtspAlSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                accu_level_sesudah
            )
//            radiator lev
            val body_txtpRlSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                radiator_sebelum
            )
            val body_txtspRlSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                radiator_sesudah
            )
//            fuel oil level
            val body_txtpFolSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                fuel_oil_sebelum
            )
            val body_txtspFolSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                fuel_oil_sesudah
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