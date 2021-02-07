package com.ratekino.repository

import com.ratekino.entity.Usr
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<Usr, Long>{
    fun findUsrById(id: Long): Usr;
    fun findUsrByName(name: String): Usr;
}