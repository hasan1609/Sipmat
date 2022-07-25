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
import com.sipmat.sipmat.databinding.ActivityDetailCekDamkarBinding
import com.sipmat.sipmat.databinding.ActivityDetailCekEdgblok1Binding
import com.sipmat.sipmat.databinding.ActivityDetailCekFfblok2Binding
import com.sipmat.sipmat.databinding.ActivityDetailCekFfblokBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.damkar.DamkarModel
import com.sipmat.sipmat.model.edgblok1.EdgBlokModel
import com.sipmat.sipmat.model.ffblok.FFBlokModel
import com.sipmat.sipmat.model.ffblok2.FFBlok2Model
import com.sipmat.sipmat.pelaksana.edgblok2pelaksana.DetailCekEdgblok2Activity
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
    var id: Int? = null
    val tw: String? = null
    var signaturePadSupervisor: MultipartBody.Part? = null
    var signaturePadOperator: MultipartBody.Part? = null
    var air_accu: String? = null
    fun air_accu() {
        val datakelamin = arrayOf("Normal", "Upnormal")
        val spinner = find<Spinner>(R.id.txtac)
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
                    air_accu = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var level_air_radiator: String? = null
    fun level_air_radiator() {
        val datakelamin = arrayOf("Normal", "Upnormal")
        val spinner = find<Spinner>(R.id.txtlar)
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
                    level_air_radiator = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var temp_mesin: String? = null
    fun temp_mesin() {
        val datakelamin = arrayOf("Normal", "Upnormal")
        val spinner = find<Spinner>(R.id.txttm)
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
                    temp_mesin = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var level_oil: String? = null
    fun level_oil() {
        val datakelamin = arrayOf("Normal", "Upnormal")
        val spinner = find<Spinner>(R.id.txtlo)
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
                    level_oil = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var filter_solar: String? = null
    fun filter_solar() {
        val datakelamin = arrayOf("Normal", "Upnormal")
        val spinner = find<Spinner>(R.id.txtfs)
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
                    filter_solar = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var lvl_minyak_rem: String? = null
    fun lvl_minyak_rem() {
        val datakelamin = arrayOf("Normal", "Upnormal")
        val spinner = find<Spinner>(R.id.txtlmr)
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
                    lvl_minyak_rem = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var suara_mesin: String? = null
    fun suara_mesin() {
        val datakelamin = arrayOf("Normal", "Upnormal")
        val spinner = find<Spinner>(R.id.txtsm)
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
                    suara_mesin = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var lampu_depan: String? = null
    fun lampu_depan() {
        val datakelamin = arrayOf("Baik", "Kurang baik", "Rusak", "Tidak ada")
        val spinner = find<Spinner>(R.id.txtld)
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
                    lampu_depan = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var lampu_belakang: String? = null
    fun lampu_belakang() {
        val datakelamin = arrayOf("Baik", "Kurang baik", "Rusak", "Tidak ada")
        val spinner = find<Spinner>(R.id.txtlb)
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
                    lampu_belakang = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var lampu_rem: String? = null
    fun lampu_rem() {
        val datakelamin = arrayOf("Baik", "Kurang baik", "Rusak", "Tidak ada")
        val spinner = find<Spinner>(R.id.txtlr)
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
                    lampu_rem = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var lampu_sein_kanan_depan: String? = null
    fun lampu_sein_kanan_depan() {
        val datakelamin = arrayOf("Baik", "Kurang baik", "Rusak", "Tidak ada")
        val spinner = find<Spinner>(R.id.txtlsknd)
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
                    lampu_sein_kanan_depan = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var lampu_sein_kanan_belakang: String? = null
    fun lampu_sein_kanan_belakang() {
        val datakelamin = arrayOf("Baik", "Kurang baik", "Rusak", "Tidak ada")
        val spinner = find<Spinner>(R.id.txtlsknb)
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
                    lampu_sein_kanan_belakang = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var lampu_sein_kiri_depan: String? = null
    fun lampu_sein_kiri_depan() {
        val datakelamin = arrayOf("Baik", "Kurang baik", "Rusak", "Tidak ada")
        val spinner = find<Spinner>(R.id.txtlskrd)
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
                    lampu_sein_kiri_depan = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var lampu_sein_kiri_belakang: String? = null
    fun lampu_sein_kiri_belakang() {
        val datakelamin = arrayOf("Baik", "Kurang baik", "Rusak", "Tidak ada")
        val spinner = find<Spinner>(R.id.txtlskrb)
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
                    lampu_sein_kiri_belakang = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var lampu_hazard: String? = null
    fun lampu_hazard() {
        val datakelamin = arrayOf("Baik", "Kurang baik", "Rusak", "Tidak ada")
        val spinner = find<Spinner>(R.id.txtlh)
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
                    lampu_hazard = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var lampu_sorot: String? = null
    fun lampu_sorot() {
        val datakelamin = arrayOf("Baik", "Kurang baik", "Rusak", "Tidak ada")
        val spinner = find<Spinner>(R.id.txtlsr)
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
                    lampu_sorot = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var lampu_dalam_depan: String? = null
    fun lampu_dalam_depan() {
        val datakelamin = arrayOf("Baik", "Kurang baik", "Rusak", "Tidak ada")
        val spinner = find<Spinner>(R.id.txtldp)
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
                    lampu_dalam_depan = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var lampu_dalam_tengah: String? = null
    fun lampu_dalam_tengah() {
        val datakelamin = arrayOf("Baik", "Kurang baik", "Rusak", "Tidak ada")
        val spinner = find<Spinner>(R.id.txtldt)
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
                    lampu_dalam_tengah = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var lampu_dalam_belakang: String? = null
    fun lampu_dalam_belakang() {
        val datakelamin = arrayOf("Baik", "Kurang baik", "Rusak", "Tidak ada")
        val spinner = find<Spinner>(R.id.txtldb)
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
                    lampu_dalam_belakang = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var wiper: String? = null
    fun wiper() {
        val datakelamin = arrayOf("Baik", "Kurang baik", "Rusak", "Tidak ada")
        val spinner = find<Spinner>(R.id.txtw)
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
                    wiper = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var spion: String? = null
    fun spion() {
        val datakelamin = arrayOf("Baik", "Kurang baik", "Rusak", "Tidak ada")
        val spinner = find<Spinner>(R.id.txtsp)
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
                    spion = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    var sirine: String? = null
    fun sirine() {
        val datakelamin = arrayOf("Baik", "Kurang baik", "Rusak", "Tidak ada")
        val spinner = find<Spinner>(R.id.txtlsr)
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
                    sirine = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }

    companion object {
        var edgblok1: DamkarModel? = null
    }

    var currentDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_cek_damkar)
        binding.lifecycleOwner = this
        sessionManager = SessionManager(this)
        progressDialog = ProgressDialog(this)
        val gson = Gson()
        edgblok1 =
            gson.fromJson(
                intent.getStringExtra("cekdamkar"),
                DamkarModel::class.java
            )
        val sdf = SimpleDateFormat("yyyy-M-dd ")

        currentDate = sdf.format(Date())
        binding.txltglPemeriksa.text = "Tgl Pemeriksa : $currentDate"
        binding.txtshift.text = "Shift : ${sessionManager.getNama()}"

        id = edgblok1.id
        val start = binding.txts.text.toString()
        val stop = binding.txtst.text.toString()
        val catatan = binding.txtcatatan.text.toString().trim()
        air_accu()
        level_air_radiator()
        temp_mesin()
        level_oil()
        filter_solar()
        lvl_minyak_rem()
        suara_mesin()
        lampu_depan()
        lampu_belakang()
        lampu_rem()
        lampu_sein_kanan_depan()
        lampu_sein_kanan_belakang()
        lampu_sein_kiri_depan()
        lampu_sein_kiri_belakang()
        lampu_hazard()
        lampu_sorot()
        lampu_dalam_depan()
        lampu_dalam_tengah()
        lampu_dalam_belakang()
        wiper()
        spion()



        binding.btnsubmit.setOnClickListener {
            if (
                catatan.isNotEmpty()
            ) {
                loading(true)
                api.update_damkar(
                    DetailCekEdgblok2Activity.edgblok1!!.id,
                    DetailCekEdgblok2Activity.edgblok1!!.tw.toString(),


                    catatan
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