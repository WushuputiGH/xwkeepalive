package com.zsrh.xwkeepalivedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zsrh.xwkeepalivedemo.ui.theme.XWKeepAliveDemoTheme
import com.zsrh.xwkeepalivelibrary.BatteryOptimizationUtil
import com.zsrh.xwkeepalivelibrary.KeepAliveManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XWKeepAliveDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Button(
                        modifier = Modifier.padding(innerPadding),
                        onClick = {
                            BatteryOptimizationUtil.requestIgnoreBatteryOptimization(applicationContext)
                        }
                    ) {
                        Text(text = "点击开启忽略电池优化")
                    }
                }
            }
        }

        KeepAliveManager(this).start()
    }
}

