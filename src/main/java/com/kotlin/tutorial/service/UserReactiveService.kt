package com.kotlin.tutorial.service

import com.kotlin.tutorial.Utils.toLower
import com.kotlin.tutorial.model.Address
import com.kotlin.tutorial.model.Audit
import com.kotlin.tutorial.model.User
import com.kotlin.tutorial.repository.AuditRepository
import com.kotlin.tutorial.repository.UserReactiveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.time.LocalDateTime

/**
 * Created by tony on 2018/11/22.
 */
@Component
class UserReactiveService {

    @Autowired
    lateinit var userRepository: UserReactiveRepository

    @Autowired
    lateinit var auditRepository: AuditRepository

    companion object {

        val cities = listOf("Shanghai", "Suzhou", "Hangzhou").toLower()
        val streets = listOf("renming road", "zhongshan road").toLower()
    }

    fun find(age: Int?, rawCity: String?): Flux<User> {
        val city = rawCity?.toLowerCase()

        return when {

            age is Int && city is String -> userRepository.findUserByAgeAndAddressCity(age, city)

            city is String -> userRepository.findUserByAddressCity(city)

            age is Int -> userRepository.findUserByAge(age)

            else -> userRepository.findAll()
        }
    }

    fun generateData(): Flux<User> {

        val list = listOf(20, 25, 33, 28, 34).map {

            val u = generate(it)

            auditRepository.save(Audit(u.name, LocalDateTime.now()))

            u
        }

        return userRepository.deleteAll().thenMany(userRepository.saveAll(list))
    }

    private fun generate(age: Int): User {

        val address = Address(age, streets[age % streets.size], cities[age % cities.size])
        return User("Tony$age", age, address)
    }
}