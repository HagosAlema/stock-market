package com.hagos.stockmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hagos.stockmarket.presentation.company_info.CompanyInfoScreen
import com.hagos.stockmarket.presentation.company_listing.CompanyListingsScreen
import com.hagos.stockmarket.theme.StackMarketTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            StackMarketTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).windowInsetsPadding(WindowInsets.systemBars),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        modifier = Modifier.fillMaxSize(),
                        navController = navController,
                        startDestination = "CompanyListing",
                        builder = {
                            composable(
                                route = "CompanyListing"
                            ){
                                CompanyListingsScreen{
                                    navController.navigate("CompanyInfo/${it}"){launchSingleTop = true}
                                }
                            }
                            composable(
                                route = "CompanyInfo/{symbol}",
                                arguments = listOf(navArgument(name = "symbol"){type = NavType.StringType})
                            ){
                                CompanyInfoScreen(
                                    symbol = it.arguments?.getString("symbol") ?: "TSLA",
                                    navigateBack = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }
                        )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StackMarketTheme {
        Greeting("Android")
    }
}