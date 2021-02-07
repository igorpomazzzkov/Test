package com.ratekino.service

import com.ratekino.entity.Review
import com.ratekino.repository.FilmRepository
import com.ratekino.repository.ReviewRepository
import com.ratekino.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.data.domain.Pageable
import java.util.*

@Service
class ReviewService(
    private val userRepository: UserRepository,
    private val filmRepository: FilmRepository,
    private val reviewRepository: ReviewRepository) {
    fun getAllReview(pageable: Pageable): MutableList<Review> {
        return this.reviewRepository.findAll(pageable).content
    }

    fun deleteReview(id: Long){
        return this.reviewRepository.deleteById(id)
    }

    fun addRateToFilm(user_id: Long, film_id: Long, rate: Int): Review {
        if (rate !in 1..5){
            throw IllegalArgumentException("Рейтинг не может быть меньше 1 и больше 5")
        }
        val userFromDB = this.userRepository.findUsrById(user_id)
        val filmFromDB = this.filmRepository.findAllById(film_id)
        val review = Review(rate, Date(), filmFromDB, userFromDB)
        return this.reviewRepository.save(review)
    }
}