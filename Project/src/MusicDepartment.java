public class MusicDepartment extends Department {
    public MusicDepartment() {
        super();
    }

    public MusicDepartment(String name, Integer ID) {
        super(name, ID);
    }

    public void accept(ShoppingCart visitor) {
        visitor.visit(this);
    }
}
