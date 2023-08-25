package com.fab1an.kotlinjsonstream.serializer.sample.data.package1

import com.fab1an.kotlinjsonstream.serializer.annotations.ParentRef
import com.fab1an.kotlinjsonstream.serializer.annotations.Ser

@Ser
data class MySetLeaf(@ParentRef val parent: MyRootWithSet, val childField: String)

