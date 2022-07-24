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
import com.sipmat.sipmat.adapter.apar.AparKadaluarsaAdapter
import com.sipmat.sipmat.pelaksana.aparpelaksana.CekAparKadaluarsaActivity
import com.sipmat.sipmat.databinding.FragmentKadaluarsaAparBinding
import com.sipmat.sipmat.model.AparModel
import com.sipmat.sipmat.model.AparResponse
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KadaluarsaAparFragment : Fragment(),AnkoLogger {

    lateinit var binding: FragmentKadaluarsaAparBinding
    var api = ApiClient.instance()
    private lateinit var mAdapter: AparKadaluarsaAdapter
    lateinit var progressDialog: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_kadaluarsa_apar, container, false)
        binding.lifecycleOwner = this

        aparkadaluarsa()

        progressDialog = ProgressDialog(requireActivity())
        return binding.root
    }



    fun aparkadaluarsa() {
        binding.rvkadaluarsa.layoutManager = LinearLayoutManager(requireContext())
        binding.rvkadaluarsa.setHasFixedSize(true)
        (binding.rvkadaluarsa.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.apar_kadaluarsa()
            .enqueue(object : Callback<AparResponse> {
                override fun onResponse(
                    call: Call<AparResponse>,
                    response: Response<AparResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<AparModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter = AparKadaluarsaAdapter(notesList, requireContext())
                                binding.rvkadaluarsa.adapter = mAdapter
                                mAdapter.setDialog(object : AparKadaluarsaAdapter.Dialog {
                                    override fun onClick(
                                        position: Int,
                                        note: AparModel
                                    ) {
                                        val builder = AlertDialog.Builder(requireActivity())
                                        builder.setMessage("Cek apar ? ")
                                        builder.setPositiveButton("Cek APAR") { dialog, which ->
                                            val gson = Gson()
                                            val noteJson = gson.toJson(note)
                                            startActivity<CekAparKadaluarsaActivity>("cekapar" to noteJson)
                                        }


                                        builder.setNegativeButton("Cancel ?") { dialog, which ->

                                        }

                                        builder.show()

                                    }

                                })
                                mAdapter.notifyDataSetChanged()
                            }
                        } else {
                            toast("gagal mendapatkan response")
                        }
                    } catch (e: Exception) {
                        info { "dinda ${e.message}" }
                    }
                }

                override fun onFailure(call: Call<AparResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })


    }

}