public class VideoDepartment extends Department {
    public VideoDepartment() {
        super();
    }

    public VideoDepartment(String name, Integer ID) {
        super(name, ID);
    }

    public void accept(ShoppingCart visitor) {
        visitor.visit(this);
    }
}
