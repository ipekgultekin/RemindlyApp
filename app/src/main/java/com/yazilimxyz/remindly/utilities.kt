package com.yazilimxyz.remindly

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun editDialogueLottie() {
    // Load the Lottie animation composition
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.credentials_animation)
    )

    // Display the animation
    LottieAnimation(
        composition = composition,
        modifier = Modifier.width(100.dp).height(100.dp)
    )
}

@Composable
fun CustomButton(title: String, color: Color, onClick: () -> Unit) {
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
