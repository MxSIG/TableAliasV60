/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.dto;

import java.io.Serializable;

/**
 *
 * @author INEGI
 */
public class BufferDto {

    //private int totalFields;
    private String data;
    private long id;

    public BufferDto(String data) {
        this.data = data;
    }

    public BufferDto(){
        
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }



}
