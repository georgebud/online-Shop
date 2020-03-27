import java.util.Vector;

public abstract class Department implements Subject {
    protected String name;
    protected Integer ID;
    protected Vector<Item> items;
    protected Vector<Customer> customersWhoBought;
    protected Vector<Customer> observers;
    protected int size;

    public Department() {
        items = new Vector<>();
        customersWhoBought = new Vector<>();
        observers = new Vector<>();
    }

    public Department(String name, Integer ID) {
        this();
        this.name = name;
        this.ID = ID;
    }

    public void getCustomers() {
        int count = 0;

        System.out.print("[");

        for(Customer customer : customersWhoBought) {
            ++count;
            System.out.print(customer.getName());

            if(count != customersWhoBought.size())
                System.out.print(", ");
        }
        System.out.println("]");
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void getObservers() {
        int count = 0;

        System.out.print("[");

        for(Customer customer : observers) {
            ++count;
            System.out.print(customer.getName());

            if(count != observers.size())
                System.out.print(", ");
        }
        System.out.println("]");
    }

    public void enter(Customer customer) {
        customersWhoBought.add(customer);
    }

    public void exit(Customer customer) {
        if(customersWhoBought.contains(customer))
            customersWhoBought.remove(customer);
    }

    public Integer getId() {
        return this.ID;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        if(items.contains(item))
            items.remove(item);
    }

    public void getItems() {
        int i = 0;
        System.out.print("[");

        for(i = 0; i < items.size() - 1; ++i) {
            System.out.print(items.get(i).getName() + ";" +
                    items.get(i).getID() + ";" +
                    items.get(i).getPrice());
            System.out.println(", ");
        }

        if(items.size() > 0)
            System.out.print(items.get(i).getName() + ";" +
                    items.get(i).getID() + ";" +
                    items.get(i).getPrice());

        System.out.println("]");
    }

    public void addObserver(Customer customer) {
        if(!customer.wishList.isEmpty() && !observers.contains(customer))
            observers.add(customer);
    }

    public void removeObserver(Customer customer) {
        if(customer.wishList.isEmpty() || observers.contains(customer))
            observers.remove(customer);
    }

    public void notifyAllObservers(Notification notification) {
        for(Customer observer : observers) {
            observer.update(notification);
        }
    }

    public abstract void accept(ShoppingCart shoppingCart);
}
