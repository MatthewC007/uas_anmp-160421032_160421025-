package com.anmp.myapplication.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: User)

    @Query("SELECT * FROM users")
    fun selectAllUsers(): List<User>

    @Delete
    fun deleteUser(user: User)

    @Query("UPDATE users SET firstname = :firstName, lastname = :lastName WHERE id = :userId")
    fun updateUserProfile(firstName: String, lastName: String, userId: Int)

    @Query("UPDATE users SET password = :newPassword WHERE id = :userId")
    fun updateUserPassword(userId: Int, newPassword: String)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun registerUser(user: User): Long

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    fun loginUser(username: String, password: String): User?
}