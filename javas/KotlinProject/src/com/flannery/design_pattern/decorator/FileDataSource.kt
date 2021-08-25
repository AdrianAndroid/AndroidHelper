package com.flannery.design_pattern.decorator

import com.flannery.design_pattern.Utils

class FileDataSource(val file: String) : DataSources {
    override fun writeData(data: String) {
        Utils.log(this, "[writeData] file = $file , data = $data")
    }

    override fun readData() {
        Utils.log(this, "[readData] file = $file")
    }
}