package com.stefanpetcu.codingproblemtrees.utils.BinaryTree.dto

import java.util.Optional

data class TreeNode<T>(
    val value: T,
    val parent: Optional<TreeNode<T>>,
    var leftChild: Optional<TreeNode<T>>,
    var rightChild: Optional<TreeNode<T>>
)
