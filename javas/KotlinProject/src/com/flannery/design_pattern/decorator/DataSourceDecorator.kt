package com.flannery.design_pattern.decorator

import com.flannery.design_pattern.Utils

open class DataSourceDecorator(val wrappee: DataSources) : DataSources {

    override fun writeData(data: String) {
        wrappee.writeData(data)
        Utils.log(this, "[writeData] 封装 ---->${wrappee.javaClass.simpleName}<----")
    }

    override fun readData() {
        wrappee.readData()
        Utils.log(this, "[readData] 封装 ---->$wrappee.javaClass.simpleName<----")
    }
}