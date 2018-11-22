package com.kotlin.tutorial.model

import org.springframework.data.annotation.Id
import java.time.LocalDateTime

/**
 * Created by tony on 2018/11/22.
 */
data class Audit(@Id val id: String? = null, val name: String, val eventDate: LocalDateTime) {

    constructor() : this(null, "",LocalDateTime.now())

    constructor(name: String, eventDate: LocalDateTime) : this(null, name, eventDate)
}