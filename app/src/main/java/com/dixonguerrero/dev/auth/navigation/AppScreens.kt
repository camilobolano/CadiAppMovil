package com.dixonguerrero.dev.auth.navigation

sealed class AppScreens(val route: String){
    object HomePage: AppScreens("home_page")
    object LoginPage: AppScreens("login_page")
    object RegisterPage: AppScreens("register_page")
    object SuccessRegisterPage: AppScreens("success_register_page")
    object ProfilePage: AppScreens("profile_page")
}


