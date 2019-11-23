package chapter6Advanced


fun main() {

    val linkedList = SinglyLinkedList()

    repeat(5) {
        linkedList.insert(it)
    }

    // Linked List
    /*
     head = {LinkedList$Link@799}
         |next = {LinkedList$Link@800}
         |   |next = {LinkedList$Link@802}
         |   |   |next = {LinkedList$Link@803}
         |   |   |   |next = {LinkedList$Link@804}
         |   |   |   |   |next = null
         |   |   |   |   |data = 4
         |   |   |   |data = 3
         |   |   |data = 2
         |   |data = 1
         |data = 0
     */

}


class SinglyLinkedList {

    private class Node(val data: Int) {
        var next: Node? = null
    }

    private var head: Node? = null

    fun append(data: Int) {
        if (head == null) {
            head = Node(data)
            return
        }

        var lasNode = head

        var counter = 0
        while (lasNode?.next != null) {
            lasNode = lasNode.next
        }

        lasNode?.next = Node(data)


    }

    fun insert(data: Int) {

        if (head == null) {
            head = Node(data)

        } else {

            // Create a new node with given data
            val newNode = Node(data)

            // Else traverse till the last node
            // and insert the new_node there
            var last = head
            while (last?.next != null) {
                last = last.next
            }

            // Insert the new_node at last node
            last?.next = newNode

        }
    }


}