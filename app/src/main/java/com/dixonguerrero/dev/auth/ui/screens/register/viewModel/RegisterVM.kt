package com.dixonguerrero.dev.auth.ui.screens.register.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.dixonguerrero.dev.auth.data.model.UserModel
import com.dixonguerrero.dev.auth.domain.RegisterUC
import com.dixonguerrero.dev.auth.navigation.AppScreens
import kotlinx.coroutines.launch

class RegisterVM: ViewModel() {

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> get() = _name

    private val _lastName = MutableLiveData<String>()
    val lastName: LiveData<String> get() = _lastName

    private val _document = MutableLiveData<String>()
    val document: LiveData<String> get() = _document

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password

    private val _register = MutableLiveData<Boolean>()
    val register: LiveData<Boolean> get() = _register

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _message = MutableLiveData<String?>()
    val message: LiveData<String?> get() = _message

    //Validation fields indicator
    private val _validName = MutableLiveData<Boolean>()
    val validName: LiveData<Boolean> get() = _validName
    private val _validLastName = MutableLiveData<Boolean>()
    val validLastName: LiveData<Boolean> get() = _validLastName
    private val _validDocument = MutableLiveData<Boolean>()
    val validDocument: LiveData<Boolean> get() = _validDocument
    private val _validUsername = MutableLiveData<Boolean>()
    val validUsername: LiveData<Boolean> get() = _validUsername
    private val _validPassword = MutableLiveData<Boolean>()
    val validPassword: LiveData<Boolean> get() = _validPassword

    fun onChangeValues(name: String, lastName: String, document: String, username: String, password: String){
        _name.value = name
        _lastName.value = lastName
        _document.value = document
        _username.value = username
        _password.value = password

        _register.value = validName(name)
                && validLastName(lastName)
                && validDocument(document)
                && validUsername(username)
                && validPassword(password)

    }

    fun validName(name: String): Boolean {
        val nameValidated = name.replace("\\s+".toRegex(), "")
        return if (nameValidated.isEmpty() || nameValidated.length < 5 || !nameValidated.all { it.isLetter() }) {
            _validName.value = false
            false
        } else {
            _validName.value = true
            true
        }
    }

    fun validLastName(lastName: String): Boolean {
        val lastNameValidated = lastName.replace("\\s+".toRegex(), "")
        return if (lastNameValidated.isEmpty() || lastNameValidated.length < 5 || !lastNameValidated.all { it.isLetter() }) {
            _validLastName.value = false
            false
        } else {
            _validLastName.value = true
            true
        }
    }

    fun validDocument(document: String): Boolean {
        val documentValidated = document.replace("\\s+".toRegex(), "")
        return if (documentValidated.isEmpty() || documentValidated.length < 8 || !documentValidated.all { it.isDigit() }) {
            _validDocument.value = false
            false
        } else {
            _validDocument.value = true
            true
        }
    }

    fun validUsername(username: String): Boolean {
        val usernameValidated = username.replace("\\s+".toRegex(), "")
        return if (usernameValidated.isEmpty() || usernameValidated.length < 5 || !usernameValidated.all { it.isLetterOrDigit() }) {
            _validUsername.value = false
            false
        } else {
            _validUsername.value = true
            true
        }
    }

    fun validPassword(password: String): Boolean {
        val passwordValidated = password.replace("\\s+".toRegex(), "")
        return if (passwordValidated.isEmpty() || passwordValidated.length < 8 || !passwordValidated.all { it.isLetterOrDigit() }) {
            _validPassword.value = false
            false
        } else {
            _validPassword.value = true
            true
        }
    }


    //Saved Data
    fun onRegisterSelect(navController: NavController){
        _isLoading.value = true

        val user = UserModel(
            name = _name.value,
            lastName = _lastName.value,
            document = _document.value,
            user = _username.value,
            password = _password.value
        )

        viewModelScope.launch {
            val response = RegisterUC().invoke(user)
            Log.i("RegisterVM", "Response: $response")
            if (response?.error == null) {
                navController.navigate(AppScreens.SuccessRegisterPage.route)
                resetDataFields()
            } else {
                _message.value = "Error al registrar: ${response.error}"
            }
            _isLoading.value = false
            }

    }

    private fun resetDataFields() {
        _name.value = ""
        _lastName.value = ""
        _document.value = ""
        _username.value = ""
        _password.value = ""
    }

    fun resetMessage() {
        _message.value = null
    }



}