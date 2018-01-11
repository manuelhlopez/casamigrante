/**
 * 
 */
package com.agt.client.db;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import com.agt.client.gui.DashboardGlobalInfo;
import com.agt.client.info.CasoDesproteccionSocialInfo;
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
public class CasoDesproteccionSocialDbActions {

	/*
	 * Método útil para guardar o editar un nuevo caso de desprotección...
	 */
	public boolean saveCasoDesproteccion(CasoDesproteccionSocialInfo casoDesproteccionSocialInfo){
		try {

			EnvironmentConfig envConfig = new EnvironmentConfig();
			envConfig.setAllowCreate(true);
			envConfig.setTransactional(true);
			Environment env = new Environment(new File(DashboardGlobalInfo.getDbPaht()), envConfig);

			StoreConfig storeConfig = new StoreConfig();
			storeConfig.setAllowCreate(true);
			storeConfig.setTransactional(true);
			EntityStore store = new EntityStore(env, "casosDesproteccionSocial", storeConfig);
			PrimaryIndex<String,CasoDesproteccionSocialInfo> casoId =
					store.getPrimaryIndex(String.class, CasoDesproteccionSocialInfo.class);

			Transaction txn = env.beginTransaction(null, null);

			if(casoDesproteccionSocialInfo != null){
				casoId.put(casoDesproteccionSocialInfo);
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
		EntityStore store =new EntityStore(env, "casosDesproteccionSocial", storeConfig);

		PrimaryIndex<String, CasoDesproteccionSocialInfo> casoId;
		SecondaryIndex<String, String, CasoDesproteccionSocialInfo> ano;

		casoId = store.getPrimaryIndex(String.class, CasoDesproteccionSocialInfo.class);
		ano = store.getSecondaryIndex(casoId, String.class, "ano");
		
		String currentAno = String.valueOf(TimeUtil.getCurrentYear());
		EntityCursor<CasoDesproteccionSocialInfo> empCursor = ano.subIndex(currentAno).entities();
		try {
			int count = 0;
			for (CasoDesproteccionSocialInfo emp : empCursor) {
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
	public ArrayList<CasoDesproteccionSocialInfo> getCurrentYearCases(){
		ArrayList<CasoDesproteccionSocialInfo> casosDesproteccionSocialInfoList = null;
		/* Open a transactional Berkeley DB engine environment. */
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);
		envConfig.setTransactional(true);
		Environment env = new Environment(new File(DashboardGlobalInfo.getDbPaht()), envConfig);

		StoreConfig storeConfig = new StoreConfig();
		storeConfig.setAllowCreate(true);
		storeConfig.setTransactional(true);
		EntityStore store =new EntityStore(env, "casosDesproteccionSocial", storeConfig);

		PrimaryIndex<String, CasoDesproteccionSocialInfo> casoId;
		SecondaryIndex<String, String, CasoDesproteccionSocialInfo> ano;

		casoId = store.getPrimaryIndex(String.class, CasoDesproteccionSocialInfo.class);
		ano = store.getSecondaryIndex(casoId, String.class, "ano");

		String currentAno = String.valueOf(TimeUtil.getCurrentYear());
		EntityCursor<CasoDesproteccionSocialInfo> empCursor = ano.subIndex(currentAno).entities();
		try {

			for (CasoDesproteccionSocialInfo emp : empCursor) {
				if(casosDesproteccionSocialInfoList == null){
					casosDesproteccionSocialInfoList = new ArrayList<CasoDesproteccionSocialInfo>();
					casosDesproteccionSocialInfoList.add(emp);
				}else{
					casosDesproteccionSocialInfoList.add(emp);
				}
				System.out.println(emp.getId());				
			}
			
		} finally {
			empCursor.close();
		}

		store.close();
		env.close();
		return casosDesproteccionSocialInfoList;
	}
	
	// Método para obtener casos por fechas
	public ArrayList<CasoDesproteccionSocialInfo> getCasosByDate(Date fechaInicial, Date fechaFinal){
		ArrayList<CasoDesproteccionSocialInfo> invoiceInfoList = null;
		/* Open a transactional Berkeley DB engine environment. */
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);
		envConfig.setTransactional(true);
		Environment env = new Environment(new File(DashboardGlobalInfo.getDbPaht()), envConfig);

		StoreConfig storeConfig = new StoreConfig();
		storeConfig.setAllowCreate(true);
		storeConfig.setTransactional(true);
		EntityStore store =new EntityStore(env, "casosDesproteccionSocial", storeConfig);

		PrimaryIndex<String, CasoDesproteccionSocialInfo> casoId;
		casoId = store.getPrimaryIndex(String.class, CasoDesproteccionSocialInfo.class);
		EntityCursor<CasoDesproteccionSocialInfo> empCursor = casoId.entities();
		try {
			for (CasoDesproteccionSocialInfo emp : empCursor) {
				String fecha = emp.getFecha();
				Date fechaRegistro = TimeUtil.getDateFormat(fecha);
				if(invoiceInfoList == null){
					invoiceInfoList = new ArrayList<CasoDesproteccionSocialInfo>();	
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
		public ArrayList<CasoDesproteccionSocialInfo> getAllCases(){
			ArrayList<CasoDesproteccionSocialInfo> casosDesproteccionSocialInfoList = null;
			/* Open a transactional Berkeley DB engine environment. */
			EnvironmentConfig envConfig = new EnvironmentConfig();
			envConfig.setAllowCreate(true);
			envConfig.setTransactional(true);
			Environment env = new Environment(new File(DashboardGlobalInfo.getDbPaht()), envConfig);

			StoreConfig storeConfig = new StoreConfig();
			storeConfig.setAllowCreate(true);
			storeConfig.setTransactional(true);
			EntityStore store =new EntityStore(env, "casosDesproteccionSocial", storeConfig);

			PrimaryIndex<String, CasoDesproteccionSocialInfo> casoId;

			casoId = store.getPrimaryIndex(String.class, CasoDesproteccionSocialInfo.class);

			EntityCursor<CasoDesproteccionSocialInfo> empCursor = casoId.entities();
			try {

				for (CasoDesproteccionSocialInfo emp : empCursor) {
					if(casosDesproteccionSocialInfoList == null){
						casosDesproteccionSocialInfoList = new ArrayList<CasoDesproteccionSocialInfo>();
						casosDesproteccionSocialInfoList.add(emp);
					}else{
						casosDesproteccionSocialInfoList.add(emp);
					}
					System.out.println(emp.getId());				
				}
				
			} finally {
				empCursor.close();
			}

			store.close();
			env.close();
			return casosDesproteccionSocialInfoList;
		}

}
