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
        airportCode.setAirportCode("PDL");
        mRealm.copyToRealmOrUpdate(airportCode);

        AirportCode airportCode1 = mRealm.createObject(AirportCode.class);
        airportCode1.setId(2);
        airportCode1.setAirportCode("PIX");
        mRealm.copyToRealmOrUpdate(airportCode1);

        AirportCode airportCode2 = mRealm.createObject(AirportCode.class);
        airportCode2.setId(3);
        airportCode2.setAirportCode("PRM");
        mRealm.copyToRealmOrUpdate(airportCode2);

        AirportCode airportCode3 = mRealm.createObject(AirportCode.class);
        airportCode3.setId(4);
        airportCode3.setAirportCode("PXO");
        mRealm.copyToRealmOrUpdate(airportCode3);

        AirportCode airportCode4 = mRealm.createObject(AirportCode.class);
        airportCode4.setId(5);
        airportCode4.setAirportCode("OPO");
        mRealm.copyToRealmOrUpdate(airportCode4);

        AirportCode airportCode5 = mRealm.createObject(AirportCode.class);
        airportCode5.setId(6);
        airportCode5.setAirportCode("LIS");
        mRealm.copyToRealmOrUpdate(airportCode5);

        AirportCode airportCode6 = mRealm.createObject(AirportCode.class);
        airportCode6.setId(7);
        airportCode6.setAirportCode("FAO");
        mRealm.copyToRealmOrUpdate(airportCode6);

        AirportCode airportCode7 = mRealm.createObject(AirportCode.class);
        airportCode7.setId(8);
        airportCode7.setAirportCode("FLW");
        mRealm.copyToRealmOrUpdate(airportCode7);

        AirportCode airportCode8 = mRealm.createObject(AirportCode.class);
        airportCode8.setId(9);
        airportCode8.setAirportCode("FNC");
        mRealm.copyToRealmOrUpdate(airportCode8);
        mRealm.commitTransaction();
    }

    public void createMessageTypeTable() {
        mRealm.beginTransaction();
        MessageType messageType = mRealm.createObject(MessageType.class);
        messageType.setId(1);
        messageType.setMessageType("DLA");
        mRealm.copyToRealmOrUpdate(messageType);

        MessageType messageType1 = mRealm.createObject(MessageType.class);
        messageType1.setId(2);
        messageType1.setMessageType("CHG");
        mRealm.copyToRealmOrUpdate(messageType1);

        MessageType messageType2 = mRealm.createObject(MessageType.class);
        messageType2.setId(3);
        messageType2.setMessageType("REQ");
        mRealm.copyToRealmOrUpdate(messageType2);
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
