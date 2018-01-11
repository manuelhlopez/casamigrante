/**
 * 
 */
package com.agt.client.db;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import com.agt.client.gui.DashboardGlobalInfo;
import com.agt.client.info.CasoDesproteccionInfo;
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
public class CasoDesproteccionDbActions {

	/*
	 * Método útil para guardar o editar un nuevo caso de desprotección...
	 */
	public boolean saveCasoDesproteccion(CasoDesproteccionInfo casoDesproteccionInfo){
		try {

			EnvironmentConfig envConfig = new EnvironmentConfig();
			envConfig.setAllowCreate(true);
			envConfig.setTransactional(true);
			Environment env = new Environment(new File(DashboardGlobalInfo.getDbPaht()), envConfig);

			StoreConfig storeConfig = new StoreConfig();
			storeConfig.setAllowCreate(true);
			storeConfig.setTransactional(true);
			EntityStore store = new EntityStore(env, "casosDesproteccion", storeConfig);
			PrimaryIndex<String,CasoDesproteccionInfo> casoId =
					store.getPrimaryIndex(String.class, CasoDesproteccionInfo.class);

			Transaction txn = env.beginTransaction(null, null);

			if(casoDesproteccionInfo != null){
				casoId.put(casoDesproteccionInfo);
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
	
	/*
	 * Método útil para obtener el siguiente correlativo para el caso de desprotección...
	 */
	public Integer getCorrelativo(){
		Integer correlativo = null;
		/* Open a transactional Berkeley DB engine environment. */
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);
		envConfig.setTransactional(true);
		Environment env = new Environment(new File(DashboardGlobalInfo.getDbPaht()), envConfig);

		StoreConfig storeConfig = new StoreConfig();
		storeConfig.setAllowCreate(true);
		storeConfig.setTransactional(true);
		EntityStore store =new EntityStore(env, "casosDesproteccion", storeConfig);

		PrimaryIndex<String, CasoDesproteccionInfo> casoId;
		SecondaryIndex<String, String, CasoDesproteccionInfo> ano;

		casoId = store.getPrimaryIndex(String.class, CasoDesproteccionInfo.class);
		ano = store.getSecondaryIndex(casoId, String.class, "ano");
		
		String currentAno = String.valueOf(TimeUtil.getCurrentYear());
		EntityCursor<CasoDesproteccionInfo> empCursor = ano.subIndex(currentAno).entities();
		try {
			int count = 0;
			for (CasoDesproteccionInfo emp : empCursor) {
				 count++;
			}
			correlativo = count + 1;
		}
		catch(Exception e){
			e.printStackTrace();
		//	return 1;
		}
		finally {
			empCursor.close();
		}

		store.close();
		env.close();
		return correlativo;
	}
	
	// Método útil para obtener la lista de casos del año en curso...
	public ArrayList<CasoDesproteccionInfo> getCurrentYearCases(){
		ArrayList<CasoDesproteccionInfo> casosDesproteccionInfoList = null;
		/* Open a transactional Berkeley DB engine environment. */
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);
		envConfig.setTransactional(true);
		Environment env = new Environment(new File(DashboardGlobalInfo.getDbPaht()), envConfig);

		StoreConfig storeConfig = new StoreConfig();
		storeConfig.setAllowCreate(true);
		storeConfig.setTransactional(true);
		EntityStore store =new EntityStore(env, "casosDesproteccion", storeConfig);

		PrimaryIndex<String, CasoDesproteccionInfo> casoId;
		SecondaryIndex<String, String, CasoDesproteccionInfo> ano;

		casoId = store.getPrimaryIndex(String.class, CasoDesproteccionInfo.class);
		ano = store.getSecondaryIndex(casoId, String.class, "ano");

		String currentAno = String.valueOf(TimeUtil.getCurrentYear());
		EntityCursor<CasoDesproteccionInfo> empCursor = ano.subIndex(currentAno).entities();
		try {

			for (CasoDesproteccionInfo emp : empCursor) {
				if(casosDesproteccionInfoList == null){
					casosDesproteccionInfoList = new ArrayList<CasoDesproteccionInfo>();
					casosDesproteccionInfoList.add(emp);
				}else{
					casosDesproteccionInfoList.add(emp);
				}
				System.out.println(emp.getId());				
			}
			
		} finally {
			empCursor.close();
		}

		store.close();
		env.close();
		return casosDesproteccionInfoList;
	}
	
	// Método para obtener casos por fechas
	public ArrayList<CasoDesproteccionInfo> getCasosByDate(Date fechaInicial, Date fechaFinal){
		ArrayList<CasoDesproteccionInfo> invoiceInfoList = null;
		/* Open a transactional Berkeley DB engine environment. */
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);
		envConfig.setTransactional(true);
		Environment env = new Environment(new File(DashboardGlobalInfo.getDbPaht()), envConfig);

		StoreConfig storeConfig = new StoreConfig();
		storeConfig.setAllowCreate(true);
		storeConfig.setTransactional(true);
		EntityStore store =new EntityStore(env, "casosDesproteccion", storeConfig);

		PrimaryIndex<String, CasoDesproteccionInfo> casoId;
		casoId = store.getPrimaryIndex(String.class, CasoDesproteccionInfo.class);
		EntityCursor<CasoDesproteccionInfo> empCursor = casoId.entities();
		try {
			for (CasoDesproteccionInfo emp : empCursor) {
				String fecha = emp.getFecha();
				Date fechaRegistro = TimeUtil.getDateFormat(fecha);
				if(invoiceInfoList == null){
					invoiceInfoList = new ArrayList<CasoDesproteccionInfo>();	
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
		public ArrayList<CasoDesproteccionInfo> getAllCases(){
			ArrayList<CasoDesproteccionInfo> casosDesproteccionInfoList = null;
			/* Open a transactional Berkeley DB engine environment. */
			EnvironmentConfig envConfig = new EnvironmentConfig();
			envConfig.setAllowCreate(true);
			envConfig.setTransactional(true);
			Environment env = new Environment(new File(DashboardGlobalInfo.getDbPaht()), envConfig);

			StoreConfig storeConfig = new StoreConfig();
			storeConfig.setAllowCreate(true);
			storeConfig.setTransactional(true);
			EntityStore store =new EntityStore(env, "casosDesproteccion", storeConfig);

			PrimaryIndex<String, CasoDesproteccionInfo> casoId;

			casoId = store.getPrimaryIndex(String.class, CasoDesproteccionInfo.class);

			EntityCursor<CasoDesproteccionInfo> empCursor = casoId.entities();
			try {

				for (CasoDesproteccionInfo emp : empCursor) {
					if(casosDesproteccionInfoList == null){
						casosDesproteccionInfoList = new ArrayList<CasoDesproteccionInfo>();
						casosDesproteccionInfoList.add(emp);
					}else{
						casosDesproteccionInfoList.add(emp);
					}
					System.out.println(emp.getId());				
				}
				
			} finally {
				empCursor.close();
			}

			store.close();
			env.close();
			return casosDesproteccionInfoList;
		}

}
