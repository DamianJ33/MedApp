package com.example.mediapp.Screen

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class UserDatabaseHelper {

    private val url = "jdbc:mysql://172.19.0.1:3306/clinic_db"// Use port 3307 if it's open
    private val username = "clinic_user"
    private val password = "clinic_password"

    fun connectToDatabase(): Connection? {
        var connection: Connection? = null
        try {
            // Connecting to the MySQL database
            connection = DriverManager.getConnection(url, username, password)
            println("Connection to MySQL database successful!")
        } catch (e: SQLException) {
            println("Error connecting to the database: ${e.message}")
        }
        return connection
    }



    // Add a new user to the database
    fun addUser(email: String, password: String): Boolean {
        val connection = connectToDatabase()
        if (connection != null) {
            val query = "INSERT INTO users (email, password) VALUES (?, ?)"
            val preparedStatement = connection.prepareStatement(query)
            preparedStatement.setString(1, email)
            preparedStatement.setString(2, password)
            preparedStatement.executeUpdate()
            println("User added successfully!")
            connection.close()
            return true
        }
        return false
    }

    // Create a table if it doesn't exist
    fun createTable() {
        val connection = connectToDatabase()
        if (connection != null) {
            val query = """
                CREATE TABLE IF NOT EXISTS users (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    email VARCHAR(255),
                    password VARCHAR(255)
                )
            """
            val statement = connection.createStatement()
            statement.executeUpdate(query)
            println("Table created successfully!")
            connection.close()
        }
    }


}
// User data class to store user information
data class User(val id: Int, val email: String, val password: String)
