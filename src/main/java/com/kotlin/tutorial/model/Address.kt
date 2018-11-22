package com.kotlin.tutorial.model

import org.springframework.data.annotation.Id

/**
 * Created by tony on 2018/11/22.
 */
data class Address(@Id val id: String? = null, val number: Int, val street: String, val city: String) {

    constructor() : this(null, 0, "", "")
    constructor(number: Int, street: String, city: String) : this(null, number, street, city)
}