package com.yazilimxyz.remindly.utilities

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController


@Composable
fun BottomNavigationBar(
    navController: NavHostController, onAddMeetingClick: () -> Unit
) {
    NavigationBar {
        val items = listOf(
            BottomNavItem.Home,
            BottomNavItem.Calendar,
            BottomNavItem.AddMeeting,
            BottomNavItem.Profile,
            BottomNavItem.Settings
        )
        items.forEach { item ->
            val isAddMeeting = item is BottomNavItem.AddMeeting
            NavigationBarItem(icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Blue,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                ),
                onClick = {
                    if (isAddMeeting) {
                        onAddMeetingClick()
                    } else {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                })
        }
    }
}
