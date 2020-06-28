package com.xently.persona.data.source.remote.services

import com.xently.persona.data.model.Person
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface PersonService {
    @POST("people/")
    suspend fun addPerson(@Body person: Person): Response<Person>

    @Multipart
    @POST("people/")
    suspend fun addPerson(
        @Part photo: MultipartBody.Part,
        @Part person: MultipartBody.Part
    ): Response<Person>

    @GET("people/")
    suspend fun getPeople(): Response<List<Person>>
}