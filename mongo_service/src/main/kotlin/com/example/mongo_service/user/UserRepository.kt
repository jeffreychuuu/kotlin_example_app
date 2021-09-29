package com.example.mongo_service.documents

import com.example.mongo_service.user.documents.UserDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<UserDocument, String> {}