package com.example.cursosapp.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val Context.cursosDataStore by preferencesDataStore(
    name = "cursos_app"
)

class CursoRepository(
    private val context: Context
) {

    private val chaveCursos = stringPreferencesKey("lista_de_cursos")

    private val json = Json {
        ignoreUnknownKeys = true
    }

    /**
     * Lista de cursos armazenada no DataStore.
     */
    val cursos: Flow<List<Curso>> =
        context.cursosDataStore.data.map { preferencias ->
            val conteudo: String? = preferencias[chaveCursos]

            if (conteudo.isNullOrBlank()) {
                emptyList<Curso>()
            } else {
                try {
                    json.decodeFromString<List<Curso>>(conteudo)
                } catch (e: Exception) {
                    emptyList<Curso>()
                }
            }
        }

    /**
     * Salva a lista completa de cursos.
     */
    private suspend fun salvarLista(lista: List<Curso>) {

        context.cursosDataStore.edit { preferencias ->
            preferencias[chaveCursos] = json.encodeToString(lista)
        }
    }

    /**
     * Adiciona um novo curso.
     */
    suspend fun adicionarCurso(curso: Curso) {

        val listaAtual: List<Curso> = cursos.first()

        salvarLista(
            listaAtual + curso
        )
    }

    /**
     * Atualiza um curso existente.
     */
    suspend fun atualizarCurso(curso: Curso) {

        val listaAtual: List<Curso> = cursos.first()

        val novaLista: List<Curso> =
            listaAtual.map { item: Curso ->
                if (item.id == curso.id) {
                    curso
                } else {
                    item
                }
            }

        salvarLista(novaLista)
    }

    /**
     * Exclui um curso pelo ID.
     */
    suspend fun excluirCurso(id: Long) {

        val listaAtual: List<Curso> = cursos.first()

        val novaLista: List<Curso> =
            listaAtual.filter { item: Curso ->
                item.id != id
            }

        salvarLista(novaLista)
    }
}