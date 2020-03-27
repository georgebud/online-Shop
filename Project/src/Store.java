import java.util.Vector;

public class Store {
    private static Store obj = null;
    public String name;
    public Vector<Department> departments;
    public Vector<Customer> customers;

    private Store() {
        departments = new Vector<>();
        customers = new Vector<>();
    }

    public static Store getInstance() {
        if(obj == null)
            obj = new Store();

        return obj;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void enter(Customer customer) {
        customers.add(customer);
    }

    public void exit(Customer customer) {
        if(customers.contains(customer))
            customers.remove(customer);
    }

    public ShoppingCart getShoppingCart(Double budget) {
        return new ShoppingCart(budget);
    }

    public Vector<Customer> getCustomers() {
        return this.customers;
    }

    public void displayCustomers() {
        for(Customer customer : customers)
            System.out.println(customer.getName() + ";" + customer.shoppingCart.getBudget());
    }

    public void getDepartments() {
        for(Department department : departments)
            System.out.print(department.name + " ");
        System.out.println();
    }

    public void addDepartment(Department department) {
        departments.add(department);
    }

    public Department getDepartment(Integer ID) {
        for(Department department : departments)
            if(department.getId().equals(ID))
//                System.out.println(department.name);
                return department;
        return null;
    }

    public Item getItem(Integer ID) {
        for(Department department : departments)
            for(Item item : department.items)
                if(item.getID().equals(ID))
                    return item;

        return null;
    }

    public Department getItemDepartment(Integer ID) {
        for(Department department : departments)
            for(Item item : department.items)
                if(item.getID().equals(ID))
                    return department;

        return null;
    }

    public Customer getCustomer(String name) {
        for(Customer customer : customers)
            if(customer.getName().equals(name))
                return customer;

        return null;
    }
}
