package dmanlancers.com.flightplanner.managers;

import dmanlancers.com.flightplanner.model.AirportCode;
import dmanlancers.com.flightplanner.model.Login;
import dmanlancers.com.flightplanner.model.MessageType;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmManager {

    private Realm mRealm;

    public RealmManager() {
        mRealm = Realm.getDefaultInstance();
    }

    //Example Names
    public void createAirportCodeTable() {
        mRealm.beginTransaction();
        AirportCode airportCode = mRealm.createObject(AirportCode.class);
        airportCode.setId(1);
        airportCode.setAirportCode("FAB");
        mRealm.copyToRealm(airportCode);

        AirportCode airportCode1 = mRealm.createObject(AirportCode.class);
        airportCode1.setId(2);
        airportCode1.setAirportCode("FOB");
        mRealm.copyToRealm(airportCode1);
        mRealm.commitTransaction();
    }

    public void createMessageTypeTable() {
        mRealm.beginTransaction();
        MessageType messageType = mRealm.createObject(MessageType.class);
        messageType.setId(1);
        messageType.setMessageType("DEL");
        mRealm.copyToRealm(messageType);

        MessageType messageType1 = mRealm.createObject(MessageType.class);
        messageType1.setId(2);
        messageType1.setMessageType("CAT");
        mRealm.copyToRealm(messageType1);
        mRealm.commitTransaction();
    }

    public void doLogin(int id, String username, String password) {
        Login login = mRealm.createObject(Login.class);
        login.setId(id);
        login.setUsername(username);
        login.setPassword(password);
        mRealm.copyToRealm(login);
        mRealm.commitTransaction();

    }

    public Realm getRealm() {
        return mRealm;
    }

    public RealmResults<AirportCode> getAllAirportCode() {
        return mRealm.where(AirportCode.class).findAll();
    }

    public RealmResults<MessageType> getAllMessageType() {
        return mRealm.where(MessageType.class).findAll();
    }
}
