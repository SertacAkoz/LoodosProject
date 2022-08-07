package com.sertac.loodosproject.view.fragment.main

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.sertac.loodosproject.R
import com.sertac.loodosproject.databinding.FragmentLoginBinding
import com.sertac.loodosproject.model.User
import com.sertac.loodosproject.util.Resource
import com.sertac.loodosproject.util.Status
import com.sertac.loodosproject.util.SweetAlert
import com.sertac.loodosproject.util.Utils
import com.sertac.loodosproject.view.activity.HamburgerActivity
import com.sertac.loodosproject.viewmodel.AuthViewModel

class LoginFragment : Fragment() {

    private lateinit var binding:FragmentLoginBinding
    private lateinit var viewModel : AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)

//        binding.inputEmail.setText("asd@gmail.com")
//        binding.inputPassword.setText("123123")

        onClicks()
        observeLiveData()

    }

    private fun onClicks(){
        binding.btnLogin.setOnClickListener {
            if (Utils.emailValidation(binding.inputEmail.text.toString()) && binding.inputPassword.text.toString().isNotEmpty()){
                val loginUser = User(
                    binding.inputEmail.text.toString(),
                    binding.inputPassword.text.toString()
                )
                viewModel.login(loginUser)
            }else {
                SweetAlert.errorPopup(context, "Input Error","Please enter inputs correctly")
            }

        }
    }

    private fun observeLiveData(){
        viewModel.isLoggedIn.observe(viewLifecycleOwner, Observer {
            println("LoginFragment-observeLiveData-isLoggedIn -> ${it}")
            println("LoginFragment-observeLiveData-isLoggedIn-Detail -> ${it.status}")
            when(it.status){
                Status.SUCCESS -> {
                    SweetAlert.successPopupClickListener(context, "Login Success", "You are logged in.")
                }
                Status.ERROR -> {
                    SweetAlert.errorPopup(context, "Login Failed", it.message)
                }
            }
//            it.data?.let { result ->
//                if (result){
//
//                }else{
//
//                }
//            }
        })
    }
}