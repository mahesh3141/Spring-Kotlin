package com.springkotlin.controller

import com.springkotlin.entities.UsersTable
import com.springkotlin.repo.UserRepository
import org.apache.tomcat.util.net.openssl.ciphers.Encryption
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/")
class UsersController(@Autowired private val userRepository: UserRepository) {

//https://dev.to/francescoxx/kotlin-crud-rest-api-using-spring-boot-hibernate-postgres-docker-and-docker-compose-1cnl
    /** Below Method will give all user list **/
    @GetMapping("viewAll")
    fun getAllUser(): List<UsersTable> = userRepository.findAll().toList();

    /**Add User **/
    @PostMapping("addUser")
    fun createUser(@RequestBody usersTable: UsersTable): ResponseEntity<UsersTable> {
        var password = usersTable.password

        val createdUser = userRepository.save(usersTable);
        return ResponseEntity(createdUser, HttpStatus.CREATED);
    }

    /** Below method will give single user response as per user id **/
    @GetMapping("users/{id}")
    fun getUserById(@PathVariable("id") userId: Long): ResponseEntity<UsersTable> {
        val users = userRepository.findById(userId).orElse(null);
        return if (users != null) ResponseEntity(users, HttpStatus.OK)
        else ResponseEntity(HttpStatus.NOT_FOUND);
    }

    /** Below method will update the single user as per given user id **/
    @PutMapping("updateUser/{id}")
    fun updateUserById(@PathVariable("id") userId: Long, @RequestBody usersTable: UsersTable)
            : ResponseEntity<UsersTable> {
        val existingUser = userRepository.findById(userId).orElse(null) ?: return ResponseEntity(HttpStatus.NOT_FOUND);

        val updatedUser = existingUser.copy(id = userId, firstName = usersTable.firstName, lastName = usersTable.lastName,
                emailId = usersTable.emailId, userName = usersTable.userName, password = usersTable.password);
        userRepository.save(updatedUser)
        return ResponseEntity(updatedUser, HttpStatus.OK);
    }

    /**Below code is for delete the user vai user id**/
    @DeleteMapping("deleteUser/{id}")
    fun deleteUserById(@PathVariable("id") userId: Long): ResponseEntity<UsersTable> {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        userRepository.deleteById(userId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    /** Login Check the user **/
    @GetMapping("/login/{username}{password}")
    fun getLogin(@PathVariable("username") userName:String,@PathVariable("password") password:String):ResponseEntity<UsersTable>{
        val usersTable : UsersTable = userRepository.findByUserNameAndPassword(userName,password);
        return ResponseEntity(usersTable,HttpStatus.OK);
    }
}