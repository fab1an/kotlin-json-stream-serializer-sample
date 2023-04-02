package com.fab1an.kotlinjsonstream.serializer.sample.data.package1

import com.fab1an.kotlinserializer.Ser

@Ser
data class MyClassWithNull(val nullableStr: String?)
