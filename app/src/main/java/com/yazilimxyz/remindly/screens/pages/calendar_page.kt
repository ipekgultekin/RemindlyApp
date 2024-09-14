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
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yazilimxyz.remindly.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val buttonColor = Color(0xFFFFB8B8)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarPage() {
    val months = remember { generateMonths() }
    val colorScheme = MaterialTheme.colorScheme

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {
        itemsIndexed(months) { _, month ->
            MonthView(month = month, colorScheme = colorScheme)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthView(month: LocalDate, colorScheme: ColorScheme) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .border(
            width = 2.dp,
            color = colorScheme.outline,
            shape = MaterialTheme.shapes.medium
        )
        .padding(16.dp)) {
        Text(
            text = formatMonthYear(month),
            style = MaterialTheme.typography.titleLarge,
            color = colorScheme.onBackground,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        DaysOfWeekHeader(colorScheme)

        CalendarGrid(month, colorScheme)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun formatMonthYear(month: LocalDate): String {
    val monthNames = listOf(
        stringResource(id = R.string.january),
        stringResource(id = R.string.february),
        stringResource(id = R.string.march),
        stringResource(id = R.string.april),
        stringResource(id = R.string.may),
        stringResource(id = R.string.june),
        stringResource(id = R.string.july),
        stringResource(id = R.string.august),
        stringResource(id = R.string.september),
        stringResource(id = R.string.october),
        stringResource(id = R.string.november),
        stringResource(id = R.string.december)
    )
    val monthIndex = month.monthValue - 1
    val monthName = monthNames[monthIndex]
    return "$monthName ${month.year}"
}


@Composable
fun DaysOfWeekHeader(colorScheme: ColorScheme) {
    val daysOfWeek = listOf(
        stringResource(id = R.string.sunday),
        stringResource(id = R.string.monday),
        stringResource(id = R.string.tuesday),
        stringResource(id = R.string.wednesday),
        stringResource(id = R.string.thursday),
        stringResource(id = R.string.friday),
        stringResource(id = R.string.saturday)
    )
    Row(modifier = Modifier.fillMaxWidth()) {
        daysOfWeek.forEach { day ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
                    .background(colorScheme.primary.copy(alpha = 0.5f), shape = MaterialTheme.shapes.small),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorScheme.onPrimary, // Update text color
                    modifier = Modifier.padding(4.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarGrid(month: LocalDate, colorScheme: ColorScheme) {
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
                                    isToday -> colorScheme.primary.copy(alpha = 0.8f)
                                    isWeekend -> colorScheme.surfaceVariant
                                    else -> Color.Transparent
                                }, shape = MaterialTheme.shapes.small
                            ), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day?.toString() ?: "",
                            color = when {
                                isToday -> colorScheme.onPrimary
                                else -> colorScheme.onSurface
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
