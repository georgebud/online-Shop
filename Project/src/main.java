import com.sun.org.apache.xpath.internal.operations.And;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class main {
    public static void main(String args[]) throws FileNotFoundException {
        File file = new File(args[0] + "/store.txt");

        Scanner scan = new Scanner(file);
        String line = scan.nextLine();
        Store store = new Store(line);
        Department department = new Department() {
            @Override
            public void accept(ShoppingCart shoppingCart) {
            }
        };

//        System.out.println("StoreName: " + store.name);

        while(scan.hasNextLine()) {
            line = scan.nextLine();
            StringTokenizer tokenizer = new StringTokenizer(line, ";\n");

            while(tokenizer.hasMoreTokens()) {
                //System.out.println(tokenizer.nextToken());
                line = tokenizer.nextToken();

                if(line.equals("BookDepartment") ||
                        line.equals("MusicDepartment") ||
                        line.equals("SoftwareDepartment") ||
                        line.equals("VideoDepartment")) {
                    department = new Department(line, Integer.parseInt(tokenizer.nextToken())) {
                        @Override
                        public void accept(ShoppingCart shoppingCart) {
                        }
                    };

                    line = scan.nextLine();
                    department.setSize(Integer.parseInt(line));
                    store.addDepartment(department);
                }
                else {
                    Item item = new Item(line,
                            Integer.parseInt(tokenizer.nextToken()),
                            Double.parseDouble(tokenizer.nextToken()));

                    department.addItem(item);
                }
            }
        }

//        store.getDepartments();
//        Department musicDepartment = store.getDepartment(2);
//        System.out.println(musicDepartment.getName() + "; " + musicDepartment.getId() + "\n");
//        musicDepartment.getItems();

        System.out.println(store.name);
        for(Department department1 : store.departments) {
            System.out.println(department1.getName() + ";" + department1.getId());
            System.out.println(department1.getSize());
            department1.getItems();
        }


        file = new File(args[0] + "/customers.txt");
        scan = new Scanner(file);
        line = scan.nextLine(); //number of customers, irrelevant
//        store.customers.setSize(Integer.parseInt(line));

        while(scan.hasNextLine()) {
            line = scan.nextLine();
            StringTokenizer tokenizer = new StringTokenizer(line, ";");

            while(tokenizer.hasMoreTokens()) {
                line = tokenizer.nextToken();
//                System.out.println(line + tokenizer.nextToken());
                Customer newCustomer = new Customer(line);
                newCustomer.shoppingCart = store.getShoppingCart(Double.parseDouble(tokenizer.nextToken()));
                store.enter(newCustomer);
                tokenizer.nextToken(); //strategy, not used (yet)
            }
        }

        System.out.println("\n" + store.customers.size() + " customers entered store:");
        store.getCustomers();
        System.out.println();

        Customer Andrei = store.customers.get(3);
        Department musicDepartment = store.getDepartment(2);

        Andrei.shoppingCart.addAll(musicDepartment.items);
//        Andrei.shoppingCart.add(musicDepartment.items.get(3));
        Andrei.shoppingCart.display();

        System.out.println(Andrei.shoppingCart.getBudget());
    }
}
