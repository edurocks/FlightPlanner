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
        mRealm.copyToRealmOrUpdate(airportCode);

        AirportCode airportCode1 = mRealm.createObject(AirportCode.class);
        airportCode1.setId(2);
        airportCode1.setAirportCode("FOB");
        mRealm.copyToRealmOrUpdate(airportCode1);
        mRealm.commitTransaction();
    }

    public void createMessageTypeTable() {
        mRealm.beginTransaction();
        MessageType messageType = mRealm.createObject(MessageType.class);
        messageType.setId(1);
        messageType.setMessageType("DEL");
        mRealm.copyToRealmOrUpdate(messageType);

        MessageType messageType1 = mRealm.createObject(MessageType.class);
        messageType1.setId(2);
        messageType1.setMessageType("CAT");
        mRealm.copyToRealmOrUpdate(messageType1);
        mRealm.commitTransaction();
    }

    public void createUsersTable() {
        mRealm.beginTransaction();
        Login login = mRealm.createObject(Login.class);
        login.setId(1);
        login.setUsername("Paulo");
        login.setPassword("123");
        login.setEmail("test@dmanlancers.pt");
        mRealm.copyToRealmOrUpdate(login);
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

    public RealmResults<Login> getAllUsers() {
        return mRealm.where(Login.class).findAll();
    }

}
