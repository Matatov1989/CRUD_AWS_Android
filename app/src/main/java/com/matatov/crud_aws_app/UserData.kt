package com.matatov.crud_aws_app

data class UserData(val uId: String, val name: String, var age: Int, var country: String){
    constructor(name: String, age: Int, country: String) : this("", name, age, country)
}
