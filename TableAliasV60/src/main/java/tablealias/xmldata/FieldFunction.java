/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.xmldata;

/**
 *
 * @author INEGI
 */
public class FieldFunction {
    private String name;
    private int order;

    public FieldFunction(String name, String order){
        this.name = name;
        this.order = Integer.parseInt(order);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the order
     */
    public int getOrder() {
        return order;
    }

}
