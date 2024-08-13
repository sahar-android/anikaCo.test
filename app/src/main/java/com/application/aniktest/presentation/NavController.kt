package com.application.aniktest.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.application.aniktest.ResturantViewModel
import com.application.aniktest.Start

@Composable
fun NavConteroller(
    navController: NavHostController,
    resturantViewModel: ResturantViewModel
) {

    NavHost(navController = navController, startDestination = "start") {
        composable("start") {
            Start(navController)
        }
        composable("add_customes") {
            AddCustomers(navController, resturantViewModel)
        }
        composable("add_foods") {
            AddFoods(navController, resturantViewModel)
        }

        composable("customer_info/{customerName}/{customerId}",
            arguments = listOf(
                navArgument("customerName") {
                    type = NavType.StringType
                },
                navArgument("customerId"){
                    type= NavType.IntType
                }
            )
        ) {
            CustomerInfo(navController, resturantViewModel,
                it.arguments?.getString("customerName"),
                it.arguments?.getInt("customerId"))
        }


    }

}