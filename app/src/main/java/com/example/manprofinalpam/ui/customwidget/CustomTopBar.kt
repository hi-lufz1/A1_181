package com.example.manprofinalpam.ui.customwidget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.manprofinalpam.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun CustomTopBar(
    title: String ="tes aja",
    onBackClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    isEditEnabled: Boolean = true
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 32.sp,
                color = Color.White, // Warna teks putih
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center // Teks di tengah
            )
        },
        navigationIcon = {
            IconButton (onClick = onBackClick) {
                Box(
                    modifier = Modifier
                        .size
                            (36.dp) // Ukuran lingkaran
                        .background(color = Color.White, shape = CircleShape), // Latar belakang lingkaran putih
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, // Ikon panah belakang
                        contentDescription = "Back",
                        tint = Color.Black // Warna ikon hitam
                    )
                }
            }
        },
        actions = {
            if (isEditEnabled) {
                IconButton(onClick = onEditClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.pencil_simple_light__streamline_phosphor),
                        contentDescription = "Edit",
                        tint = Color.White // Warna ikon putih
                    )
                }
            }
            else {
                IconButton(onClick = {}, enabled = false) {
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.primary),
            titleContentColor = Color.White
        )
    )
}