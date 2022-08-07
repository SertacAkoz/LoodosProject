package com.sertac.loodosproject.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sertac.loodosproject.model.User
import com.sertac.loodosproject.repository.AuthRepository
import com.sertac.loodosproject.util.CustomSharedPreferences
import com.sertac.loodosproject.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AuthViewModel @ViewModelInject constructor(
    private val authRepository: AuthRepository,
    private val customSharedPreferences: CustomSharedPreferences
): ViewModel() {

    private val loginResponse = MutableLiveData<Resource<Boolean>>()
    val isLoggedIn : LiveData<Resource<Boolean>>
        get() = loginResponse

    private val registerResponse = MutableLiveData<Resource<Boolean>>()
    val isRegistered : LiveData<Resource<Boolean>>
        get() = registerResponse

    val isLoggedOut = MutableLiveData<Boolean>()

    fun login(user:User){
        viewModelScope.launch {
            authRepository.login(user){
                loginResponse.value = it
            }
        }
    }

    fun register(user:User){
        viewModelScope.launch {
            authRepository.register(user){
                registerResponse.value = it
            }
        }
    }

    fun getSharedEmail(): String {
        return customSharedPreferences.getEmail()
    }

    fun logOut(){
        authRepository.logOut()
        isLoggedOut.value = true
    }
}