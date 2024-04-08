package com.phucbruh.giua_ki.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phucbruh.giua_ki.presentation.components.TextIconButton

@Composable
fun EquationScreen() {
    var a by remember { mutableStateOf("") }
    var b by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(text = "a*x + b = c", fontSize = 24.sp)
        TextField(
            value = a,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            onValueChange = {
                if (it.toFloatOrNull() != null || it.isEmpty())
                    a = it
                checked = false
            },
            label = { Text(text = "Enter value a") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        TextField(
            value = b,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            onValueChange = {
                if (it.toFloatOrNull() != null || it.isEmpty())
                    b = it
                checked = false
            },
            label = { Text(text = "Enter value b") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        if (checked) {
            if (b.toFloatOrNull() != null && a.toFloatOrNull() != null) {
                val x = (-b.toFloat()) / a.toFloat()
                Text(
                    text = "x = $x", fontSize = 24.sp
                )
            }
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
                    a = ""
                    b = ""
                    checked = false
                },
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            )
            TextIconButton(
                text = "Calculate",
                icon = Icons.Default.Calculate,
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
