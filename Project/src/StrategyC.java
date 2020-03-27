public class StrategyC implements Strategy {
    private Item recentlyAdded;

    public StrategyC() {
        this.recentlyAdded = null;
    }

    public Item execute(WishList wishList) {
        this.recentlyAdded = wishList.getLastItemAdded();

        System.out.println(this.recentlyAdded.getName() + ";" +
                this.recentlyAdded.getID() + ";" +
                this.recentlyAdded.getPrice());

        return recentlyAdded;
    }
}
