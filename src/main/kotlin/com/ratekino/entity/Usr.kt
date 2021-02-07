package com.ratekino.entity

import com.fasterxml.jackson.annotation.*
import javax.persistence.*
import kotlin.random.Random

@Table
@Entity
class Usr(var name: String) {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0

        @OneToMany(
                mappedBy = "usr",
                orphanRemoval = true,
                fetch = FetchType.LAZY,
                cascade = [CascadeType.ALL])
        @JsonBackReference
        var reviews: MutableList<Review> = mutableListOf()

        constructor(id: Long, name: String, reviews: MutableList<Review>) : this(name) {
                this.id = id
                this.reviews = reviews
        }

        override fun toString(): String {
                return "Usr(id=$id, name='$name')"
        }

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Usr

                if (id != other.id) return false
                if (name != other.name) return false

                return true
        }

        override fun hashCode(): Int {
                var result = id.hashCode()
                result = Random.nextInt(0, 100)  * result + name.hashCode()
                return result
        }
}