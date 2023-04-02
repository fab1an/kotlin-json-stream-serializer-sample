package com.fab1an.kotlinjsonstream.serializer.sample

import com.fab1an.kotlinjsonstream.JsonReader
import com.fab1an.kotlinjsonstream.JsonWriter
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.MyClass
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.MyClassWithNull
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.MyEnum
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.MyEnumHolder
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.MyListHolder
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.MySetHolder
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.nextMyClass
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.nextMyClassWithNull
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.nextMyEnumHolder
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.nextMyListHolder
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.nextMySetHolder
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.valueMyClass
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.valueMyClassWithNull
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.valueMyEnumHolder
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.valueMyListHolder
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.valueMySetHolder
import com.fab1an.kotlinjsonstream.serializer.sample.data.package2.SubObj
import okio.Buffer
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class BasicSerializationTest {

    @Test
    fun standardObjects() {
        val buffer = Buffer()

        val obj = MyClass(false, "test", 5, SubObj(""))
        val jsonWriter = JsonWriter(buffer)
        jsonWriter.valueMyClass(obj)

        val jsonOutput = buffer.readUtf8()
        jsonOutput shouldEqual """
            {"bool":false,"str":"test","int":5,"subObj":{"data":""}}
        """.trimIndent()

        JsonReader(jsonOutput).nextMyClass() shouldEqual obj
    }

    @Test
    fun deserializeNull() {
        val buffer = Buffer()

        var obj = MyClassWithNull("test")
        var jsonWriter = JsonWriter(buffer)
        jsonWriter.valueMyClassWithNull(obj)

        var jsonOutput = buffer.readUtf8()
        jsonOutput shouldEqual """
            {"nullableStr":"test"}
        """.trimIndent()
        JsonReader(jsonOutput).nextMyClassWithNull() shouldEqual obj

        obj = MyClassWithNull(null)
        jsonWriter = JsonWriter(buffer)
        jsonWriter.valueMyClassWithNull(obj)

        jsonOutput = buffer.readUtf8()
        jsonOutput shouldEqual """
            {"nullableStr":null}
        """.trimIndent()
        JsonReader(jsonOutput).nextMyClassWithNull() shouldEqual obj
    }

    @Test
    fun lists() {
        val buffer = Buffer()

        val obj = MyListHolder(listOf(MyClass(true, "", 1, SubObj("")), MyClass(false, "", 2, SubObj(""))))

        val jsonWriter = JsonWriter(buffer)
        jsonWriter.valueMyListHolder(obj)
        JsonReader(buffer.readUtf8()).nextMyListHolder() shouldEqual obj
    }

    @Test
    fun sets() {
        val buffer = Buffer()

        val obj = MySetHolder(setOf(1, 2, 3))

        val jsonWriter = JsonWriter(buffer)
        jsonWriter.valueMySetHolder(obj)
        JsonReader(buffer.readUtf8()).nextMySetHolder() shouldEqual obj
    }

    @Test
    fun enum() {
        val buffer = Buffer()

        val obj = MyEnumHolder(MyEnum.BB)

        val jsonWriter = JsonWriter(buffer)
        jsonWriter.valueMyEnumHolder(obj)
        JsonReader(buffer.readUtf8()).nextMyEnumHolder() shouldEqual obj
    }

    @Test
    fun readFieldCaseInsensitive() {
        val obj = JsonReader("""{"bool": true, "sTr": "test", "INT": 5,"sUbObJ":{"data":""}}""").nextMyClass()
        obj.bool shouldEqual true
        obj.str shouldEqual "test"
        obj.int shouldEqual 5
    }

    @Test
    fun readEnumCaseInsensitive() {
        val obj = JsonReader("""{"myEnum": "bb"}""").nextMyEnumHolder()

        obj.myEnum shouldEqual MyEnum.BB
    }

    @Test
    fun provideEnumErrorMessage() {
        val exception = assertFailsWith<IllegalStateException> {
            JsonReader("""{"myEnum": "xxx"}""").nextMyEnumHolder()
        }

        exception.message shouldEqual "enumValue 'xxx' not found"
    }

    @Test
    fun provideErrorMessageIfFieldNotFound() {
        val exception = assertFailsWith<IllegalStateException> {
            JsonReader("""{"bool": true, "INT": 5}""").nextMyClass()
        }
        exception.message shouldEqual "field 'str' not found"
    }
}
