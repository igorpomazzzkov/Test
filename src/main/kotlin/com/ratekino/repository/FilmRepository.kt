package com.ratekino.repository

import com.ratekino.entity.Film
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FilmRepository : JpaRepository<Film, Long> {
    fun findAllById(id: Long): Film
    fun findAllByNameContaining(name: String): MutableList<Film>
}