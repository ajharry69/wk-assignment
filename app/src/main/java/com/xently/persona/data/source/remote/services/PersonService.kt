package com.xently.persona.data.source.remote.services

import com.xently.persona.data.model.Person
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PersonService {
    @POST("people/")
    suspend fun addPerson(@Body person: Person): Response<Person>

    @GET("people/")
    suspend fun getPeople(): Response<List<Person>>
}