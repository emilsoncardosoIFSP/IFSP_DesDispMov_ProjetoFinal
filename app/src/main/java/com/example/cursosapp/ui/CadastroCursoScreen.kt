package com.example.cursosapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.ExposedDropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cursosapp.data.Curso
import com.example.cursosapp.viewmodel.CursoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroCursoScreen(
    viewModel: CursoViewModel,
    cursoId: Long?,
    onVoltar: () -> Unit,
    onSalvo: () -> Unit
) {

    val cursoExistente = cursoId?.let {
        viewModel.buscarCurso(it)
    }

    var nome by rememberSaveable {
        mutableStateOf("")
    }

    var categoria by rememberSaveable {
        mutableStateOf("")
    }

    var nivel by rememberSaveable {
        mutableStateOf("")
    }

    var descricao by rememberSaveable {
        mutableStateOf("")
    }

    var duracao by rememberSaveable {
        mutableStateOf("")
    }

    var erro by rememberSaveable {
        mutableStateOf("")
    }

    var categoriaAberta by rememberSaveable {
        mutableStateOf(false)
    }

    var nivelAberto by rememberSaveable {
        mutableStateOf(false)
    }

    val categorias = listOf(
        "Programação",
        "Design",
        "Idiomas",
        "Administração",
        "Marketing",
        "Tecnologia",
        "Outros"
    )

    val niveis = listOf(
        "Iniciante",
        "Intermediário",
        "Avançado"
    )

    LaunchedEffect(cursoExistente?.id) {

        cursoExistente?.let { curso ->

            nome = curso.nome
            categoria = curso.categoria
            nivel = curso.nivel
            descricao = curso.descricao
            duracao = curso.duracao
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (cursoId == null) {
                            "Cadastrar curso"
                        } else {
                            "Editar curso"
                        }
                    )
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = nome,
                onValueChange = {
                    nome = it
                    erro = ""
                },
                label = {
                    Text("Nome do curso")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            ExposedDropdownMenuBox(
                expanded = categoriaAberta,
                onExpandedChange = {
                    categoriaAberta = !categoriaAberta
                }
            ) {

                OutlinedTextField(
                    value = categoria,
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text("Categoria")
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = categoriaAberta
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = categoriaAberta,
                    onDismissRequest = {
                        categoriaAberta = false
                    }
                ) {

                    categorias.forEach { item ->

                        DropdownMenuItem(
                            text = {
                                Text(item)
                            },
                            onClick = {
                                categoria = item
                                categoriaAberta = false
                                erro = ""
                            }
                        )
                    }
                }
            }

            ExposedDropdownMenuBox(
                expanded = nivelAberto,
                onExpandedChange = {
                    nivelAberto = !nivelAberto
                }
            ) {

                OutlinedTextField(
                    value = nivel,
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text("Nível")
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = nivelAberto
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = nivelAberto,
                    onDismissRequest = {
                        nivelAberto = false
                    }
                ) {

                    niveis.forEach { item ->

                        DropdownMenuItem(
                            text = {
                                Text(item)
                            },
                            onClick = {
                                nivel = item
                                nivelAberto = false
                                erro = ""
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = duracao,
                onValueChange = {
                    duracao = it
                    erro = ""
                },
                label = {
                    Text("Duração — exemplo: 20 horas")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = descricao,
                onValueChange = {
                    descricao = it
                    erro = ""
                },
                label = {
                    Text("Descrição do curso")
                },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4
            )

            if (erro.isNotBlank()) {
                Text(
                    text = erro
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {

                    if (
                        nome.isBlank() ||
                        categoria.isBlank() ||
                        nivel.isBlank() ||
                        duracao.isBlank() ||
                        descricao.isBlank()
                    ) {

                        erro = "Preencha todos os campos."

                    } else {

                        if (cursoExistente == null) {

                            viewModel.adicionarCurso(
                                nome = nome,
                                categoria = categoria,
                                nivel = nivel,
                                descricao = descricao,
                                duracao = duracao
                            )

                        } else {

                            viewModel.atualizarCurso(
                                Curso(
                                    id = cursoExistente.id,
                                    nome = nome.trim(),
                                    categoria = categoria,
                                    nivel = nivel,
                                    descricao = descricao.trim(),
                                    duracao = duracao.trim()
                                )
                            )
                        }

                        onSalvo()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    if (cursoId == null) {
                        "Salvar curso"
                    } else {
                        "Atualizar curso"
                    }
                )
            }
        }
    }
}