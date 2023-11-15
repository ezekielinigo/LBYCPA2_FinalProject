package com.shopapp.finalproject.DataStructs;

public class Stack<E> {
    private Node<E> top;
    private int size;

    public Stack() {
        top = null;
        size = 0;
    }

    public void push(E data) {
        Node<E> newNode = new Node<>(data);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public E pop() {
        if (top == null) {
            return null;
        }
        E data = top.data;
        top = top.next;
        size--;
        return data;
    }

    public E peek() {
        if (top == null) {
            return null;
        }
        return top.data;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return top == null;
    }

    private static class Node<E> {
        E data;
        Node<E> next;

        public Node(E data) {
            this.data = data;
        }
    }

}
