package com.example.kotlin_example_app.documents

import com.example.kotlin_example_app.user.documents.UserDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<UserDocument, String> {}