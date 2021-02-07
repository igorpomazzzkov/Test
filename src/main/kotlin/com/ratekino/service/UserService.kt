package com.ratekino.service

import com.ratekino.controller.TypeRate
import com.ratekino.entity.Usr
import com.ratekino.repository.UserRepository
import javassist.NotFoundException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors
import java.util.stream.IntStream
import java.util.stream.Stream
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.Query
import javax.persistence.TemporalType

@Service
class UserService(
    private val userRepository: UserRepository,
    private val entityManagerFactory: EntityManagerFactory
) {
    fun addUser(usr: Usr): Usr {
        return this.userRepository.save(usr)
    }

    fun editUser(usr: Usr): Usr {
        try {
            val userFromDB = this.userRepository.findUsrById(usr.id!!)
            val newUser = Usr(userFromDB.id!!, usr.name, usr.reviews)
            return this.userRepository.save(newUser)
        } catch (ex: NullPointerException) {
            throw NotFoundException("Пользователь с таким id не найден")
        }
    }

    fun deleteUserById(id: Long) {
        try {
            val userFromDB = userRepository.findUsrById(id)
            this.userRepository.deleteById(userFromDB.id!!)
        } catch (ex: EmptyResultDataAccessException) {
            throw NotFoundException("Пользователь с таким id не найден");
        }
    }

    fun deleteUser(usr: Usr) {
        return this.userRepository.delete(usr)
    }

    fun findUserById(id: Long): Usr {
        try {
            return userRepository.findUsrById(id);
        } catch (ex: EmptyResultDataAccessException) {
            throw NotFoundException("Пользователь с таким id не найден");
        }
    }

    fun findUserByName(name: String): Usr {
        try {
            return userRepository.findUsrByName(name);
        } catch (ex: EmptyResultDataAccessException) {
            throw NotFoundException("Пользователь с таким именем не найден");
        }
    }

    fun showAllUsers(): MutableList<Usr> {
        return this.userRepository.findAll();
    }

    fun showPersonalRate(id: Long, type: TypeRate, from: Date, to: Date): MutableList<Any?>? {
        var usrFromDB: Usr = this.findUserById(id)
        val em: EntityManager = this.entityManagerFactory.createEntityManager();
        val query: Query = em.createQuery(
            "SELECT r.rate FROM Review r WHERE r.usr.id = :id AND r.created_at >= :from AND r.created_at <= :to"
        )
        query.setParameter("id", id)
        query.setParameter("from", from, TemporalType.DATE)
        query.setParameter("to", to, TemporalType.DATE)
        return this.sortRateByType(query.resultList as MutableList<Int>, type)
    }

    private fun sortRateByType(list: MutableList<Int>, type: TypeRate): MutableList<Any?>? {
        val intStream = list.toTypedArray().toIntArray()
        return when (type) {
            TypeRate.max -> Stream.of(intStream.maxOrNull()).collect(Collectors.toList())
            TypeRate.min -> Stream.of(intStream.minOrNull()).collect(Collectors.toList())
            TypeRate.avg -> Stream.of(intStream.average()).collect(Collectors.toList())
            else -> Stream.of(list).collect(Collectors.toList())
        }
    }
}