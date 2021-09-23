package cn.kuwo.networker

interface ApiTest{
    fun test()
    fun test2()
}

interface Api22{
    fun funk()
}

fun test() {
    RetrofitClient.api(ApiTest::class.java).test()
    RetrofitClient.api(Api22::class.java).funk()
}