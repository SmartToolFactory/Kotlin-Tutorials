package chapter6Advanced

import java.lang.Integer.max

fun main() {

    /*
                    15
                 /      \
                8        20
              /  \      /  \
            5   10     17   28
           /\
          3 6
    */

    val root = Node(15)

    root.left = Node(8)
    root.left?.left = Node(5)
    root.left?.right = Node(10)

    root.left?.left?.left = Node(3)
    root.left?.left?.right = Node(6)

    root.right = Node(20)
    root.right?.left = Node(17)
    root.right?.right = Node(28)

    val treeHeight = maxDepth(root)
    println("Tree height: $treeHeight")

    root.printInOrder()

    println("********************")
    val anotherRoot = Node(15)
    // height: 1
    anotherRoot.insert(8)
    anotherRoot.insert(20)

    // height: 2
    // Left
    anotherRoot.insert(5)
    anotherRoot.insert(10)
    // Right
    anotherRoot.insert(17)
    anotherRoot.insert(28)

    println("Another root contains 17 -> ${anotherRoot.containsValue(7)}")
    println("Another root contains 17 -> ${anotherRoot.containsValue(20)}")

}

class Node(val data: Int) {
    var left: Node? = null
    var right: Node? = null


    fun insert(value: Int) {

        // Check left of the node
        if (value < data) {
            if (left == null) {
                left = Node(value)
            } else {
                left?.insert(value)
            }
        } else {

            // Check right of the node
            if (right == null) {
                right = Node(value)
            } else {
                right?.insert(value)
            }
        }
    }

    fun containsValue(value: Int): Boolean {

        if (value == data) return true

        // Check for left side of the current node if it's not null, if it's null return false
        return if (value < data) {
            left?.containsValue(value) ?: false
        } else {
            // Check for right side of the current node if it's not null, if it's null return false
            right?.containsValue(value) ?: false
        }
    }

    fun printInOrder() {
        if (left != null) {
            left?.printInOrder()
        }

        println("$data")

        if (right != null) {
            right?.printInOrder()
        }
    }

}


class BinaryTree(var root: Node?) {


}

/* Compute the "maxDepth" of a tree -- the number of
   nodes along the longest path from the root node
   down to the farthest leaf node.*/
fun maxDepth(node: Node?): Int {

    if (node?.left == null && node?.right == null)
        return 0


    /* compute the depth of each subtree */
    val lDepth = node.left?.let {
        maxDepth(node.left)
    } ?: 0

    val rDepth = node.right?.let {
        maxDepth(node.right)
    } ?: 0

    return 1 + max(lDepth, rDepth)

}




