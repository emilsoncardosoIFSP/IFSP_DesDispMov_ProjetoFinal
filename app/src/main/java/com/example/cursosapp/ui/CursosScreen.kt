package com.example.cursosapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cursosapp.data.Curso
import com.example.cursosapp.viewmodel.CursoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CursosScreen(
    viewModel: CursoViewModel,
    onVoltar: () -> Unit,
    onCadastrar: () -> Unit,
    onEditar: (Long) -> Unit
) {

    val cursos by viewModel.cursos.collectAsState()

    var cursoParaExcluir by remember {
        mutableStateOf<Curso?>(null)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Meus cursos")
                },
                navigationIcon = {
                    TextButton(onClick = onVoltar) {
                        Text("Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onCadastrar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Adicionar novo curso")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (cursos.isEmpty()) {

                Text(
                    text = "Nenhum curso cadastrado.",
                    style = MaterialTheme.typography.bodyLarge
                )

            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    items(
                        items = cursos,
                        key = { it.id }
                    ) { curso ->

                        CursoCard(
                            curso = curso,
                            onEditar = {
                                onEditar(curso.id)
                            },
                            onExcluir = {
                                cursoParaExcluir = curso
                            }
                        )
                    }
                }
            }
        }
    }

    cursoParaExcluir?.let { curso ->

        AlertDialog(
            onDismissRequest = {
                cursoParaExcluir = null
            },
            title = {
                Text("Excluir curso?")
            },
            text = {
                Text(
                    "Deseja realmente excluir o curso \"${curso.nome}\"?"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.excluirCurso(curso.id)
                        cursoParaExcluir = null
                    }
                ) {
                    Text("Excluir")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        cursoParaExcluir = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun CursoCard(
    curso: Curso,
    onEditar: () -> Unit,
    onExcluir: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = curso.nome,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Categoria: ${curso.categoria}")
            Text("Nível: ${curso.nivel}")
            Text("Duração: ${curso.duracao}")

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = curso.descricao,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                OutlinedButton(
                    onClick = onEditar
                ) {
                    Text("Editar")
                }

                Spacer(modifier = Modifier.padding(4.dp))

                Button(
                    onClick = onExcluir
                ) {
                    Text("Excluir")
                }
            }
        }
    }
}