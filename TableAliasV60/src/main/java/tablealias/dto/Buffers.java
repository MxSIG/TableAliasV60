/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.dto;

import java.util.List;

/**
 *
 * @author INEGI
 */
public class Buffers {

    private int totalFields;
    private String aliasUsuario;
    private List<BufferDto> datos;

    /**
     * @return the datos
     */
    public List<BufferDto> getDatos() {
        return datos;
    }

    /**
     * @param datos the datos to set
     */
    public void setDatos(List<BufferDto> datos) {
        this.datos = datos;
        this.totalFields = datos.size();
    }

    /**
     * @return the aliasUsuario
     */
    public String getAliasUsuario() {
        return aliasUsuario;
    }

    /**
     * @param aliasUsuario the aliasUsuario to set
     */
    public void setAliasUsuario(String aliasUsuario) {
        this.aliasUsuario = aliasUsuario;
    }

    /**
     * @return the totalFields
     */    

}
