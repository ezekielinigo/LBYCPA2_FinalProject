package com.shopapp.finalproject.DataStructs;

/**
 * Queue implemented using linked lists
 */

public class Queue<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    private int capacity;

    public Queue(int capacity) {
        this.capacity = capacity;
        this.size = 0;
    }

    public void push(E item) {
        if (isFull()) {
            System.out.println("Queue is full. Cannot push.");
            return;
        }
        Node<E> node = new Node<>(item);
        if (isEmpty()) {
            head = node;
            tail = node;
        }else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    private boolean isEmpty() {
        return size == 0;
    }
    public boolean isFull() {
        return size == capacity;
    }

    public E pop() {
        if (isEmpty()) {
            System.out.println("Queue is empty. Cannot pop.");
            return null;
        }
        E item = head.item;
        head = head.next;
        size--;
        return item;
    }

    public E front() {
        if (isEmpty()) {
            System.out.println("Queue is empty. No front element.");
            return null;
        }
        return head.item;
    }

    public E back() {
        if (isEmpty()) {
            System.out.println("Queue is empty. No back element.");
            return null;
        }
        return tail.item;
    }

    private class Node<E> {
        E item;
        Node<E> next;

        public Node(E item) {
            this.item = item;
        }
    }

}