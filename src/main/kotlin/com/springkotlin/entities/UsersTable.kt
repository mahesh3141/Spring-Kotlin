package com.springkotlin.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "UsersTable")
data class UsersTable(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id:Long,
        @Column(name = "firstName")
        val firstName:String,
        @Column(name = "lastName")
        val lastName:String,
        @Column(name = "emailId")
        val emailId:String,
        @Column(name = "userName")
        val userName:String,
        @Column(name = "password")
        val password:String

)
