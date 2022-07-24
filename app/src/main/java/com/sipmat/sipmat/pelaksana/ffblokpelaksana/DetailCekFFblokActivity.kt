package com.sipmat.sipmat.pelaksana.ffblokpelaksana

import android.app.ProgressDialog
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.github.gcacace.signaturepad.views.SignaturePad
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.databinding.ActivityDetailCekFfblokBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.ffblok.FFBlokModel
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

class DetailCekFFblokActivity : AppCompatActivity(), AnkoLogger {
    lateinit var binding: ActivityDetailCekFfblokBinding
    var api = ApiClient.instance()
    lateinit var sessionManager: SessionManager
    lateinit var progressDialog: ProgressDialog

    var ttd_k3: MultipartBody.Part? = null
    var signaturePadSupervisor: MultipartBody.Part? = null
    var signaturePadOperator: MultipartBody.Part? = null

    companion object {
        var cekffblok: FFBlokModel? = null
    }

    var currentDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_cek_ffblok)
        binding.lifecycleOwner = this
        sessionManager = SessionManager(this)
        progressDialog = ProgressDialog(this)
        val gson = Gson()
        cekffblok =
            gson.fromJson(
                intent.getStringExtra("cekffblok"),
                FFBlokModel::class.java
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
                cekffblok!!.tw.toString()
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
                cekffblok!!.tahun
            )

            val body_id: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                "${cekffblok!!.id}"
            )

            val sdf = SimpleDateFormat("yyyy-M-dd ")
            val tanggal_cek = sdf.format(Date())

            val body_tanggal_cek: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                "${tanggal_cek}"
            )
            //=================Service Pump===================
            //waktu pencatatan
            val txtspWpSbs = binding.txtspWpSbs.text.toString()
            val txtspWpSds = binding.txtspWpSds.text.toString()
            val body_txtspWpSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspWpSbs
            )
            val body_txtspWpSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspWpSds
            )
            //Sunction Press
            val txtspSpSbs = binding.txtspSpSbs.text.toString()
            val txtspSpSds = binding.txtspSpSds.text.toString()
            val body_txtspSpSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspSpSbs
            )
            val body_txtspSpSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspSpSds
            )
            //Discharge Press
            val txtspDpSbs = binding.txtspDpSbs.text.toString()
            val txtspDpSds = binding.txtspDpSds.text.toString()
            val body_txtspDpSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspDpSbs
            )
            val body_txtspDpSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspDpSds
            )
            //Fuel Level
            val txtspFlSbs = binding.txtspFlSbs.text.toString()
            val txtspFlSds = binding.txtspFlSds.text.toString()
            val body_txtspFlSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspFlSbs
            )
            val body_txtspFlSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspFlSds
            )
            //Auto Start
            val txtspAsSbs = binding.txtspAsSbs.text.toString()
            val txtspAsSds = binding.txtspAsSds.text.toString()
            val body_txtspAsSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspAsSbs
            )
            val body_txtspAsSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspAsSds
            )
            //=================Motor Driven===================
            //waktu pencatatan
            val txtmdWpSbs = binding.txtmdWpSbs.text.toString()
            val txtmdWpSds = binding.txtmdWpSds.text.toString()
            val body_txtmdWpSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtmdWpSbs
            )
            val body_txtmdWpSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtmdWpSds
            )
            //sunction press
            val txtmdSpSbs = binding.txtmdSpSbs.text.toString()
            val txtmdSpSds = binding.txtmdSpSds.text.toString()
            val body_txtmdSpSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtmdSpSbs
            )
            val body_txtmdSpSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtmdSpSds
            )
            //discharge press
            val txtmdDpSbs = binding.txtmdDpSbs.text.toString()
            val txtmdDpSds = binding.txtmdDpSds.text.toString()
            val body_txtmdDpSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtmdDpSbs
            )
            val body_txtmdDpSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtmdDpSds
            )
            //Fuel Level
            val txtmdFlSbs = binding.txtmdFlSbs.text.toString()
            val txtmdFlSds = binding.txtmdFlSds.text.toString()
            val body_txtmdFlSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtmdFlSbs
            )
            val body_txtmdFlSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtmdFlSds
            )
            //Auto Start
            val txtmdAsSbs = binding.txtmdAsSbs.text.toString()
            val txtmdAsSds = binding.txtmdAsSds.text.toString()
            val body_txtmdAsSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtmdAsSbs
            )
            val body_txtmdAsSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtmdAsSds
            )
            //=================Diesel Engine===================
            //waktu pencatatan
            val de_waktu_pencatatan_sebelum_start = binding.txtdeWpSbs.text.toString().trim()
            val de_waktu_pencatatan_sesudah_start = binding.txtdeWpSds.text.toString().trim()
            val body_de_waktu_pencatatan_sebelum_start: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                de_waktu_pencatatan_sebelum_start
            )
            val body_de_waktu_pencatatan_sesudah_start: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                de_waktu_pencatatan_sesudah_start
            )
            //Lube Oil Press
            val txtdeLubeOilPressSbs = binding.txtdeLubeOilPressSbs.text.toString().trim()
            val txtdeLubeOilPressSds = binding.txtdeLubeOilPressSds.text.toString().trim()
            val body_txtdeLubeOilPressSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtdeLubeOilPressSbs
            )
            val body_txtdeLubeOilPressSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtdeLubeOilPressSds
            )
            //Battery VOltage
            val de_battery_voltage_sebelum_start =
                binding.txtdeBatteryVoltageSebelumStart.text.toString().trim()
            val de_battery_voltage_sesudah_start =
                binding.txtdeBatteryVoltageSesudahStart.text.toString().trim()
            val body_de_battery_voltage_sebelum_start: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                de_battery_voltage_sebelum_start
            )
            val body_de_battery_voltage_sesudah_start: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                de_battery_voltage_sesudah_start
            )
            //Battery Ampere
            val de_battery_ampere_sebelum_start =
                binding.txtdeBatteryAmpereSebelumStart.text.toString().trim()
            val de_battery_ampere_sesudah_start =
                binding.txtdeBatteryAmpereSesudahStart.text.toString().trim()
            val body_de_battery_ampere_sebelum_start: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                de_battery_ampere_sebelum_start
            )
            val body_de_battery_ampere_sesudah_start: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                de_battery_ampere_sesudah_start
            )
            //Battery Level
            val txtdeBatteryLevelSebelumStart =
                binding.txtdeBatteryLevelSebelumStart.text.toString().trim()
            val txtdeBatteryLevelSesudahStart =
                binding.txtdeBatteryLevelSesudahStart.text.toString().trim()
            val body_txtdeBatteryLevelSebelumStart: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtdeBatteryLevelSebelumStart
            )
            val body_txtdeBatteryLevelSesudahStart: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtdeBatteryLevelSesudahStart
            )
            //Fuel Level
            val txtdeFuelLevelSebelumStart =
                binding.txtdeFuelLevelSebelumStart.text.toString().trim()
            val txtdeFuelLevelSesudahStart =
                binding.txtdeFuelLevelSesudahStart.text.toString().trim()
            val body_txtdeFuelLevelSebelumStart: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtdeFuelLevelSebelumStart
            )
            val body_txtdeFuelLevelSesudahStart: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtdeFuelLevelSesudahStart
            )
            //Lube Oil Level
            val txtdeLubeOilLevelSbs =
                binding.txtdeLubeOilLevelSbs.text.toString().trim()
            val txtdeLubeOilLevelSds =
                binding.txtdeLubeOilLevelSds.text.toString().trim()
            val body_txtdeLubeOilLevelSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtdeLubeOilLevelSbs
            )
            val body_txtdeLubeOilLevelSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtdeLubeOilLevelSds
            )
            //Water Cooler level
            val txtdeWaterCoolerLevelSbs =
                binding.txtdeWaterCoolerLevelSbs.text.toString().trim()
            val txtdeWaterCoolerLevelSds =
                binding.txtdeWaterCoolerLevelSds.text.toString().trim()
            val body_txtdeWaterCoolerLevelSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtdeWaterCoolerLevelSbs
            )
            val body_txtdeWaterCoolerLevelSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtdeWaterCoolerLevelSds
            )
            //Speed
            val txtdeSpeedSbs =
                binding.txtdeSpeedSbs.text.toString().trim()
            val txtdeSpeedSds =
                binding.txtdeSpeedSds.text.toString().trim()
            val body_txtdeSpeedSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtdeSpeedSbs
            )
            val body_txtdeSpeedSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtdeSpeedSds
            )
            //Sunction Press
            val de_suction_press_sebelum_start =
                binding.txtdeSuctionPressSebelumStart.text.toString().trim()
            val de_suction_press_sesudah_start =
                binding.txtdeSuctionPressSesudahStart.text.toString().trim()
            val body_de_suction_press_sebelum_start: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                de_suction_press_sebelum_start
            )
            val body_de_suction_press_sesudah_start: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                de_suction_press_sesudah_start
            )
            //Discharge Press
            val de_discharge_press_sebelum_start =
                binding.txtdeDischargePressSebelumStart.text.toString().trim()
            val de_discharge_press_sesudah_start =
                binding.txtdeDischargePressSesudahStart.text.toString().trim()
            val body_de_discharge_press_sebelum_start: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                de_discharge_press_sebelum_start
            )
            val body_de_discharge_press_sesudah_start: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                de_discharge_press_sesudah_start
            )
            //Auto Start
            val txtdeAutoStartSbs =
                binding.txtdeAutoStartSbs.text.toString().trim()
            val txtdeAutoStartSds =
                binding.txtdeAutoStartSds.text.toString().trim()
            val body_txtdeAutoStartSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtdeAutoStartSbs
            )
            val body_txtdeAutoStartSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtdeAutoStartSds
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
            if (txtspWpSbs.isNotEmpty() &&
                txtspWpSds.isNotEmpty() &&
                txtspSpSbs.isNotEmpty() &&
                txtspSpSds.isNotEmpty() &&
                txtspDpSbs.isNotEmpty() &&
                txtspDpSds.isNotEmpty() &&
                txtspFlSbs.isNotEmpty() &&
                txtspFlSds.isNotEmpty() &&
                txtspAsSbs.isNotEmpty() &&
                txtspAsSds.isNotEmpty() &&
                txtspFlSbs.isNotEmpty() &&
                txtspFlSds.isNotEmpty() &&
                txtspAsSbs.isNotEmpty() &&
                txtspAsSds.isNotEmpty() &&
                txtmdWpSbs.isNotEmpty() &&
                txtmdWpSds.isNotEmpty() &&
                txtmdDpSbs.isNotEmpty() &&
                txtmdDpSds.isNotEmpty() &&
                txtmdSpSbs.isNotEmpty() &&
                txtmdSpSds.isNotEmpty() &&
                txtmdFlSbs.isNotEmpty() &&
                txtmdFlSds.isNotEmpty() &&
                txtmdAsSbs.isNotEmpty() &&
                txtmdAsSds.isNotEmpty() &&
                de_waktu_pencatatan_sebelum_start.isNotEmpty() &&
                de_waktu_pencatatan_sesudah_start.isNotEmpty() &&
                txtdeLubeOilPressSbs.isNotEmpty() &&
                txtdeLubeOilPressSds.isNotEmpty() &&
                de_battery_voltage_sebelum_start.isNotEmpty() &&
                de_battery_voltage_sesudah_start.isNotEmpty() &&
                de_battery_ampere_sebelum_start.isNotEmpty() &&
                de_battery_ampere_sesudah_start.isNotEmpty() &&
                txtdeBatteryLevelSebelumStart.isNotEmpty() &&
                txtdeBatteryLevelSesudahStart.isNotEmpty() &&
                txtdeFuelLevelSebelumStart.isNotEmpty() &&
                txtdeFuelLevelSesudahStart.isNotEmpty() &&
                txtdeLubeOilLevelSbs.isNotEmpty() &&
                txtdeLubeOilLevelSds.isNotEmpty() &&
                txtdeWaterCoolerLevelSbs.isNotEmpty() &&
                txtdeWaterCoolerLevelSds.isNotEmpty() &&
                txtdeSpeedSbs.isNotEmpty() &&
                txtdeSpeedSds.isNotEmpty() &&
                txtdeAutoStartSbs.isNotEmpty() &&
                txtdeAutoStartSds.isNotEmpty() &&
                de_suction_press_sebelum_start.isNotEmpty() &&
                de_suction_press_sesudah_start.isNotEmpty() &&
                de_discharge_press_sebelum_start.isNotEmpty() &&
                de_discharge_press_sesudah_start.isNotEmpty() &&
                catatan.isNotEmpty() &&
                namak3.isNotEmpty() &&
                namaoperator.isNotEmpty() &&
                namasupervisor.isNotEmpty() &&
                signaturePadSupervisor != null &&
                signaturePadOperator != null &&
                ttd_k3 != null
            ) {
                loading(true)

                api.update_ffblok(
                    body_id,
                    body_tw,
                    body_tahun,
                    body_tanggal_cek,
                    body_shift,
                    body_is_status,
                    body_txtspWpSbs,
                    body_txtspWpSds,
                    body_txtspSpSbs,
                    body_txtspSpSds,
                    body_txtspDpSbs,
                    body_txtspDpSds,
                    body_txtspFlSbs,
                    body_txtspFlSds,
                    body_txtspAsSbs,
                    body_txtspAsSds,
                    body_txtmdWpSbs,
                    body_txtmdWpSds,
                    body_txtmdSpSbs,
                    body_txtmdSpSds,
                    body_txtmdDpSbs,
                    body_txtmdDpSds,
                    body_txtmdFlSbs,
                    body_txtmdFlSds,
                    body_txtmdAsSbs,
                    body_txtmdAsSds,
                    body_de_waktu_pencatatan_sebelum_start,
                    body_de_waktu_pencatatan_sesudah_start,
                    body_txtdeLubeOilPressSbs,
                    body_txtdeLubeOilPressSds,
                    body_de_battery_voltage_sebelum_start,
                    body_de_battery_voltage_sesudah_start,
                    body_de_battery_ampere_sebelum_start,
                    body_de_battery_ampere_sesudah_start,
                    body_txtdeBatteryLevelSebelumStart,
                    body_txtdeBatteryLevelSesudahStart,
                    body_txtdeFuelLevelSebelumStart,
                    body_txtdeFuelLevelSesudahStart,
                    body_txtdeLubeOilLevelSbs,
                    body_txtdeLubeOilLevelSds,
                    body_txtdeWaterCoolerLevelSbs,
                    body_txtdeWaterCoolerLevelSds,
                    body_txtdeSpeedSbs,
                    body_txtdeSpeedSds,
                    body_de_suction_press_sebelum_start,
                    body_de_suction_press_sesudah_start,
                    body_de_discharge_press_sebelum_start,
                    body_de_discharge_press_sesudah_start,
                    body_txtdeAutoStartSbs,
                    body_txtdeAutoStartSds,
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
        cekffblok = null
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