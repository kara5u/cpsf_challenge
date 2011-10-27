/* file name  : LinkedList.java
 * authors    : Yutaka Karatsu
 * created    : Wed Oct 26 17:01:02 2011
 * copyright  : Yutaka Karatsu
 *
 * modifications:
 *
 */

package jp.ac.keio.sfc.ht.cpsf.util.collection;

public class LinkedList<T> implements Collection<T> {

    private class Entry<T> {
        private T value = null;
        private Entry<T> next = null;
        private Entry<T> prev = null;

        public T getValue() {
            return value;
        }
    
        public void setValue(T value) {
            this.value = value;
        }

        public Entry<T> getNext() {
            return next;
        }

        public void setNext(Entry<T> next) {
            this.next = next;
        }

        public Entry<T> getPrev() {
            return prev;
        }

        public void setPrev(Entry<T> prev) {
            this.prev = prev;
        }
    }

    private Entry<T> first = null;
    private Entry<T> last = null;

    public LinkedList() {

    }   

    @Override
    public boolean add(T value) {

        if (first == null) {
            Entry<T> entry = new Entry<T>();
            entry.setValue(value);
            first = entry;
            last = entry;
        } else {
            Entry<T> entry = new Entry<T>();
            entry.setValue(value);
            last.setNext(entry);
            entry.setPrev(last);
            last = entry;
        }   
        return true;
    }   

    @Override
    public boolean delete(int i) {
        if (i == 0) {
            first = first.getNext();
        } else if (i == this.size() - 1) {
            //System.out.println("last value:" + last.getValue() );
            last = last.getPrev();
            last.setNext(null);
        } else if ( 0 < i && i < this.size() - 1) {
            Entry<T> entry = getEntry(i);
            entry.getNext().setPrev(entry.getPrev());
            entry.getPrev().setNext(entry.getNext());
            entry.setNext(null);
            entry.setPrev(null);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public T get(int i) {
        if (i < 0 || this.size() - 1 < i) {
            throw new IndexOutOfBoundsException();
        }

        Entry<T> entry = getEntry(i);
        if (entry == null) {
            throw new IndexOutOfBoundsException();
        }
        return entry.getValue();
    }

    private Entry<T> getEntry(int i) {
        int count = 0;
        Entry<T> current = first;
        while(current != null) {
            if (count == i) {
                return current;
            }
            current = current.getNext();
            ++count;
        }
        return null;
    }

    @Override
    public int size() {
        int count = 0;
        Entry<T> current = first;
        while(current != null) {
            current = current.getNext();
            ++count;
        }
        return count;
    }

}
