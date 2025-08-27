import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int  INIT_CAPACITY = 8 ;//这里的语法是一个仅此类可见，所有对象共享的整数常量
    private Item[] v;
    private int size;
    private int first;
    private int last;



    // construct an empty randomized queue
    public RandomizedQueue() {
        v = (Item[]) new Object[INIT_CAPACITY];
        size = 0;
        first = 0;
        last = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resize(int capacity) {
        Item[] newV = (Item[])new Object[capacity];
        for (int i = 0; i < size ;i++) 
            newV[i] = v[(first + i) % v.length];
        v = newV;
        first = 0;
        last = size;
    }
    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Item is null");
        if (size == v.length) resize(2 * v.length);
        v[last++] = item;
        if(last == v.length) last = 0;
        size ++;
        // 这行代码的意思是如果last指针指向了数组的末尾，且没有触发上面的resize，则说明数组
        //前面还有未使用的空间，为了充分利用所以置last=0
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int id = (first + StdRandom.uniformInt(size)) % v.length;
        Item item = v[id];
        v[id] = v[first];//维护数组秩序，防止出现断开
        v[first++] = null;//避免内存泄漏
        size--;
        if (size > 0 && size == v.length / 4) resize(v.length/2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int id = (first + StdRandom.uniformInt(size)) % v.length;
        Item item = v[id];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    } 
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i;
        private int[] idx;

        public RandomizedQueueIterator() {
        //在于实现完全随机的遍历    
            i = 0;
            idx = new int[size];
            for (int k = 0; k < size; k++) 
                idx[k] = (first + k) % v.length;
            StdRandom.shuffle(idx);
        }

        
        public boolean hasNext() {
            return i < size;
        }
        public Item next() {
            if(!hasNext())  throw new NoSuchElementException();
            return v[idx[i++]];//按我们生成的索引数组idx[]作为索引在v[]中取值
        }

    }
    // unit testing (required)
    public static void main(String[] args) {
        int size = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < size; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
    }

}
}