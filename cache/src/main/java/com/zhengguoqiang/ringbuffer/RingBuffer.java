package com.zhengguoqiang.ringbuffer;

/**
 * 环形缓冲区，借用Disruptor的序号单调递增的思想
 *
 * @param <E>
 */
public class RingBuffer<E> {
    private static final int DEFAULT_CAPACITY = 8;

    private final int capacity;
    private final E[] data;
    //volatile 只适用于单producer、单consumer的生产者-消费者模型
    private volatile int writeSequence, readSequence;

    public RingBuffer(int capacity) {
        this.capacity = capacity < 1 ? DEFAULT_CAPACITY : capacity;
        this.data = (E[]) new Object[this.capacity];
        this.writeSequence = -1;
        this.readSequence = 0;
    }

    public boolean isEmpty() {
        return writeSequence < readSequence;
    }

    public int size() {
        return (writeSequence - readSequence) + 1;
    }

    public boolean isFull() {
        return size() >= capacity;
    }

    public boolean offer(E element) {
        if (!isFull()) {
            int nextSlot = writeSequence + 1;
            data[nextSlot % capacity] = element;

            writeSequence++;
            return true;
        }

        return false;
    }

    public E poll() {
        if (!isEmpty()) {
            E nextValue = data[readSequence % capacity];
            readSequence++;
            return nextValue;
        }

        return null;
    }

}
