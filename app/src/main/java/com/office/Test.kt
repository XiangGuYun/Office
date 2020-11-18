package com.office

import com.google.gson.Gson

class Test {

    data class Person(val name: String? =null)

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val p1 = Person()
            val text = p1.name?:""
            val jsonString = "{'name':null}"
            val person = Gson().fromJson<Person>(jsonString, Person::class.java)
            println("此人的姓名是：" + person.name)
        }
    }

}