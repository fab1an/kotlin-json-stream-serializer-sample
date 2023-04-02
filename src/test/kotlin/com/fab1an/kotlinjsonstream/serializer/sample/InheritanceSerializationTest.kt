package com.fab1an.kotlinjsonstream.serializer.sample

import com.fab1an.kotlinjsonstream.JsonReader
import com.fab1an.kotlinjsonstream.JsonWriter
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.MyInterfaceHolder
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.MyInterfaceImplA
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.MyInterfaceImplB
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.StandaloneImplPackage1
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.StandaloneInterface
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.nextMyInterfaceHolder
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.nextStandaloneInterface
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.valueMyInterfaceHolder
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.valueStandaloneInterface
import com.fab1an.kotlinjsonstream.serializer.sample.data.package2.StandaloneImplAPackage2
import okio.Buffer
import org.junit.jupiter.api.Test

class InheritanceSerializationTest {

    @Test
    fun abstractClasses() {
        val buffer = Buffer()

        val obj = MyInterfaceHolder(MyInterfaceImplA("aa"), MyInterfaceImplB(5))

        val jsonWriter = JsonWriter(buffer)
        jsonWriter.valueMyInterfaceHolder(obj)

        val deser = JsonReader(buffer.readUtf8()).nextMyInterfaceHolder()
        deser shouldEqual obj

        obj.inter1.call() shouldEqual "aaA"
        obj.inter2.call() shouldEqual "5"
    }

    @Test
    fun interfaceWithImplementationInDifferentPackage() {
        val buffer = Buffer()

        val objA: StandaloneInterface = StandaloneImplPackage1(data = "test")
        val objB: StandaloneInterface = StandaloneImplAPackage2(data = 1)

        var jsonWriter = JsonWriter(buffer)
        jsonWriter.valueStandaloneInterface(objA)

        var deser = JsonReader(buffer.readUtf8()).nextStandaloneInterface()
        deser.shouldBe<StandaloneImplPackage1> {
            this shouldEqual objA
            data shouldEqual "test"
        }

        jsonWriter = JsonWriter(buffer)
        jsonWriter.valueStandaloneInterface(objB)

        deser = JsonReader(buffer.readUtf8()).nextStandaloneInterface()
        deser.shouldBe<StandaloneImplAPackage2> {
            this shouldEqual objB
            data shouldEqual 1
        }
    }
}
