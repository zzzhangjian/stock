package cn.irss.stock.domain;

import java.io.Serializable;

import cn.irss.stock.core.ConstantProperties;

public class JsonResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 31964213438208504L;
	
	private String code;
	private String meassage;
	private Boolean success;
	private Object result;
	
	public JsonResult() {}
	
	public JsonResult(String code, String meassage, Boolean success, Object result) {
		this.code = code;
		this.meassage = meassage;
		this.success = success;
		this.result = result;
	}
	
	public void setError(String code,String meassage) {
		this.success = false;
		this.code = code;
		this.meassage = meassage;
	}
	
	public void setError(String meassage) {
		this.success = false;
		this.meassage = meassage;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMeassage() {
		return meassage;
	}
	public void setMeassage(String meassage) {
		this.meassage = meassage;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Object getReult() {
		return result;
	}
	public void setReult(Object result) {
		this.result = result;
	}
	
	public static JsonResult setOk(Object obj,String meassage) {
		JsonResult jsonResult = new JsonResult();
		jsonResult.setReult(obj);
		jsonResult.setSuccess(ConstantProperties.RESULT_OK_SUCCESS);
		jsonResult.setCode(ConstantProperties.RESULT_OK_CODE);
		jsonResult.setMeassage(meassage);
		return jsonResult;
	}
	@Override
	public String toString() {
		return "JsonResult [code=" + code + ", meassage=" + meassage + ", success=" + success + ", result=" + result
				+ "]";
	}
}
