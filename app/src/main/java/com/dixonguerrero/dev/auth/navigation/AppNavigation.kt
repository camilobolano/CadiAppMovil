package com.dixonguerrero.dev.auth.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dixonguerrero.dev.auth.ui.screens.homePage.view.HomePage
import com.dixonguerrero.dev.auth.ui.screens.login.view.LoginPage
import com.dixonguerrero.dev.auth.ui.screens.profile.view.ProfilePage
import com.dixonguerrero.dev.auth.ui.screens.profile.viewModel.ProfileVM
import com.dixonguerrero.dev.auth.ui.screens.register.view.RegisterPage
import com.dixonguerrero.dev.auth.ui.screens.successRegister.view.SuccessRegisterPage

@Composable
fun AppNavigation(modifier: Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.HomePage.route) {

        //Page Profile
        composable(
            route = AppScreens.ProfilePage.route + "/{document}",
            arguments = listOf(navArgument(name = "document") {
                type = NavType.StringType
            })
        ) {
            ProfilePage(modifier ,navController, it.arguments?.getString("document"))
        }

        //Page Home
        composable(route = AppScreens.HomePage.route) {
            HomePage(modifier, navController = navController)
        }

        //Page Login
        composable(
            AppScreens.LoginPage.route )
        {
            LoginPage(modifier, navController = navController)
        }

        //Page Register
        composable(route = AppScreens.RegisterPage.route) {
            RegisterPage(modifier,navController)
        }

        //Page Success Register
        composable(route = AppScreens.SuccessRegisterPage.route) {
            SuccessRegisterPage(modifier,navController)
        }




    }

}