public interface Subject {
    void addObserver(Customer customer);
    void removeObserver(Customer customer);
    void notifyAllObservers(Notification notification);
}
