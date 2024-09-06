package com.yazilimxyz.remindly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yazilimxyz.remindly.ui.theme.RemindlyTheme
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

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        BottomNavigationBar(navController = navController,
            onAddMeetingClick = { scope.launch { sheetState.show() } })
    }) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            NavigationHost(navController = navController)
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

@Composable
fun BottomNavigationBar(
    navController: NavHostController, onAddMeetingClick: () -> Unit
) {
    NavigationBar {
        val items = listOf(
            BottomNavItem.Home, BottomNavItem.AddMeeting, BottomNavItem.Settings
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
                }
            )
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Home.route) {
            HomeScreen()
        }
        composable(BottomNavItem.Settings.route) {
            SettingsScreenContent()
        }
    }
}

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Home Screen")
    }
}

@Composable
fun SettingsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Settings Screen")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMeetingSheet() {

    var selectedAvatarIndex by remember { mutableStateOf(0) }

    // List of avatars (replace these with actual resource IDs or URLs)
    val avatars = listOf(
        R.drawable.avatar1,
        R.drawable.avatar2,
        R.drawable.avatar3,
        R.drawable.avatar5,
        R.drawable.avatar6
    )


    // Use LazyColumn for scrollable content
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(26.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        item {
            Text(
                text = "Create\nNew Meeting", style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.SemiBold, // Set text bold
                    fontSize = 40.sp // Set custom font size
                )
            )
            Spacer(modifier = Modifier.height(30.dp))
        }

        item {
            Row {
                Icon(imageVector = Icons.Default.Info, contentDescription = "meeting icon")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Set a meeting", style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(5.dp))

            TextField(
                value = "",
                onValueChange = {},
                label = {
                    Text(
                        "Meeting Name", style = TextStyle(
                            color = Color.Black.copy(alpha = 0.4f), fontSize = 18.sp
                        )
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.LightGray.copy(alpha = 0.3f), // Light gray background
                    focusedIndicatorColor = Color.Transparent, // Removes the underline when focused
                    unfocusedIndicatorColor = Color.Transparent, // Removes the underline when not focused
                    cursorColor = MaterialTheme.colorScheme.primary // Customize cursor color
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp) // Adjust height for better appearance
                    .padding(horizontal = 8.dp),
                shape = MaterialTheme.shapes.medium // Add rounded corners
            )
            Spacer(modifier = Modifier.height(30.dp))
        }

        item {
            Row {
                Icon(imageVector = Icons.Default.Info, contentDescription = "meeting icon")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Set a description", style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(5.dp))

            TextField(
                value = "",
                onValueChange = {},
                label = {
                    Text(
                        "Description", style = TextStyle(
                            color = Color.Black.copy(alpha = 0.4f), fontSize = 18.sp
                        )
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.LightGray.copy(alpha = 0.3f), // Light gray background
                    focusedIndicatorColor = Color.Transparent, // Removes the underline when focused
                    unfocusedIndicatorColor = Color.Transparent, // Removes the underline when not focused
                    cursorColor = MaterialTheme.colorScheme.primary // Customize cursor color
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp) // Adjust height for better appearance
                    .padding(horizontal = 8.dp),
                shape = MaterialTheme.shapes.medium // Add rounded corners
            )
            Spacer(modifier = Modifier.height(30.dp))
        }

        item {
            Row {
                Icon(imageVector = Icons.Default.Info, contentDescription = "meeting icon")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Meeting Time", style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(5.dp))

            SelectTimeButton {
                // Handle time selection logic here
            }

            Spacer(modifier = Modifier.height(30.dp))
        }

        item {
            Row {
                Icon(imageVector = Icons.Default.Face, contentDescription = "meeting icon")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Assigned For", style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            // Horizontal scrollable avatars
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                AvatarImage(avatar = R.drawable.avatar1, content = "avatar 1") {
                    // Handle avatar click here
                }
                AvatarImage(avatar = R.drawable.avatar2, content = "avatar 2") {
                    // Handle avatar click here
                }
                AvatarImage(avatar = R.drawable.avatar3, content = "avatar 3") {
                    // Handle avatar click here
                }
                AvatarImage(avatar = R.drawable.avatar4, content = "avatar 4") {
                    // Handle avatar click here
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }

        item {
            MeetingButton(title = "Create Meeting", color = Color.Black) {
                // Handle create meeting action here
            }

            Spacer(modifier = Modifier.height(10.dp))

            MeetingButton(title = "Clear Fields", color = MaterialTheme.colorScheme.secondary) {
                // Handle clear fields action here
            }
        }
    }
}


@Composable
fun AvatarImage(@DrawableRes avatar: Int, content: String?, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = avatar),
            contentDescription = content,
            modifier = Modifier
                .width(120.dp)
                .height(120.dp)
                .padding(10.dp)
                .clickable(onClick = onClick), // Add clickable behavior
            contentScale = ContentScale.Crop
        )
        Text(text = content ?: "", style = MaterialTheme.typography.labelLarge)
    }
}



@Composable
fun SelectTimeButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 8.dp),
        shape = MaterialTheme.shapes.medium, // Add rounded corners
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            text = "Select Date & Time", style = TextStyle(
                color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp
            )
        )
    }
}

@Composable
fun MeetingButton(title:String, color:Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 8.dp),
        shape = MaterialTheme.shapes.medium, // Add rounded corners
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        )
    ) {
        Text(
            text = title, style = TextStyle(
                color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp
            )
        )
    }
}


sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Home : BottomNavItem("home", "Home", Icons.Default.Home)
    object AddMeeting : BottomNavItem("add_meeting", "Add", Icons.Default.AddCircle)
    object Settings : BottomNavItem("settings", "Settings", Icons.Default.Settings)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RemindlyTheme {
        MainScreen()
    }
}

