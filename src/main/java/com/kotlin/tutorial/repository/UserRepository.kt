package com.kotlin.tutorial.repository

import com.kotlin.tutorial.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * Created by tony on 2018/11/23.
 */
@Repository
interface UserRepository : CrudRepository<User, String> {

    fun findUserByName(name: String): User
}