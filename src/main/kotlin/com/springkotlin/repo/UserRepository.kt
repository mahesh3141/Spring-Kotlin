package com.springkotlin.repo

import com.springkotlin.entities.UsersTable
import org.springframework.data.jpa.repository.JpaRepository


interface UserRepository : JpaRepository<UsersTable, Long>{
    fun findByUserNameAndPassword(username:String,password:String):UsersTable
}