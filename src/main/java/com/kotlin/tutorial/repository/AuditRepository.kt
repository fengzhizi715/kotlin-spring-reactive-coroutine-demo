package com.kotlin.tutorial.repository

import com.kotlin.tutorial.model.Audit
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * Created by tony on 2018/11/22.
 */
@Repository
interface AuditRepository: CrudRepository<Audit, String> {

    fun findByName(name: String): Audit
}