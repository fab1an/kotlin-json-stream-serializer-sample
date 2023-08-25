package com.fab1an.kotlinjsonstream.serializer.sample.data.package2

import com.fab1an.kotlinjsonstream.serializer.annotations.Ser
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.StandaloneInterface

@Ser
data class StandaloneImplBPackage2(val data: Int) : StandaloneInterface
