package com.kotlin.tutorial.service

import com.kotlin.tutorial.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by tony on 2018/11/23.
 */
@Component
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository


    fun findByName(name: String) = userRepository.findUserByName(name)
}