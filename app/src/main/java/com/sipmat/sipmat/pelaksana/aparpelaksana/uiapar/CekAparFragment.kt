package com.sipmat.sipmat.pelaksana.aparpelaksana.uiapar

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.pelaksana.AparPelaksanaAdapter
import com.sipmat.sipmat.pelaksana.aparpelaksana.CekAparActivity
import com.sipmat.sipmat.databinding.FragmentCekAparBinding
import com.sipmat.sipmat.model.ScheduleAparPelaksanaModel
import com.sipmat.sipmat.model.ScheduleAparPelaksanaResponse
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CekAparFragment : Fragment(), AnkoLogger {

    lateinit var binding: FragmentCekAparBinding
    var api = ApiClient.instance()
    private lateinit var mAdapter: AparPelaksanaAdapter
    lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cek_apar, container, false)
        binding.lifecycleOwner = this

        progressDialog = ProgressDialog(requireActivity())
        return binding.root

    }

    override fun onStart() {
        super.onStart()
        getaparpelaksana()
    }

    fun getaparpelaksana() {
        binding.rvaparpelaksana.layoutManager = LinearLayoutManager(requireContext())
        binding.rvaparpelaksana.setHasFixedSize(true)
        (binding.rvaparpelaksana.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        loading(true)
        api.getschedule_pelaksana()
            .enqueue(object : Callback<ScheduleAparPelaksanaResponse> {
                override fun onResponse(
                    call: Call<ScheduleAparPelaksanaResponse>,
                    response: Response<ScheduleAparPelaksanaResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            loading(false)
                            if (response.body()!!.data!!.isNotEmpty()) {
                                val notesList = mutableListOf<ScheduleAparPelaksanaModel>()
                                val data = response.body()
                                for (hasil in data!!.data!!) {
                                    notesList.add(hasil)
                                    mAdapter = AparPelaksanaAdapter(notesList, requireContext())
                                    binding.rvaparpelaksana.adapter = mAdapter
                                    mAdapter.setDialog(object : AparPelaksanaAdapter.Dialog {
                                        override fun onClick(
                                            position: Int,
                                            note: ScheduleAparPelaksanaModel
                                        ) {
                                            val builder = AlertDialog.Builder(requireActivity())
                                            builder.setMessage("Cek apar ? ")
                                            builder.setPositiveButton("Cek APAR") { dialog, which ->
                                                val gson = Gson()
                                                val noteJson = gson.toJson(note)
                                                startActivity<CekAparActivity>("cekapar" to noteJson)
                                            }
                                            builder.setNegativeButton("Cancel ?") { dialog, which ->
                                            }

                                            builder.show()

                                        }

                                    })
                                    mAdapter.notifyDataSetChanged()
                                }
                                }else{
                                binding.tvkosong.visibility = View.VISIBLE
                            }
                        } else {
                            loading(false)
                            toast("gagal mendapatkan response")
                        }
                    } catch (e: Exception) {
                        loading(false)
                        info { "dinda ${e.message}" }
                    }
                }

                override fun onFailure(call: Call<ScheduleAparPelaksanaResponse>, t: Throwable) {
                    loading(false)
                    info { "dinda ${t.message}" }
                }

            })


    }
    fun loading(status : Boolean){
        if (status){
            progressDialog.setTitle("Loading...")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
        }else{
            progressDialog.dismiss()
        }
    }

}