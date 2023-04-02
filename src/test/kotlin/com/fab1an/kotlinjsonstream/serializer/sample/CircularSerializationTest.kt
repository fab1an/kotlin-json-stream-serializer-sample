package com.fab1an.kotlinjsonstream.serializer.sample

import com.fab1an.kotlinjsonstream.JsonReader
import com.fab1an.kotlinjsonstream.JsonWriter
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.MyLeaf
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.MyListLeaf
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.MyRoot
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.MyRootWithList
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.MyRootWithSet
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.MySetLeaf
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.nextMyRoot
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.nextMyRootWithList
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.nextMyRootWithSet
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.valueMyRoot
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.valueMyRootWithList
import com.fab1an.kotlinjsonstream.serializer.sample.data.package1.valueMyRootWithSet
import okio.Buffer
import org.junit.jupiter.api.Test

class CircularSerializationTest {

    @Test
    fun circularSerialisationWithNullableField() {
        val buffer = Buffer()

        val root = MyRoot(rootField = "parent", myLeaf = null)
        val leaf = MyLeaf(childField = "child", parent = root)
        root.myLeaf = leaf

        (leaf.parent === root) shouldEqual true

        val jsonWriter = JsonWriter(buffer)
        jsonWriter.valueMyRoot(root)

        val jsonOutput = buffer.readUtf8()
        jsonOutput shouldEqual """
            {"myLeaf":{"childField":"child"},"rootField":"parent"}
        """.trimIndent()

        val reconstructed = JsonReader(jsonOutput).nextMyRoot()
        reconstructed.rootField shouldEqual "parent"
        reconstructed.myLeaf!!.childField shouldEqual "child"
        (reconstructed.myLeaf!!.parent === reconstructed) shouldEqual true
    }

    @org.junit.jupiter.api.Test
    fun circularSerialisationWithList() {
        val buffer = Buffer()

        val root = MyRootWithList(rootField = "parent", myLeafs = emptyList())
        val leaf1 = MyListLeaf(childField = "child1", parent = root)
        val leaf2 = MyListLeaf(childField = "child2", parent = root)
        root.myLeafs = listOf(leaf1, leaf2)

        (leaf1.parent === root) shouldEqual true
        (leaf2.parent === root) shouldEqual true

        val jsonWriter = JsonWriter(buffer)
        jsonWriter.valueMyRootWithList(root)

        val jsonOutput = buffer.readUtf8()
        jsonOutput shouldEqual """
            {"myLeafs":[{"childField":"child1"},{"childField":"child2"}],"rootField":"parent"}
        """.trimIndent()

        val reconstructed = JsonReader(jsonOutput).nextMyRootWithList()
        reconstructed.rootField shouldEqual "parent"
        reconstructed.myLeafs.size shouldEqual 2
        reconstructed.myLeafs[0].childField shouldEqual "child1"
        (reconstructed.myLeafs[0].parent === reconstructed) shouldEqual true
        reconstructed.myLeafs[1].childField shouldEqual "child2"
        (reconstructed.myLeafs[1].parent === reconstructed) shouldEqual true
    }

    @org.junit.jupiter.api.Test
    fun circularSerialisationWithSet() {
        val buffer = Buffer()

        val root = MyRootWithSet(leafSet = emptySet())
        val leaf1 = MySetLeaf(childField = "child1", parent = root)
        val leaf2 = MySetLeaf(childField = "child2", parent = root)
        root.leafSet = setOf(leaf1, leaf2)
        root.leafSet.size shouldEqual 2

        (leaf1.parent === root) shouldEqual true
        (leaf2.parent === root) shouldEqual true

        val jsonWriter = JsonWriter(buffer)
        jsonWriter.valueMyRootWithSet(root)

        val jsonOutput = buffer.readUtf8()
        jsonOutput shouldEqual """
            {"leafSet":[{"childField":"child1"},{"childField":"child2"}]}
        """.trimIndent()

        val reconstructed = JsonReader(jsonOutput).nextMyRootWithSet()
        reconstructed.leafSet.size shouldEqual 2
        reconstructed.leafSet.toList()[0].childField shouldEqual "child1"
        (reconstructed.leafSet.toList()[0].parent === reconstructed) shouldEqual true
        reconstructed.leafSet.toList()[1].childField shouldEqual "child2"
        (reconstructed.leafSet.toList()[1].parent === reconstructed) shouldEqual true
    }
}
