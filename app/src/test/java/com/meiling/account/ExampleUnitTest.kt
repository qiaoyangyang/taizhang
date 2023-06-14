package com.meiling.account

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun test(){

        var encodeString=DesUtils.encode("Admin8888!!")
        println("encodeString=="+encodeString)
        var decodeString=DesUtils.decode("9588028820109132570743325311898426347857298773549468758875018579537757772163084478873699447306034466200616411960574122434059469100235892702736860872901247123456",encodeString)
        println("decodeString=="+decodeString)
    }

}