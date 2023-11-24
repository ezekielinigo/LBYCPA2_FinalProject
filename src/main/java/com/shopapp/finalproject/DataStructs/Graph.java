package com.shopapp.finalproject.DataStructs;

/**
 * Graph implemented using adjacency list
 * mainly used to store sellers and their relationships
 * for example: seller A has and B have similar tags, so they are connected
 *              so when viewing seller A, seller B will be displayed as a recommended seller
 */

public class Graph<E> {

    private Node<E> head;
    private int size;

    private static class Node<E> {
        E item;
        Node<E> next;

        public Node(E item) {
            this.item = item;
            this.next = null;
        }
    }

    public Graph() {
        head = null;
        size = 0;
    }

    public void add(E item) {
        Node<E> node = new Node<>(item);
        if (head == null) {
            head = node;
        } else {
            Node<E> temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = node;
        }
        size++;
    }

    public void add(E item, int index) {
        if (index < 0 || index > size) {
            System.out.println("Invalid index.");
            return;
        }
        Node<E> node = new Node<>(item);
        if (index == 0) {
            node.next = head;
            head = node;
        } else {
            Node<E> temp = head;
            for (int i = 0; i < index - 1; i++) {
                temp = temp.next;
            }
            node.next = temp.next;
            temp.next = node;
        }
        size++;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            System.out.println("Invalid index.");
            return;
        }
        if (index == 0) {
            head = head.next;
        } else {
            Node<E> temp = head;
            for (int i = 0; i < index - 1; i++) {
                temp = temp.next;
            }
            temp.next = temp.next.next;
        }
        size--;
    }

    public E get(int index) {
        if (index < 0 || index >= size) {
            System.out.println("Invalid index.");
            return null;
        }
        Node<E> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp.item;
    }

    public int size() {
        return size;
    }

    public void addEdge(E item1, E item2) {
        Node<E> temp = head;
        Node<E> temp2 = head;
        while (temp != null) {
            if (temp.item.equals(item1)) {
                while (temp2 != null) {
                    if (temp2.item.equals(item2)) {
                        Node<E> node = new Node<>(item2);
                        node.next = temp.next;
                        temp.next = node;
                        break;
                    }
                    temp2 = temp2.next;
                }
                break;
            }
            temp = temp.next;
        }

    }
}
