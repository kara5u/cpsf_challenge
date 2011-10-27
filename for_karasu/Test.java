
//package jp.ac.keio.sfc.ht.cpsf.util.collection;
import jp.ac.keio.sfc.ht.cpsf.util.collection.Collection;
import jp.ac.keio.sfc.ht.cpsf.util.collection.ArrayList;
import jp.ac.keio.sfc.ht.cpsf.util.collection.LinkedList;

public class Test {
    public static void main(String[] args) {
        //Collection<Integer> list = new ArrayList<Integer>();
        Collection<Integer> list = new LinkedList<Integer>();
        
        // add
        System.out.println("add");
        System.out.println("size: " + list.size());
        for (int i = 0; i < 20; ++i) {
            list.add(i);
        }
        
        // size
        System.out.println("size: " + list.size());

        // get 
        System.out.println("get");
        for (int i = 0; i < list.size(); ++i) {
            System.out.println(String.valueOf(list.get(i)));
        }

        // delete 
        System.out.println("delete");
        for (int i = 0; i < 5; ++i) {
            list.delete(list.size() - 1);
        }
        for (int i = 0; i < list.size(); ++i) {
            System.out.println(String.valueOf(list.get(i)));
        }
    }
}
