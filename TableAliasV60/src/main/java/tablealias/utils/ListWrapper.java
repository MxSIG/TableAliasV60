/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.utils;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author INEGI
 */
public class ListWrapper {

	@JsonProperty
	private int totalFields;
	private List list;
	private transient String transErrorMsg;
	private String errorMsg;
	private String aliasUsuario;

	public ListWrapper(String errorMsg) {
		this.transErrorMsg = errorMsg;
	}

	/**
	 * @return the list
	 */
	public List getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List list) {
		if (list != null && list.size() > 0)
			totalFields = list.size();
		else
			errorMsg = transErrorMsg;
		this.list = list;
	}

	/**
	 * @return the aliasUsuario
	 */
	public String getAliasUsuario() {
		return aliasUsuario;
	}

	/**
	 * @param aliasUsuario
	 *            the aliasUsuario to set
	 */
	public void setAliasUsuario(String aliasUsuario) {
		this.aliasUsuario = aliasUsuario;
	}

}
