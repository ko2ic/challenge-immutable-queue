package com.ko2ic.challenge;

import java.util.ArrayList;
import java.util.List;

public class ImmutableQueue<T extends MyCloneable<T>> implements Queue<T> {

    private T head;
    private List<T> tail;
    private int size;

    public ImmutableQueue(T t) {
        this.head = t;
        this.tail = new ArrayList<>();
        this.size = 1;
    }

    public ImmutableQueue() {
        this.head = null;
        this.tail = new ArrayList<>();
        this.size = 0;
    }

    private ImmutableQueue(T head, List<T> tail) {
        this.head = head;
        this.tail = tail;
        this.size = this.tail.size() + 1;
    }

    @Override
    public Queue<T> enQueue(T t) {
        if (this.size == 0) {
            return new ImmutableQueue<>(t.clone());
        } else if (this.size == 1) {
            List<T> newTail = new ArrayList<>();
            newTail.add(t.clone());
            return new ImmutableQueue<>(head(), newTail);
        }
        List<T> newTail = new ArrayList<>();
        this.tail.forEach(item -> newTail.add(item.clone()));
        newTail.add(t.clone());
        return new ImmutableQueue<>(head(), newTail);
    }

    @Override
    public Queue<T> deQueue() {
        if (this.size <= 1) {
            return new ImmutableQueue<>();
        } else if (this.size == 2) {
            return new ImmutableQueue<>(this.tail.get(0));
        }
        return new ImmutableQueue<>(this.tail.get(0), this.tail.subList(1, this.tail.size() - 1));
    }

    @Override
    public T head() {
        return this.head;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }
}
