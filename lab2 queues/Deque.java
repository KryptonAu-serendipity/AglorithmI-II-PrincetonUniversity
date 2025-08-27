import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;
public class Deque <Item> implements Iterable<Item>{
    // 定义核心数据类型
    private class Node {
        Item item;
        Node next;
        Node prev;
    }
    //定义头尾节点
    private   Node first;
    private   Node last;
    //定义大小
    private   int size;
    //构造函数
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }
    //判断是否为空
    public boolean isEmpty() {
        return size == 0;
    }
    //判断输入数据是否有效
    private void validate(Item item) {
        if (item == null) throw new IllegalArgumentException("Item is null");
    }
    public int size() {
        return size;
    }
    //进行add的操作
    public void addFirst(Item item) {
        validate(item);
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        first.next = oldFirst;
        if (isEmpty()) {
            last = first;
        }
        else {
            oldFirst.prev = first;
        }
        size++;
    }
    public void addLast(Item item) {
        validate(item);
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (isEmpty()) {
            first = last;
        }
        else {
            oldLast.next = last;
        }
        size++;
        }
    
    //进行remove的操作
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Item item =first.item;
        Node oldfirst = first;
        first = oldfirst.next;
        oldfirst.next = null;
        size--;
        if (isEmpty()) {
            last = null;
        } else {
            first.prev = null;//不修改的话仍指向原来的节点
        }
        return item;
        }
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Item item =last.item;
        Node oldlast = last;
        last = oldlast.prev;
        oldlast.prev = null;
        size--;
        if (isEmpty()) {
            first = null;
        } else {
            last.next = null;//不修改的话仍指向原来的节点
        }
        return item;
        }
    public Iterator<Item> iterator() {
        return new DequeIterator(first);
    }
    private class DequeIterator implements Iterator<Item> {
        private Node current;
        public DequeIterator(Node first) {
            current = first;
        }
        public boolean hasNext() {
            return current != null;
        }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

        public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(666);
        deque.addFirst(888);
        deque.addFirst(999);
        deque.addLast(100);
        for (int i : deque)
            StdOut.print(i + " ");
            StdOut.print("\n");
        for (int i : deque)
            StdOut.print(i + " ");
        StdOut.print("\n");
        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.isEmpty());
    }

    }




    


    

