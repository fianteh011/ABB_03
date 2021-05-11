package de.hsos.prog3.team5.ab05;

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
    private boolean fixedCapacity = true;
    //  legt fest, ob Elemente überschrieben werden dürfen
    private boolean discarding = false;
    // Faktor der Vergrößerung
    private int faktor = 5; //?



    //TODO: fixedCapacity und discarding dürfen nicht beide true sein (lt. Aufgabenstellung)
    public <T> Ringpuffer(int capacity, boolean expandableCapacity, boolean discarding ){
        if (!expandableCapacity && discarding) {
            this.discarding = true;
        }else if (expandableCapacity && !discarding) {
            this.fixedCapacity = false;
        }
        this.capacity = capacity;
        elements = new ArrayList<>(capacity);
        // assign null Objects
        for (int i = 0; i < capacity; i++){
            elements.add(null);
        }

    }
//------------DEQUE Utilities---------------
    @Override
    public void addFirst(T t) {
        if(size < capacity) {
            insertHelp(t);
        }
        else{
            throw new IllegalStateException();
        }
    }

    @Override
    public T removeFirst() {
        if (size >= tail) {
            return removeFirstHelp();
        }else{
            throw new IllegalStateException();
        }
    }

    @Override
    public T getFirst() {
        if (size > tail){
            return peekHelp(tail);
        }else{
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean offerFirst(T t) {
        if (size < capacity) {
            insertHelp(t);
            return true;
        } else
            return false;
    }

    @Override
    public T pollFirst() {
        if (size > tail) {
            return removeFirstHelp();
        }
        return null;
    }

    @Override
    public T peekFirst() {
        if (size > tail){
            return peekHelp(tail);
        }else{
            return null;
        }
    }


    @Override
    public void addLast(T t) {  //same as addfirst
        if(size < capacity) {
            insertHelp(t);
        }
        else{
            throw new IllegalStateException();
        }
    }

    @Override
    public T removeLast() {
        if (size < head){
            return removeLastHelp();
        }else {
            throw new IllegalStateException();
        }
    }

    @Override
    public T getLast() {
        if (size > head){
            return peekHelp(head-1);
        }else{
            throw new IllegalStateException();
        }    }

    @Override
    public boolean offerLast(T t) {
        if (size < capacity) {
            insertHelp(t);
            return true;
        } else
            return false;
    }

    @Override
    public T pollLast() {
        if (size > head) {
            return removeLastHelp();
        }
        return null;
    }

    @Override
    public T peekLast() {
        if (size > head){
            return peekHelp(head-1);
        }else{
            return null;
        }
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }
//----------------HILFSMETHODEN-----------------
    private void insertHelp(T t){
        elements.set(head,t);
        //falls !( ueberschrieben werden darf und das erste element schon vorhanden):
        //size hochzaehlen
        if (!(discarding && elements.get(head) != null)){
            size++;
        }
        head++;
    }
    private T removeFirstHelp(){
        T tmp = elements.get(tail);
        tail++;
        return tmp;
    }
    private T removeLastHelp(){
        T tmp = elements.get(head-1);
        head--;
        return tmp;
    }
    private T peekHelp(int tail){
        return elements.get(tail);
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


//------------------QUEUE Utilities--------------

    // füge element in tail hinzu
    @Override
    public boolean add(T t) {
        try {
            addLast(t);
            return true;
        }catch (IllegalStateException e){
            throw new IllegalStateException();
        }
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
    }

    @Override
    public boolean offer(T t) {
        return offerLast(t);
    }

    @Override
    public T remove() {
        return removeFirst();
    }

    @Override
    public T poll() {
        return pollFirst();
    }

    @Override
    public T element() {
        return getFirst();
    }
//-------------------STACK AND QUEUE-----------------
    @Override
    public T peek() {
        return peekFirst();
    }
//-------------------STACK---------------------------
    @Override
    public void push(T t) {
        try {
            addFirst(t);
        }catch (IllegalStateException e){
            throw new IllegalStateException();
        }
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

    }

    @Override
    public T pop() {
        return removeFirst();
    }
//-----------------------------------------------------
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
