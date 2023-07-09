package com.myfitness.ui.booking_list.model

data class User(
    val name: BookingListResponseModel.Result.Name,
    val email: String,
    val picture: BookingListResponseModel.Result.Picture,
    val dob: Dob,
    val location: Location,
    val imageUrl: String
)

data class Dob(
    val date: String,
    val age: Int
)

data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: String
)

data class Street(
    val number: Int,
    val name: String
)
