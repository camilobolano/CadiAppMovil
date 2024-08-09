package com.dixonguerrero.dev.auth.ui.screens.profile.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.dixonguerrero.dev.auth.data.model.UserModel
import com.dixonguerrero.dev.auth.domain.GetOneUserUC
import com.dixonguerrero.dev.auth.domain.UpdateUserUC
import com.dixonguerrero.dev.auth.navigation.AppScreens
import kotlinx.coroutines.launch

class ProfileVM : ViewModel() {
    private val _name = MutableLiveData<String?>()
    val name: LiveData<String?> get() = _name

    private val _lastName = MutableLiveData<String?>()
    val lastName: LiveData<String?> get() = _lastName

    private val _document = MutableLiveData<String?>()
    val document: LiveData<String?> get() = _document

    private val _username = MutableLiveData<String?>()
    val username: LiveData<String?> get() = _username

    private val _password = MutableLiveData<String?>()
    val password: LiveData<String?> get() = _password

    private val _update = MutableLiveData<Boolean>()
    val update: LiveData<Boolean> get() = _update

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _message = MutableLiveData<String?>()
    val message: LiveData<String?> get() = _message

    private var _copyName: String = "";
    private var _copyLastName: String = "";
    private var _copyDocument: String = "";
    private var _copyUsername: String = "";
    private var _copyPassword: String = "";

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


    fun getUser(document: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val fetchedUser = GetOneUserUC().invoke(document)
            _name.postValue(fetchedUser?.name)
            _copyName = fetchedUser?.name ?: ""
            _lastName.postValue(fetchedUser?.lastName)
            _copyLastName = fetchedUser?.lastName ?: ""
            _document.postValue(fetchedUser?.document)
            _copyDocument = fetchedUser?.document ?: ""
            _username.postValue(fetchedUser?.user)
            _copyUsername = fetchedUser?.user ?: ""
            _password.postValue(fetchedUser?.password)
            _copyPassword = fetchedUser?.password ?: ""
            _isLoading.value = false
            Log.i("ProfileVM", "Updated LiveData values")

        }
    }

    fun onChangeValues(
        name: String,
        lastName: String,
        document: String,
        username: String,
        password: String
    ) {
        _name.value = name
        _lastName.value = lastName
        _document.value = _copyDocument
        _username.value = username
        _password.value = password

        Log.i("ProfileVM", "Values on change is: $name $lastName $document $username $password ")
        Log.i(
            "ProfileVM",
            "Values copied is: $_copyName $_copyLastName $_copyDocument $_copyUsername $_copyPassword "
        )

        val validaciones: Boolean  = (
                validName(name)
                && validLastName(lastName)
                && validDocument(document)
                && validUsername(username)
                && validPassword(password)
                )

        _update.value = !(
                name == _copyName
                && lastName == _copyLastName
                && username == _copyUsername
                && password == _copyPassword
                )

        _update.value = _update.value!! && validaciones


    }

    fun validName(name: String): Boolean {
        val nameValidated = name.replace("\\s+".toRegex(), "")

        if (
            nameValidated.isEmpty()
            || nameValidated.length < 5
            || !nameValidated.all { it.isLetter() }
        ) {
            _validName.value = false
            return false

        } else {
            _validName.value = true
            return true
        }
    }

    fun validLastName(lastName: String): Boolean {
        val lastNameValidated = lastName.replace("\\s+".toRegex(), "")



        if (
            lastNameValidated.isEmpty()
            || lastNameValidated.length < 5
            || !lastNameValidated.all { it.isLetter() }
        ) {
            _validLastName.value = false
            return false

        } else {
            _validLastName.value = true
            return true
        }
    }

    fun validDocument(document: String): Boolean {
        val documentValidated = document.replace("\\s+".toRegex(), "")



        if (documentValidated.isEmpty()
            || documentValidated.length < 8
            || !documentValidated.all { it.isDigit() }
            ) {
            _validDocument.value = false
            return false
        } else {
            _validDocument.value = true
            return true
        }
    }

    fun validUsername(username: String): Boolean {
        val usernameValidated = username.replace("\\s+".toRegex(), "")



      if (usernameValidated.isEmpty()
            || usernameValidated.length < 5
            || !usernameValidated.all { it.isLetterOrDigit() }
           ) {

            _validUsername.value = false
          return false
        } else {
            _validUsername.value = true
          return true
        }
    }

    fun validPassword(password: String): Boolean {
        val passwordValidated = password.replace("\\s+".toRegex(), "")


        return if (passwordValidated.isEmpty() ||
            passwordValidated.length < 8 ||
            !passwordValidated.all { it.isLetterOrDigit() }) {
            _validPassword.value = false
            false
        } else {
            _validPassword.value = true
            true
        }
    }


    fun Logout(navController: NavController) {
        navController.navigate(AppScreens.HomePage.route)
    }

    fun updateUser() {
        _isLoading.value = true

        val user = UserModel(
            name = _name.value,
            lastName = _lastName.value,
            document = _document.value,
            user = _username.value,
            password = _password.value
        )

        viewModelScope.launch {
            val response = UpdateUserUC().invoke(user)

            if (response?.error == null) {

                _copyName = _name.value ?: ""
                _copyLastName = _lastName.value ?: ""
                _copyDocument = _document.value ?: ""
                _copyUsername = _username.value ?: ""
                _copyPassword = _password.value ?: ""
                _update.value = false
                _message.value = "Actualización exitosa"
            } else {

                _message.value = "Error al actualizar: ${response.error}"
            }

            _isLoading.value = false
            Log.i("ProfileVM Update", "Response: $response")
        }
    }

    fun resetMessage() {
        _message.value = null
    }

}
