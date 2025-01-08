package com.example.intercambioderegalos.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.intercambioderegalos.AgregarTemasScreen
import com.example.intercambioderegalos.GestionarIntercambioScreen
import com.example.intercambioderegalos.HomeScreen
import com.example.intercambioderegalos.LoginScreen
import com.example.intercambioderegalos.NuevoScreen
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
        composable(AppScreens.NuevoScreen.route) {
            NuevoScreen(navController)
        }

        composable(
            route = "${AppScreens.AgregarTemas.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: -1
            AgregarTemasScreen(navController = navController, intercambioId = id)
        }

        composable(AppScreens.GestionarIntercambio.route + "/{intercambioId}") { backStackEntry ->
            val intercambioId = backStackEntry.arguments?.getString("intercambioId")?.toInt() ?: 0
            GestionarIntercambioScreen(navController, intercambioId)
        }
    }

}