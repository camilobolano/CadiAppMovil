package com.dixonguerrero.dev.auth.ui.screens.login.viewModel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.dixonguerrero.dev.auth.data.model.LoginModel
import com.dixonguerrero.dev.auth.data.model.UserModel
import com.dixonguerrero.dev.auth.domain.LoginUC
import com.dixonguerrero.dev.auth.navigation.AppScreens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginVM : ViewModel() {
    private val _user = MutableLiveData<String>()
    val user: LiveData<String> = _user

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _isLoginEnable = MutableLiveData<Boolean>()
    val isLoginEnable: LiveData<Boolean> = _isLoginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String?>()
    val message: LiveData<String?> = _message

    fun onLoginChanged(email: String, password: String){
        _user.value = email
        _password.value = password
        _isLoginEnable.value = isValidUser(email) && isValidPassword(password)
    }



    fun isValidUser(user: String): Boolean = user.isNotEmpty() && user.length >= 5

    fun isValidPassword(password: String): Boolean = password.length >= 8

    fun onLoginSelected(navController: NavController) {
        viewModelScope.launch {
            _isLoading.value = true

            val userOnLogin = LoginModel(_user.value, _password.value)

            val response : UserModel? = LoginUC().invoke(userOnLogin)
            if (response != null) {

                navController.navigate(AppScreens.ProfilePage.route + "/${response.document}")
            }else{
                _message.value = "Usuario o contraseña incorrectos"
            }
            _isLoading.value = false
        }

    }

    fun resetMessage() {
        _message.value = null
    }

}