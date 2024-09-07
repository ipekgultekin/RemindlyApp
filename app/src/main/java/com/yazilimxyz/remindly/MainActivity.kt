package com.yazilimxyz.remindly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.yazilimxyz.remindly.screens.AddMeetingSheet
import com.yazilimxyz.remindly.ui.theme.RemindlyTheme
import com.yazilimxyz.remindly.utilities.BottomNavigationBar
import com.yazilimxyz.remindly.utilities.NavigationHost
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RemindlyTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
        BottomNavigationBar(
            navController = navController,
            onAddMeetingClick = { scope.launch { sheetState.show() } }
        )
    }) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {

            // sayfalara yönlendirir
            NavigationHost(navController = navController)

            // eğer add tıklanmışsa sheet'e yönlendirir
            if (sheetState.isVisible) {
                ModalBottomSheet(
                    onDismissRequest = { scope.launch { sheetState.hide() } },
                    sheetState = sheetState
                ) {
                    AddMeetingSheet()
                }
            }
        }
    }
}



