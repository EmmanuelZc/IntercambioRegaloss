package com.example.intercambioderegalos.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.intercambioderegalos.HomeScreen
import com.example.intercambioderegalos.LoginScreen
import com.example.intercambioderegalos.RegisterScreen
import com.example.intercambioderegalos.SplashScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.SplashScreen.route
    ){
        composable(AppScreens.SplashScreen.route){
            SplashScreen(navController)
        }
        composable(AppScreens.LoginScreen.route){
            LoginScreen(navController)
        }
        composable(AppScreens.RegisterScreen.route){
            RegisterScreen(navController)
        }
        composable(AppScreens.HomeScreen.route) {
            HomeScreen(navController)
        }
    }
}