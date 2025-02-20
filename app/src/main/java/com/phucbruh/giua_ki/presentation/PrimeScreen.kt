package com.phucbruh.giua_ki.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phucbruh.giua_ki.presentation.components.TextIconButton

@Composable
fun PrimeScreen() {
    var input by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(text = "Check the prime", fontSize = 24.sp)
        TextField(
            value = input,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                if (it.toIntOrNull() != null || it.isEmpty())
                    input = it
                checked = false
            },
            label = { Text(text = "Enter a number") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        if (checked) {
            val announcement =
                if (isPrime(input.toInt())) {
                    "This is a prime number"
                } else {
                    "This is not a prime number"
                }
            Text(
                text = announcement,
                fontSize = 24.sp
            )
        } else {
            Spacer(modifier = Modifier.height(8.dp))
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextIconButton(
                text = "Refresh",
                icon = Icons.Default.Refresh,
                onClick = {
                    input = ""
                    checked = false
                },
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            )
            TextIconButton(
                text = "Check",
                icon = Icons.Default.Check,
                onClick = {
                    checked = true
                },
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            )
        }
    }
}

fun isPrime(num: Int): Boolean {
    if (num <= 1) return false

    for (i in 2..num / 2) {
        if (num % i == 0) {
            return false
        }
    }
    return true
}