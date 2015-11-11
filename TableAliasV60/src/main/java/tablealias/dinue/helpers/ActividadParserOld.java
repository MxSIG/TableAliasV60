/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.dinue.helpers;

import tablealias.exceptions.ActividadInvalidaException;



/**
 *
 * @author INEGI
 */
public class ActividadParserOld implements ParserSqlCreator{

    private String sector;
    private String subSector;
    private String rama;
    private String subRama;
    private String claseAct;
    private boolean hassubsector;
    private boolean hasrama;
    private boolean hassubrama;
    private boolean hasclaseAct;    

    public ActividadParserOld(String value) throws ActividadInvalidaException {
        if(value.length() >= 2){
            sector = value.substring(0, 2);
        }
        if(value.length() >= 5){
            subSector = value.substring(2, 5);
            hassubsector = true;
        }
        if(value.length() >= 9){
            rama = value.substring(5, 9);
            hasrama = true;
        }
        if(value.length() >= 14){
            subRama = value.substring(9, 14);
            hassubrama = true;
        }
        if(value.length() == 20){
            claseAct = value.substring(14);
            hasclaseAct = true;
        }
        isValid();
    }

    private void isValid() throws ActividadInvalidaException{
        boolean exito = true;
        if(sector == null || (hassubsector && subSector.length() != 3)
                || (hasrama && rama.length() !=4)
                || (hassubrama && subRama.length() != 5 )
                || (hasclaseAct && claseAct.length() != 6))
            exito = false;
        /*if(!exito)
            throw new ActividadInvalidaException(this);*/
    }

    @Override
    public String toString() {
        return String.format("sector = %s subSector = %s rama = %s subRama = %s claseAct = %s",
                sector,subSector,rama,subRama,claseAct);
    }



    /**
     * @return the sector
     */
    public String getSector() {
        return sector;
    }

    /**
     * @return the subSector
     */
    public String getSubSector() {
        return subSector;
    }

    /**
     * @return the rama
     */
    public String getRama() {
        return rama;
    }

    /**
     * @return the subRama
     */
    public String getSubRama() {
        return subRama;
    }

    /**
     * @return the claseAct
     */
    public String getClaseAct() {
        return claseAct;
    }

    /**
     * @return the hassubsector
     */
    public boolean hasSubsector() {
        return hassubsector;
    }

    /**
     * @return the hasrama
     */
    public boolean hasRama() {
        return hasrama;
    }

    /**
     * @return the hassubrama
     */
    public boolean hasSubRama() {
        return hassubrama;
    }

    /**
     * @return the hasclaseAct
     */
    public boolean hasClaseAct() {
        return hasclaseAct;
    }

    public String toSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("SECTOR_ACTIVIDAD_ID='").append(sector).append("' ");
        if(hassubsector)
            sb.append(" and ").append("SUBSECTOR_ACTIVIDAD_ID='").append(subSector).append("'");
        if(hasrama)
            sb.append(" and ").append("RAMA_ACTIVIDAD_ID='").append(rama).append("'");
        if(hassubrama)
            sb.append(" and ").append("SUBRAMA_ACTIVIDAD_ID='").append(subRama).append("'");
        if(hasclaseAct)
            sb.append(" and ").append("CLASE_ACTIVIDAD_ID='").append(claseAct).append("'");
        return sb.toString();
    }
}
