package com.ratekino.entity

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import javax.persistence.*
import kotlin.random.Random

@Entity
@Table
class Film(var name: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @OneToMany(
        mappedBy = "film",
        orphanRemoval = true,
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL]
    )
    @JsonManagedReference
    var reviews: MutableList<Review> = mutableListOf()

    constructor(id: Long, name: String, reviews: MutableList<Review>) : this(name) {
        this.id = id
        this.reviews = reviews
    }

    override fun toString(): String {
        return "Film(id=$id, name='$name')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Film

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = Random.nextInt(0, 100) * result + name.hashCode()
        return result;
    }
}