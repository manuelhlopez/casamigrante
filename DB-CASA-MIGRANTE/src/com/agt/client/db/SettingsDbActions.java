/**
 * 
 */
package com.agt.client.db;

import java.io.File;

import com.agt.client.gui.DashboardGlobalInfo;
import com.agt.client.info.SettingsInfo;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.Transaction;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

/**
 * @author MLOPEZ
 *
 */
public class SettingsDbActions {

	public SettingsInfo getSettingsInfo(){
		Integer id = 1;
		SettingsInfo settingsInfo = null;
		try{
			/* Open a transactional Berkeley DB engine environment. */
			EnvironmentConfig envConfig = new EnvironmentConfig();
			envConfig.setAllowCreate(true);
			envConfig.setTransactional(true);
			Environment env = new Environment(new File(DashboardGlobalInfo.getDbPaht()), envConfig);

			StoreConfig storeConfig = new StoreConfig();
			storeConfig.setAllowCreate(true);
			storeConfig.setTransactional(true);
			EntityStore store = new EntityStore(env, "settings", storeConfig);

			PrimaryIndex<Integer, SettingsInfo> settingsId;

			settingsId = store.getPrimaryIndex(Integer.class, SettingsInfo.class);

			settingsInfo = settingsId.get(id);

			store.close();
			env.close();

		}catch(Exception e){
			e.printStackTrace();
		}
		return settingsInfo;
	}

	public boolean saveSettings(SettingsInfo settingsInfo){
		try {

			EnvironmentConfig envConfig = new EnvironmentConfig();
			envConfig.setAllowCreate(true);
			envConfig.setTransactional(true);
			Environment env = new Environment(new File(DashboardGlobalInfo.getDbPaht()), envConfig);

			StoreConfig storeConfig = new StoreConfig();
			storeConfig.setAllowCreate(true);
			storeConfig.setTransactional(true);
			EntityStore store = new EntityStore(env, "settings", storeConfig);
			PrimaryIndex<Integer,SettingsInfo> settingsId =
					store.getPrimaryIndex(Integer.class, SettingsInfo.class);

			Transaction txn = env.beginTransaction(null, null);

			if(settingsInfo != null){
				settingsId.put(settingsInfo);
			}
			txn.commit();
			store.close();
			env.close();
			return true;
		} catch (Exception dbe) {
			dbe.printStackTrace();
			return false;
		}
	}

}
