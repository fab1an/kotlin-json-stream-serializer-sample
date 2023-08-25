package com.fab1an.kotlinjsonstream.serializer.sample.data.package1

import com.fab1an.kotlinjsonstream.serializer.annotations.Ser

@Ser
data class MyRootWithList(var myLeafs: List<MyListLeaf>, val rootField: String)
