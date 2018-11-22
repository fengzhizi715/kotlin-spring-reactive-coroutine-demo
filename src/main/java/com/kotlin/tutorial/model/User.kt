package com.kotlin.tutorial.model

import org.springframework.data.annotation.Id

/**
 * Created by tony on 2018/11/22.
 */
data class User(@Id val id: String? = null, val name: String, val age: Int, val address: Address) {

    constructor() : this(null, "", 0, Address())
    constructor(name: String, age: Int, address: Address) : this(null, name = name, age = age, address = address)
}
