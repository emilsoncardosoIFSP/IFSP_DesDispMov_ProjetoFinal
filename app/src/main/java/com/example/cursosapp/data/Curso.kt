package com.example.cursosapp.data

import kotlinx.serialization.Serializable

@Serializable
data class Curso(
    val id: Long,
    val nome: String,
    val categoria: String,
    val nivel: String,
    val descricao: String,
    val duracao: String
)