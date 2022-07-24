package com.sipmat.sipmat.admin.ui.PELAKSANA

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.UsersAdapter
import com.sipmat.sipmat.databinding.FragmentArsipBinding
import com.sipmat.sipmat.databinding.FragmentPelaksanaBinding
import com.sipmat.sipmat.model.RegisterResponse
import com.sipmat.sipmat.model.UsersModel
import com.sipmat.sipmat.model.UsersResponse
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PelaksanaFragment : Fragment(), AnkoLogger {
    //copy 1
    lateinit var binding: FragmentPelaksanaBinding
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    private lateinit var mAdapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pelaksana, container, false)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(activity)

        binding.btncreate.setOnClickListener {
            val username = binding.edtid.text.toString().trim()
            val password = binding.edtpassword.text.toString().trim()
            val nama = binding.edtnama.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty() && nama.isNotEmpty()) {
                loading(true)
                info { "dinda $nama $username $password" }
                api.register(nama, username, password, 1)
                    .enqueue(object : Callback<RegisterResponse> {
                        override fun onResponse(
                            call: Call<RegisterResponse>,
                            response: Response<RegisterResponse>
                        ) {
                            try {
                                if (response.isSuccessful) {
                                    info { "dinda ${response.body()}" }
                                    if (response.body()!!.status == 1) {
                                        loading(false)
                                        snackbar("Berhasil tambah user", it)
                                        onStart()
                                    } else if (response.body()!!.status == 0) {
                                        loading(false)
                                        snackbar("mungkin username sama", it)
                                    }

                                } else {
                                    snackbar("Kesalahan aplikasi", it)
                                }


                            } catch (e: Exception) {
                                loading(false)
                                info { "dinda ${e.message}${response.code()} " }
                            }
                        }

                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            loading(false)
                            info { "dinda ${t.message} " }
                            snackbar("Kesalahan Jaringan", it)
                        }

                    })
            } else {
                snackbar("jangan kosongi kolom", it)
            }
        }

        //copy3
        return binding.root
    }
    
    fun snackbar(text: String, view: View) {
        Snackbar.make(view, text, 3000).show()
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

    override fun onStart() {
        super.onStart()
        getusers()
    }

    fun getusers() {
        binding.rvuser.layoutManager = LinearLayoutManager(requireContext().applicationContext)
        binding.rvuser.setHasFixedSize(true)
        (binding.rvuser.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.getusers(1)
            .enqueue(object : Callback<UsersResponse> {
                override fun onResponse(
                    call: Call<UsersResponse>,
                    response: Response<UsersResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<UsersModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter =
                                    UsersAdapter(notesList, requireContext().applicationContext)
                                binding.rvuser.adapter = mAdapter
                                mAdapter.notifyDataSetChanged()
                            }
                        } else {
                            toast("gagal mendapatkan response")
                        }
                    } catch (e: Exception) {
                        info { "dinda ${e.message}" }
                    }
                }

                override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })

    }

}