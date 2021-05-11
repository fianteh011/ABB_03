package de.hsos.prog3.team5.ab05;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.function.Consumer;
import java.io.Serializable;
import java.util.*;


public class Ringpuffer<T> implements Deque<T>, RandomAccess, Serializable, Cloneable {
    private ArrayList<T> elements = new ArrayList<>();
    // definiert die End-Position des Puffers
    private int head = 0;
    // definiert die Anfangs-Position des Puffers
    private int tail = 0;
    // definiert die Anzahl der tatsächlich verwalteten Elemente
    private int size = 0;
    // definiert die maximale Anzahl der Elemente des Puffers
    private int capacity;
    // legt fest, ob die Kapazität vergrößert werden darf
    private boolean fixedCapacity;
    //  legt fest, ob Elemente überschrieben werden dürfen
    private boolean discarding;
    // Faktor der Vergrößerung
    private int faktor = 5;

    public <T> Ringpuffer(int capacity, boolean fixedCapacity, boolean discarding) {
        this.capacity = capacity;
        this.fixedCapacity = fixedCapacity;
        this.discarding = discarding;
        elements = new ArrayList<>(capacity);
        // assign null Objects
        for (int i = 0; i < capacity; i++) {
            elements.add(null);
        }

    }

    @Override
    public void addFirst(T t) {
        if (size < capacity)
            elements.set(0, t);
    }

    @Override
    public void addLast(T t) {
        if (size < capacity)
            elements.set(capacity - 1, t);
    }

    @Override
    public boolean offerFirst(T t) {
        if (size < capacity) {
            addFirst(t);
            return true;
        } else
            return false;
    }

    @Override
    public boolean offerLast(T t) {
        if (size < capacity) {
            addLast(t);
            return true;
        } else
            return false;
    }

    @Override
    public T removeFirst() {
        if (size > 0) {
            tail++;
            return elements.get(0);
        }
        return null;
    }

    @Override
    public T removeLast() {
        return null;
    }

    @Override
    public T pollFirst() {
        if (size > 0) {
            return elements.get(0);
        }
        return null;
    }

    @Override
    public T pollLast() {
        return null;
    }

    @Override
    public T getFirst() {
        return null;
    }

    @Override
    public T getLast() {
        return null;
    }

    @Override
    public T peekFirst() {
        return null;
    }

    @Override
    public T peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    // füge element in tail hinzu
    @Override
    public boolean add(T t) {
        // wenn es voll ist
        if (size == capacity)
            if (fixedCapacity == true) {
                // Wenn wir Elemente nicht überschreiben können, dann exception
                if (discarding == false)
                    throw new IllegalStateException("Elemente dürfen nicht überschrieben werden!");
            }
            // Wenn die Kapazität nicht festgelegt ist,dann fügen den Faktor hinzu
            else {
                capacity += faktor;
                for (int i = 0; i < capacity; i++) {
                    elements.add(null);
                }
            }
        // wenn size = 0 (leer)
        if (size == 0) {
            elements.set(tail, t);
        } else {
            --tail;
            if (tail == -1) {
                tail = capacity - 1;
            }
            elements.set(tail, t);
        }
        // wenn size == capacity, dann tail soll bewegen
        if (size == capacity) {
            --head;
            if (head == -1) {
                head = capacity - 1;
            }
            return true;
        }
        ++size;
        return true;
    }

    @Override
    public boolean offer(T t) {
        return false;
    }

    @Override
    public T remove() {
        return null;
    }

    @Override
    public T poll() {
        if (elements.isEmpty()) {
            return null;
        }
        return remove();
    }

    @Override
    public T element() {
        return null;
    }

    @Override
    public T peek() {
        return elements.get(tail);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void push(T t) {
        // wenn es voll ist
        if (size == capacity)
            if (fixedCapacity == true) {
                // Wenn wir Elemente nicht überschreiben können, dann exception
                if (discarding == false)
                    throw new IllegalStateException("Elemente dürfen nicht überschrieben werden!");
            }
            // Wenn die Kapazität nicht festgelegt ist,dann fügen den Faktor hinzu
            else {
                capacity += faktor;
                for (int i = 0; i < capacity; i++) {
                    elements.add(null);
                }
            }
        // wenn size = 0 (leer)
        if (size == 0) {
            elements.set(head, t);
        } else {
            ++head;
            if (head == capacity) {
                head = 0;
            }
            elements.set(head, t);
        }
        // wenn size == capacity, dann tail soll bewegen
        if (size == capacity) {
            ++tail;
            if (tail == capacity) {
                tail = 0;
            }
            return;
        }
        ++size;
    }

    @Override
    public T pop() {
        return remove();
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return elements.iterator();
    }

    @Override
    public Object[] toArray() {
        return elements.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return elements.toArray(a);
    }

    @Override
    public Iterator<T> descendingIterator() {
        return elements.iterator();
    }
}