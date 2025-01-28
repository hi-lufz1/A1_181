package com.example.manprofinalpam.ui.view.tim


import com.example.manprofinalpam.ui.viewmodel.tim.UpdateTimVM
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.manprofinalpam.R
import com.example.manprofinalpam.model.dataTim
import com.example.manprofinalpam.ui.customwidget.CustomTopBar
import com.example.manprofinalpam.ui.navigasi.DesUpdatePry
import com.example.manprofinalpam.ui.viewmodel.PenyediaVM
import kotlinx.coroutines.launch

@Composable
@Preview
fun UpdateTimScreen(
    modifier: Modifier = Modifier,
    viewModel: UpdateTimVM = viewModel(factory = PenyediaVM.Factory),
    navigateBack: () -> Unit = {},
    navigateBackDetail: (String) -> Unit = {},
){
    val coroutineScope = rememberCoroutineScope()

    Scaffold (
        modifier = Modifier,
    ){ innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = colorResource(R.color.primary))
        ) {
            Spacer(
                Modifier
                    .height(16.dp)
                    .background(color = colorResource(id = R.color.primary))
            )
            CustomTopBar(title = "Edit Tim", onEditClick = {
            }, onBackClick = navigateBack, isEditEnabled = false)
            Spacer(
                Modifier
                    .height(16.dp)
                    .background(color = colorResource(R.color.primary))
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                HorizontalDivider(
                    thickness = 5.dp,
                    modifier = Modifier.padding(horizontal = 128.dp)
                )
                Spacer(Modifier.padding(8.dp))
                TimFormInput(
                    insertTimUiEvent = viewModel.uiState.insertTimUiEvent,
                    onValueChange = { updateEvent ->
                        viewModel.updateState(updateEvent)
                    }
                )
                Spacer(Modifier.padding(16.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primary)),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        coroutineScope.launch {
                            viewModel.updateTim()
                            navigateBackDetail(viewModel._idTim)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Simpan", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}