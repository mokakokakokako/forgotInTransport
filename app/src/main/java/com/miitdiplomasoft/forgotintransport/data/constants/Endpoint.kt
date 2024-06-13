package com.miitdiplomasoft.forgotintransport.data.constants

object Endpoint {


    val baseURL: String
        get() = "datebasefitdefaultrtdb.com/v1"
        //get() = "datebasefit-default-rtdb.firebaseio.com"

    val authorizationUser: String
        get() = "/users"

    val authorizationAdmin: String
        get() = "/admins"

    val registration: String
        get() = "/registration"

    val getAllItems: String
        get() = "/get-items-all"

    val getFavItems: String
        get() = "/get-items-fav"

    val getNamItems: String
        get() = "/get-items-name"

    val getItem: String
        get() = "/get-item"

    val addItem: String
        get() = "/add-item"

    val deleteItem: String
        get() = "/delete-item"

    val changeFavStatus: String
        get() = "/change-fav-status"

}