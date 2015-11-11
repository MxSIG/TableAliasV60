/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.dto;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author INEGI
 */
public class EstratosW {

    private List lista;
    private List listaIndefinida;

    public EstratosW() {
        this.lista = new LinkedList();
    }

    public EstratosW(List lista) {
        this.lista = lista;
    }

   public void add2List(Object o){
       this.lista.add(o);
   }


    /**
     * @return the lista
     */
    public List getLista() {
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(List lista) {
        this.lista = lista;
    }

    public List getListaIndefinida() {
        return listaIndefinida;
    }

    public void setListaIndefinida(List listaIndefinida) {
        this.listaIndefinida = listaIndefinida;
    }
}
