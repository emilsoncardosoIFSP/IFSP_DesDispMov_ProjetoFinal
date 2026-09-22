package com.example.cursosapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cursosapp.data.Curso
import com.example.cursosapp.data.CursoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CursoViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        CursoRepository(application.applicationContext)

    val cursos: StateFlow<List<Curso>> =
        repository.cursos.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun adicionarCurso(
        nome: String,
        categoria: String,
        nivel: String,
        descricao: String,
        duracao: String
    ) {
        val novoCurso = Curso(
            id = System.currentTimeMillis(),
            nome = nome.trim(),
            categoria = categoria,
            nivel = nivel,
            descricao = descricao.trim(),
            duracao = duracao.trim()
        )

        viewModelScope.launch {
            repository.adicionarCurso(novoCurso)
        }
    }

    fun atualizarCurso(curso: Curso) {
        viewModelScope.launch {
            repository.atualizarCurso(curso)
        }
    }

    fun excluirCurso(id: Long) {
        viewModelScope.launch {
            repository.excluirCurso(id)
        }
    }

    fun buscarCurso(id: Long): Curso? {
        return cursos.value.firstOrNull {
            it.id == id
        }
    }
}