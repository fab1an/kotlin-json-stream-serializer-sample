package com.fab1an.kotlinjsonstream.serializer.sample.data.package1

import com.fab1an.kotlinserializer.ParentRef
import com.fab1an.kotlinserializer.Ser

@Ser
data class MyListLeaf(@ParentRef val parent: MyRootWithList, val childField: String)
