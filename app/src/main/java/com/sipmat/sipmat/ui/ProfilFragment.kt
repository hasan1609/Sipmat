package com.sipmat.sipmat.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.sipmat.sipmat.R
import com.sipmat.sipmat.admin.LoginAdminActivity
import com.sipmat.sipmat.databinding.FragmentArsipBinding
import com.sipmat.sipmat.databinding.FragmentProfilBinding
import com.sipmat.sipmat.session.SessionManager
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity

class ProfilFragment : Fragment() {
    //copy 1
    lateinit var binding : FragmentProfilBinding
    lateinit var sessionManager : SessionManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profil,container,false)
        binding.lifecycleOwner = this
        sessionManager = SessionManager(requireContext().applicationContext)

        binding.btnlogout.setOnClickListener {
            sessionManager.setLoginadmin(false)
            sessionManager.setLogin(false)
            sessionManager.setToken("")
            startActivity<LoginAdminActivity>()
            requireActivity().finish()
        }


        //copy3
        return  binding.root
    }


}