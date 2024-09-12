package com.yazilimxyz.remindly.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val buttonColor = Color(0xFFFFB8B8)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarPage() {
    val months = remember { generateMonths() }

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {
        itemsIndexed(months) { _, month ->
            MonthView(month = month)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthView(month: LocalDate) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .border(
            width = 2.dp,  // Border thickness
            color = Color.Gray,  // Border color
            shape = MaterialTheme.shapes.medium  // Shape of the border, can customize the corner radius here
        )
        .padding(16.dp)) {
        Text(
            text = month.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        DaysOfWeekHeader()

        CalendarGrid(month)


    }
}


@Composable
fun DaysOfWeekHeader() {
    val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    Row(modifier = Modifier.fillMaxWidth()) {
        daysOfWeek.forEach { day ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
                    .background(buttonColor.copy(alpha = 0.5f), shape = MaterialTheme.shapes.small),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    modifier = Modifier.padding(4.dp),
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarGrid(month: LocalDate) {
    val daysInMonth = month.month.length(month.isLeapYear)
    val startDayOfWeek = month.withDayOfMonth(1).dayOfWeek.value

    val weeks = remember {
        generateCalendarWeeks(startDayOfWeek, daysInMonth)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        weeks.forEach { week ->
            Row(modifier = Modifier.fillMaxWidth()) {
                week.forEach { day ->
                    val isToday =
                        day == LocalDate.now().dayOfMonth && month.month == LocalDate.now().month && month.year == LocalDate.now().year
                    val isWeekend = day?.let {
                        val dayOfWeek = (startDayOfWeek - 1 + it) % 7
                        dayOfWeek == 0 || dayOfWeek == 6
                    } ?: false

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .background(
                                when {
                                    isToday -> Color.Black.copy(alpha = 0.8f)
                                    isWeekend -> Color.LightGray
                                    else -> Color.Transparent
                                }, shape = MaterialTheme.shapes.small
                            ), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day?.toString() ?: "", color = when {
                                isToday -> Color.White
                                else -> Color.Black
                            }
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun generateMonths(): List<LocalDate> {
    val today = LocalDate.now()
    val months = mutableListOf<LocalDate>()
    val endMonth = today.plusMonths(12) // Display up to a year from now

    var current = today.withDayOfMonth(1) // Start from the current month
    while (current.isBefore(endMonth)) {
        months.add(current)
        current = current.plusMonths(1)
    }
    return months
}

fun generateCalendarWeeks(startDayOfWeek: Int, daysInMonth: Int): List<List<Int?>> {
    val weeks = mutableListOf<List<Int?>>()
    var week = MutableList(7) { null as Int? }
    var day = 1

    for (i in 1..(startDayOfWeek - 1)) {
        week[i - 1] = null
    }

    for (i in startDayOfWeek..7) {
        week[i - 1] = day++
    }

    weeks.add(week)

    while (day <= daysInMonth) {
        week = MutableList(7) { null }
        for (i in 0..6) {
            if (day <= daysInMonth) {
                week[i] = day++
            }
        }
        weeks.add(week)
    }

    return weeks
}
