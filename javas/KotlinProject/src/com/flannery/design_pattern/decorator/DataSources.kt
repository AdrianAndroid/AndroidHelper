package com.flannery.design_pattern.decorator

interface DataSources {
    fun writeData(data:String)
    fun readData()
}