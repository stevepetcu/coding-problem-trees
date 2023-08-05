package com.stefanpetcu.codingproblemtrees.utils.BinaryTree

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows

class BinaryTreeTest {
    @Test
    fun initialisation_willThrowIllegalArgumentException_givenEmptyListIsPassedAsConstructorArg() {
        // Given + When + Then
        val exception = assertThrows<IllegalArgumentException> {
            BinaryTree(arrayOf<Int>())
        }

        assertEquals("Unexpected empty Array as BinaryTree constructor argument.", exception.message)
    }

    @Test
    fun getRoot_willReturnTheRootOfTheTree_givenTreeInitialisedWithNonEmptyArray() {
        // Given
        val tree = BinaryTree(arrayOf(30))

        // When
        val root = tree.getRoot()

        // Then
        assertNotNull(root, "The root of the tree must not be null.")
        assertEquals(30, root.value)
        assertTrue(root.parent.isEmpty, "The parent of the root of the tree must be null.")
        assertTrue(root.leftChild.isEmpty, "A tree with one node (root) cannot have any children for the root.")
        assertTrue(root.rightChild.isEmpty, "A tree with one node (root) cannot have any children for the root.")
    }

    @Test
    fun initialisation_willBuildATreeWithThreeNodes_givenTreeInitialisedWithArrayOfThreeElements() {
        // Given
        val tree = BinaryTree(arrayOf(30, 15, 25))

        // When
        val root = tree.getRoot()

        // Then
        assertEquals(30, root.value)

        assertTrue(root.leftChild.isPresent)
        assertEquals(15, root.leftChild.get().value)

        assertTrue(root.rightChild.isPresent)
        assertEquals(25, root.rightChild.get().value)
    }

    @Test
    fun initialisation_willBuildATreeWithEightNodes_givenTreeInitialisedWithArrayOfEightElements() {
        // Given
        val tree = BinaryTree(arrayOf(0, 1, 2, 3, 4, 5, 6, 7))

        // When
        val root = tree.getRoot()

        // Then root = 0
        assertEquals(0, root.value)

        // Left side
        // root.left == 1
        assertTrue(root.leftChild.isPresent)
        assertEquals(1, root.leftChild.get().value)

        // 1.left == 3
        assertTrue(root.leftChild.get().leftChild.isPresent)
        assertEquals(3, root.leftChild.get().leftChild.get().value)

        // 3.left == 7
        assertTrue(root.leftChild.get().leftChild.get().leftChild.isPresent)
        assertEquals(7, root.leftChild.get().leftChild.get().leftChild.get().value)

        // 7.left == empty
        assertTrue(root.leftChild.get().leftChild.get().leftChild.get().leftChild.isEmpty)

        // 3.right = empty
        assertTrue(root.leftChild.get().leftChild.get().rightChild.isEmpty)

        // 1.right == 4
        assertTrue(root.leftChild.get().rightChild.isPresent)
        assertEquals(4, root.leftChild.get().rightChild.get().value)

        // 4.left == empty
        assertTrue(root.leftChild.get().rightChild.get().leftChild.isEmpty)

        // 4.right == empty
        assertTrue(root.leftChild.get().rightChild.get().rightChild.isEmpty)

        // Right side
        // root.right == 2
        assertTrue(root.rightChild.isPresent)
        assertEquals(2, root.rightChild.get().value)

        // 2.left == 5
        assertTrue(root.rightChild.get().leftChild.isPresent)
        assertEquals(5, root.rightChild.get().leftChild.get().value)

        // 5.left == empty
        assertTrue(root.rightChild.get().leftChild.get().leftChild.isEmpty)

        // 5.right == empty
        assertTrue(root.rightChild.get().leftChild.get().rightChild.isEmpty)

        // 2.right == 6
        assertTrue(root.rightChild.get().rightChild.isPresent)
        assertEquals(6, root.rightChild.get().rightChild.get().value)

        // 6.left == empty
        assertTrue(root.rightChild.get().rightChild.get().leftChild.isEmpty)

        // 6.right == empty
        assertTrue(root.rightChild.get().rightChild.get().rightChild.isEmpty)
    }
}
