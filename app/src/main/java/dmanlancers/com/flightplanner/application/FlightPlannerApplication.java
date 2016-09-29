package dmanlancers.com.flightplanner.application;


import android.app.Application;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import dmanlancers.com.flightplanner.managers.RealmManager;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class FlightPlannerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initStetho();
        initRealm();
        initData();
    }

    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
    }

    private void initRealm() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
    }

    private void initData() {
        //Example to create all the necessary tables
        RealmManager realmManager = new RealmManager();
        if (realmManager.getAllMessageType().size() == 0 && realmManager.getAllAirportCode().size() == 0) {
            realmManager.createAirportCodeTable();
            realmManager.createMessageTypeTable();
        }
    }
    }
