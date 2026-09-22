package com.example.cursosapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.cursosapp.ui.AppNavigation
import com.example.cursosapp.ui.theme.CursosAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            CursosAppTheme {
                AppNavigation()
            }
        }
    }
}