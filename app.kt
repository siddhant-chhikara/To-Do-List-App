package com.example.huh

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs

fun hsvToColor(hue: Float, saturation: Float, value: Float): Color {
    val h = (hue % 360 + 360) % 360 // ensure hue is in [0,360)
    val s = saturation.coerceIn(0f, 1f)
    val v = value.coerceIn(0f, 1f)

    val c = v * s
    val x = c * (1 - abs((h / 60) % 2 - 1))
    val m = v - c

    return when (h.toInt() / 60) {
        0 -> Color(red = c + m, green = x + m, blue = m)
        1 -> Color(red = x + m, green = c + m, blue = m)
        2 -> Color(red = m, green = c + m, blue = x + m)
        3 -> Color(red = m, green = x + m, blue = c + m)
        4 -> Color(red = x + m, green = m, blue = c + m)
        else -> Color(red = c + m, green = m, blue = x + m)
    }
}

@Composable
fun HomePage() {
    var tasks by remember { mutableStateOf(listOf<String>()) }
    var newTask by remember { mutableStateOf("") }

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val hue1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 55555, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val hue2 by infiniteTransition.animateFloat(
        initialValue = 180f,
        targetValue = 540f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 55555, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val color1 = hsvToColor(hue1, 1f, 0.3f)
    val color2 = hsvToColor(hue2, 1f, 0.3f)

    Box(
        modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(color2, color1)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 17.dp)
        ) {
            Text(
                text = "Welcome 😊",
                color = Color.White,
                fontSize = 33.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 33.dp, bottom = 0.dp),
                onTextLayout = {}
            )
            Spacer(modifier = Modifier.height(0.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 33.dp, horizontal = 0.dp) // Reduced horizontal padding to 0
                    .background(
                        color = Color.Black, // Change this to black
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(17.dp) // Add padding to the Box
            ) {
                BasicTextField(
                    value = newTask,
                    onValueChange = { newTask = it },
                    textStyle = TextStyle(color = Color.White, fontSize = 17.sp),
                    modifier = Modifier.fillMaxWidth(), // fill the width
                    keyboardActions = KeyboardActions(onDone = {
                        if (newTask.isNotBlank()) { // check if newTask is not blank
                            tasks = listOf(newTask) + tasks
                            newTask = "" // clear the input field after adding the task
                        }
                    }),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.background(Color.Black)
                        ) {
                            innerTextField()
                        }
                    }
                )
            }
            Column {
                tasks.forEach { task ->
                    Text(
                        text = task,
                        color = Color.White,
                        fontSize = 17.sp,
                        modifier = Modifier.padding(vertical = 9.dp, horizontal = 13.dp),
                        onTextLayout = {}
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewHomePage() {
    HomePage()
}