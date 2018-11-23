package com.kotlin.tutorial.controller

import com.kotlin.tutorial.service.AuditService
import com.kotlin.tutorial.service.UserReactiveService
import com.kotlin.tutorial.service.UserRxJavaService
import com.kotlin.tutorial.service.UserService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * Created by tony on 2018/11/22.
 */
@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    lateinit var userReactiveService: UserReactiveService

    @Autowired
    lateinit var userRxJavaService: UserRxJavaService

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var auditService: AuditService

    @GetMapping("/reactive/find")
    fun findByReactive(@RequestParam age: Int?, @RequestParam city: String?) = userReactiveService.find(age, city)

    @GetMapping("/reactive/generate")
    fun genDataByReactive() = userReactiveService.generateData()

    @GetMapping("/rxjava/find")
    fun findByRx(@RequestParam age: Int?, @RequestParam city: String?) = userRxJavaService.find(age, city)

    @GetMapping("/rxjava/generate")
    fun genDateByRx() = userRxJavaService.generateData()

    @GetMapping("/rxjava/login")
    fun mockLogin(@RequestParam username: String) = userRxJavaService.login(username)

    @GetMapping("/blocking/{username}")
    fun getNormalLoginMessage(@PathVariable username: String):String {

        val user = userService.findByName(username)

        val lastLoginTime = auditService.findByName(user.name).eventDate

        return "Hi ${user.name}, you have logged in since $lastLoginTime"
    }

    @GetMapping("/rxjava/{username}")
    fun getRxLoginMessage(@PathVariable username: String)=
            userRxJavaService.findByName(username)
                    .map {

                        auditService.findByName(it.name).eventDate
                    }
                    .map {

                        "Hi ${username}, you have logged in since $it"
                    }

    @GetMapping("/coroutine/{username}")
    fun getLoginMessage(@PathVariable username: String) = runBlocking {

        val user = userRxJavaService.findByName(username).awaitSingle()

        val lastLoginTime = GlobalScope.async {

            auditService.findByName(user.name).eventDate

        }.await()

        "Hi ${user.name}, you have logged in since $lastLoginTime"
    }
}