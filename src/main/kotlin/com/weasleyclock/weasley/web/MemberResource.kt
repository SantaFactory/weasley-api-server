package com.weasleyclock.weasley.web

import com.weasleyclock.weasley.domain.Member
import com.weasleyclock.weasley.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/member")
class MemberResource(private val service: MemberService) {

    @GetMapping("")
    fun showByMembers() : ResponseEntity<List<Member>>{
        val body = service.getAllByMembers()
        return ResponseEntity.ok(body)
    }

}
