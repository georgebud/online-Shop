import java.util.ListIterator;

public class StrategyA implements Strategy {
    private Item cheapestItem;

    public StrategyA() {
        this.cheapestItem = null;
    }

    public Item execute(WishList wishList) {
        ListIterator<Item> iterator = wishList.listIterator();

        if(!wishList.isEmpty()) {
            this.cheapestItem = iterator.next();

            while(iterator.hasNext()) {
                Item item = iterator.next();

                if(this.cheapestItem.getPrice() > item.getPrice())
                    this.cheapestItem = item;
            }
        }

        System.out.println(this.cheapestItem.getName() + ";" +
                this.cheapestItem.getID() + ";" +
                this.cheapestItem.getPrice());

        return this.cheapestItem;
    }
}
