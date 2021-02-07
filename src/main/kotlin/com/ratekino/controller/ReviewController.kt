package com.ratekino.controller

import com.ratekino.entity.Review
import com.ratekino.service.ReviewService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("review")
class ReviewController(private val reviewService: ReviewService) {

    @GetMapping("allReviews")
    fun getAllReviews(pageable: Pageable): MutableList<Review> {
        return this.reviewService.getAllReview(pageable)
    }

    @DeleteMapping("deleteReview")
    fun deleteReview(@RequestParam(value = "id") id: Long){
        return this.reviewService.deleteReview(id)
    }

    @GetMapping("addNewReview")
    fun addNewReview(
        @RequestParam(value = "user_id") user_id: Long,
        @RequestParam(value = "film_id") film_id: Long,
        @RequestParam(value = "rate") rate: Int,
    ): Review{
        return this.reviewService.addRateToFilm(user_id, film_id, rate)
    }

}