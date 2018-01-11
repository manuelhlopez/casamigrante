/**
 * 
 */
package com.agt.client.db;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import com.agt.client.gui.DashboardGlobalInfo;
import com.agt.client.info.CasoPrevencionInfo;
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
public class CasoPrevencionDbActions {

	/*
	 * Método útil para guardar o editar un nuevo caso de desprotección...
	 */
	public boolean saveCasoPrevencion(CasoPrevencionInfo casoPrevencionInfo){
		try {

			EnvironmentConfig envConfig = new EnvironmentConfig();
			envConfig.setAllowCreate(true);
			envConfig.setTransactional(true);
			Environment env = new Environment(new File(DashboardGlobalInfo.getDbPaht()), envConfig);

			StoreConfig storeConfig = new StoreConfig();
			storeConfig.setAllowCreate(true);
			storeConfig.setTransactional(true);
			EntityStore store = new EntityStore(env, "casosPrevencion", storeConfig);
			PrimaryIndex<String,CasoPrevencionInfo> casoId =
					store.getPrimaryIndex(String.class, CasoPrevencionInfo.class);

			Transaction txn = env.beginTransaction(null, null);

			if(casoPrevencionInfo != null){
				casoId.put(casoPrevencionInfo);
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
	public ArrayList<CasoPrevencionInfo> getCurrentYearCases(){
		ArrayList<CasoPrevencionInfo> casosPrevencionInfoList = null;
		/* Open a transactional Berkeley DB engine environment. */
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);
		envConfig.setTransactional(true);
		Environment env = new Environment(new File(DashboardGlobalInfo.getDbPaht()), envConfig);

		StoreConfig storeConfig = new StoreConfig();
		storeConfig.setAllowCreate(true);
		storeConfig.setTransactional(true);
		EntityStore store =new EntityStore(env, "casosPrevencion", storeConfig);

		PrimaryIndex<String, CasoPrevencionInfo> casoId;
		SecondaryIndex<String, String, CasoPrevencionInfo> ano;

		casoId = store.getPrimaryIndex(String.class, CasoPrevencionInfo.class);
		ano = store.getSecondaryIndex(casoId, String.class, "ano");

		String currentAno = String.valueOf(TimeUtil.getCurrentYear());
		EntityCursor<CasoPrevencionInfo> empCursor = ano.subIndex(currentAno).entities();
		try {

			for (CasoPrevencionInfo emp : empCursor) {
				if(casosPrevencionInfoList == null){
					casosPrevencionInfoList = new ArrayList<CasoPrevencionInfo>();
					casosPrevencionInfoList.add(emp);
				}else{
					casosPrevencionInfoList.add(emp);
				}
				System.out.println(emp.getId());				
			}
			
		} finally {
			empCursor.close();
		}

		store.close();
		env.close();
		return casosPrevencionInfoList;
	}
	
	// Método para obtener casos por fechas
	public ArrayList<CasoPrevencionInfo> getCasosByDate(Date fechaInicial, Date fechaFinal){
		ArrayList<CasoPrevencionInfo> invoiceInfoList = null;
		/* Open a transactional Berkeley DB engine environment. */
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);
		envConfig.setTransactional(true);
		Environment env = new Environment(new File(DashboardGlobalInfo.getDbPaht()), envConfig);

		StoreConfig storeConfig = new StoreConfig();
		storeConfig.setAllowCreate(true);
		storeConfig.setTransactional(true);
		EntityStore store =new EntityStore(env, "casosPrevencion", storeConfig);

		PrimaryIndex<String, CasoPrevencionInfo> casoId;
		casoId = store.getPrimaryIndex(String.class, CasoPrevencionInfo.class);
		EntityCursor<CasoPrevencionInfo> empCursor = casoId.entities();
		try {
			for (CasoPrevencionInfo emp : empCursor) {
				String fecha = emp.getFecha();
				Date fechaRegistro = TimeUtil.getDateFormat(fecha);
				if(invoiceInfoList == null){
					invoiceInfoList = new ArrayList<CasoPrevencionInfo>();	
				}
				// La fecha inicial es la misma...
				if(fechaRegistro.compareTo(fechaInicial) == 0 || fechaRegistro.compareTo(fechaFinal) == 0){
					invoiceInfoList.add(emp);
				}else{
					if(fechaInicial.compareTo(fechaRegistro) < 0){
						if(fechaFinal.compareTo(fechaRegistro) > 0){
							invoiceInfoList.add(emp);
						}
					}	
				}
				
			}
		} finally {
			empCursor.close();
		}

		store.close();
		env.close();
		return invoiceInfoList;
	}
	
	// Método útil para obtener la lista de casos del año en curso...
	public ArrayList<CasoPrevencionInfo> getAllCases(){
		ArrayList<CasoPrevencionInfo> casosPrevencionInfoList = null;
		/* Open a transactional Berkeley DB engine environment. */
		try{
			EnvironmentConfig envConfig = new EnvironmentConfig();
			envConfig.setAllowCreate(true);
			envConfig.setTransactional(true);
			Environment env = new Environment(new File(DashboardGlobalInfo.getDbPaht()), envConfig);

			StoreConfig storeConfig = new StoreConfig();
			storeConfig.setAllowCreate(true);
			storeConfig.setTransactional(true);
			EntityStore store =new EntityStore(env, "casosPrevencion", storeConfig);

			PrimaryIndex<String, CasoPrevencionInfo> casoId;

			casoId = store.getPrimaryIndex(String.class, CasoPrevencionInfo.class);

			EntityCursor<CasoPrevencionInfo> empCursor = casoId.entities();
			try {

				for (CasoPrevencionInfo emp : empCursor) {
					if(casosPrevencionInfoList == null){
						casosPrevencionInfoList = new ArrayList<CasoPrevencionInfo>();
						casosPrevencionInfoList.add(emp);
					}else{
						casosPrevencionInfoList.add(emp);
					}
					System.out.println(emp.getId());				
				}

			} finally {
				empCursor.close();
			}

			store.close();
			env.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return casosPrevencionInfoList;
	}

}
