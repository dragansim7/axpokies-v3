package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.screens.PartnershipDetailScreen
import com.example.ui.screens.PartnershipDirectoryScreen
import com.example.ui.theme.CharcoalBackground
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.PartnershipViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = CharcoalBackground
        ) {
          AXPokiesApp()
        }
      }
    }
  }
}

@Composable
fun AXPokiesApp(
  viewModel: PartnershipViewModel = viewModel()
) {
  val navController = rememberNavController()
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  NavHost(
    navController = navController,
    startDestination = "directory",
    modifier = Modifier
      .fillMaxSize()
      .background(CharcoalBackground)
  ) {
    composable("directory") {
      PartnershipDirectoryScreen(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChanged,
        onClearSearch = viewModel::clearSearch,
        onPartnershipClick = { partnershipId ->
          viewModel.selectPartnership(partnershipId)
          navController.navigate("detail/$partnershipId")
        }
      )
    }

    composable(
      route = "detail/{partnershipId}",
      arguments = listOf(navArgument("partnershipId") { type = NavType.StringType })
    ) { backStackEntry ->
      val partnershipId = backStackEntry.arguments?.getString("partnershipId")
      val partnership = uiState.searchResults.find { it.partnership.id == partnershipId }?.partnership
        ?: uiState.selectedPartnership

      PartnershipDetailScreen(
        partnership = partnership,
        onBackClick = {
          navController.popBackStack()
        }
      )
    }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  MyApplicationTheme { Greeting("Android") }
}

