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
    // eine Art "Pointer" für die Tail
    private int tailLifo = 0;
    // definiert die Anzahl der tatsächlich verwalteten Elemente
    private int size = 0;
    // definiert die maximale Anzahl der Elemente des Puffers
    private int capacity;
    // legt fest, ob die Kapazität vergrößert werden darf
    private boolean fixedCapacity = true;
    //  legt fest, ob Elemente überschrieben werden dürfen
    private boolean discarding = false;
    // Faktor der Vergrößerung
    private int faktor;


    //TODO: fixedCapacity und discarding dürfen nicht beide true sein (lt. Aufgabenstellung)
    public <T> Ringpuffer(int capacity, int faktor, boolean expandableCapacity, boolean discarding) {
        if (!expandableCapacity && discarding) {
            this.discarding = true;
        } else if (expandableCapacity && !discarding) {
            this.fixedCapacity = false;
        }

        this.capacity = capacity;
        this.faktor = faktor;
        this.elements = new ArrayList<>(capacity);
        // assign null Objects
        for (int i = 0; i < capacity; i++) {
            this.elements.add(null);
        }
    }

    //------------DEQUE Utilities---------------
    @Override
    public void addFirst(T t) {

        if (this.size <= this.capacity) {
            this.insertLastHelp(t);
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public T removeFirst() {
        if (this.size >= this.tail) {
            System.out.println(this.tail);
            return this.removeFirstHelp();
        } else {
            throw new IllegalStateException();
        }

    }

    @Override
    public T getFirst() {
        if (this.size > this.tail) {
            return this.peekHelp(this.tail);
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean offerFirst(T t) {
        if (this.size <= this.capacity) {
            this.insertLastHelp(t);
            return true;
        } else
            return false;
    }

    @Override
    public T pollFirst() {
        if (this.size > this.tail) {
            return this.removeFirstHelp();
        }
        return null;
    }

    @Override
    public T peekFirst() {
        if (this.size > this.tail) {
            return this.peekHelp(this.tail);
        } else {
            return null;
        }
    }


    @Override
    public void addLast(T t) {  //same as addfirst
        if (this.size <= this.capacity) {
            this.insertLastHelp(t);
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public T removeLast() {
        if (this.size <= this.head) {
            return removeLastHelp();
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public T getLast() {
        if (size >= head) {
            return peekHelp(head - 1);
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean offerLast(T t) {
        if (size <= capacity) {
            insertLastHelp(t);
            return true;
        } else
            return false;
    }

    @Override
    public T pollLast() {
        if (size >= head) {
            return removeLastHelp();
        }
        return null;
    }

    @Override
    public T peekLast() {
        if (size >= head && head != 0) {
            return peekHelp(head - 1);
        } else if (head == 0) {
            return peekHelp(capacity - 1);
        } else {
            return null;
        }
    }


    @Override
    public boolean removeFirstOccurrence(Object o) {
        for (int i = 0; i < capacity; i++) {
            if (this.elements.get(i).equals(o)) {
                this.elements.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        for (int i = capacity; i >= 0; i--) {
            if (this.elements.get(i).equals(o)) {
                this.elements.remove(i);
                return true;
            }
        }
        return false;
    }


    //----------------HILFSMETHODEN-----------------
    private void insertLastHelp(T t) {
        elements.set(head, t);
        //falls !( ueberschrieben werden darf und das erste element schon vorhanden):
        //size hochzaehlen
        if (size < capacity) {
            size++;
        }
        if (head == capacity - 1) {
            if (!fixedCapacity) {
                this.capacity += faktor;
                for (int i = 0; i < faktor; i++) {
                    this.elements.add(null);

                }
                this.head++;
            } else if (this.discarding) {
                //falls ueberschrieben werden soll
                //setze head wieder auf 0 (Ring voll)
                this.head = 0;
            } else {
                System.out.println("Maximale Groesse erreicht, Elemente werden nicht ueberschrieben");
            }
        } else {
            this.head++;
        }
    }


    private T removeFirstHelp() {
        /*
         * HilfsMethode zum Lesen des ersten Elements in der Liste
         */
        T tmp = this.elements.get(this.tail);
        // Beim Lesen des Elements wird zuerst die Tail als nächstes versetzt
        this.tail++;

        if (this.tail == this.capacity) {
            this.tail = 0;
        }
        return tmp;
    }

    private T removeLastHelp() {
        T tmp = elements.get(tailLifo);
        tailLifo--;
        return tmp;
    }

    //Hilfsmethode fuer die Peek-Methoden
    private T peekHelp(int _tail) {
        return this.elements.get(_tail);
    }


//------------------QUEUE Utilities--------------

    // füge element in tail hinzu
    @Override
    public boolean add(T t) {
        try {
            this.addLast(t);

            return true;
        } catch (IllegalStateException e) {
            throw new IllegalStateException();
        }

    }

    @Override
    public boolean offer(T t) {
        return this.offerLast(t);
    }

    @Override
    public T remove() {
        return this.removeFirst();
    }

    @Override
    public T poll() {
        return this.pollFirst();
    }

    @Override
    public T element() {
        return this.getFirst();
    }

    //-------------------STACK AND QUEUE-----------------
    @Override
    public T peek() {
        return this.peekLast();
    }

    //-------------------STACK---------------------------
    @Override
    public void push(T t) {
        try {
            this.addFirst(t);
            if (this.head != 0) {
                this.tailLifo = this.head - 1;
            } else {
                this.tailLifo = this.capacity - 1;
            }
        } catch (IllegalStateException e) {
            throw new IllegalStateException();
        }
    }

    @Override
    public T pop() {
        return removeLast();
    }

    //-----------------------------------------------------
    @Override
    public boolean addAll(Collection<? extends T> c) {
        return this.elements.addAll(c);

    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.elements.removeAll(c);

    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.elements.retainAll(c);

    }

    @Override
    public void clear() {
        this.elements.clear();
    }


    @Override
    public boolean remove(Object o) {
        return this.removeFirstOccurrence(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.elements.containsAll(c);
    }

    @Override
    public boolean contains(Object o) {
        return this.elements.contains(o);
    }

    @Override
    public int size() {
        // gibt die tatsächlich Menge an Elemente zurück
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < this.capacity; i++) {
            if (this.elements.get(i) != null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        // auf sämtliche Elemente soll iterativ lesend zugegriffen werden können
        return this.elements.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.elements.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.elements.toArray(a);
    }

    @Override
    public Iterator<T> descendingIterator() {
        return this.elements.iterator();
    }
}



    /*
    //pruefe, dass nicht sowohl das flag fuers ueberschreiben, als auch fuers Kapazitaet erhoehen true sind
    //wenn doch, wird es behandelt als ob keins von beiden

    private int pruefeVerhalten(boolean fixCap, boolean discard) {
        if (!fixCap && discard) {
            return 1;   //ueberschreiben
        }else if (fixCap && !discard) {
            return 2;   //Kapazitaet erhoehen
        }else {
            return 0;   //keins von beiden/beides
        }
    }
*/

 /*
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
                for (int i = 0; i < capacity; i++){
                    elements.add(null);
                }
            }
        // wenn size = 0 (leer)
        if (size == 0){
            elements.set(tail, t);
        }else{
            --tail;
            if(tail == -1){
                tail = capacity - 1;
            }
            elements.set(tail, t);
        }
        // wenn size == capacity, dann tail soll bewegen
        if (size == capacity){
            --head;
            if (head == -1) {
                head = capacity - 1;
            }
            return true;
        }
        ++size;
        return true;*/

  /*
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
                for (int i = 0; i < capacity; i++){
                    elements.add(null);
                }
            }
        // wenn size = 0 (leer)
        if (size == 0){
            elements.set(head, t);
        }else{
            ++head;
            if(head == capacity){
                head = 0;
            }
            elements.set(head, t);
        }
        // wenn size == capacity, dann tail soll bewegen
        if (size == capacity){
            ++tail;
            if (tail == capacity) {
                tail = 0;
            }
            return;
        }
        ++size;
        */

