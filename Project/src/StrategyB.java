import java.util.ListIterator;

public class StrategyB implements Strategy {
    private Item firstItem;

    public StrategyB() {
        this.firstItem = null;
    }

    public Item execute(WishList wishList) {
        ListIterator<Item> iterator = wishList.listIterator();

        if(!wishList.isEmpty())
            this.firstItem = iterator.next();

        System.out.println(this.firstItem.getName() + ";" +
                this.firstItem.getID() + ";" +
                this.firstItem.getPrice());

        return firstItem;
    }
}
