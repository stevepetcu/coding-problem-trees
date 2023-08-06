package com.stefanpetcu.codingproblemtrees.utils.BinaryTree

import com.stefanpetcu.codingproblemtrees.utils.BinaryTree.dto.TreeNode
import java.util.*

class BinaryTree<T>(values: Array<T>) {
    private val root: TreeNode<T>

    init {
        if (values.isEmpty()) {
            throw IllegalArgumentException("Unexpected empty Array as BinaryTree constructor argument.")
        }

        root = TreeNode(values.first(), Optional.empty(), Optional.empty(), Optional.empty())

        buildTree(root, values, 0)
    }

    private fun buildTree(root: TreeNode<T>, values: Array<T>, currentIndex: Int) {
        if (currentIndex >= values.size) {
            return
        }

        if (currentIndex * 2 + 1 < values.size) {
            root.leftChild =
                Optional.of(TreeNode(values[currentIndex * 2 + 1], Optional.of(root), Optional.empty(), Optional.empty()))

            buildTree(root.leftChild.get(), values, currentIndex * 2 + 1)
        }

        if (currentIndex * 2 + 2 < values.size) {
            root.rightChild =
                Optional.of(TreeNode(values[currentIndex * 2 + 2], Optional.of(root), Optional.empty(), Optional.empty()))

            buildTree(root.rightChild.get(), values, currentIndex * 2 + 2)
        }
    }

    fun getRoot(): TreeNode<T> {
        return root
    }

    fun getHeight(): UInt {
        return getHeightFrom()
    }

    private fun getHeightFrom(node: TreeNode<T> = root, level: UInt = 1u): UInt {
        // Since this tree always assigns the left child nodes before assigning a right child node,
        // if the leftChild.isEmpty, we know that there's no rightChild for that root node. That
        // also means that the left branch will always be the longest branch, so we can just do
        // a depth first traversal of the left branch.
        if (node.leftChild.isEmpty) {
            return level
        }

        return getHeightFrom(node.leftChild.get(), level + 1u)
    }
}
