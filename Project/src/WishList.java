import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public class WishList extends ItemList implements Strategy{
    private Strategy strategy;
    private Item lastItemAdded;

    public WishList() {
        super();
        this.comparator = new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
    }

    public boolean add(Item element) {
        ItemIterator iterator = new ItemIterator();

        if(this.contains(element))
            return false;

        if(this.isEmpty()) { //adauga primul element in lista
            iterator.add(element);
        }
        else {
            while (iterator.hasNext()) {
                Item currentItem = iterator.next();
                if (this.comparator.compare(currentItem, element) >= 0) {
                    iterator.current = iterator.current.prev;
                    iterator.add(element);
                    break;
                }
            }
            if(iterator.current == sentinel) //adauga la final
                iterator.add(element);
        }
        this.setLastItemAdded(element);

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Item> c) {
        boolean result = false;

        for(Iterator<? extends Item> itr = c.iterator(); itr.hasNext();) {
            Item element = itr.next();

            if(!this.contains(element)) {
                result = true;
                add(element);
            }
        }

        return result;
    }

    public void setLastItemAdded(Item lastItemAdded) {
        this.lastItemAdded = lastItemAdded;
    }

    public Item getLastItemAdded() {
        return this.lastItemAdded;
    }

    public Strategy getStrategy() {
        return this.strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public Item execute(WishList wishList) {
        return this.strategy.execute(this);
    }

    public Item executeStrategy() {
        return this.getStrategy().execute(this);
    }
}
