package com.ratekino.controller

import com.ratekino.entity.Usr
import com.ratekino.service.UserService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("user")
class UserController(private val userService: UserService) {

    @GetMapping("/getUserById")
    fun getUserById(@RequestParam(value = "id", defaultValue = "1") id: Long): Usr {
        return userService.findUserById(id)
    }

    @GetMapping("/getUserByName")
    fun getUserById(@RequestParam(value = "name") name: String): Usr {
        return userService.findUserByName(name)
    }

    @PostMapping("newUser")
    fun newUser(@RequestBody usr: Usr): Usr {
        return userService.addUser(usr)
    }

    @DeleteMapping("deleteUserById")
    fun deleteUser(@RequestParam id: Long) {
        this.userService.deleteUserById(id)
    }

    @DeleteMapping("deleteUser")
    fun deleteUser(@RequestBody usr: Usr) {
        this.userService.deleteUser(usr)
    }

    @PutMapping("editUser")
    fun editUser(@RequestBody usr: Usr): Usr{
        return this.userService.editUser(usr)
    }

    @GetMapping("allUsers")
    fun showAllUsers(): MutableList<Usr> {
        return this.userService.showAllUsers();
    }

    // пример строки http://localhost:8080/user/getMaxRateByPeriod?user_id=1&from=04.02.2021&to=07.02.2021&rate=avg
    @GetMapping("getMaxRateByPeriod")
    fun getRateByPeriod(
        @RequestParam(value = "user_id") id: Long,
        @RequestParam(value = "from") @DateTimeFormat(pattern = "dd.MM.yyyy") from: Date,
        @RequestParam(value = "to") @DateTimeFormat(pattern = "dd.MM.yyyy") to: Date,
        @RequestParam(value = "rate", defaultValue = "none") rate: TypeRate
    ): MutableList<Any?>? {
        return this.userService.showPersonalRate(id, rate, from, to)
    }
}