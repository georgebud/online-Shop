import com.sun.org.apache.xpath.internal.operations.And;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Test {
    private Store store;

    public Test() {
        store = Store.getInstance();
    }

    public void initializeStore(String pathname) throws FileNotFoundException {
        File file = new File(pathname + "/store.txt");

        Scanner scan = new Scanner(file);
        String line = scan.nextLine();
        store.setName(line);

        Department department = null;

        while (scan.hasNextLine()) {
            line = scan.nextLine();
            StringTokenizer tokenizer = new StringTokenizer(line, ";");

            while (tokenizer.hasMoreTokens()) {
                line = tokenizer.nextToken();

                switch(line) {
                    case "BookDepartment":
                        department = new BookDepartment(line, Integer.parseInt(tokenizer.nextToken()));
                        line = scan.nextLine();
                        department.setSize(Integer.parseInt(line));
                        store.addDepartment(department);
                        break;
                    case "MusicDepartment":
                        department = new MusicDepartment(line, Integer.parseInt(tokenizer.nextToken()));
                        line = scan.nextLine();
                        department.setSize(Integer.parseInt(line));
                        store.addDepartment(department);
                        break;
                    case "SoftwareDepartment":
                        department = new SoftwareDepartment(line, Integer.parseInt(tokenizer.nextToken()));
                        line = scan.nextLine();
                        department.setSize(Integer.parseInt(line));
                        store.addDepartment(department);
                        break;
                    case "VideoDepartment":
                        department = new VideoDepartment(line, Integer.parseInt(tokenizer.nextToken()));
                        line = scan.nextLine();
                        department.setSize(Integer.parseInt(line));
                        store.addDepartment(department);
                        break;
                    default:
                        Item item = new Item(line,
                                Integer.parseInt(tokenizer.nextToken()),
                                Double.parseDouble(tokenizer.nextToken()));
                        if(department != null)
                            department.addItem(item);
                        break;
                }

            }
        }

//        store.getDepartments();
//        Department musicDepartment = store.getDepartment(2);
//        System.out.println(musicDepartment.getName() + "; " + musicDepartment.getId() + "\n");
//        musicDepartment.getItems();

        /*System.out.println(store.name);
        for(Department department1 : store.departments) {
            System.out.println(department1.getName() + ";" + department1.getId());
            System.out.println(department1.getSize());
            department1.getItems();
        }*/
    }

    public void initializeCustomers(String pathname) throws FileNotFoundException {
        File file = new File(pathname + "/customers.txt");
        Scanner scan = new Scanner(file);
        String line = scan.nextLine(); //number of customers, irrelevant
//        store.customers.setSize(Integer.parseInt(line));

        while(scan.hasNextLine()) {
            line = scan.nextLine();
            StringTokenizer tokenizer = new StringTokenizer(line, ";");

            while(tokenizer.hasMoreTokens()) {
                line = tokenizer.nextToken();
//                System.out.println(line + tokenizer.nextToken());
                Customer newCustomer = new Customer(line);
                newCustomer.shoppingCart = store.getShoppingCart(Double.parseDouble(tokenizer.nextToken()));
                String strategy = tokenizer.nextToken(); //strategy, not used (yet)

                switch(strategy) {
                    case "A":
                        newCustomer.wishList.setStrategy(new StrategyA());
                        break;

                    case "B":
                        newCustomer.wishList.setStrategy(new StrategyB());
                        break;

                    case "C":
                        newCustomer.wishList.setStrategy(new StrategyC());
                        break;

                    default:
                        break;
                }
                store.enter(newCustomer);
            }
        }
    }

    public void processEvents(String pathname) throws FileNotFoundException {
        File file = new File(pathname + "/events.txt");
        Scanner scan = new Scanner(file);

        String line = scan.nextLine();

        while(scan.hasNextLine()) {
            line = scan.nextLine();
            StringTokenizer tokenizer = new StringTokenizer(line, ";");

            while(tokenizer.hasMoreTokens()) {
                line = tokenizer.nextToken();
                Integer itemID;
                Customer customer;

                switch (line) {
                    case "addItem":
                        itemID = Integer.parseInt(tokenizer.nextToken());
                        if(tokenizer.nextToken().equals("ShoppingCart"))
                            addToShoppingCart(itemID, tokenizer.nextToken());
                        else
                            addToWishlist(itemID, tokenizer.nextToken());
                        break;

                    case "addProduct":
                        Integer departmentID = Integer.parseInt(tokenizer.nextToken());
                        itemID = Integer.parseInt(tokenizer.nextToken());
                        Double itemPrice = Double.parseDouble(tokenizer.nextToken());
                        addProduct(departmentID, itemID, itemPrice, tokenizer.nextToken());
                        break;

                    case "getItems":
                        if(tokenizer.nextToken().equals("ShoppingCart"))
                            getShoppingCart(tokenizer.nextToken());
                        else
                            getWishList(tokenizer.nextToken());
                        break;

                    case "delProduct":
                        delProduct(Integer.parseInt(tokenizer.nextToken()));
                        break;

                    case "delItem":
                        Item item = store.getItem(Integer.parseInt(tokenizer.nextToken()));
                        if(tokenizer.nextToken().equals("ShoppingCart"))
                            deleteItemFromShoppingCart(store.getCustomer(tokenizer.nextToken()), item);
                        else
                            deleteItemFromWishList(store.getCustomer(tokenizer.nextToken()), item,
                                    store.getItemDepartment(item.getID()));
                        break;

                    case "modifyProduct":
                        modifyProduct(Integer.parseInt(tokenizer.nextToken()),
                                Integer.parseInt(tokenizer.nextToken()),
                                Double.parseDouble(tokenizer.nextToken()));
                        break;

                    case "getTotal":
                        if(tokenizer.nextToken().equals("ShoppingCart"))
                            getShoppingCartTotal(tokenizer.nextToken());
                        else
                            getWishListTotal(tokenizer.nextToken());
                        break;

                    case "getObservers":
                        getObservers(Integer.parseInt(tokenizer.nextToken()));
                        break;

                    case "getNotifications":
                        customer = store.getCustomer(tokenizer.nextToken());
                        getNotifications(customer);
                        break;

                    case "accept":
                        Department department = store.getDepartment(Integer.parseInt(tokenizer.nextToken()));
                        customer = store.getCustomer(tokenizer.nextToken());
                        department.accept(customer.shoppingCart);
                        break;

                    case "getItem":
                        customer = store.getCustomer(tokenizer.nextToken());
                        Item itemStrategy = customer.wishList.executeStrategy();
                        addToShoppingCart(itemStrategy.getID(), customer.getName());
                        break;

                    default:
                        /*for(Customer customerDone : store.getCustomers())
                            store.exit(customerDone);
                        System.out.println("Magazinul " + store.name + " s-a inchis.");*/
                        break;
                }
            }
        }

        while(!store.getCustomers().isEmpty()) {
            Customer customer = store.getCustomers().get(0);
            store.exit(customer);
        }
        System.out.println("\n\tMagazinul " + store.name + " s-a inchis.");
    }

    public void delProduct(Integer itemID) {
        Item item = store.getItem(itemID);
        Department department = store.getItemDepartment(itemID);
        department.removeItem(item);
        department.setSize(department.getSize() - 1);

        for(Customer customer : store.getCustomers())
            if(customer.shoppingCart.contains(item))
                deleteItemFromShoppingCart(customer, item);
            else
                deleteItemFromWishList(customer, item, department);

        department.notifyAllObservers
                (new Notification(Notification.NotificationType.REMOVE, itemID, department.getId()));
    }

    public void modifyProduct(Integer departmentID, Integer itemID, Double newPrice) {
        Department department = store.getDepartment(departmentID);
        Item item = store.getItem(itemID);
        Item newItem = new Item(item);
        newItem.setPrice(newPrice);
        department.removeItem(item);
        department.addItem(newItem);

        for(Customer customer : store.getCustomers())
            if(customer.shoppingCart.contains(item) && department.observers.contains(customer)) {
                customer.shoppingCart.setBudget(customer.shoppingCart.getBudget() + item.getPrice() - newPrice);
                customer.shoppingCart.remove(item);
                customer.shoppingCart.add(newItem);
            }
            else
                if(customer.wishList.contains(item)) {
                    customer.wishList.remove(item);
                    customer.wishList.add(newItem);
                }

        department.notifyAllObservers
                (new Notification(Notification.NotificationType.MODIFY, itemID, departmentID));
    }

    public void addProduct(Integer departmentID, Integer itemID, Double itemPrice, String itemName) {
        Item item = new Item(itemName, itemID, itemPrice);
        Department department = store.getDepartment(departmentID);
        department.addItem(item);
        department.setSize(department.getSize() + 1);

        department.notifyAllObservers(new Notification(Notification.NotificationType.ADD, itemID, departmentID));
    }

    public void addToWishlist(Integer itemID, String customerName) {
        Item item = Store.getInstance().getItem(itemID);
        Department itemDepartment = Store.getInstance().getItemDepartment(itemID);
        Customer customer = Store.getInstance().getCustomer(customerName);

        customer.wishList.add(item);
        itemDepartment.addObserver(customer);
    }

    public void addToShoppingCart(Integer itemID, String customerName) {
        Item item = store.getItem(itemID);
        Department department = store.getItemDepartment(itemID);
        Customer customer = store.getCustomer(customerName);

        customer.shoppingCart.add(item);
        department.enter(customer);
        deleteItemFromWishList(customer, item, department);
    }

    public void deleteItemFromShoppingCart(Customer customer, Item item) {
        customer.shoppingCart.remove(item);
        customer.shoppingCart.setBudget(customer.shoppingCart.getBudget() + item.getPrice());
    }

    public void deleteItemFromWishList(Customer customer, Item item, Department department) {
        if(customer.wishList.contains(item)) {
            customer.wishList.remove(item);

            if(customer.wishList.isEmpty())
                department.removeObserver(customer);
            else {
                boolean result = true;
                for(Item itemDepartment : department.items)
                    if(customer.wishList.contains(itemDepartment))
                        result = false;
                if(result)
                    department.removeObserver(customer);
            }
        }
    }

    public void getShoppingCart(String customerName) {
        Customer customer = Store.getInstance().getCustomer(customerName);
        customer.shoppingCart.display();
    }

    public void getWishList(String customerName) {
        Customer customer = Store.getInstance().getCustomer(customerName);
        customer.wishList.display();
    }

    public void getShoppingCartTotal(String customerName) {
        Customer customer = store.getCustomer(customerName);

        System.out.println(customer.shoppingCart.getTotalPrice());
    }

    public void getWishListTotal(String customerName) {
        Customer customer = Store.getInstance().getCustomer(customerName);

        System.out.println(customer.wishList.getTotalPrice());
    }

    public void getObservers(Integer departmentID) {
        Department department = store.getDepartment(departmentID);
        department.getObservers();
    }

    public void getNotifications(Customer customer) {
        customer.showNotifications();
    }

    public static void main(String args[]) throws FileNotFoundException {

        Test test = new Test();
        test.initializeStore(args[0]);
        test.initializeCustomers(args[0]);
        test.processEvents(args[0]);

//        System.out.println();
//        test.store.getDepartments();
//        test.store.displayCustomers();
//
//        System.out.println();
//        for(Department department1 : test.store.departments) {
//            System.out.println(department1.getName() + ";" + department1.getId());
//            System.out.println(department1.getSize());
//            department1.getItems();
//        }

        /*test.store.getDepartments();
        test.store.displayCustomers();

        test.addToWishlist(104, "Andrei");
        test.addToWishlist(200, "Andrei");
        test.addToWishlist(403, "Andrei");

        test.addToWishlist(102, "Mihai");

        test.addToShoppingCart(105, "Andrei");
        test.addToShoppingCart(200, "Andrei");
        test.addToShoppingCart(402, "Andrei");

        test.getWishList("Andrei");
        test.getShoppingCart("Andrei");
        test.getWishListTotal("Andrei");
        test.getShoppingCartTotal("Andrei");

        test.getObservers(1);*/

//  test.store.getDepartment(1).getCustomers();


//        Department musicDepartment = store.getDepartment(2);
//        System.out.println(musicDepartment.getName() + "; " + musicDepartment.getId() + "\n");
//        musicDepartment.getItems();

        /*System.out.println(store.name);
        for(Department department1 : store.departments) {
            System.out.println(department1.getName() + ";" + department1.getId());
            System.out.println(department1.getSize());
            department1.getItems();
        }


        System.out.println("\n" + store.customers.size() + " customers entered store:");
        store.getCustomers();
        System.out.println();

        Customer Andrei = store.customers.get(3);
        Department musicDepartment = store.getDepartment(2);

        Andrei.shoppingCart.addAll(musicDepartment.items);
//        Andrei.shoppingCart.add(musicDepartment.items.get(3));
        Andrei.shoppingCart.display();

        System.out.println(Andrei.shoppingCart.getBudget());*/
    }
}
