package list;


import java.util.AbstractList;
import java.util.NoSuchElementException;

public class CustomList<T> extends AbstractList<T> {
    private class Node {
        public Node next;
        public T value;

        public Node(T value) {
            this.next = null;
            this.value = value;
        }
    }

    private Node head, tail;
    private int size = 0;






    public CustomList(){
        this.head = null;
        this.tail = null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {
        Node temp = head;
        for(int i = 0; i < index; i++){
            temp = temp.next;
            if(temp == null){
                throw new NoSuchElementException("index is out of bounds");
            }
        }
        return temp.value;
    }

    public void addLast(T value){
        if(tail == null){
            tail = new Node(value);
            head = tail;
        }
        else{
            tail.next = new Node(value);
            tail = tail.next;
        }
        size++;
    }


    public T getLast(){
        if(tail == null)
            throw new NoSuchElementException("List is empty");
        return tail.value;
    }


    public void addFirst(T value){
        if(head == null){
            head = new Node(value);
            tail = head;
        }else{
            Node newHead = new Node(value);
            newHead.next = head;
            head = newHead;
        }
        size++;
    }

    public T getFirst(){
        if(head == null){
            throw new NoSuchElementException("List is empty");
        }
        return head.value;
    }

    public T removeFirst(){
        if(head == null){
            throw new NoSuchElementException("List is empty");
        }

        Node temp = head;
        head = head.next;
        size--;
        return temp.value;
    }

    public T removeLast(){
        if(tail == null){
            throw new NoSuchElementException("List is empty");
        }

        if(head == tail){
            Node temp = head;
            head = tail = null;
            return temp.value;
        }else{
            Node temp = head;
            while(temp.next != tail) {
                temp = temp.next;
            }
            tail = temp;
            Node tempVal = temp.next;
            temp.next = null;
            size--;
            return tempVal.value;
        }

    }
}
