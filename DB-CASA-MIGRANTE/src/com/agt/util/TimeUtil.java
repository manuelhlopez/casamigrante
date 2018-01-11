/*
 * TimeUtil.java
 *
 * Created on June 12, 2007, 2:03 AM
 *
 */

package com.agt.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author Marco Castillo
 */
public class TimeUtil
{

	public TimeUtil()
	{

	}

	public static String getMesEnLetras(int mes)
	{
		String mesFecha = "";
		switch (mes)
		{
			case 0:
				mesFecha = "ENERO";
				break;
			case 1:
				mesFecha = "FEBRERO";
				break;
			case 2:
				mesFecha = "MARZO";
				break;
			case 3:
				mesFecha = "ABRIL";
				break;
			case 4:
				mesFecha = "MAYO";
				break;
			case 5:
				mesFecha = "JUNIO";
				break;
			case 6:
				mesFecha = "JULIO";
				break;
			case 7:
				mesFecha = "AGOSTO";
				break;
			case 8:
				mesFecha = "SEPTIEMBRE";
				break;
			case 9:
				mesFecha = "OCTUBRE";
				break;
			case 10:
				mesFecha = "NOVIEMBRE";
				break;
			case 11:
				mesFecha = "DICIEMBRE";
				break;
		}
		return mesFecha;
	}

	public static Date getDate(Date currentDate, int hour, int minute)
	{
		Date newDate = new Date();
		if (currentDate != null)
		{
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(currentDate);
			calendar.set(Calendar.HOUR_OF_DAY, hour);
			calendar.set(Calendar.MINUTE, minute);
			newDate = calendar.getTime();
		}
		return newDate;
	}

	public static Date now()
	{
		Calendar rightNow = Calendar.getInstance();
		return rightNow.getTime();
	}

	public static String getTimeFormat(Date date)
	{
		if (date != null)
		{
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			String dateFormatted = format.format(date);
			return dateFormatted;
		}
		return "";
	}

	public static String getSybaseTimeFormat(Date date)
	{
		if (date != null)
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateFormatted = format.format(date);
			return dateFormatted;
		}
		return "";
	}

	public static String getTimeMinutos(Date date)
	{
		if (date != null)
		{
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			String MinuteFormatted = format.format(date);
			return MinuteFormatted;
		}
		return "00:00";
	}

	public static String getSimpleTimeFormat(Date date)
	{
		if (date != null)
		{
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			String dateFormatted = format.format(date);
			return dateFormatted;
		}
		return "";
	}

	/**
	 * Método para validar si la fecha es correcta
	 * 
	 * @param fechaInput
	 * @return
	 */
	public static boolean isDate(String date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date testDate = null;
		sdf.setLenient(false);
		if (date != null)
		{
			try
			{
				testDate = sdf.parse(date);
			}
			catch (Exception e)
			{
				return false;
			}
			return true;
		}
		else
		{
			return false;
		}

	}

	/**
	 * Método que me devuelve un tipo Date del parametro String recibido...
	 * 
	 * @param fecha
	 * @return
	 */
	public static Date getDateFormat(String fecha)
	{
		Date dateFormat = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			if (TimeUtil.isDate(fecha))
			{
				dateFormat = sdf.parse(fecha);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return dateFormat;
	}

	/**
	 * Método para validar si la fecha y hora es correcta
	 * 
	 * @param fechaInput
	 * @return
	 */
	public static boolean isDateTime(String date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date testDate = null;
		try
		{
			testDate = sdf.parse(date);
		}
		catch (Exception e)
		{
			return false;
		}
		if (!sdf.format(testDate).equals(date))
		{
			return false;
		}
		return true;
	}

	/**
	 * Método que me devuelve un tipo Date del parametro String dd/MM/yyyy HH:mm recibido...
	 * 
	 * @param fecha
	 * @return
	 */
	public static Date getDateTimeFormat(String fecha)
	{
		Date dateFormat = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try
		{
			if (TimeUtil.isDateTime(fecha))
			{
				dateFormat = sdf.parse(fecha);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return dateFormat;
	}

	public static boolean isLaterThanNow(String date)
	{

		if (!isDate(date))
			return true;

		Date fechaActual = new Date();
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		Date dateParameter;
		try
		{
			dateParameter = formatoFecha.parse(date);
			if (fechaActual.compareTo(dateParameter) < 0)
				return true;

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	/***
	 * Verifica si las fechas son validas, compara con la fecha actual cada una y verifica que la
	 * fecha inicial sea menor que la final
	 * 
	 * @param dateInit
	 *            fecha inicial
	 * @param dateEnd
	 *            fecha final
	 * @return mensaje de validaciòn
	 */
	public static String compareDatesString(String dateInit, String dateEnd)
	{

		if (!isDate(dateInit))
			return "La fecha inicial no es valida. ";

		if (!isDate(dateEnd))
			return "La fecha final no es valida. ";

		try
		{
			Date parameterDateInit = getDateFormat(dateInit);
			Date parameterDateEnd = getDateFormat(dateEnd);
			Date now = new Date();

			if (now.compareTo(parameterDateInit) < 0)
				return "Fecha inicial no puede ser mayor a la de hoy. ";

			if (now.compareTo(parameterDateEnd) < 0)
				return "Fecha final no puede ser mayor a la de hoy. ";

			if (parameterDateEnd.compareTo(parameterDateInit) < 0)
				return "Fecha inicial no puede ser mayor a Fecha final. ";

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	public static String getDateTimeFormat(Date date)
	{
		if (date != null)
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dateFormatted = format.format(date);
			return dateFormatted;
		}
		return "";
	}

	public static String getTimeMinutesFormat(Date date)
	{
		if (date != null)
		{
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			String MinuteFormatted = format.format(date);
			return MinuteFormatted;
		}
		return "00:00";
	}

	public static Date addMinutesToDate(Date date, int minutes)
	{
		Calendar calendarDate = Calendar.getInstance();
		calendarDate.setTime(date);
		calendarDate.add(Calendar.MINUTE, minutes);
		return calendarDate.getTime();
	}
	
	public static Date getPrimerDiaDelMes() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), 
                cal.get(Calendar.MONTH),
                cal.getActualMinimum(Calendar.DAY_OF_MONTH),
                cal.getMinimum(Calendar.HOUR_OF_DAY),
                cal.getMinimum(Calendar.MINUTE),
                cal.getMinimum(Calendar.SECOND));
        return cal.getTime();
    }

    public static Date getUltimoDiaDelMes() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.getActualMaximum(Calendar.DAY_OF_MONTH),
                cal.getMaximum(Calendar.HOUR_OF_DAY),
                cal.getMaximum(Calendar.MINUTE),
                cal.getMaximum(Calendar.SECOND));
        return cal.getTime();
    }
	
    public static int getCurrentYear(){
    	return Calendar.getInstance().get(Calendar.YEAR);
    }
	public static void main(String[] args)
	{
			 System.out.println("El primer día del mes es: "+getPrimerDiaDelMes());
        System.out.println("El último día del mes es: "+getUltimoDiaDelMes());
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.get(Calendar.YEAR));
		//Date date = TimeUtil.getDate(TimeUtil.now(), 10, 15);
		//Date datea = TimeUtil.now();
		// -->// System.out.println(datea);
		// -->// System.out.println(TimeUtil.getTimeFormat(TimeUtil.now()));
		// -->// System.out.println(TimeUtil.getSimpleTimeFormat(TimeUtil.now()));
	}
}
