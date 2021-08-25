package com.flannery.design_pattern.decorator.use

import com.flannery.design_pattern.decorator.CompressionDecorator
import com.flannery.design_pattern.decorator.DataSources
import com.flannery.design_pattern.decorator.EncryptionDecorator
import com.flannery.design_pattern.decorator.FileDataSource

class Application {
}

fun main() {
    var source: DataSources = FileDataSource("file://smoefile.txt") // 读取源头

    // 已将明码数据写入目标文件
    source = CompressionDecorator(source)
    source.writeData("**明码数据**")
    // 已将压缩数据写入目标文件
    source = EncryptionDecorator(source)
    source.writeData("**压缩数据**")

}