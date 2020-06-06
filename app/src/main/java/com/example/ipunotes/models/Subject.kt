package com.example.ipunotes.models

data class Subject(
    var id: String,
    var name: String
): Comparable<Subject>{
    override fun compareTo(other: Subject): Int {
        return this.name.compareTo(other.name)
    }

    override fun equals(other: Any?): Boolean {
        return if(other is Subject){
            this.id == other.id
        }else{
            super.equals(other)
        }
    }
}