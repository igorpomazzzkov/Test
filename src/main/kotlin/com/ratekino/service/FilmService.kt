package com.ratekino.service

import com.ratekino.entity.Film
import com.ratekino.repository.FilmRepository
import javassist.NotFoundException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.Query

@Service
class FilmService(
    private var filmRepository: FilmRepository,
    private var emf: EntityManagerFactory) {

    fun showFilmByPage(page: Int, size: Int): MutableList<Film>? {
        val em: EntityManager = emf.createEntityManager();
        val query: Query =
            em.createQuery("SELECT f FROM Film f INNER JOIN f.reviews r GROUP BY f ORDER BY AVG(r.rate) DESC")
        query.firstResult = ((page - 1) * size)
        query.maxResults = size
        return query.resultList as MutableList<Film>?
    }

    fun findFilmById(id: Long): Film {
        try {
            return this.filmRepository.findAllById(id)
        } catch (ex: EmptyResultDataAccessException) {
            throw NotFoundException("Фильм с таким id не найден");
        }
    }

    fun findFilmByName(name: String): MutableList<Film> {
        try {
            return this.filmRepository.findAllByNameContaining(name)
        } catch (ex: EmptyResultDataAccessException) {
            throw NotFoundException("Фильмы не найден");
        }
    }

    fun addFilm(film: Film): Film {
        return this.filmRepository.save(film)
    }

    fun editFilm(film: Film): Film {
        try {
            val filmFromDB = this.filmRepository.findAllById(film.id)
            val newFilm = Film(filmFromDB.id, film.name, film.reviews)
            return this.filmRepository.save(newFilm)
        } catch (ex: EmptyResultDataAccessException) {
            throw NotFoundException("Фильм с таким id не найден")
        }
    }

    fun deleteFilm(film: Film) {
        return this.filmRepository.delete(film)
    }

    fun deleteFilmById(id: Long){
        try {
            val filmFromDB = this.filmRepository.findAllById(id)
            this.filmRepository.deleteById(filmFromDB.id)
        } catch (ex: EmptyResultDataAccessException) {
            throw NotFoundException("Фильм с таким id не найден");
        }
    }
}