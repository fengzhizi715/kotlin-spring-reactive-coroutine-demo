package com.kotlin.tutorial.service

import com.kotlin.tutorial.Utils.toLower
import com.kotlin.tutorial.model.Address
import com.kotlin.tutorial.model.Audit
import com.kotlin.tutorial.model.User
import com.kotlin.tutorial.repository.AuditRepository
import com.kotlin.tutorial.repository.UserRxJavaRepository
import io.reactivex.Flowable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * Created by tony on 2018/11/22.
 */
@Component
class UserRxJavaService {

    @Autowired
    lateinit var userRepository: UserRxJavaRepository

    @Autowired
    lateinit var auditRepository: AuditRepository

    companion object {

        val cities = listOf("Shanghai", "Suzhou", "Hangzhou").toLower()
        val streets = listOf("renming road", "zhongshan road").toLower()
    }

    fun findByName(name: String): Flowable<User> = userRepository.findUserByName(name)

    fun find(age: Int?, rawCity: String?): Flowable<User> {

        val city = rawCity?.toLowerCase()

        return when {

            age is Int && city is String -> userRepository.findUserByAgeAndAddressCity(age, city)

            city is String -> userRepository.findUserByAddressCity(city)

            age is Int -> userRepository.findUserByAge(age)

            else -> userRepository.findAll()
        }
    }

    fun generateData(): Flowable<User> {

        val list = listOf(20, 25, 33, 28, 34).map {

            val u = generate(it)

            auditRepository.save(Audit(u.name, LocalDateTime.now()))

            u
        }

        return userRepository.deleteAll().andThen(userRepository.saveAll(list))
    }

    private fun generate(age: Int): User {

        val address = Address(age, streets[age % streets.size], cities[age % cities.size])

        return User("Tony$age", age, address)
    }

    fun login(name: String) =
            userRepository.findUserByName(name)
            .map {
                auditRepository.save(Audit(it.name, LocalDateTime.now()))
            }
}