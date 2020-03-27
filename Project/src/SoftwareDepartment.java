public class SoftwareDepartment extends Department {
    public SoftwareDepartment() {
        super();
    }

    public SoftwareDepartment(String name, Integer ID) {
        super(name, ID);
    }

    public void accept(ShoppingCart visitor) {
        visitor.visit(this);
    }
}
