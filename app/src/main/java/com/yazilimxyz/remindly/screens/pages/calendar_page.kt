package com.yazilimxyz.remindly.screens

import android.os.Build
import android.util.Log
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yazilimxyz.remindly.R
import com.yazilimxyz.remindly.models.HomeViewModel
import com.yazilimxyz.remindly.screens.pages.TaskItem
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

/*
* 2024-09-28 00:16:51.643 16365-16365 mesajj                  com.yazilimxyz.remindly              D  [TaskItem(title=renk, timeLeft=26-9-2024 14:54, color=Color(0.03529412, 0.38039216, 0.7137255, 1.0, sRGB IEC61966-2.1), colorName=Blue, description=renk), TaskItem(title=yazilim.xyz, timeLeft=27-9-2024 23:40, color=Color(0.03529412, 0.38039216, 0.7137255, 1.0, sRGB IEC61966-2.1), colorName=Blue, description=123deneme), TaskItem(title=deneme2, timeLeft=18-9-2024 16:20, color=Color(0.53333336, 0.53333336, 0.53333336, 1.0, sRGB IEC61966-2.1), colorName=Gray, description=merhaba), TaskItem(title=deneme, timeLeft=18-9-2024 4:10, color=Color(0.0, 0.54509807, 0.0, 1.0, sRGB IEC61966-2.1), colorName=Green, description=deneme123), TaskItem(title=sgsg, timeLeft=22-9-2024 9:14, color=Color(0.03529412, 0.38039216, 0.7137255, 1.0, sRGB IEC61966-2.1), colorName=Blue, description=sdgdg), TaskItem(title=xyz, timeLeft=25-9-2024 15:18, color=Color(0.03529412, 0.38039216, 0.7137255, 1.0, sRGB IEC61966-2.1), colorName=Blue, description=remindly), TaskItem(title=., timeLeft=24-9-2024 15:1, color=Color(0.03529412, 0.38039216, 0.7137255, 1.0, sRGB IEC61966-2.1), colorName=Blue, description=.), TaskItem(title=wef, timeLeft=22-9-2024 10:52, color=Color(0.03529412, 0.38039216, 0.7137255, 1.0, sRGB IEC61966-2.1), colorName=Blue, description=wefe), TaskItem(title=renkdeneme, timeLeft=22-9-2024 17:30, color=Color(0.03529412, 0.38039216, 0.7137255, 1.0, sRGB IEC61966-2.1), colorName=Blue, description=fmdikici), TaskItem(title=yazilim.xyz, timeLeft=28-9-2024 20:25, color=Color(0.03529412, 0.38039216, 0.7137255, 1.0, sRGB IEC61966-2.1), colorName=Blue, description=ddadasdasdas), TaskItem(title=saaa, timeLeft=9-9-2024 6:42, color=Color(0.53333336, 0.53333336, 0.53333336, 1.0, sRGB IEC61966-2.1), colorName=Gray, description=sbbb), TaskItem(title=fmd, timeLeft=22-9-2024 17:31, color=Color(0.03529412, 0.38039216, 0.7137255, 1.0, sRGB IEC61966-2.1), colorName=Blue, description=fmd), TaskItem(title=xxx, timeLeft=12-9-2024 23:58, color=Color(0.6431373, 0.0, 0.0, 1.0, sRGB IEC61966-2.1), colorName=Red, description=xxx), TaskItem(title=merhaba dunya, timeLeft=19-9-2024 16:55, color=Color(0.53333336, 0.53333336, 0.53333336, 1.0, sRGB IEC61966-2.1), colorName=Gray, description=merhaba merhaba merhaba merhaba merhaba merhaba merhaba merhaba merhaba merhaba  ), TaskItem(title=dxfv<sd, timeLeft=22-9-2024 22:53, color=Color(0.03529412, 0.38039216, 0.7137255, 1.0, sRGB IEC61966-2.1), colorName=Blue, description=dsvv), TaskItem(title=toplanti, timeLeft=13-9-2024 15:20, color=Color(0.0, 0.54509807, 0.0, 1.0, sRGB IEC61966-2.1), colorName=Green, description=deneme), TaskItem(title=merhaba, timeLeft=13-9-2024 14:15, color=Color(0.03529412, 0.38039216, 0.7137255, 1.0, sRGB IEC61966-2.1), colorName=Blue, description=bu bir denemedir.), TaskItem(title=fmd2, timeLeft=23-9-2024 14:30, color=Color(0.03529412, 0.38039216, 0.7137255, 1.0, sRGB IEC61966-2.1), colorName=Blue, description=renkdeneme), TaskItem(title=rertey, timeLeft=22-9-2024 10:50, color=Color(0.03529412, 0.38039216, 0.7137255, 1.0, sRGB IEC61966-2.1), colorName=Blue, description=reyer), TaskItem(title=hh, timeLeft=9-9-2024 12:1, color=Color(0.03529412, 0.38039216, 0.7137255, 1.0, sRGB IEC61966-2.1), colorName=Blue, description=), TaskItem(title=deneme3, timeLeft=19-9-2024 5:24, color=Color(0.53333336, 0.53333336, 0.53333336, 1.0, sRGB IEC61966-2.1), colorName=Gray, description=123), TaskItem(title=yazilim.xyz, timeLeft=14-9-2024 18:36, color=Color(0.03529412, 0.38039216, 0.7137255, 1.0, sRGB IEC61966-2.1), colorName=Blue, description=deneme deneme 123), TaskItem(title=fdd, timeLeft=8-9-2024 21:16, color=Color(0.03529412, 0.38039216, 0.7137255, 1.0, sRGB IEC61966-2.1), colorName=Blue, description=), TaskItem(title=xyz, timeLeft=25-9-2024 15:48, color=Color(0.03529412, 0.38039216, 0.7137255, 1.0, sRGB IEC61966-2.1), colorName=Blue, description=remindly), TaskItem(title=fmd2, timeLeft=23-9-2024 14:30, color=Color(0.03529412, 0.38039216, 0.7137255, 1.0, sRGB IEC61966-2.1), colorName=Blue, description=renkdeneme), TaskItem(title=fff, timeLeft=11-9-2024 23:45, color=Color(0.53333336,

*
* */


@Composable
fun TaskItemView(task: TaskItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(task.color)
            .padding(16.dp)
    ) {
        Text(
            text = task.title,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = task.description,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
        Text(
            text = task.timeLeft.toString(),  // Format time as needed
            style = MaterialTheme.typography.bodySmall,
            color = Color.White
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthView(month: LocalDate, colorScheme: ColorScheme) {

    val homeViewModel: HomeViewModel = viewModel()
    val taskList by homeViewModel.meetings.collectAsState()
    Log.d("mesajj", taskList.toString())

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

        taskList.forEach { task ->
            TaskItemView(task)
            Spacer(modifier = Modifier.height(8.dp))
        }


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
