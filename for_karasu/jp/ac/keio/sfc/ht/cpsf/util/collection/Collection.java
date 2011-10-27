/* file name  : Collection.java
 * authors    : Yutaka Karatsu
 * created    : Wed Oct 26 04:04:43 2011
 * copyright  : Yutaka Karatsu
 *
 * modifications:
 *
 */

package jp.ac.keio.sfc.ht.cpsf.util.collection;

/**
 * jp.ac.keio.sfc.ht.cpsf.util.collection.Collection.java
 * 
 * the interface of my other collection class.
 * for cpsf challenge
 *
 * @author Yutaka Karatsu (karasu@ht.sfc.keio.ac.jp)
 * @date 2011.10.26
 */
public interface Collection<T> {

    /** 
     * the abstract method to get a specified element 
     * 
     * @param i index of the element
     * @return element
     */
    public T get(int i);
    
    /** 
     * the abstract method to add new element 
     * 
     * @param value new element
     * @return 
     */
    public boolean add(T value);
    
    /** 
     * the abstract method to delete a specified element
     * 
     * @param i the index of a deleted element
     * @return 
     */
    public boolean delete(int i);
    
    /** 
     * the abstract method to get the length of the list 
     * 
     * @return length
     */
    public int size();
}
