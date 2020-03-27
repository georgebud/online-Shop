public class BookDepartment extends Department {
    public BookDepartment() {
        super();
    }

    public BookDepartment(String name, Integer ID) {
        super(name, ID);
    }

    public void accept(ShoppingCart visitor) {
        visitor.visit(this);
    }
}
