package com.example.manprofinalpam.ui.customwidget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.manprofinalpam.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownWidget(
    selectedValue: String,
    options: List<String>,
    onValueChangeEvent: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = {},
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp) ,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.Transparent,
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray,
                focusedTextColor = Color.Black,
                disabledTextColor = Color.Black,
                disabledBorderColor = Color.LightGray,
            ),textStyle = TextStyle(
                color = Color.Black, fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            ),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {

            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option, color = Color.Black, fontWeight = FontWeight.Normal,
                        fontSize = 16.sp) },
                    onClick = {
                        expanded = false
                        onValueChangeEvent(option)
                    }
                )
            }
        }
    }
}

