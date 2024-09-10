package com.yazilimxyz.remindly.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen() {
    val months = remember { generateMonths() }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        itemsIndexed(months) { _, month ->
            MonthView(month = month)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthView(month: LocalDate) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
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

val buttonColor = Color(0xFFFFB8B8)

@Composable
fun DaysOfWeekHeader() {
    val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    Row(modifier = Modifier.fillMaxWidth()) {
        daysOfWeek.forEach { day ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
                    .background(buttonColor),
                contentAlignment = Alignment.Center
            ) {
                Text(text = day)
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
                    val isToday = day == LocalDate.now().dayOfMonth && month.month == LocalDate.now().month && month.year == LocalDate.now().year
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
                                    isToday -> Color.Red
                                    isWeekend -> Color.LightGray
                                    else -> Color.Transparent
                                },
                                shape = MaterialTheme.shapes.small
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day?.toString() ?: "",
                            color = when {
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
