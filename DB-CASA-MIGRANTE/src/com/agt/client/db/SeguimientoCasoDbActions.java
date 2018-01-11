/**
 * 
 */
package com.agt.client.db;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import com.agt.client.gui.DashboardGlobalInfo;
import com.agt.client.info.CasoDesproteccionInfo;
import com.agt.client.info.SeguimientoCasoInfo;
import com.agt.client.info.SettingsInfo;
import com.agt.util.TimeUtil;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.Transaction;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;
import com.sleepycat.persist.StoreConfig;

/**
 * @author MLOPEZ
 *
 */
public class SeguimientoCasoDbActions {

	/*
	 * Método útil para guarder el nuevo evento del seguimiento...
	 */
	public boolean saveSeguimiento(SeguimientoCasoInfo seguimientoCasoInfo){
		try {

			EnvironmentConfig envConfig = new EnvironmentConfig();
			envConfig.setAllowCreate(true);
			envConfig.setTransactional(true);
			Environment env = new Environment(new File(DashboardGlobalInfo.getDbPaht()), envConfig);

			StoreConfig storeConfig = new StoreConfig();
			storeConfig.setAllowCreate(true);
			storeConfig.setTransactional(true);
			EntityStore store = new EntityStore(env, "casoSeguimiento", storeConfig);
			PrimaryIndex<String,SeguimientoCasoInfo> casoId =
					store.getPrimaryIndex(String.class, SeguimientoCasoInfo.class);

			Transaction txn = env.beginTransaction(null, null);

			if(seguimientoCasoInfo != null){
				casoId.put(seguimientoCasoInfo);
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

	// Método útil para obtener la lista de casos del año en curso...
	public ArrayList<SeguimientoCasoInfo> getEventosSeguimiento(String casoId){
		ArrayList<SeguimientoCasoInfo> seguimientoCasoInfoList = null;
		/* Open a transactional Berkeley DB engine environment. */
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);
		envConfig.setTransactional(true);
		Environment env = new Environment(new File(DashboardGlobalInfo.getDbPaht()), envConfig);

		StoreConfig storeConfig = new StoreConfig();
		storeConfig.setAllowCreate(true);
		storeConfig.setTransactional(true);
		EntityStore store =new EntityStore(env, "casoSeguimiento", storeConfig);

		PrimaryIndex<String, SeguimientoCasoInfo> id;
		SecondaryIndex<String, String, SeguimientoCasoInfo> caso;

		id = store.getPrimaryIndex(String.class, SeguimientoCasoInfo.class);
		caso = store.getSecondaryIndex(id, String.class, "casoId");
		try{
			EntityCursor<SeguimientoCasoInfo> empCursor = caso.subIndex(casoId).entities();
			try {

				for (SeguimientoCasoInfo emp : empCursor) {
					String status = emp.getStatus();
					if(status != null){
						if(status.equals("1")){
							if(seguimientoCasoInfoList == null){
								seguimientoCasoInfoList = new ArrayList<SeguimientoCasoInfo>();
								seguimientoCasoInfoList.add(emp);
							}else{
								seguimientoCasoInfoList.add(emp);
							}
							System.out.println(emp.getId());
						}
					}
				}

			} finally {
				empCursor.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		store.close();
		env.close();
		return seguimientoCasoInfoList;
	}
	
	// Método útil para obtener la lista de casos del año en curso...
	public ArrayList<SeguimientoCasoInfo> getAllEventos(){
		ArrayList<SeguimientoCasoInfo> seguimientoCasoInfoList = null;
		/* Open a transactional Berkeley DB engine environment. */
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);
		envConfig.setTransactional(true);
		Environment env = new Environment(new File(DashboardGlobalInfo.getDbPaht()), envConfig);

		StoreConfig storeConfig = new StoreConfig();
		storeConfig.setAllowCreate(true);
		storeConfig.setTransactional(true);
		EntityStore store =new EntityStore(env, "casoSeguimiento", storeConfig);

		PrimaryIndex<String, SeguimientoCasoInfo> id;

		id = store.getPrimaryIndex(String.class, SeguimientoCasoInfo.class);
		
		try{
			EntityCursor<SeguimientoCasoInfo> empCursor = id.entities();
			try {

				for (SeguimientoCasoInfo emp : empCursor) {
					String status = emp.getStatus();
					if(status != null){
						if(status.equals("1")){
							if(seguimientoCasoInfoList == null){
								seguimientoCasoInfoList = new ArrayList<SeguimientoCasoInfo>();
								seguimientoCasoInfoList.add(emp);
							}else{
								seguimientoCasoInfoList.add(emp);
							}
							System.out.println(emp.getId());
						}
					}
				}

			} finally {
				empCursor.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		store.close();
		env.close();
		return seguimientoCasoInfoList;
	}
}
