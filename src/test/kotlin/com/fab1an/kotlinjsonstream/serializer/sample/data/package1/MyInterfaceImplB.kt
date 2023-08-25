package com.fab1an.kotlinjsonstream.serializer.sample.data.package1

import com.fab1an.kotlinjsonstream.serializer.annotations.Ser

@Ser
data class MyInterfaceImplB(val somethingElse: Int) : MyInterface {
    override fun call(): String {
        return somethingElse.toString()
    }
}
