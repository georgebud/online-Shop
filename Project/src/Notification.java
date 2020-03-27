import java.util.Date;

public class Notification {
    public Date date;
    public Integer departmentID, itemID;
    public NotificationType type;

    public enum NotificationType {
        ADD, REMOVE, MODIFY;
    }

    public Notification(NotificationType type, Integer itemID, Integer departmentID) {
        this.type = type;
        this.itemID = itemID;
        this.departmentID = departmentID;
    }

}
