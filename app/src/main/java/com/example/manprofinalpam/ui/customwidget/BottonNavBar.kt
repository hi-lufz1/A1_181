package com.example.manprofinalpam.ui.customwidget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.manprofinalpam.R


@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    onProyek: () -> Unit ,
    onTim: () -> Unit ,
    onAnggota: () -> Unit ,
) {

    var navNum by remember {
        mutableStateOf(0)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(Color.White)
            .border(
                1.dp,
                Color.LightGray,
                RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
            ) // Menambahkan garis tepi
            .padding(vertical = 15.dp, horizontal = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier)
        if (navNum == 0) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                IconButton(onClick =  onProyek ) {

                    Icon(
                        painter = painterResource(id = R.drawable.clipboard_text_fill__streamline_phosphor_fill__1_),
                        contentDescription = "Proyek",
                        tint = colorResource(id = R.color.primary),
                        modifier = Modifier.size(32.dp)
                    )
                }
                Text(
                    text = "Proyek", // Label untuk ikon pertama
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.primary)
                )
            }

        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = { navNum = 0 }) {
                    Icon(
                        painter = painterResource(id = R.drawable.clipboard_text__streamline_phosphor__1_),
                        contentDescription = "Proyek",
//                        tint = ThinTextColor,
                        modifier = Modifier
                            .size(32.dp)
                    )
                }
                Text(
                    text = "Proyek", // Label untuk ikon pertama
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
            }
        }
        if (navNum == 1) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = onTim ) {
                    Icon(
                        painter = painterResource(id = R.drawable.multiple_neutral_2__streamline_ultimate),
                        contentDescription = "home",
                        tint = colorResource(id = R.color.primary),
                        modifier = Modifier
                            .size(32.dp)
                    )
                }
                Text(
                    text = "Tim",
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.primary),
                )
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = { navNum = 1 }) {
                    Icon(
                        painter = painterResource(id = R.drawable.multiple_neutral_2__streamline_ultimate__1_),
                        contentDescription = "home",
//                        tint = ThinTextColor,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Text(
                    text = "Tim",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
            }
        }

        if (navNum == 2) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = onAnggota) {
                    Icon(
                        painter = painterResource(id = R.drawable.user_filled__streamline_carbon),
                        contentDescription = "home",
                        tint = colorResource(id = R.color.primary),
                        modifier = Modifier.size(32.dp),
                    )
                }
                Text(
                    text = "Anggota",
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.primary),
                )
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = { navNum = 2 }) {
                    Icon(
                        painter = painterResource(id = R.drawable.user__streamline_carbon),
                        contentDescription = "home",
//                        tint = ThinTextColor,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Text(
                    text = "Anggota",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier)
    }
}


