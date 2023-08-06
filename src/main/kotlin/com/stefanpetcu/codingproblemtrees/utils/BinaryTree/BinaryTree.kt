package com.stefanpetcu.codingproblemtrees.utils.BinaryTree

import com.stefanpetcu.codingproblemtrees.utils.BinaryTree.dto.TreeNode
import java.util.*
import kotlin.collections.HashMap

class BinaryTree<T>(values: Array<T>) {
    private val root: TreeNode<T>

    private enum class SEARCH_DIRECTION { LEFT, RIGHT }

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
                Optional.of(
                    TreeNode(
                        values[currentIndex * 2 + 1],
                        Optional.of(root),
                        Optional.empty(),
                        Optional.empty()
                    )
                )

            buildTree(root.leftChild.get(), values, currentIndex * 2 + 1)
        }

        if (currentIndex * 2 + 2 < values.size) {
            root.rightChild =
                Optional.of(
                    TreeNode(
                        values[currentIndex * 2 + 2],
                        Optional.of(root),
                        Optional.empty(),
                        Optional.empty()
                    )
                )

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

    fun find(value: T): Optional<TreeNode<T>> {
        return depthFirstSearch(value)
    }

    private fun depthFirstSearch(
        value: T,
        currentNode: TreeNode<T> = root,
        direction: SEARCH_DIRECTION = SEARCH_DIRECTION.LEFT,
        visitedNodes: HashMap<T, MutableList<TreeNode<T>>> = hashMapOf()
    ): Optional<TreeNode<T>> {
        if (currentNode.value == value) {
            return Optional.of(currentNode)
        }

        if (visitedNodes.containsKey(currentNode.value)) {
            visitedNodes[currentNode.value]!!.add(currentNode)
        } else {
            visitedNodes[currentNode.value] = mutableListOf(currentNode)
        }

        if (direction == SEARCH_DIRECTION.LEFT) {
            if (currentNode.leftChild.isEmpty) {
                if (currentNode.parent.isEmpty) {
                    return Optional.empty()
                }

                return depthFirstSearch(value, currentNode.parent.get(), SEARCH_DIRECTION.RIGHT, visitedNodes)
            }

            return depthFirstSearch(value, currentNode.leftChild.get(), SEARCH_DIRECTION.LEFT, visitedNodes)
        } else { // Search direction == SearchDirection.RIGHT
            if (currentNode.rightChild.isEmpty ||
                visitedNodes.containsKey(currentNode.rightChild.get().value) &&
                visitedNodes[currentNode.rightChild.get().value]!!.contains(currentNode.rightChild.get())
            ) {
                // If there's no right child, or we are backtracking from a right branch,
                // we have to stop going back down the right branch again and go up to
                // the current node parent and try going down its right branch.
                if (currentNode.parent.isEmpty) {
                    return Optional.empty()
                }

                return depthFirstSearch(value, currentNode.parent.get(), SEARCH_DIRECTION.RIGHT, visitedNodes)
            }

            return depthFirstSearch(value, currentNode.rightChild.get(), SEARCH_DIRECTION.LEFT, visitedNodes)
        }
    }
}
