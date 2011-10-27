/* file name  : ArrayList.java
 * authors    : Yutaka Karatsu
 * created    : Wed Oct 26 04:20:44 2011
 * copyright  : Yutaka Karatsu
 *
 * modifications:
 *
 */
package jp.ac.keio.sfc.ht.cpsf.util.collection;

/**
 * jp.ac.keio.sfc.ht.cpsf.util.collection.ArrayList
 *
 * A minimum implementation of java.util.ArrayList 
 * This is a variable-length array by increasing the menber array's size,
 * when it is not enough to add new element.
 * for cpsf challenge 
 *
 * @author Yutaka Karatsu (karasu@ht.sfc.keio.ac.jp)
 * @date 2011.10.26
 * @version $Revision: 1.6
 */
public class ArrayList<T> implements Collection<T> {

    /** internal array */
    private Object[] array = new Object[10];

    /** the current number of elements in the array */
    private int size = 0;

    
    /** 
     * add new element 
     * 
     * @param value 
     * @return 
     */
    @Override
    public boolean add(T value) {
        if (array.length > size) { // if the array has capacities enough to keep new element
            array[size] = value;
        } else if (array.length <= size) { // not enough
            Object[] newArray = new Object[(int)(size * 1.5)]; // resize
            for (int i = 0; i < array.length; ++i) { // translocating
                newArray[i] = array[i];
            }
            newArray[size] = value;
            array = newArray;
        }
        ++size; // update size
        return true;
    }

    /** 
     * delete the specfied element in the list
     * 
     * @param i index
     * @return 
     */
    @Override
    public boolean delete(int i) {
        if (i < 0 || size - 1 < i) {
            throw new IndexOutOfBoundsException();
        }
        for ( int n = i; n < array.length - 1; ++n) { // overriding the target and closing up the empty
            array[n] = array[n+1]; // overriding
        }
        --size; // update size
        return true;
    }

    /** 
     * get the element
     * 
     * @param i index
     * @return 
     */
    @Override
    public T get(int i) {
        if (i < 0 || size - 1< i) {
            throw new IndexOutOfBoundsException();
        }
        return (T)array[i]; // casting Object instance to T instance
    }
    
    /** 
     * get the length of this list 
     * 
     * @return 
     */
    @Override
    public int size() {
        return size;
    }
}
