package com.kotlin.tutorial.service

import com.kotlin.tutorial.repository.AuditRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by tony on 2018/11/22.
 */
@Component
class AuditService {

    @Autowired
    lateinit var auditRepository: AuditRepository

    fun findByName(name: String) = auditRepository.findFirstByNameOrderByEventDateDesc(name)
}