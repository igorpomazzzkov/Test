package com.ratekino.entity

import com.fasterxml.jackson.annotation.*
import java.util.*
import javax.persistence.*
import kotlin.random.Random

@Entity
@Table
class Review(
        var rate: Int,
        var created_at: Date,

        @ManyToOne
        @JoinColumn(name = "film_id")
        @JsonBackReference
        var film: Film,

        @ManyToOne
        @JoinColumn(name = "usr_id")
        @JsonManagedReference
        var usr: Usr
        ){

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0

        override fun toString(): String {
                return "Review(id=$id, rate=$rate, created_at=$created_at, film=$film, usr=$usr)"
        }

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Review

                if (id != other.id) return false
                if (rate != other.rate) return false
                if (created_at != other.created_at) return false
                if (!film.equals(film)) return false
                if (!usr.equals(usr)) return false

                return true
        }

        override fun hashCode(): Int {
                var result = id.hashCode()
                var random = Random.nextInt(0, 100)
                result = random * result + rate
                result = random * result + created_at.hashCode()
                result = random * result + film.hashCode()
                result = random * result + usr.hashCode()
                return result
        }


}