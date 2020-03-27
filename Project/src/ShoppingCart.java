import java.util.*;

public class ShoppingCart extends ItemList implements Visitor {
    private Double budget;

    public ShoppingCart(Double budget) {
        super();
        this.budget = budget;
    }

    public ShoppingCart() {
        super();
    }

    @Override
    public boolean add(Item element) {
        boolean result = false;
        Double price = element.getPrice();
        ItemIterator iterator = new ItemIterator();

        if(price <= budget) {
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
            this.budget = this.budget - price;
            result = true;
        }
        return result;
    }

    @Override
    public boolean addAll(Collection<? extends Item> c) {
        boolean result = false;
        Double collectionPrice = 0.0;

        for(Iterator<? extends Item> itr = c.iterator(); itr.hasNext();) {
            Item element = itr.next();
            collectionPrice += element.getPrice();
        }

        if(collectionPrice > this.budget)
            return result;
        else {
            result = true;
            for(Iterator<? extends Item> itr = c.iterator(); itr.hasNext();) {
                Item element = itr.next();
                add(element);
            }
        }

        return result;
    }

    public Double getBudget() {
        return this.budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    @Override
    public void visit(BookDepartment bookDepartment) {
//        for(Item item : items) {
//            if(bookDepartment.getItems().contains(item))
//                item.setPrice(item.getPrice() - 0.1 * item.getPrice());
//        }

        ItemList.ItemIterator itr = new ItemList.ItemIterator();

        while(itr.hasNext()) {
            Item item = itr.next();
            if(bookDepartment.items.contains(item))
                item.setPrice(item.getPrice() - 0.1 * item.getPrice());
        }
    }

    @Override
    public void visit(MusicDepartment musicDepartment) {
        ItemList.ItemIterator itr = new ItemList.ItemIterator();
        Double totalPrice = 0.0;

        while(itr.hasNext()) {
            Item item = itr.next();
            if(musicDepartment.items.contains(item))
                totalPrice += item.getPrice();
        }

        this.budget += 0.1 * totalPrice;
    }

    @Override
    public void visit(SoftwareDepartment softwareDepartment) {
        ItemList.ItemIterator itr = new ItemList.ItemIterator();

        if(itr.current != sentinel) {
            Double smallestPrice = itr.next().getPrice();

            while(itr.hasNext()) {
                Item item = itr.next();
                if(smallestPrice > item.getPrice())
                    smallestPrice = item.getPrice();
            }

            if(this.budget < smallestPrice) {
                itr.current = sentinel.next;

                while(itr.hasNext()) {
                    Item item = itr.next();
                    if(softwareDepartment.items.contains(item))
                        item.setPrice(item.getPrice() - 0.2 * item.getPrice());
                }
            }
        }
    }

    @Override
    public void visit(VideoDepartment videoDepartment) {
        ItemList.ItemIterator itr = new ItemList.ItemIterator();
        Double totalPrice = 0.0;

        while(itr.hasNext()) {
            Item item = itr.next();
            if(videoDepartment.items.contains(item))
                totalPrice += item.getPrice();
        }

        Double biggestPrice = videoDepartment.items.get(0).getPrice();
        for(int i = 1; i < videoDepartment.items.size(); ++i) {
            if(biggestPrice < videoDepartment.items.get(i).getPrice())
                biggestPrice = videoDepartment.items.get(i).getPrice();
        }

        if(totalPrice > biggestPrice) {
            itr.current = sentinel.next;

            while(itr.hasNext()) {
                Item item = itr.next();
                if(videoDepartment.items.contains(item))
                    item.setPrice(item.getPrice() - 0.15 * item.getPrice());
            }
        }

        this.budget += 0.05 * totalPrice;
    }
}
