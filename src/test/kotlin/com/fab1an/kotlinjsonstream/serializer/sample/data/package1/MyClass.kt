package com.fab1an.kotlinjsonstream.serializer.sample.data.package1

import com.fab1an.kotlinjsonstream.serializer.annotations.Ser
import com.fab1an.kotlinjsonstream.serializer.sample.data.package2.SubObj

@Ser
data class MyClass(val bool: Boolean, val str: String, val int: Int, val subObj: SubObj)
