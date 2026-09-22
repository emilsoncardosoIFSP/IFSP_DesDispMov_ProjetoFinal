package com.example.cursosapp.ui

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onVerCursos: () -> Unit,
    onCadastrar: () -> Unit
) {

    val context = LocalContext.current

    var mostrarDialogoSair by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Cursos App",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Aprenda, organize e gerencie seus cursos.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Button(
            onClick = onVerCursos
        ) {
            Text("Ver cursos")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = onCadastrar
        ) {
            Text("Cadastrar curso")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = {
                mostrarDialogoSair = true
            }
        ) {
            Text("Sair")
        }
    }

    // Caixa de confirmação
    if (mostrarDialogoSair) {

        AlertDialog(
            onDismissRequest = {
                mostrarDialogoSair = false
            },

            title = {
                Text("Sair do Cursos App")
            },

            text = {
                Text("Deseja realmente sair do aplicativo?")
            },

            confirmButton = {

                TextButton(
                    onClick = {
                        mostrarDialogoSair = false

                        (context as? Activity)?.finish()
                    }
                ) {
                    Text("Sim")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        mostrarDialogoSair = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}