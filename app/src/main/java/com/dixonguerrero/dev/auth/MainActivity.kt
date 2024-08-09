package com.dixonguerrero.dev.auth

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dixonguerrero.dev.auth.navigation.AppNavigation
import com.dixonguerrero.dev.auth.ui.screens.homePage.view.HomePage
import com.dixonguerrero.dev.auth.ui.screens.login.view.LoginPage
import com.dixonguerrero.dev.auth.ui.screens.profile.view.ProfilePage
import com.dixonguerrero.dev.auth.ui.screens.register.view.RegisterPage
import com.dixonguerrero.dev.auth.ui.screens.successRegister.view.SuccessRegisterPage
import com.dixonguerrero.dev.auth.ui.theme.AuthTheme
import com.dixonguerrero.dev.auth.ui.theme.Purple80

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AuthTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    AppNavigation(modifier = Modifier.padding(innerPadding))
                    Log.i("MainActivity", "AppNavigation called")
                }
            }
        }
    }
}

