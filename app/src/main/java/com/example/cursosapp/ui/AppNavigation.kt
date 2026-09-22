package com.example.cursosapp.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cursosapp.viewmodel.CursoViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val viewModel: CursoViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(
                onVerCursos = {
                    navController.navigate("cursos")
                },
                onCadastrar = {
                    navController.navigate("cadastro")
                }
            )
        }

        composable("cursos") {
            CursosScreen(
                viewModel = viewModel,
                onVoltar = {
                    navController.popBackStack()
                },
                onCadastrar = {
                    navController.navigate("cadastro")
                },
                onEditar = { id ->
                    navController.navigate("editar/$id")
                }
            )
        }

        composable("cadastro") {
            CadastroCursoScreen(
                viewModel = viewModel,
                cursoId = null,
                onVoltar = {
                    navController.popBackStack()
                },
                onSalvo = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "editar/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                }
            )
        ) { entrada ->

            val id = entrada.arguments?.getLong("id")

            CadastroCursoScreen(
                viewModel = viewModel,
                cursoId = id,
                onVoltar = {
                    navController.popBackStack()
                },
                onSalvo = {
                    navController.popBackStack()
                }
            )
        }
    }
}