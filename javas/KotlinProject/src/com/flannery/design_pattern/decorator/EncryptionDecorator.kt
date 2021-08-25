package com.flannery.design_pattern.decorator

import com.flannery.design_pattern.Utils

class EncryptionDecorator(wrappee: DataSources) : DataSourceDecorator(wrappee) {

    override fun writeData(data: String) {
        super.writeData(data)
        Utils.log(this, "[writeData]")
    }

    override fun readData() {
        super.readData()
        Utils.log(this, "[readData]")
    }

}