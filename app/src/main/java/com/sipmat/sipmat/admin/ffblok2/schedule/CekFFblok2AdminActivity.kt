package com.sipmat.sipmat.admin.ffblok2.schedule

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
import com.sipmat.sipmat.databinding.ActivityCekFfblok2AdminBinding
import com.sipmat.sipmat.databinding.ActivityCekFfblokAdminBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.ffblok.FFBlokModel
import com.sipmat.sipmat.model.ffblok2.FFBlok2Model
import com.sipmat.sipmat.webservice.ApiClient
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CekFFblok2AdminActivity : AppCompatActivity(), AnkoLogger {
    lateinit var cekffblok2: FFBlok2Model
    lateinit var binding: ActivityCekFfblok2AdminBinding

    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cek_ffblok2_admin)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)
        val gson = Gson()
        cekffblok2 =
            gson.fromJson(intent.getStringExtra("cekffblok2"), FFBlok2Model::class.java)

        if (cekffblok2.isStatus == 0 || cekffblok2.isStatus ==3){
            binding.btnApprove.visibility = View.GONE
        }
        if (cekffblok2.isStatus ==1){
            binding.btnReturn.visibility = View.VISIBLE
        }
        if (cekffblok2.isStatus ==2 ){
            binding.btnApprove.visibility = View.GONE
            binding.btnReturn.visibility = View.GONE
            binding.btncetakpdf.visibility = View.VISIBLE

        }

        //servicepump
        binding.txtspWpSbs.text = "${cekffblok2.spWaktuPencatatanSebelumStart.toString()}"
        binding.txtspWpSds.text = "${cekffblok2.spWaktuPencatatanSesudahStart.toString()}"

        binding.txtspSpSbs.text = "${cekffblok2.spSuctionPressSebelumStart.toString()}"
        binding.txtspSpSds.text = "${cekffblok2.spSuctionPressSesudahStart.toString()}"

        binding.txtspDpSbs.text = "${cekffblok2.spDischargePressSebelumStart.toString()}"
        binding.txtspDpSds.text = "${cekffblok2.spDischargePressSesudahStart.toString()}"

        binding.txtspAsSbs.text = "${cekffblok2.spAutoStartSebelumStart.toString()}"
        binding.txtspAsSds.text = "${cekffblok2.spAutoStartSesudahStart.toString()}"

        binding.txtspAstSbs.text = "${cekffblok2.spAutoStopSebelumStart.toString()}"
        binding.txtspAstSds.text = "${cekffblok2.spAutoStopSesudahStart.toString()}"

        //================Motor Driven==============
        binding.txtmdWpSbs.text = "${cekffblok2.mdWaktuPencatatanSebelumStart.toString()}"
        binding.txtmdWpSds.text = "${cekffblok2.mdWaktuPencatatanSesudahStart.toString()}"
        //discharge press
        binding.txtmdDpSbs.text = "${cekffblok2.mdDischargePressSebelumStart.toString()}"
        binding.txtmddppSds.text = "${cekffblok2.mdDischargePressSesudahStart.toString()}"
        //sunction press
        binding.txtmdSpSbs.text = "${cekffblok2.mdSuctionPressSebelumStart.toString()}"
        binding.txtmdSpSds.text = "${cekffblok2.mdSuctionPressSesudahStart.toString()}"
        //fuel level
        binding.txtmdFlSbs.text = "${cekffblok2.mdFullLevelSebelumStart.toString()}"
        binding.txtmdFlSds.text = "${cekffblok2.mdFullLevelSesudahStart.toString()}"
        //autostart
        binding.txtmdAsSbs.text = "${cekffblok2.mdAutoStartSebelumStart.toString()}"
        binding.txtmdAsSds.text = "${cekffblok2.mdAutoStartSesudahStart.toString()}"

        //================Diesel  Engine==============
        //waktu start
        binding.txtdeWpSbs.text = "${cekffblok2.deWaktuPencatatanSebelumStart.toString()}"
        binding.txtdeWpSds.text = "${cekffblok2.deWaktuPencatatanSesudahStart.toString()}"
        //Lube Oil Press
        binding.txtdeLubeOilPressSbs.text = "${cekffblok2.deLubeOilPressSebelumStart.toString()}"
        binding.txtdeLubeOilPressSds.text = "${cekffblok2.deLubeOilPressSesudahStart.toString()}"
        //Battery Voltage
        binding.txtdeBatteryVoltageSebelumStart.text = "${cekffblok2.deBatteryVoltageSebelumStart.toString()}"
        binding.txtdeBatteryVoltageSesudahStart.text = "${cekffblok2.deBatteryVoltageSesudahStart.toString()}"
       //Battery AMpere
        binding.txtdeBatteryAmpereSebelumStart.text = "${cekffblok2.deBatteryAmpereSebelumStart.toString()}"
        binding.txtdeBatteryAmpereSesudahStart.text = "${cekffblok2.deBatteryAmpereSesudahStart.toString()}"
       //Battery Level
        binding.txtdeBatteryLevelSebelumStart.text = "${cekffblok2.deBatteryLevelSebelumStart.toString()}"
        binding.txtdeBatteryLevelSesudahStart.text = "${cekffblok2.deBatteryLevelSesudahStart.toString()}"
        //Fuel level
        binding.txtdeFuelLevelSebelumStart.text = "${cekffblok2.deFuelLevelSebelumStart.toString()}"
        binding.txtdeFuelLevelSesudahStart.text = "${cekffblok2.deFuelLevelSesudahStart.toString()}"
        //Lube Oil Level
        binding.txtdeLubeOilLevelSbs.text = "${cekffblok2.deLubeOilLevelSebelumStart.toString()}"
        binding.txtdeLubeOilLevelSds.text = "${cekffblok2.deLubeOilLevelSesudahStart.toString()}"
        //Water COoler level
        binding.txtdeWaterCoolerLevelSbs.text = "${cekffblok2.deWaterCoolerLevelSebelumStart.toString()}"
        binding.txtdeWaterCoolerLevelSds.text = "${cekffblok2.deWaterCoolerLevelSesudahStart.toString()}"
        //Spped
        binding.txtdeSpeedSbs.text = "${cekffblok2.deSpeedSebelumStart.toString()}"
        binding.txtdeSpeedSds.text = "${cekffblok2.deSpeedSesudahStart.toString()}"
        //Sunction Press
        binding.txtdeSuctionPressSebelumStart.text = "${cekffblok2.deSuctionPressSebelumStart.toString()}"
        binding.txtdeSuctionPressSesudahStart.text = "${cekffblok2.deSuctionPressSesudahStart.toString()}"
        //Discharge PRess
        binding.txtdeDischargePressSebelumStart.text = "${cekffblok2.deDischargePressSebelumStart.toString()}"
        binding.txtdeDischargePressSesudahStart.text = "${cekffblok2.deDischargePressSesudahStart.toString()}"
        //Auto Start
        binding.txtdeAutoStartSbs.text = "${cekffblok2.deAutoStartSebelumStart.toString()}"
        binding.txtdeAutoStartSds.text = "${cekffblok2.deAutoStartSesudahStart.toString()}"

        binding.txtcatatan.text = "${cekffblok2.catatan.toString()}"
        binding.txtnamak3.text = "${cekffblok2.k3Nama.toString()}"
        binding.txtnamaoperator.text = "${cekffblok2.operatorNama.toString()}"
        binding.txtnamasupervisor.text = "${cekffblok2.supervisorNama.toString()}"

        binding.txltglPemeriksa.text = "Tgl Pemeriksa : ${cekffblok2.tanggalCek.toString()}"
        binding.txtshift.text = "Shift : ${cekffblok2.shift.toString()}"

        Picasso.get().load("${Constant.foto_url}${cekffblok2.k3Ttd}").into(binding.signaturePadK3)
        Picasso.get().load("${Constant.foto_url}${cekffblok2.operatorTtd}").into(binding.signaturePadOperator)
        Picasso.get().load("${Constant.foto_url}${cekffblok2.supervisorTtd}").into(binding.signaturePadSupervisor)
        binding.btnReturn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("ffblok 2")
            builder.setMessage("Return ffblok 2 ? ")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                loading(true)
                api.return_ffblok2(cekffblok2.id!!).enqueue(object :
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
            builder.setTitle("ffblok 2")
            builder.setMessage("ACC ffblok 2 ? ")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                loading(true)
                api.acc_ffblok2(cekffblok2.id!!).enqueue(object :
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
        api.ffblok2_pdf(cekffblok2.id!!).enqueue(object :
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