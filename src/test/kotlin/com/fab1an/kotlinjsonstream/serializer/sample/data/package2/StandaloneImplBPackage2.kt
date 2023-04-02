package com.fab1an.kotlinjsonstream.serializer.sample.data.package2

import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.StandaloneInterface
import com.fab1an.kotlinserializer.Ser

@Ser
data class StandaloneImplBPackage2(val data: Int) : StandaloneInterface
