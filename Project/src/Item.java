public class Item {
    private String name;
    private Integer ID;
    private Double price;

    public Item(String name, Integer ID, Double price) {
        this.name = name;
        this.ID = ID;
        this.price = price;
    }

    public Item(Item item) {
        this.name = item.getName();
        this.ID = item.getID();
        this.price = item.getPrice();
    }

    /*@Override
    public boolean equals(Object obj) {
        return this.getID().equals(((Item)obj).getID());
    }*/

    public String getName() {
        return name;
    }

    public Integer getID() {
        return ID;
    }

    public Double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
