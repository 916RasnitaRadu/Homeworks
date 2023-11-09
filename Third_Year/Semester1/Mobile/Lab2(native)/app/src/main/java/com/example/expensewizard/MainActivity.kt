package com.example.expensewizard

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.expensewizard.data.TransactionViewModel
import com.example.expensewizard.model.Transaction
import com.example.expensewizard.ui.theme.ExpenseWizardTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expensewizard.ui.components.list.TransactionList
import com.example.expensewizard.ui.pages.addPage.AddPage
import com.example.expensewizard.ui.pages.homePage.HomePage
import com.example.expensewizard.ui.pages.updatePage.UpdatePage

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            ExpenseWizardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavComponent()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExpenseWizardTheme {
        NavComponent()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavComponent() {
    val navController = rememberNavController()
    val viewModel: TransactionViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomePage(navController = navController, viewModel)
        }
        composable("add") {
            AddPage(navController = navController, viewModel)
        }
        composable("update/{transactionId}") {backStackEntry ->
            val transactionId = backStackEntry.arguments?.getString("transactionId")
            if (transactionId != null) {
                UpdatePage(navController = navController, viewModel,transactionId.toInt())
            }
        }
    }
}


