package com.zhengguoqiang.lockfree;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class NonBlockingQueue<T> {

    private static class Node<T>{
        private volatile T value;
        private volatile Node<T> next;
        private volatile Node<T> prev;

        public Node(T value){
            this.value = value;
            this.next = null;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }
    }

    private final AtomicReference<Node<T>> head,tail;
    private final AtomicInteger size;
    private static final int DEFAULT_CAPACITY = 8;

    public NonBlockingQueue(int capacity){
        head = new AtomicReference<>(null);
        tail = new AtomicReference<>(null);
        size = new AtomicInteger();
        size.set(capacity < 1 ? DEFAULT_CAPACITY : capacity);
    }


    public void add(T element){
        if (element == null){
            throw new NullPointerException();
        }
        Node<T> node = new Node<>(element);
        Node<T> currentTail;
        do {
            currentTail = tail.get();
            node.setPrev(currentTail);
        }while (!tail.compareAndSet(currentTail,node));

        if (node.getPrev() != null){
            node.getPrev().setNext(node);
        }

        head.compareAndSet(null,node);
        size.incrementAndGet();
    }

    public T get(){
        if (head.get() == null){
            throw new NoSuchElementException();
        }

        Node<T> currentHead;
        Node<T> nextNode;
        do {
            currentHead = head.get();
            nextNode = currentHead.getNext();
        }while (!head.compareAndSet(currentHead,nextNode));

        size.decrementAndGet();
        return currentHead.getValue();
    }
}
