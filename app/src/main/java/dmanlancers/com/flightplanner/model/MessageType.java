package dmanlancers.com.flightplanner.model;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MessageType extends RealmObject {

    @PrimaryKey
    private int id;
    private String messageType;

    public MessageType() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
