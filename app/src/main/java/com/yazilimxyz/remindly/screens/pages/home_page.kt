package com.yazilimxyz.remindly.screens.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yazilimxyz.remindly.R

import androidx.lifecycle.viewmodel.compose.viewModel
import com.yazilimxyz.remindly.models.HomeViewModel

@Composable
fun HomePage(homeViewModel: HomeViewModel = viewModel()) {
    var searchText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("All") }

    // ViewModel'deki görev listesine erişiyoruz
    val taskList by homeViewModel.meetings.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Metin
        Text(text = stringResource(id = R.string.searchandsort), style = TextStyle(fontSize = 20.sp, color = MaterialTheme.colorScheme.onBackground ))
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchBar(
                searchText = searchText,
                onSearchTextChanged = { searchText = it },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Sharp.Menu,
                contentDescription = stringResource(id = R.string.search),
                modifier = Modifier
                    .clickable { expanded = true }
                    .padding(8.dp)
                    .size(42.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )

            // DropdownMenu
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(id = R.string.accdate))  },
                    onClick = {
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text(stringResource(id = R.string.acccolor)) },
                    onClick = {
                        expanded = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Kategori seçim
        Text(text = stringResource(id = R.string.choose), style = TextStyle(fontSize = 20.sp,  color = MaterialTheme.colorScheme.onBackground))
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            CategoryButton(stringResource(id = R.string.all), selectedCategory, Color.Gray) { selectedCategory = "All" }
            CategoryButton(stringResource(id = R.string.red), selectedCategory, Color(0xFFa40000)) { selectedCategory = "Red" }
            CategoryButton(stringResource(id = R.string.green), selectedCategory, Color(0xFF008b00)) { selectedCategory = "Green" }
            CategoryButton(stringResource(id = R.string.blue), selectedCategory, Color(0xFF0961B6)) { selectedCategory = "Blue" }
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Görev Liste
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            taskList.forEach { task ->
                if (selectedCategory == "All" || selectedCategory == task.colorName) {
                    TaskCard(task)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchText: String, onSearchTextChanged: (String) -> Unit, modifier: Modifier = Modifier) {
    TextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = stringResource(id = R.string.search),
                fontSize = 16.sp,
                color = Color.Gray
            ) // Updated
        },
        textStyle = TextStyle(fontSize = 16.sp),
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search)
            ) // Updated
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}


@Composable
fun TaskCard(task: TaskItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), // Surface color from theme
        border = BorderStroke(1.dp, task.color)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .width(10.dp)
                            .height(40.dp)
                            .background(task.color)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = task.title,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface // Text color from theme
                    )
                }
                Text(text = task.timeLeft, color = MaterialTheme.colorScheme.onSurfaceVariant) // Adjust for readability
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = task.description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant, // Adjust for readability
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


// Kategori
@Composable
fun CategoryButton(
    category: String,
    selectedCategory: String,
    color: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (category == selectedCategory) color else Color.LightGray
        ),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.padding(4.dp)
    ) {
        Text(text = category,
            color = Color.White,
            fontSize = 18.sp)
    }
}

data class TaskItem(
    val title: String,
    val timeLeft: String,
    val color: Color,
    val colorName: String,
    val description: String // Yeni açıklama alanı
)
