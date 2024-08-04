@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.weathersdkexample

import  android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weathersdk.ui.events.FinishEvent
import com.example.weathersdkexample.ui.theme.ButtonContainerColor
import com.example.weathersdkexample.ui.theme.DefaultTextColor
import com.example.weathersdkexample.ui.theme.SecondaryTextColor
import com.example.weathersdkexample.ui.theme.TopBarContainer

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val context = LocalContext.current

    LaunchedEffect(viewModel.events) {
        viewModel.events.collect { event ->
            when (event) {
                MainUiEvent.EmptyTextError -> showErrorToast(context)
            }
        }
    }

    LaunchedEffect(viewModel.forecastDismissSignalEvents) {
        viewModel.forecastDismissSignalEvents.collect { event ->
            when (event) {
                FinishEvent.OnFinished -> {
                    Log.d("WeatherSdkExampleApp", "forecast closed without error")
                }

                FinishEvent.OnFinishedWithError -> {
                    Log.d("WeatherSdkExampleApp", "forecast closed with error")
                }
            }
        }
    }

    MainView(viewModel::onAction)
}

@Composable
private fun MainView(onAction: (MainAction) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.top_bar_text),
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal,
                        color = DefaultTextColor
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = TopBarContainer)
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            val focusManager = LocalFocusManager.current
            var text by rememberSaveable { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                trailingIcon = {
                    if (text.isNotEmpty()) {
                        Icon(
                            modifier = Modifier.clickable { text = "" },
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = "clear text"
                        )
                    }
                },
                textStyle = TextStyle.Default.copy(fontSize = 16.sp, color = DefaultTextColor),
                value = text,
                singleLine = true,
                onValueChange = { text = it },
                label = {
                    Text(
                        stringResource(id = R.string.city_search_bar_label),
                        color = SecondaryTextColor
                    )
                },
            )

            Text(
                modifier = Modifier.padding(start = 32.dp),
                text = stringResource(id = R.string.city_search_bar_description),
                fontSize = 12.sp,
                color = SecondaryTextColor
            )

            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    focusManager.clearFocus()
                    onAction(MainAction.ForecastButtonClicked(text))
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonContainerColor
                )
            ) {
                Text(
                    text = stringResource(id = R.string.city_search_button_text),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

private fun showErrorToast(context: Context) {
    Toast.makeText(
        context,
        context.getString(R.string.city_search_bar_text_empty_error),
        Toast.LENGTH_SHORT,
    ).show()
}


@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainView {}
}