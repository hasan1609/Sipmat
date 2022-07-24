package com.sipmat.sipmat.admin.ffblok1.schedule

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sipmat.sipmat.R
import com.sipmat.sipmat.constant.Constant
import com.sipmat.sipmat.databinding.ActivityCekFfblokAdminBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.ffblok.FFBlokModel
import com.sipmat.sipmat.webservice.ApiClient
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CekFFblokAdminActivity : AppCompatActivity(), AnkoLogger {
    lateinit var cekffblok: FFBlokModel
    lateinit var binding: ActivityCekFfblokAdminBinding

    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cek_ffblok_admin)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)
        val gson = Gson()
        cekffblok =
            gson.fromJson(intent.getStringExtra("cekffblok"), FFBlokModel::class.java)

        if (cekffblok.isStatus == 0 || cekffblok.isStatus ==3){
            binding.btnApprove.visibility = View.GONE
        }
        if (cekffblok.isStatus ==1){
            binding.btnReturn.visibility = View.VISIBLE
        }
        if (cekffblok.isStatus ==2 ){
            binding.btnApprove.visibility = View.GONE
            binding.btnReturn.visibility = View.GONE
            binding.btncetakpdf.visibility = View.VISIBLE

        }

        //servicepump
        binding.txtspWpSbs.text = "${cekffblok.spWaktuPencatatanSebelumStart.toString()}"
        binding.txtspWpSds.text = "${cekffblok.spWaktuPencatatanSesudahStart.toString()}"

        binding.txtspSpSbs.text = "${cekffblok.spSuctionPressSebelumStart.toString()}"
        binding.txtspSpSds.text = "${cekffblok.spSuctionPressSesudahStart.toString()}"

        binding.txtspDpSbs.text = "${cekffblok.spDischargePressSebelumStart.toString()}"
        binding.txtspDpSds.text = "${cekffblok.spDischargePressSesudahStart.toString()}"

        binding.txtspFlSbs.text = "${cekffblok.spFuelLevelSebelumStart.toString()}"
        binding.txtspFlSds.text = "${cekffblok.spFuelLevelSesudahStart.toString()}"

        binding.txtspAsSbs.text = "${cekffblok.spAutoStartSebelumStart.toString()}"
        binding.txtspAsSds.text = "${cekffblok.spAutoStartSesudahStart.toString()}"

        //================Motor Driven==============
        binding.txtmdWpSbs.text = "${cekffblok.mdWaktuPencatatanSebelumStart.toString()}"
        binding.txtmdWpSds.text = "${cekffblok.mdWaktuPencatatanSesudahStart.toString()}"
        //discharge press
        binding.txtmdDpSbs.text = "${cekffblok.mdDischargePressSebelumStart.toString()}"
        binding.txtmddppSds.text = "${cekffblok.mdDischargePressSesudahStart.toString()}"
        //sunction press
        binding.txtmdSpSbs.text = "${cekffblok.mdSuctionPressSebelumStart.toString()}"
        binding.txtmdSpSds.text = "${cekffblok.mdSuctionPressSesudahStart.toString()}"
        //fuel level
        binding.txtmdFlSbs.text = "${cekffblok.mdFullLevelSebelumStart.toString()}"
        binding.txtmdFlSds.text = "${cekffblok.mdFullLevelSesudahStart.toString()}"
        //autostart
        binding.txtmdAsSbs.text = "${cekffblok.mdAutoStartSebelumStart.toString()}"
        binding.txtmdAsSds.text = "${cekffblok.mdAutoStartSesudahStart.toString()}"

        //================Diesel  Engine==============
        //waktu start
        binding.txtdeWpSbs.text = "${cekffblok.deWaktuPencatatanSebelumStart.toString()}"
        binding.txtdeWpSds.text = "${cekffblok.deWaktuPencatatanSesudahStart.toString()}"
        //Lube Oil Press
        binding.txtdeLubeOilPressSbs.text = "${cekffblok.deLubeOilPressSebelumStart.toString()}"
        binding.txtdeLubeOilPressSds.text = "${cekffblok.deLubeOilPressSesudahStart.toString()}"
        //Battery Voltage
        binding.txtdeBatteryVoltageSebelumStart.text = "${cekffblok.deBatteryVoltageSebelumStart.toString()}"
        binding.txtdeBatteryVoltageSesudahStart.text = "${cekffblok.deBatteryVoltageSesudahStart.toString()}"
       //Battery AMpere
        binding.txtdeBatteryAmpereSebelumStart.text = "${cekffblok.deBatteryAmpereSebelumStart.toString()}"
        binding.txtdeBatteryAmpereSesudahStart.text = "${cekffblok.deBatteryAmpereSesudahStart.toString()}"
       //Battery Level
        binding.txtdeBatteryLevelSebelumStart.text = "${cekffblok.deBatteryLevelSebelumStart.toString()}"
        binding.txtdeBatteryLevelSesudahStart.text = "${cekffblok.deBatteryLevelSesudahStart.toString()}"
        //Fuel level
        binding.txtdeFuelLevelSebelumStart.text = "${cekffblok.deFuelLevelSebelumStart.toString()}"
        binding.txtdeFuelLevelSesudahStart.text = "${cekffblok.deFuelLevelSesudahStart.toString()}"
        //Lube Oil Level
        binding.txtdeLubeOilLevelSbs.text = "${cekffblok.deLubeOilLevelSebelumStart.toString()}"
        binding.txtdeLubeOilLevelSds.text = "${cekffblok.deLubeOilLevelSesudahStart.toString()}"
        //Water COoler level
        binding.txtdeWaterCoolerLevelSbs.text = "${cekffblok.deWaterCoolerLevelSebelumStart.toString()}"
        binding.txtdeWaterCoolerLevelSds.text = "${cekffblok.deWaterCoolerLevelSesudahStart.toString()}"
        //Spped
        binding.txtdeSpeedSbs.text = "${cekffblok.deSpeedSebelumStart.toString()}"
        binding.txtdeSpeedSds.text = "${cekffblok.deSpeedSesudahStart.toString()}"
        //Sunction Press
        binding.txtdeSuctionPressSebelumStart.text = "${cekffblok.deSuctionPressSebelumStart.toString()}"
        binding.txtdeSuctionPressSesudahStart.text = "${cekffblok.deSuctionPressSesudahStart.toString()}"
        //Discharge PRess
        binding.txtdeDischargePressSebelumStart.text = "${cekffblok.deDischargePressSebelumStart.toString()}"
        binding.txtdeDischargePressSesudahStart.text = "${cekffblok.deDischargePressSesudahStart.toString()}"
        //Auto Start
        binding.txtdeAutoStartSbs.text = "${cekffblok.deAutoStartSebelumStart.toString()}"
        binding.txtdeAutoStartSds.text = "${cekffblok.deAutoStartSesudahStart.toString()}"

        binding.txtcatatan.text = "${cekffblok.catatan.toString()}"
        binding.txtnamak3.text = "${cekffblok.k3Nama.toString()}"
        binding.txtnamaoperator.text = "${cekffblok.operatorNama.toString()}"
        binding.txtnamasupervisor.text = "${cekffblok.supervisorNama.toString()}"

        binding.txltglPemeriksa.text = "Tgl Pemeriksa : ${cekffblok.tanggalCek.toString()}"
        binding.txtshift.text = "Shift : ${cekffblok.shift.toString()}"

        Picasso.get().load("${Constant.foto_url}${cekffblok.k3Ttd}").into(binding.signaturePadK3)
        Picasso.get().load("${Constant.foto_url}${cekffblok.operatorTtd}").into(binding.signaturePadOperator)
        Picasso.get().load("${Constant.foto_url}${cekffblok.supervisorTtd}").into(binding.signaturePadSupervisor)
        binding.btnReturn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("ffblok")
            builder.setMessage("Return ffblok ? ")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                loading(true)
                api.return_ffblok(cekffblok.id!!).enqueue(object :
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
            builder.setTitle("ffblok")
            builder.setMessage("ACC ffblok ? ")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                loading(true)
                api.acc_ffblok(cekffblok.id!!).enqueue(object :
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
            cetakpdf()
        }

    }

    fun cetakpdf(){
        loading(true)
        api.ffblok_pdf(cekffblok.id!!).enqueue(object :
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