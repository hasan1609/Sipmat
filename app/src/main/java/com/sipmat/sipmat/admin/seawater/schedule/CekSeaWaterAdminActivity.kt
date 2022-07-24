package com.sipmat.sipmat.admin.seawater.schedule

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sipmat.sipmat.R
import com.sipmat.sipmat.constant.Constant
import com.sipmat.sipmat.databinding.ActivityCekSeaWaterAdminBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.seawater.SeaWaterModel
import com.sipmat.sipmat.webservice.ApiClient
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CekSeaWaterAdminActivity : AppCompatActivity(), AnkoLogger {
    lateinit var cekseawater: SeaWaterModel
    lateinit var binding: ActivityCekSeaWaterAdminBinding

    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cek_sea_water_admin)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)
        val gson = Gson()
        cekseawater =
            gson.fromJson(intent.getStringExtra("cekseawater"), SeaWaterModel::class.java)

        if (cekseawater.isStatus == 0 || cekseawater.isStatus ==3){
            binding.btnApprove.visibility = View.GONE
        }
        if (cekseawater.isStatus ==1){
            binding.btnReturn.visibility = View.VISIBLE
        }
        if (cekseawater.isStatus ==2 ){
            binding.btnApprove.visibility = View.GONE
            binding.btnReturn.visibility = View.GONE
            binding.btncetakpdf.visibility = View.VISIBLE

        }
        binding.txtmdWpSbs.text = "${cekseawater.mdWaktuPencatatanSebelumStart.toString()}"
        binding.txtmdWpSds.text = "${cekseawater.mdWaktuPencatatanSesudahStart.toString()}"
        binding.txtmdDpSbs.text = "${cekseawater.mdDischargePressSebelumStart.toString()}"
        binding.txtmddppSds.text = "${cekseawater.mdDischargePressSesudahStart.toString()}"
        binding.txtdeWpSbs.text = "${cekseawater.deWaktuPencatatanSebelumStart.toString()}"
        binding.txtdeWpSds.text = "${cekseawater.deWaktuPencatatanSesudahStart.toString()}"
        binding.txtdeEngineOperatingHoursSebelumStart.text = "${cekseawater.deEngineOperatingHoursSebelumStart.toString()}"
        binding.txtdeEngineOperatingHoursSesudahStart.text = "${cekseawater.deEngineOperatingHoursSesudahStart.toString()}"
        binding.txtdeBatteryVoltageSebelumStart.text = "${cekseawater.deBatteryVoltageSebelumStart.toString()}"
        binding.txtdeBatteryVoltageSesudahStart.text = "${cekseawater.deBatteryVoltageSesudahStart.toString()}"
        binding.txtdeBatteryAmpereSebelumStart.text = "${cekseawater.deBatteryAmpereSebelumStart.toString()}"
        binding.txtdeBatteryAmpereSesudahStart.text = "${cekseawater.deBatteryAmpereSesudahStart.toString()}"
        binding.txtdeOilPressureSebelumStart.text = "${cekseawater.deOilPressureSebelumStart.toString()}"
        binding.txtdeOilPressureSesudahStart.text = "${cekseawater.deOilPressureSesudahStart.toString()}"
        binding.deCoolingWaterPressureAfterRegulatorSebelumStart.text = "${cekseawater.deCoolingWaterPressureAfterRegulatorSebelumStart.toString()}"
        binding.deCoolingWaterPressureAfterRegulatorSesudahStart.text = "${cekseawater.deCoolingWaterPressureAfterRegulatorSesudahStart.toString()}"
        binding.txtdeCoolantTemperatureSebelumStart.text = "${cekseawater.deCoolantTemperatureSebelumStart.toString()}"
        binding.txtdeCoolantTemperatureSesudahStart.text = "${cekseawater.deCoolantTemperatureSesudahStart.toString()}"
        binding.txtdeSpeedSebelumStart.text = "${cekseawater.deSpeedSebelumStart.toString()}"
        binding.txtdeSpeedSesudahStart.text = "${cekseawater.deSpeedSesudahStart.toString()}"
        binding.txtdeSuctionPressSebelumStart.text = "${cekseawater.deSuctionPressSebelumStart.toString()}"
        binding.txtdeSuctionPressSesudahStart.text = "${cekseawater.deSuctionPressSesudahStart.toString()}"
        binding.txtdeDischargePressSebelumStart.text = "${cekseawater.deDischargePressSebelumStart.toString()}"
        binding.txtdeDischargePressSesudahStart.text = "${cekseawater.deDischargePressSesudahStart.toString()}"
        binding.txtcatatan.text = "${cekseawater.catatan.toString()}"
        binding.txtnamak3.text = "${cekseawater.k3Nama.toString()}"
        binding.txtnamaoperator.text = "${cekseawater.operatorNama.toString()}"
        binding.txtnamasupervisor.text = "${cekseawater.supervisorNama.toString()}"
        binding.txtmdFlSbs.text = "${cekseawater.mdFullLevelSebelumStart.toString()}"
        binding.txtmdFlSds.text = "${cekseawater.mdFullLevelSesudahStart.toString()}"
        binding.txtdeBatteryLevelSebelumStart.text = "${cekseawater.deBatteryLevelSebelumStart.toString()}"
        binding.txtdeBatteryLevelSesudahStart.text = "${cekseawater.deBatteryLevelSesudahStart.toString()}"
        binding.txtdeFuelLevelSebelumStart.text = "${cekseawater.deFuelLevelSebelumStart.toString()}"
        binding.txtdeFuelLevelSesudahStart.text = "${cekseawater.deFuelLevelSesudahStart.toString()}"
        binding.txtdeCrankcaseSebelumStart.text = "${cekseawater.de_crankcase_sebelum_start.toString()}"
        binding.txtdeCrankcaseSesudahStart.text = "${cekseawater.de_crankcase_sesudah_start.toString()}"
        binding.txtdeEngineCoolantTankSebelumStart.text = "${cekseawater.deEngineCoolantTankSebelumStart.toString()}"
        binding.txtdeEngineCoolantTankSesudahStart.text = "${cekseawater.deEngineCoolantTankSesudahStart.toString()}"
        binding.txtdeCoolingWaterInletValveSebelumStart.text = "${cekseawater.deCoolingWaterInletValveSebelumStart.toString()}"
        binding.txtdeCoolingWaterInletValveSesudahStart.text = "${cekseawater.deCoolingWaterInletValveSesudahStart.toString()}"

        binding.txltglPemeriksa.text = "Tgl Pemeriksa : ${cekseawater.tanggalPemeriksa.toString()}"
        binding.txtshift.text = "Shift : ${cekseawater.shift.toString()}"

        Picasso.get().load("${Constant.foto_url}${cekseawater.k3Ttd}").into(binding.signaturePadK3)
        Picasso.get().load("${Constant.foto_url}${cekseawater.operatorTtd}").into(binding.signaturePadOperator)
        Picasso.get().load("${Constant.foto_url}${cekseawater.supervisorTtd}").into(binding.signaturePadSupervisor)
        binding.btnReturn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("seawater")
            builder.setMessage("Return seawater ? ")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                loading(true)
                api.return_seawater(cekseawater.id!!).enqueue(object :
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
            builder.setTitle("seawater")
            builder.setMessage("ACC seawater ? ")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                loading(true)
                api.acc_seawater(cekseawater.id!!).enqueue(object :
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
        api.seawater_pdf(cekseawater.id!!).enqueue(object :
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