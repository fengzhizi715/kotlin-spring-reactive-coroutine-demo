package com.kotlin.tutorial.repository

import com.kotlin.tutorial.model.User
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

/**
 * Created by tony on 2018/11/22.
 */
interface UserReactiveRepository : ReactiveMongoRepository<User, String> {

    fun findUserByAge(age: Int): Flux<User>

    fun findUserByAddressCity(city: String): Flux<User>

    fun findUserByAgeAndAddressCity(age: Int, city: String): Flux<User>
}