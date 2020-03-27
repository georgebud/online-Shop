import java.util.Collection;
import java.util.Comparator;
import java.util.ListIterator;

public abstract class ItemList {
    protected Node<Item> sentinel;
    protected Node<Item> tail;
    protected Comparator<Item> comparator;
    protected ItemIterator iterator;
    protected int size;

    public ItemList() {
        this.tail = null;
        this.sentinel = new Node<>();
//        sentinel.next = sentinel.prev = sentinel;
        this.iterator = new ItemIterator();
        this.size = 0;

        this.comparator = new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                if (o1.getPrice() > o2.getPrice())
                    return 1;
                else if (o1.getPrice() < o2.getPrice())
                        return -1;
                    /*else {
                        for(int i = 0; i < o1.getName().length() && i < o2.getName().length(); i++) {
                            if(o1.getName().charAt(i) > o2.getName().charAt(i))
                                return 1;
                            else if(o1.getName().charAt(i) < o2.getName().charAt(i))
                                return -1;
                        }
                        if(o1.getName().length() == o2.getName().length())
                            return 0;
                        else if(o1.getName().length() > o2.getName().length())
                            return 1;
                        else return -1;
                    }*/
                return o1.getName().compareTo(o2.getName());

                    /*else {
                    String a = o1.getName(), b = o2.getName();
                    for(int i=0; i<a.length() && i<b.length(); i++) {
                        if(a.charAt(i) != b.charAt(i))
                            return a.charAt(i) < b.charAt(i) ? -1 : 1;
                    }
                    return a.length() < b.length() ? -1 : a.length() == b.length() ? 0 : 1;
                    }*/
            }
        };
    }

    protected static class Node<T> {
        T element;
        Node<T> next;
        Node<T> prev;

        public Node(T element, Node<T> next, Node<T> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }

        public Node() {
            this.element = null;
            this.next = this;
            this.prev = this;
        }
    }

    protected class ItemIterator implements ListIterator<Item> {
        protected Node<Item> current;
        Integer index, state;
        public ItemIterator() {
            this.current = sentinel.next;
            this.index = 0;
            this.state = 0;
        }

        public Node<Item> getTail() {
            /*ItemIterator iterator = new ItemIterator();

            while(iterator.hasNext()) {
                if(this.current.next == null)
                    tail = this.current;
                iterator.next();
            }

            return tail;*/

            tail = sentinel.prev;
            return tail;
        }

        @Override
        public boolean hasNext() {
            this.state = 0;
            boolean result = false;

            if(this.current != sentinel)
                result = true;
//            else
//                this.current = getTail();

            return result;
        }

        @Override
        public Item next() {
            Item currentItem = this.current.element;

            this.state = 1;
            this.current = this.current.next;
            this.index++;

            return currentItem;
        }

        @Override
        public boolean hasPrevious() {
            this.state = 0;

            return this.current.prev != sentinel;
        }

        @Override
        public Item previous() {
            this.state = 2;
            this.current = this.current.prev;
            this.index--;

            return this.current.element;
        }

        @Override
        public int nextIndex() {
            this.state = 0;
            return ++this.index;
        }

        @Override
        public int previousIndex() {
            this.state = 0;
            return --this.index;
        }

        @Override
        public void remove() {
            Node<Item> prev = sentinel, next = sentinel;

            if(this.state == 2) {
                prev = this.current.prev;
                next = this.current.next;
               /* if(prev == null)
                    sentinel = next;*/
            }
            else if(this.state == 1) {
                prev = this.current.prev.prev;
                next = this.current;
            }

            /*if(prev != null)
                prev.next = next;
            if(next != null
                next.prev = prev;*/

            prev.next = next;
            next.prev = prev;

            this.current = next;
            this.state = 0;
        }

        @Override
        public void set(Item item) {
            if(this.state == 1) {
                this.current.prev.element = item;
            }
            this.state = 0;
        }

        @Override
        public void add(Item item) {
            Node<Item> newNode = new Node<>(item, this.current, this.current.prev);

            /*if(sentinel == null) {
                sentinel = newNode;
                this.current = sentinel;
                ++size;
            }*/

            newNode.prev.next = newNode;
            this.current.prev = newNode;

            ++size;

            this.current = sentinel.next;
        }
    }

    public abstract boolean add(Item element) ;
    public abstract boolean addAll(Collection<? extends Item> c);

    public Item getItem(int index) {
        Node<Item> itemNode = this.sentinel.next;
        int count = 0;

        while(count < index) {
            itemNode = itemNode.next;
            count++;
        }

        return itemNode.element;
    }

    public Node<Item> getNode(int index) {
        Node<Item> itemNode = this.sentinel.next;
        int count = 0;

        while(count < index) {
            itemNode = itemNode.next;
            count++;
        }

        return itemNode;
    }

    public int indexOf(Item item) {
        int index = 0;
        Node<Item> itemNode = this.sentinel.next;

        while(!(itemNode.element.equals(item))) {
            ++index;
            itemNode = itemNode.next;
        }

        return index;
    }

    public int indexOf(Node<Item> node) {
        int index = 0;
        Node<Item> itemNode = this.sentinel.next;

        while(!(itemNode.equals(node))) {
            ++index;
            itemNode = itemNode.next;
        }

        return index;
    }

    public boolean contains(Node<Item> node) {
        Node<Item> itemNode = this.sentinel.next;
        boolean result = false;

        while(itemNode != this.sentinel) {
            if(itemNode.equals(node)) {
                result = true;
                break;
            }
            else
                itemNode = itemNode.next;
        }

        return result;
    }

    public boolean contains(Item item) {
        Node<Item> itemNode = this.sentinel.next;
        boolean result = false;

        while(itemNode != this.sentinel) {
            if(itemNode.element.equals(item)) {
                result = true;
                break;
            }
            else
                itemNode = itemNode.next;
        }

        return result;
    }

    public Item remove(int index) {
        Node<Item> toRemove = getNode(index);
        Node<Item> prevNode = toRemove.prev;
        Node<Item> nextNode = toRemove.next;

        prevNode.next = nextNode;
        nextNode.prev = prevNode;

        this.size--;

        return toRemove.element;
    }

    public boolean remove(Item item) {
        boolean result = false;

        if(this.contains(item)) {
            result = true;
            int index = indexOf(item);
            Item removed = remove(index);
        }

        return result;
    }

    public boolean removeAll(Collection <? extends Item> collection) {
        boolean result = false;

        ItemIterator iterator = new ItemIterator();
//        iterator.current = this.sentinel.next;

        while(iterator.hasNext()) {
            if(collection.contains(iterator.next())) {
                result = true;
                iterator.remove();
                this.size--;
            }
        }

        return result;
    }

    public boolean isEmpty() {
        return sentinel.next == sentinel;
    }

    public ListIterator<Item> listIterator(int index) {
        ItemIterator iterator = new ItemIterator();
        iterator.current = this.sentinel.next;
        int count = 0;

        while(count < index) {
            iterator.current = iterator.current.next;
            count++;
        }

        return iterator;
    }

    public ListIterator<Item> listIterator() {
        return new ItemIterator();
    }

    public Double getTotalPrice() {
        Double totalPrice = 0.0;
        ItemIterator iterator = new ItemIterator();
//        iterator.current = this.sentinel.next;

        while(iterator.hasNext()) {
            totalPrice = totalPrice + iterator.next().getPrice();
        }

        return totalPrice;
    }

    public void display() {
        ListIterator<Item> itr = listIterator();
        int count = 0;

        System.out.print("[");

        while(itr.hasNext()) {
            Item item = itr.next();
            ++count;
            System.out.print(item.getName() + ";" + item.getID() + ";" + item.getPrice());

            if(count != this.size)
                System.out.print(", ");
        }

        System.out.println("]");
    }

}
