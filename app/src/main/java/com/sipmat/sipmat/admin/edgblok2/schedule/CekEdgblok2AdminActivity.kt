package com.sipmat.sipmat.admin.edgblok2.schedule

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
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.edgblok1.EdgBlokModel
import com.sipmat.sipmat.model.edgblok2.EdgBlok2Model
import com.sipmat.sipmat.model.ffblok.FFBlokModel
import com.sipmat.sipmat.webservice.ApiClient
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CekEdgblok2AdminActivity : AppCompatActivity(), AnkoLogger {
    lateinit var cekedgblok1: EdgBlok2Model
    lateinit var binding: ActivityDetailCekEdgblok2AdminBinding

    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_cek_edgblok2_admin)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)
        val gson = Gson()
        cekedgblok1 =
            gson.fromJson(intent.getStringExtra("cekedgblok2"), EdgBlok2Model::class.java)

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
        binding.txtpWpSds.text = "${cekedgblok1.pWaktuPencatatanSesudahStart.toString()}"
        binding.txtpWppSbs.text = "${cekedgblok1.pWaktuPencatatanSebelumStart.toString()}"

        binding.txtpOpSbs.text = "${cekedgblok1.pOilPressureSebelumStart.toString()}"
        binding.txtspOpSds.text = "${cekedgblok1.pOilPressureSesudahStart.toString()}"

        binding.txtpFpSbs.text = "${cekedgblok1.pFuelPressureSebelumStart.toString()}"
        binding.txtpFpSds.text = "${cekedgblok1.pFuelPressureSesudahStart.toString()}"

        binding.txtpFtSbs.text = "${cekedgblok1.pFuelTempratureSebelumStart.toString()}"
        binding.txtpFtSds.text = "${cekedgblok1.pFuelTempratureSesudahStart.toString()}"

        binding.txtpCpSbs.text = "${cekedgblok1.pCoolantPressureSebelumStart.toString()}"
        binding.txtpCpSds.text = "${cekedgblok1.pCoolantPressureSesudahStart.toString()}"

        binding.txtpCtSbs.text = "${cekedgblok1.pCoolantTempratureSebelumStart.toString()}"
        binding.txtpCtSds.text = "${cekedgblok1.pCoolantTempratureSesudahStart.toString()}"

        binding.txtpSSbs.text = "${cekedgblok1.pSpeedSebelumStart.toString()}"
        binding.txtspSSds.text = "${cekedgblok1.pSpeedSesudahStart.toString()}"

        binding.txtpItSbs.text = "${cekedgblok1.pInletTempratureSebelumStart.toString()}"
        binding.txtspItSds.text = "${cekedgblok1.pInletTempratureSesudahStart.toString()}"

        binding.txtpTpSbs.text = "${cekedgblok1.pTurboPleasureSebelumStart.toString()}"
        binding.txtspTpSds.text = "${cekedgblok1.pTurboPleasureSesudahStart.toString()}"

        binding.txtpGbSbs.text = "${cekedgblok1.pGeneratorBreakerSebelumStart.toString()}"
        binding.txtspGbSds.text = "${cekedgblok1.pGeneratorBreakerSesudahStart.toString()}"

        binding.txtpGvSbs.text = "${cekedgblok1.pGeneratorVoltageSebelumStart.toString()}"
        binding.txtspGvSds.text = "${cekedgblok1.pGeneratorVoltageSesudahStart.toString()}"

        binding.txtpGfSbs.text = "${cekedgblok1.pGeneratorFrequencySebelumStart.toString()}"
        binding.txtspGfSds.text = "${cekedgblok1.pGeneratorFrequencySesudahStart.toString()}"

        binding.txtpGcSbs.text = "${cekedgblok1.pGeneratorCurrentSebelumStart.toString()}"
        binding.txtspGcSds.text = "${cekedgblok1.pGeneratorCurrentSesudahStart.toString()}"

        binding.txtpLSbs.text = "${cekedgblok1.pLoadSebelumStart.toString()}"
        binding.txtspLSds.text = "${cekedgblok1.pLoadSesudahStart.toString()}"

        binding.txtpBcvSbs.text = "${cekedgblok1.pBatteryChargeVoltaseSebelumStart.toString()}"
        binding.txtspBcvSds.text = "${cekedgblok1.pBatteryChargeVoltaseSesudahStart.toString()}"

        binding.txtpLoSbs.text = "${cekedgblok1.pLubeOilLevelSebelumStart.toString()}"
        binding.txtspLoSds.text = "${cekedgblok1.pLubeOilLevelSesudahStart.toString()}"

        binding.txtpAlSbs.text = "${cekedgblok1.pAccuLevelSebelumStart.toString()}"
        binding.txtspAlSds.text = "${cekedgblok1.pAccuLevelSesudahStart.toString()}"

        binding.txtpRlSbs.text = "${cekedgblok1.pRadiatorLevelSebelumStart.toString()}"
        binding.txtspRlSds.text = "${cekedgblok1.pRadiatorLevelSesudahStart.toString()}"

        binding.txtpFolSbs.text = "${cekedgblok1.pFuelOilLevelSebelumStart.toString()}"
        binding.txtspFolSds.text = "${cekedgblok1.pFuelOilLevelSesudahStart.toString()}"

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
            builder.setTitle("ffblok")
            builder.setMessage("Return ffblok ? ")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                loading(true)
                api.return_edgblok2(cekedgblok1.id!!).enqueue(object :
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
                api.acc_edgblok2(cekedgblok1.id!!).enqueue(object :
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
        api.edgblok2_pdf(cekedgblok1.id!!).enqueue(object :
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