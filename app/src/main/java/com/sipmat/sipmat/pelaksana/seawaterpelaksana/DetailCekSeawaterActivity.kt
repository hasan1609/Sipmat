package com.sipmat.sipmat.pelaksana.seawaterpelaksana

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import com.github.gcacace.signaturepad.views.SignaturePad
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sipmat.sipmat.R
import com.sipmat.sipmat.databinding.ActivityDetailCekSeawaterBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.seawater.SeaWaterModel
import com.sipmat.sipmat.session.SessionManager
import com.sipmat.sipmat.webservice.ApiClient
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class DetailCekSeawaterActivity : AppCompatActivity(), AnkoLogger {
    lateinit var binding: ActivityDetailCekSeawaterBinding
    var api = ApiClient.instance()
    lateinit var sessionManager: SessionManager
    lateinit var progressDialog: ProgressDialog

    var spnmd_full_level_sebelum_start: String? = null
    var spnmd_full_level_sesudah_start: String? = null
    var spnde_battery_level_sebelum_start: String? = null
    var spnde_battery_level_sesudah_start: String? = null
    var spnde_fuel_level_sebelum_start: String? = null
    var spnde_fuel_level_sesudah_start: String? = null
    var spnde_crankcase_sebelum_start: String? = null
    var spnde_crankcase_sesudah_start: String? = null
    var spnde_engine_coolant_tank_sebelum_start: String? = null
    var spnde_engine_coolant_tank_sesudah_start: String? = null
    var spnde_cooling_water_inlet_valve_sebelum_start: String? = null
    var spnde_cooling_water_inlet_valve_sesudah_start: String? = null

    var ttd_k3:MultipartBody.Part? = null
    var signaturePadSupervisor:MultipartBody.Part? = null
    var signaturePadOperator:MultipartBody.Part? = null
    companion object {
        var cekseawater: SeaWaterModel? = null

    }

    var currentDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_cek_seawater)
        binding.lifecycleOwner = this

        val gson = Gson()
        cekseawater =
            gson.fromJson(
                intent.getStringExtra("cekseawater"),
                SeaWaterModel::class.java
            )

        val sdf = SimpleDateFormat("yyyy-M-dd")
        currentDate = sdf.format(Date())
        binding.txltglPemeriksa.text = "Tgl Pemeriksa : $currentDate"

        sessionManager = SessionManager(this)
        progressDialog = ProgressDialog(this)

        binding.txtshift.text = "Shift : ${sessionManager.getNama()}"

        spnmd_full_level_sebelum_start()
        spnmd_full_level_sesudah_start()
        spnde_battery_level_sebelum_start()
        spnde_battery_level_sesudah_start()
        spnde_fuel_level_sebelum_start()
        spnde_fuel_level_sesudah_start()
        spnde_crankcase_sebelum_start()
        spnde_crankcase_sesudah_start()
        spnde_engine_coolant_tank_sebelum_start()
        spnde_engine_coolant_tank_sesudah_start()
        spnde_cooling_water_inlet_valve_sebelum_start()
        spnde_cooling_water_inlet_valve_sesudah_start()

        binding.signaturePadK3.setOnSignedListener(object : SignaturePad.OnSignedListener {
            override fun onStartSigning() {
            }

            override fun onSigned() {

                //create a file to write bitmap data
                //create a file to write bitmap data
                val f: File = File(cacheDir,"foto")
                f.createNewFile()

                //Convert bitmap
                //ini bitmapnya
                val bitmap = binding.signaturePadK3.transparentSignatureBitmap
                val bos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG,0/*ignored for PNG*/, bos)
                val bitmapdata = bos.toByteArray()
                val foto = bitmapToBytes(bitmap)

                val reqFile = RequestBody.create(MediaType.parse("image/*"),foto)
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
                val f: File = File(cacheDir,"foto")
                f.createNewFile()

                //Convert bitmap
                //ini bitmapnya
                val bitmap = binding.signaturePadOperator.transparentSignatureBitmap
                val bos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG,0/*ignored for PNG*/, bos)
                val bitmapdata = bos.toByteArray()
                val foto = bitmapToBytes(bitmap)

                val reqFile = RequestBody.create(MediaType.parse("image/*"),foto)
                signaturePadOperator = MultipartBody.Part.createFormData("operator_ttd", f.name, reqFile)

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
                val f: File = File(cacheDir,"foto")
                f.createNewFile()

                //Convert bitmap
                //ini bitmapnya
                val bitmap = binding.signaturePadSupervisor.transparentSignatureBitmap
                val bos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG,0/*ignored for PNG*/, bos)
                val bitmapdata = bos.toByteArray()
                val foto = bitmapToBytes(bitmap)

                val reqFile = RequestBody.create(MediaType.parse("image/*"),foto)
                signaturePadSupervisor = MultipartBody.Part.createFormData("supervisor_ttd", f.name, reqFile)

            }

            override fun onClear() {

            }

        })

        binding.btnsubmit.setOnClickListener {
            info { "dinda $ttd_k3" }
            val md_waktu_pencatatan_sebelum_start = binding.txtmdWpSbs.text.toString().trim()
            val md_waktu_pencatatan_sesudah_start = binding.txtmdWpSds.text.toString().trim()
            val md_discharge_press_sebelum_start = binding.txtmdDpSbs.text.toString().trim()
            val md_discharge_press_sesudah_start = binding.txtmddppSds.text.toString().trim()
            val de_waktu_pencatatan_sebelum_start = binding.txtdeWpSbs.text.toString().trim()
            val de_waktu_pencatatan_sesudah_start = binding.txtdeWpSds.text.toString().trim()
            val de_engine_operating_hours_sebelum_start =
                binding.txtdeEngineOperatingHoursSebelumStart.text.toString().trim()
            val de_engine_operating_hours_sesudah_start =
                binding.txtdeEngineOperatingHoursSesudahStart.text.toString().trim()
            val de_battery_voltage_sebelum_start =
                binding.txtdeBatteryVoltageSebelumStart.text.toString().trim()
            val de_battery_voltage_sesudah_start =
                binding.txtdeBatteryVoltageSesudahStart.text.toString().trim()
            val de_battery_ampere_sebelum_start =
                binding.txtdeBatteryAmpereSebelumStart.text.toString().trim()
            val de_battery_ampere_sesudah_start =
                binding.txtdeBatteryAmpereSesudahStart.text.toString().trim()
            val de_oil_pressure_sebelum_start =
                binding.txtdeOilPressureSebelumStart.text.toString().trim()
            val de_oil_pressure_sesudah_start =
                binding.txtdeOilPressureSesudahStart.text.toString().trim()
            val de_cooling_water_pressure_after_regulator_sebelum_start =
                binding.deCoolingWaterPressureAfterRegulatorSebelumStart.text.toString().trim()
            val de_cooling_water_pressure_after_regulator_sesudah_start =
                binding.deCoolingWaterPressureAfterRegulatorSesudahStart.text.toString().trim()
            val de_coolant_temperature_sebelum_start =
                binding.txtdeCoolantTemperatureSebelumStart.text.toString().trim()
            val de_coolant_temperature_sesudah_start =
                binding.txtdeCoolantTemperatureSesudahStart.text.toString().trim()
            val de_speed_sebelum_start = binding.txtdeSpeedSebelumStart.text.toString().trim()
            val de_speed_sesudah_start = binding.txtdeSpeedSesudahStart.text.toString().trim()
            val de_suction_press_sebelum_start =
                binding.txtdeSuctionPressSebelumStart.text.toString().trim()
            val de_suction_press_sesudah_start =
                binding.txtdeSuctionPressSesudahStart.text.toString().trim()
            val de_discharge_press_sebelum_start =
                binding.txtdeDischargePressSebelumStart.text.toString().trim()
            val de_discharge_press_sesudah_start =
                binding.txtdeDischargePressSesudahStart.text.toString().trim()
            val catatan = binding.txtcatatan.text.toString().trim()
            val namak3 = binding.txtnamak3.text.toString().trim()
            val namaoperator = binding.txtnamaoperator.text.toString().trim()
            val namasupervisor = binding.txtnamasupervisor.text.toString().trim()

            if (md_waktu_pencatatan_sebelum_start.isNotEmpty() &&
                md_waktu_pencatatan_sesudah_start.isNotEmpty() &&
                md_discharge_press_sebelum_start.isNotEmpty() &&
                md_discharge_press_sesudah_start.isNotEmpty() &&
                de_waktu_pencatatan_sebelum_start.isNotEmpty() &&
                de_waktu_pencatatan_sesudah_start.isNotEmpty() &&
                de_engine_operating_hours_sebelum_start.isNotEmpty() &&
                de_engine_operating_hours_sesudah_start.isNotEmpty() &&
                de_battery_voltage_sebelum_start.isNotEmpty() &&
                de_battery_voltage_sesudah_start.isNotEmpty() &&
                de_battery_ampere_sebelum_start.isNotEmpty() &&
                de_battery_ampere_sesudah_start.isNotEmpty() &&
                de_oil_pressure_sebelum_start.isNotEmpty() &&
                de_oil_pressure_sesudah_start.isNotEmpty() &&
                de_cooling_water_pressure_after_regulator_sebelum_start.isNotEmpty() &&
                de_cooling_water_pressure_after_regulator_sesudah_start.isNotEmpty() &&
                de_coolant_temperature_sebelum_start.isNotEmpty() &&
                de_coolant_temperature_sesudah_start.isNotEmpty() &&
                de_speed_sebelum_start.isNotEmpty() &&
                de_speed_sesudah_start.isNotEmpty() &&
                de_suction_press_sebelum_start.isNotEmpty() &&
                de_suction_press_sesudah_start.isNotEmpty() &&
                de_discharge_press_sebelum_start.isNotEmpty() &&
                de_discharge_press_sesudah_start.isNotEmpty() &&
                catatan.isNotEmpty() &&
                namak3.isNotEmpty() &&
                namaoperator.isNotEmpty() &&
                namasupervisor.isNotEmpty() &&
                signaturePadSupervisor!=null &&
                signaturePadOperator!=null &&
                ttd_k3!=null
            ) {
                loading(true)

                val body_md_waktu_pencatatan_sebelum_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    md_waktu_pencatatan_sebelum_start
                )
                val body_md_waktu_pencatatan_sesudah_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    md_waktu_pencatatan_sesudah_start
                )
                val body_md_discharge_press_sebelum_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    md_discharge_press_sebelum_start
                )
                val body_md_discharge_press_sesudah_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    md_discharge_press_sesudah_start
                )
                val body_de_waktu_pencatatan_sebelum_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    de_waktu_pencatatan_sebelum_start
                )
                val body_de_waktu_pencatatan_sesudah_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    de_waktu_pencatatan_sesudah_start
                )
                val body_de_engine_operating_hours_sebelum_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                de_engine_operating_hours_sebelum_start
                )
                val body_de_engine_operating_hours_sesudah_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    de_engine_operating_hours_sesudah_start
                )
                val body_de_battery_voltage_sebelum_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    de_battery_voltage_sebelum_start
                )
                val body_de_battery_voltage_sesudah_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    de_battery_voltage_sesudah_start
                )
                val body_de_battery_ampere_sebelum_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    de_battery_ampere_sebelum_start
                )
                val body_de_battery_ampere_sesudah_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    de_battery_ampere_sesudah_start
                )
                val body_de_oil_pressure_sebelum_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    de_oil_pressure_sebelum_start
                )
                val body_de_oil_pressure_sesudah_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    de_oil_pressure_sesudah_start
                )
                val body_de_cooling_water_pressure_after_regulator_sebelum_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    de_cooling_water_pressure_after_regulator_sebelum_start
                )
                val body_de_cooling_water_pressure_after_regulator_sesudah_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    de_cooling_water_pressure_after_regulator_sesudah_start
                )
                val body_de_coolant_temperature_sebelum_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    de_coolant_temperature_sebelum_start
                )
                val body_de_coolant_temperature_sesudah_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    de_coolant_temperature_sesudah_start
                )
                val body_de_speed_sebelum_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    de_speed_sebelum_start
                )
                val body_de_speed_sesudah_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    de_speed_sesudah_start
                )
                val body_de_suction_press_sebelum_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    de_suction_press_sebelum_start
                )
                val body_de_suction_press_sesudah_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    de_suction_press_sesudah_start
                )
                val body_de_discharge_press_sebelum_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    de_discharge_press_sebelum_start
                )
                val body_de_discharge_press_sesudah_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    de_discharge_press_sesudah_start
                )
                val body_catatan: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    catatan
                )
                val body_namak3: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    namak3
                )
                val body_namaoperator: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    namaoperator
                )
                val body_namasupervisor: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    namasupervisor
                )

                val body_tw: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    cekseawater!!.tw
                )

                val body_shift: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    sessionManager.getNama().toString()
                )
                val body_spnde_engine_coolant_tank_sesudah_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    spnde_engine_coolant_tank_sesudah_start
                )

                val body_spnde_engine_coolant_tank_sebelum_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    spnde_engine_coolant_tank_sebelum_start
                )

                val body_is_status: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),"1"
                )
                val body_spnde_battery_level_sesudah_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    spnde_battery_level_sesudah_start
                )
                val body_spnde_battery_level_sebelum_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    spnde_battery_level_sebelum_start
                )

                val body_spnmd_full_level_sebelum_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    spnmd_full_level_sebelum_start
                )
                val body_spnde_fuel_level_sebelum_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    spnde_fuel_level_sebelum_start
                )
                val body_tahun: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    cekseawater!!.tahun
                )
                val body_spnde_cooling_water_inlet_valve_sesudah_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    spnde_cooling_water_inlet_valve_sesudah_start
                )
                val body_spnde_fuel_level_sesudah_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    spnde_fuel_level_sesudah_start
                )
                val body_spnmd_full_level_sesudah_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    spnmd_full_level_sesudah_start
                )
                val body_spnde_cooling_water_inlet_valve_sebelum_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    spnde_cooling_water_inlet_valve_sebelum_start
                )
                val body_spnde_crankcase_sebelum_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    spnde_crankcase_sebelum_start
                )
                val body_spnde_crankcase_sesudah_start: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    spnde_crankcase_sesudah_start
                )
                val body_id: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    "${cekseawater!!.id}"
                )
                val body_tgl_pemeriksa: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    currentDate
                )
                api.update_seawater(
                    body_id,
                    body_tw,
                    body_tahun,
                    body_shift,
                    body_is_status,
                    body_tgl_pemeriksa,
                    body_md_waktu_pencatatan_sebelum_start,
                    body_md_waktu_pencatatan_sesudah_start,
                    body_md_discharge_press_sebelum_start,
                    body_md_discharge_press_sesudah_start,
                    body_spnmd_full_level_sebelum_start,
                    body_spnmd_full_level_sesudah_start,
                    body_de_waktu_pencatatan_sebelum_start,
                    body_de_waktu_pencatatan_sesudah_start,
                    body_de_engine_operating_hours_sebelum_start,
                    body_de_engine_operating_hours_sesudah_start,
                    body_de_battery_voltage_sebelum_start,
                    body_de_battery_voltage_sesudah_start,
                    body_de_battery_ampere_sebelum_start,
                    body_de_battery_ampere_sesudah_start,
                    body_spnde_battery_level_sebelum_start,
                    body_spnde_battery_level_sesudah_start,
                    body_spnde_fuel_level_sebelum_start,
                    body_spnde_fuel_level_sesudah_start,
                    body_spnde_engine_coolant_tank_sebelum_start,
                    body_spnde_engine_coolant_tank_sesudah_start,
                    body_spnde_cooling_water_inlet_valve_sebelum_start,
                    body_spnde_cooling_water_inlet_valve_sesudah_start,
                    body_de_oil_pressure_sebelum_start,
                    body_de_oil_pressure_sesudah_start,
                    body_de_cooling_water_pressure_after_regulator_sebelum_start,
                    body_de_cooling_water_pressure_after_regulator_sesudah_start,
                    body_de_coolant_temperature_sebelum_start,
                    body_de_coolant_temperature_sesudah_start,
                    body_de_speed_sebelum_start,
                    body_de_speed_sesudah_start,
                    body_de_suction_press_sebelum_start,
                    body_de_suction_press_sesudah_start,
                    body_de_discharge_press_sebelum_start,
                    body_de_discharge_press_sesudah_start,
                    body_catatan,
                    body_namak3,
                    ttd_k3,
                    body_namaoperator,
                    signaturePadOperator,
                    body_namasupervisor,
                    signaturePadSupervisor,
                    body_spnde_crankcase_sebelum_start,
                    body_spnde_crankcase_sesudah_start

                ) .enqueue(object :
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
        cekseawater = null
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

    fun spnmd_full_level_sebelum_start() {
        val datakelamin = arrayOf("LOW", "NORMAL", "FULL")
        val spinner = find<Spinner>(R.id.spnmd_fl_sbs)
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
                    spnmd_full_level_sebelum_start = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }
    }

    fun spnmd_full_level_sesudah_start() {
        val datakelamin = arrayOf("LOW", "NORMAL", "FULL")
        val spinner = find<Spinner>(R.id.spnmd_fl_sds)
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
                    spnmd_full_level_sesudah_start = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }
    }

    fun spnde_battery_level_sebelum_start() {
        val datakelamin = arrayOf("LOW", "NORMAL", "FULL")
        val spinner = find<Spinner>(R.id.spnde_battery_level_sebelum_start)
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
                    spnde_battery_level_sebelum_start = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }
    }

    fun spnde_battery_level_sesudah_start() {
        val datakelamin = arrayOf("LOW", "NORMAL", "FULL")
        val spinner = find<Spinner>(R.id.spnde_battery_level_sesudah_start)
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
                    spnde_battery_level_sesudah_start = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }
        fun spnde_fuel_level_sebelum_start() {
            val datakelamin = arrayOf("LOW", "NORMAL", "FULL")
            val spinner = find<Spinner>(R.id.spnde_fuel_level_sebelum_start)
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
                        spnde_fuel_level_sebelum_start = datakelamin[position]
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // write code to perform some action
                    }
                }

            }
        }
    }

    fun spnde_fuel_level_sebelum_start() {
        val datakelamin = arrayOf("LOW", "NORMAL", "FULL")
        val spinner = find<Spinner>(R.id.spnde_fuel_level_sebelum_start)
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
                    spnde_fuel_level_sebelum_start = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }
    }

    fun spnde_fuel_level_sesudah_start() {
        val datakelamin = arrayOf("LOW", "NORMAL", "FULL")
        val spinner = find<Spinner>(R.id.spnde_fuel_level_sesudah_start)
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
                    spnde_fuel_level_sesudah_start = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }
        fun spnde_fuel_level_sebelum_start() {
            val datakelamin = arrayOf("LOW", "NORMAL", "FULL")
            val spinner = find<Spinner>(R.id.spnde_fuel_level_sebelum_start)
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
                        spnde_fuel_level_sebelum_start = datakelamin[position]
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // write code to perform some action
                    }
                }

            }
        }
    }

    fun spnde_crankcase_sebelum_start() {
        val datakelamin = arrayOf("LOW", "NORMAL", "FULL")
        val spinner = find<Spinner>(R.id.spnde_crankcase_sebelum_start)
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
                    spnde_crankcase_sebelum_start = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }
        fun spnde_fuel_level_sebelum_start() {
            val datakelamin = arrayOf("LOW", "NORMAL", "FULL")
            val spinner = find<Spinner>(R.id.spnde_fuel_level_sebelum_start)
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
                        spnde_fuel_level_sebelum_start = datakelamin[position]
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // write code to perform some action
                    }
                }

            }
        }
    }

    fun spnde_crankcase_sesudah_start() {
        val datakelamin = arrayOf("LOW", "NORMAL", "FULL")
        val spinner = find<Spinner>(R.id.spnde_crankcase_sesudah_start)
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
                    spnde_crankcase_sesudah_start = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }

    fun spnde_engine_coolant_tank_sebelum_start() {
        val datakelamin = arrayOf("LOW", "NORMAL", "FULL")
        val spinner = find<Spinner>(R.id.spnde_engine_coolant_tank_sebelum_start)
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
                    spnde_engine_coolant_tank_sebelum_start = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }

    fun spnde_engine_coolant_tank_sesudah_start() {
        val datakelamin = arrayOf("LOW", "NORMAL", "FULL")
        val spinner = find<Spinner>(R.id.spnde_engine_coolant_tank_sesudah_start)
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
                    spnde_engine_coolant_tank_sesudah_start = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }

    fun spnde_cooling_water_inlet_valve_sebelum_start() {
        val datakelamin = arrayOf("Open", "NORMAL", "Close")
        val spinner = find<Spinner>(R.id.spnde_cooling_water_inlet_valve_sebelum_start)
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
                    spnde_cooling_water_inlet_valve_sebelum_start = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }

    fun spnde_cooling_water_inlet_valve_sesudah_start() {
        val datakelamin = arrayOf("Open", "NORMAL", "Close")
        val spinner = find<Spinner>(R.id.spnde_cooling_water_inlet_valve_sesudah_start)
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
                    spnde_cooling_water_inlet_valve_sesudah_start = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }

    fun bitmapToBytes(photo: Bitmap): ByteArray? {
        val stream = ByteArrayOutputStream()
        photo.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

}