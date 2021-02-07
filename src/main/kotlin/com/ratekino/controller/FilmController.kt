package com.ratekino.controller

import com.ratekino.entity.Film
import com.ratekino.service.FilmService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("film")
class FilmController(private var filmService: FilmService) {
    // Пример строки http://localhost:8080/film/films?page=1&size=5
    @GetMapping("films")
    fun showAllFilms(
        @RequestParam(value = "page", defaultValue = "1") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int): MutableList<Film>? {
        return this.filmService.showFilmByPage(page, size)
    }

    @GetMapping("getFilmById")
    fun getFilmById(@RequestParam(value = "id", defaultValue = "1") id: Long): Film {
        return this.filmService.findFilmById(id);
    }

    @GetMapping("getFilmsByName")
    fun getFilmsBuNames(@RequestParam(value = "name") name: String): MutableList<Film> {
        return this.filmService.findFilmByName(name);
    }

    @PostMapping("newFilm")
    fun addNewFilm(@RequestBody film: Film): Film {
        return this.filmService.addFilm(film)
    }

    @PutMapping("editFilm")
    fun editFilm(@RequestBody film: Film): Film {
        return this.filmService.editFilm(film)
    }

    @DeleteMapping("deleteFilmById")
    fun deleteFilmById(@RequestParam(value = "id") id: Long) {
        this.filmService.deleteFilmById(id)
    }

    @DeleteMapping("deleteFilm")
    fun deleteFilm(@RequestBody film: Film) {
        this.filmService.deleteFilm(film)
    }
}