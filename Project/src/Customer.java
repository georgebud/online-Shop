import java.util.Vector;

public class Customer implements Observer {
    private String name;
    public ShoppingCart shoppingCart;
    public WishList wishList;
    public Vector<Notification> notifications;

    public Customer(String name) {
        this.name = name;
        this.shoppingCart = new ShoppingCart();
        this.wishList = new WishList();
        this.notifications = new Vector<>();
    }

    public String getName() {
        return this.name;
    }

    public void update(Notification notification) {
        this.notifications.add(notification);
    }

    public void showNotifications() {
        System.out.print("[");

        for(int i = 0; i < this.notifications.size(); ++i) {
            System.out.print(notifications.get(i).type + ";" +
                    notifications.get(i).itemID + ";" +
                    notifications.get(i).departmentID);

            if(i != this.notifications.size() - 1)
                System.out.print(", ");
        }

        System.out.println("]");
    }
}
