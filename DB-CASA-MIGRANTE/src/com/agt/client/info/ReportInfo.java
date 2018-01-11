/**
 * 
 */
package com.agt.client.info;

/**
 * @author MLOPEZ
 *
 */
public class ReportInfo {
	private String id;
	private String label;
	private Double count = 0.0;
	
	public ReportInfo(){
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}
}
