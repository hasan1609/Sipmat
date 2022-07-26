package com.sipmat.sipmat.pelaksana.ffblok2pelaksana

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
import com.sipmat.sipmat.databinding.ActivityDetailCekFfblok2Binding
import com.sipmat.sipmat.databinding.ActivityDetailCekFfblokBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.ffblok.FFBlokModel
import com.sipmat.sipmat.model.ffblok2.FFBlok2Model
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

class DetailCekFFblok2Activity : AppCompatActivity(), AnkoLogger {
    lateinit var binding: ActivityDetailCekFfblok2Binding
    var api = ApiClient.instance()
    lateinit var sessionManager: SessionManager
    lateinit var progressDialog: ProgressDialog

    var ttd_k3: MultipartBody.Part? = null
    var signaturePadSupervisor: MultipartBody.Part? = null
    var signaturePadOperator: MultipartBody.Part? = null

    var auto_start_sebelum: String? = null
    fun auto_start_sebelum() {
        val datakelamin = arrayOf("Low", "Normal", "Full")
        val spinner = find<Spinner>(R.id.txtsp_as_sbs)
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
                    auto_start_sebelum = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var auto_start_sesudah: String? = null
    fun auto_start_sesudah() {
        val datakelamin = arrayOf("Low", "Normal", "Full")
        val spinner = find<Spinner>(R.id.txtsp_as_sds)
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
                    auto_start_sesudah = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var fuel_level_sebelum: String? = null
    fun fuel_level_sebelum() {
        val datakelamin = arrayOf("Low", "Normal", "Full")
        val spinner = find<Spinner>(R.id.txtmd_fl_sbs)
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
                    fuel_level_sebelum = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var fuel_level_sesudah: String? = null
    fun fuel_level_sesudah() {
        val datakelamin = arrayOf("Low", "Normal", "Full")
        val spinner = find<Spinner>(R.id.txtmd_fl_sds)
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
                    fuel_level_sesudah = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var battery_level_sebelum: String? = null
    fun battery_level_sebelum() {
        val datakelamin = arrayOf("Low", "Normal", "Full")
        val spinner = find<Spinner>(R.id.txtde_battery_level_sebelum_start)
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
                    battery_level_sebelum = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var battery_level_sesudah: String? = null
    fun battery_level_sesudah() {
        val datakelamin = arrayOf("Low", "Normal", "Full")
        val spinner = find<Spinner>(R.id.txtde_battery_level_sesudah_start)
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
                    battery_level_sesudah = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var de_fuel_level_sebelum: String? = null
    fun de_fuel_level_sebelum() {
        val datakelamin = arrayOf("Low", "Normal", "Full")
        val spinner = find<Spinner>(R.id.txtde_fuel_level_sebelum_start)
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
                    de_fuel_level_sebelum = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var de_fuel_level_sesudah: String? = null
    fun de_fuel_level_sesudah() {
        val datakelamin = arrayOf("Low", "Normal", "Full")
        val spinner = find<Spinner>(R.id.txtde_fuel_level_sesudah_start)
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
                    de_fuel_level_sesudah = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var de_lube_oil_level_sebelum: String? = null
    fun de_lube_oil_level_sebelum() {
        val datakelamin = arrayOf("Low", "Normal", "Full")
        val spinner = find<Spinner>(R.id.txtde_lube_oil_level_sbs)
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
                    de_lube_oil_level_sebelum = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var de_lube_oil_level_sesudah: String? = null
    fun de_lube_oil_level_sesudah() {
        val datakelamin = arrayOf("Low", "Normal", "Full")
        val spinner = find<Spinner>(R.id.txtde_lube_oil_level_sds)
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
                    de_lube_oil_level_sesudah = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var de_water_cooler_sebelum: String? = null
    fun de_water_cooler_sebelum() {
        val datakelamin = arrayOf("Low", "Normal", "Full")
        val spinner = find<Spinner>(R.id.txtde_water_cooler_level_sbs)
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
                    de_water_cooler_sebelum = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var de_water_cooler_sesudah: String? = null
    fun de_water_cooler_sesudah() {
        val datakelamin = arrayOf("Low", "Normal", "Full")
        val spinner = find<Spinner>(R.id.txtde_water_cooler_level_sds)
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
                    de_water_cooler_sesudah = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }

    companion object {
        var cekffblok: FFBlok2Model? = null
    }

    var currentDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_cek_ffblok2)
        binding.lifecycleOwner = this
        sessionManager = SessionManager(this)
        progressDialog = ProgressDialog(this)
        auto_start_sebelum()
        auto_start_sesudah()
        fuel_level_sebelum()
        fuel_level_sesudah()
        battery_level_sebelum()
        battery_level_sesudah()
        de_fuel_level_sebelum()
        de_fuel_level_sesudah()
        de_lube_oil_level_sebelum()
        de_lube_oil_level_sesudah()
        de_water_cooler_sebelum()
        de_water_cooler_sesudah()
        val gson = Gson()
        cekffblok =
            gson.fromJson(
                intent.getStringExtra("cekffblok2"),
                FFBlok2Model::class.java
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
            //=================Jockey Pump===================
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
            //Auto Start
            val body_txtspAsSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                auto_start_sebelum
            )
            val body_txtspAsSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                auto_start_sesudah
            )
            //Auto Stop
            val txtspAstSbs = binding.txtspAstSbs.text.toString()
            val txtspAstSds = binding.txtspAstSds.text.toString()
            val body_txtspAstSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspAstSbs
            )
            val body_txtspAstSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                txtspAstSds
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
            val body_txtmdFlSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                fuel_level_sebelum
            )
            val body_txtmdFlSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                fuel_level_sesudah
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
            val body_txtdeBatteryLevelSebelumStart: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                battery_level_sebelum
            )
            val body_txtdeBatteryLevelSesudahStart: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                battery_level_sesudah
            )
            //Fuel Level
            val body_txtdeFuelLevelSebelumStart: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                de_fuel_level_sebelum
            )
            val body_txtdeFuelLevelSesudahStart: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                de_fuel_level_sesudah
            )
            //Lube Oil Level
            val body_txtdeLubeOilLevelSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                de_lube_oil_level_sebelum
            )
            val body_txtdeLubeOilLevelSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                de_lube_oil_level_sesudah
            )
            //Water Cooler level
            val body_txtdeWaterCoolerLevelSbs: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                de_water_cooler_sebelum
            )
            val body_txtdeWaterCoolerLevelSds: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                de_water_cooler_sesudah
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
                txtspAstSbs.isNotEmpty() &&
                txtspAstSds.isNotEmpty() &&
                txtmdWpSbs.isNotEmpty() &&
                txtmdWpSds.isNotEmpty() &&
                txtmdDpSbs.isNotEmpty() &&
                txtmdDpSds.isNotEmpty() &&
                txtmdSpSbs.isNotEmpty() &&
                txtmdSpSds.isNotEmpty() &&
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

                api.update_ffblok2(
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
                    body_txtspAsSbs,
                    body_txtspAsSds,
                    body_txtspAstSbs,
                    body_txtspAstSds,
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