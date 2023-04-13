package com.example.hwlesson5

class Note ( var image: String, var title: String, var desc: String, var date: String):java.io.Serializable{

    fun getImagee(): String {
        return image
    }

    fun getTitlee(): String {
        return title
    }

    fun getDescc(): String {
        return desc
    }

    fun getDatee(): String {
        return date
    }
}