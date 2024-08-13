package com.application.aniktest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.application.aniktest.presentation.NavConteroller
import com.application.aniktest.ui.theme.AnikTestTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private  val TAG = "test1234"
    val resturantViewModel by viewModels<ResturantViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnikTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavConteroller(navController = navController,resturantViewModel)
                }
            }
        }
    }
}

@Composable
fun Start(navHostController: NavHostController) {
    val scope=rememberCoroutineScope()
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
                     scope.launch {
                         navHostController.navigate("add_customes")
                     }

        }, modifier = Modifier
            .padding(horizontal = 2.dp)
            .fillMaxWidth()
            .height(80.dp)) {
            Text(text = "Customers", fontSize = 32.sp, textAlign = TextAlign.Center)
        }

        Spacer(modifier = Modifier.height(60.dp))

        Button(onClick = {
                scope.launch {
                    navHostController.navigate("add_foods")
                }

        }, modifier = Modifier
            .padding(horizontal = 2.dp)
            .fillMaxWidth()
            .height(80.dp)) {
            Text(text = "Foods", fontSize = 32.sp, textAlign = TextAlign.Center)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnikTestTheme {

    }
}