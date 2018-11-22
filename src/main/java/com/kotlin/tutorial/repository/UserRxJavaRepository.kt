package com.kotlin.tutorial.repository

import com.kotlin.tutorial.model.User
import io.reactivex.Flowable
import org.springframework.data.repository.reactive.RxJava2CrudRepository
import org.springframework.stereotype.Repository

/**
 * Created by tony on 2018/11/22.
 */
@Repository
interface UserRxJavaRepository : RxJava2CrudRepository<User, String> {

    fun findUserByName(name: String): Flowable<User>

    fun findUserByAge(age: Int): Flowable<User>

    fun findUserByAddressCity(city: String): Flowable<User>

    fun findUserByAgeAndAddressCity(age: Int, city: String): Flowable<User>
}