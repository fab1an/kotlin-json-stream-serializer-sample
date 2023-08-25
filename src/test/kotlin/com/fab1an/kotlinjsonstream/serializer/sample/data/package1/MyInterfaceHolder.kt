package com.fab1an.kotlinjsonstream.serializer.sample.data.package1

import com.fab1an.kotlinjsonstream.serializer.annotations.Ser

@Ser
data class MyInterfaceHolder(val inter1: MyInterface, val inter2: MyInterface)
