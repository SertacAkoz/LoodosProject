package com.sertac.loodosproject.view.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sertac.loodosproject.R
import com.sertac.loodosproject.databinding.FragmentRegisterBinding
import com.sertac.loodosproject.model.User
import com.sertac.loodosproject.util.Status
import com.sertac.loodosproject.util.SweetAlert
import com.sertac.loodosproject.viewmodel.AuthViewModel

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel : AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)

        onClicks()
        observeLiveData()
    }

    private fun onClicks(){
        binding.btnRegister.setOnClickListener {

            if (binding.inputPassword.text.toString().equals(binding.inputPasswordAgain.text.toString())){
                val newUser = User(
                    binding.inputEmail.text.toString(),
                    binding.inputPassword.text.toString()
                )
                viewModel.register(newUser)
            }else {
                SweetAlert.errorPopup(context, "Invalid Password","Plese enter your password correctly.")
            }

        }
    }

    private fun observeLiveData(){
        viewModel.isRegistered.observe(viewLifecycleOwner, Observer {



            when(it.status){
                Status.SUCCESS -> {
                    SweetAlert.successPopup(context, "Register Success", "You can login in login page.")
                }
                Status.ERROR -> {
                    SweetAlert.errorPopup(context, "Register Failed",it.message)
                }
            }

//            it.data?.let { result ->
//                if (result){
//                    SweetAlert.successPopup(context, "Register Success", "You can login in login page.")
//                }else {
//                    SweetAlert.errorPopup(context, "Register Failed","Please try again later.")
//                }
//            }
        })
    }


}