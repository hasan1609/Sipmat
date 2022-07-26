package com.sipmat.sipmat.admin.edgblok3.schedule

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
import com.sipmat.sipmat.databinding.ActivityCekEdgblok1AdminBinding
import com.sipmat.sipmat.databinding.ActivityDetailCekEdgblok2AdminBinding
import com.sipmat.sipmat.databinding.ActivityDetailCekEdgblok3AdminBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.edgblok1.EdgBlokModel
import com.sipmat.sipmat.model.edgblok2.EdgBlok2Model
import com.sipmat.sipmat.model.edgblok3.EdgBlok3Model
import com.sipmat.sipmat.model.ffblok.FFBlokModel
import com.sipmat.sipmat.webservice.ApiClient
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CekEdgblok3AdminActivity : AppCompatActivity(), AnkoLogger {
    lateinit var cekedgblok1: EdgBlok3Model
    lateinit var binding: ActivityDetailCekEdgblok3AdminBinding

    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_cek_edgblok3_admin)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)
        val gson = Gson()
        cekedgblok1 =
            gson.fromJson(intent.getStringExtra("cekedgblok3"), EdgBlok3Model::class.java)

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
        binding.txtpOpSbs.text = "${cekedgblok1.oilPressSebelumStart.toString()}"
        binding.txtspOpSds.text = "${cekedgblok1.oilPressSesudahStart.toString()}"

        binding.txtpOtSbs.text = "${cekedgblok1.oilTempSebelumStart.toString()}"
        binding.txtpOtSds.text = "${cekedgblok1.oilTempSesudahStart.toString()}"

        binding.txtpWtSbs.text = "${cekedgblok1.waterCoolTempSebelumStart.toString()}"
        binding.txtpWtSds.text = "${cekedgblok1.waterCoolTempSesudahStart.toString()}"

        binding.txtpOpSbs.text = "${cekedgblok1.oilPressSebelumStart.toString()}"
        binding.txtspOpSds.text = "${cekedgblok1.oilPressSesudahStart.toString()}"

        binding.txtpSSbs.text = "${cekedgblok1.speedSebelumStart.toString()}"
        binding.txtspSSds.text = "${cekedgblok1.speedSesudahStart.toString()}"

        binding.txtpHmSbs.text = "${cekedgblok1.hourMeterSebelumStart.toString()}"
        binding.txtspHmSds.text = "${cekedgblok1.hourMeterSesudahStart.toString()}"

        binding.txtpMlvSbs.text = "${cekedgblok1.mlVoltageSebelumStart.toString()}"
        binding.txtspMlvSds.text = "${cekedgblok1.mlVoltageSesudahStart.toString()}"

        binding.txtpMlfSbs.text = "${cekedgblok1.mlFreqSebelumStart.toString()}"
        binding.txtspMlfSds.text = "${cekedgblok1.mlFreqSesudahStart.toString()}"

        binding.txtpGbSbs.text = "${cekedgblok1.genBreakSebelumStart.toString()}"
        binding.txtspGbSds.text = "${cekedgblok1.genBreakSesudahStart.toString()}"

        binding.txtpGvSbs.text = "${cekedgblok1.genVolSebelumStart.toString()}"
        binding.txtspGvSds.text = "${cekedgblok1.genVolSesudahStart.toString()}"

        binding.txtpGfSbs.text = "${cekedgblok1.genFreqSebelumStart.toString()}"
        binding.txtspGfSds.text = "${cekedgblok1.genFreqSesudahStart.toString()}"

        binding.txtpLcSbs.text = "${cekedgblok1.loadCurSebelumStart.toString()}"
        binding.txtspLcSds.text = "${cekedgblok1.loadCurSesudahStart.toString()}"

        binding.txtpDSbs.text = "${cekedgblok1.dayaSebelumStart.toString()}"
        binding.txtspDSds.text = "${cekedgblok1.dayaSesudahStart.toString()}"

        binding.txtpCSbs.text = "${cekedgblok1.cosSebelumStart.toString()}"
        binding.txtspCSds.text = "${cekedgblok1.cosSesudahStart.toString()}"

        binding.txtpBcvSbs.text = "${cekedgblok1.batChargeVolSebelumStart.toString()}"
        binding.txtspBcvSds.text = "${cekedgblok1.batChargeVolSesudahStart.toString()}"

        binding.txtpHSbs.text = "${cekedgblok1.hourSebelumStart.toString()}"
        binding.txtspHSds.text = "${cekedgblok1.hourSesudahStart.toString()}"

        binding.txtpLoSbs.text = "${cekedgblok1.lubeOilSebelumStart.toString()}"
        binding.txtspLoSds.text = "${cekedgblok1.lubeOilSesudahStart.toString()}"

        binding.txtpAlSbs.text = "${cekedgblok1.accuSebelumStart.toString()}"
        binding.txtspAlSds.text = "${cekedgblok1.accuSesudahStart.toString()}"

        binding.txtpRlSbs.text = "${cekedgblok1.radiatorSebelumStart.toString()}"
        binding.txtspRlSds.text = "${cekedgblok1.radiatorSesudahStart.toString()}"

        binding.txtpFolSbs.text = "${cekedgblok1.fuelOilSebelumStart.toString()}"
        binding.txtspFolSds.text = "${cekedgblok1.fuelOilSesudahStart.toString()}"

        binding.txtpTbgSbs.text = "${cekedgblok1.tempBearingGenSebelumStart.toString()}"
        binding.txtpTbgSds.text = "${cekedgblok1.tempBearingGenSesudahStart.toString()}"

        binding.txtpTbuSbs.text = "${cekedgblok1.tampWindingUSebelumStart.toString()}"
        binding.txtpTbuSds.text = "${cekedgblok1.tampWindingUSesudahStart.toString()}"

        binding.txtpTbvSbs.text = "${cekedgblok1.tempWindingVSebelumStart.toString()}"
        binding.txtpTbvSds.text = "${cekedgblok1.tempWindingVSesudahStart.toString()}"

        binding.txtpTbwSbs.text = "${cekedgblok1.tempWindingWSebelumStart.toString()}"
        binding.txtpTbwSds.text = "${cekedgblok1.tempWindingWSesudahStart.toString()}"

        binding.txtpBrfSbs.text = "${cekedgblok1.beltRadiatorFanSebelumStart.toString()}"
        binding.txtpBrfSds.text = "${cekedgblok1.beltRadiatorFanSesudahStart.toString()}"

        binding.txtcatatan.text = "${cekedgblok1.catatan.toString()}"
        binding.txtnamak3.text = "${cekedgblok1.k3Nama.toString()}"
        binding.txtnamaoperator.text = "${cekedgblok1.operatorNama.toString()}"
        binding.txtnamasupervisor.text = "${cekedgblok1.supervisorNama.toString()}"

        binding.txltglPemeriksa.text = "Tgl Pemeriksa : ${cekedgblok1.tanggalCek.toString()}"
        binding.txtshift.text = "Shift : ${cekedgblok1.shift.toString()}"

        Picasso.get().load("${Constant.foto_url}${cekedgblok1.k3Ttd}").into(binding.signaturePadK3)
        Picasso.get().load("${Constant.foto_url}${cekedgblok1.operatorTtd}").into(binding.signaturePadOperator)
        Picasso.get().load("${Constant.foto_url}${cekedgblok1.supervisorTtd}").into(binding.signaturePadSupervisor)
        binding.btnReturn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("edgblok")
            builder.setMessage("Return edgblok ? ")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                loading(true)
                api.return_edgblok3(cekedgblok1.id!!).enqueue(object :
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
                api.acc_edgblok3(cekedgblok1.id!!).enqueue(object :
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
        api.edgblok3_pdf(cekedgblok1.id!!).enqueue(object :
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