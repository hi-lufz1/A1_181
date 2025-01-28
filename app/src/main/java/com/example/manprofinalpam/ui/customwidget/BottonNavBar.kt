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
import androidx.compose.runtime.saveable.rememberSaveable
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
    navNum: Int,
    onNavChange: (Int) -> Unit, // Callback untuk mengubah navNum secara manual
    onProyek: () -> Unit,
    onTim: () -> Unit,
    onAnggota: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(Color.White)
            .border(
                1.dp,
                Color.LightGray,
                RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
            )
            .padding(vertical = 15.dp, horizontal = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier)
        // Tombol Proyek
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(onClick = {
                onNavChange(0) // Mengubah navNum menjadi 0
                onProyek() // Navigasi ke halaman Proyek
            }) {
                Icon(
                    painter = painterResource(
                        id = if (navNum == 0)
                            R.drawable.clipboard_text_fill__streamline_phosphor_fill__1_
                        else
                            R.drawable.clipboard_text__streamline_phosphor__1_
                    ),
                    contentDescription = "Proyek",
                    tint = if (navNum == 0) colorResource(id = R.color.primary) else Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }
            Text(
                text = "Proyek",
                style = MaterialTheme.typography.bodySmall,
                color = if (navNum == 0) colorResource(id = R.color.primary) else Color.Black
            )
        }

        // Tombol Tim
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(onClick = {
                onNavChange(1) // Mengubah navNum menjadi 1
                onTim() // Navigasi ke halaman Tim
            }) {
                Icon(
                    painter = painterResource(
                        id = if (navNum == 1)
                            R.drawable.multiple_neutral_2__streamline_ultimate
                        else
                            R.drawable.multiple_neutral_2__streamline_ultimate__1_
                    ),
                    contentDescription = "Tim",
                    tint = if (navNum == 1) colorResource(id = R.color.primary) else Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }
            Text(
                text = "Tim",
                style = MaterialTheme.typography.bodySmall,
                color = if (navNum == 1) colorResource(id = R.color.primary) else Color.Black
            )
        }

        // Tombol Anggota
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(onClick = {
                onNavChange(2) // Mengubah navNum menjadi 2
                onAnggota() // Navigasi ke halaman Anggota
            }) {
                Icon(
                    painter = painterResource(
                        id = if (navNum == 2)
                            R.drawable.user_filled__streamline_carbon
                        else
                            R.drawable.user__streamline_carbon
                    ),
                    contentDescription = "Anggota",
                    tint = if (navNum == 2) colorResource(id = R.color.primary) else Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }
            Text(
                text = "Anggota",
                style = MaterialTheme.typography.bodySmall,
                color = if (navNum == 2) colorResource(id = R.color.primary) else Color.Black
            )
        }
        Spacer(modifier = Modifier)
    }
}
