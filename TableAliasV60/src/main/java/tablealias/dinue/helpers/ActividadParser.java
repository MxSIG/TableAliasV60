/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.dinue.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import tablealias.actividadesareas.helpers.ActividadRowItem;
import tablealias.exceptions.ActividadInvalidaException;

/**
 *
 * @author INEGI
 */
public class ActividadParser implements ParserSqlCreator {

    private String sector;
    private String subSector;
    private String rama;
    private String subRama;
    private String claseAct;
    private boolean hassubsector;
    private boolean hasrama;
    private boolean hassubrama;
    private boolean hasclaseAct;
    //private final String pattern = "\\b(\\d{2})(\\d{3})(\\d{4})(\\d{5})(\\d{6})\\b|\\b(\\d{2})(\\d{3})(\\d{4})(\\d{5})\\b|\\b(\\d{2})(\\d{3})(\\d{4})\\b|\\b(\\d{2})(\\d{3})\\b|\\b(\\d{2})\\b";
    private final String pattern = "\\b(\\d{6})\\b|\\b(\\d{5})\\b|\\b(\\d{4})\\b|\\b(\\d{3})\\b|\\b(\\d{2})\\b";

    private boolean areGroupsNull(Matcher match, Integer... grupos) {
        int cont = 0;
        for (Integer i : grupos) {
            if (match.group(i) == null) {
                cont++;
            }
        }
        return cont == grupos.length;
    }

    private void setData(Matcher match) {
        if (areGroupsNull(match, 6, 10, 13, 15)) {
            sector = match.group(1);
            subSector = match.group(2);
            hassubsector = true;
            rama = match.group(3);
            hasrama = true;
            subRama = match.group(4);
            hassubrama = true;
            claseAct = match.group(5);
            hasclaseAct = true;
        } else if (areGroupsNull(match, 1, 10, 13, 15)) {
            sector = match.group(6);
            subSector = match.group(7);
            hassubsector = true;
            rama = match.group(8);
            hasrama = true;
            subRama = match.group(9);
            hassubrama = true;
        } else if (areGroupsNull(match, 1, 6, 13, 15)) {
            sector = match.group(10);
            subSector = match.group(11);
            hassubsector = true;
            rama = match.group(12);
            hasrama = true;
        } else if (areGroupsNull(match, 1, 6, 10, 15)) {
            sector = match.group(13);
            subSector = match.group(14);
            hassubsector = true;
        } else if (areGroupsNull(match, 1, 6, 10, 13)) {
            sector = match.group(15);
        }
    }

    public boolean isValid(){
        int secto = Integer.parseInt(sector);
        return secto != 9999;
    }

    public ActividadParser(String value) throws ActividadInvalidaException {
        Pattern regex = Pattern.compile(pattern, Pattern.DOTALL | Pattern.MULTILINE);
        Matcher match = regex.matcher(value);
        if (match.matches()) {
            if (value.length() == 2) {
                sector = value;
            }
            if (value.length() == 3) {
                sector = value.substring(0, 2);
                subSector = value;
                hassubsector = true;
            }
            if (value.length() == 4) {
                sector = value.substring(0, 2);
                subSector = value.substring(0, 3);
                hassubsector = true;
                rama = value;
                hasrama = true;
            }
            if (value.length() == 5) {
                sector = value.substring(0, 2);
                subSector = value.substring(0, 3);
                hassubsector = true;
                rama = value.substring(0, 4);
                hasrama = true;
                subRama = value;
                hassubrama = true;
            }
            if (value.length() == 6) {
                sector = value.substring(0, 2);
                subSector = value.substring(0, 3);
                hassubsector = true;
                rama = value.substring(0, 4);
                hasrama = true;
                subRama = value.substring(0, 5);
                hassubrama = true;
                claseAct = value;
                hasclaseAct = true;
            }
        } else {
            throw new ActividadInvalidaException(value);
        }
        //isValid();
    }

    /*private void isValid() throws ActividadInvalidaException {
        boolean exito = true;
        if (sector == null || (hassubsector && subSector.length() != 3)
                || (hasrama && rama.length() != 4)
                || (hassubrama && subRama.length() != 5)
                || (hasclaseAct && claseAct.length() != 6)) {
            exito = false;
        }
        if (!exito) {
            throw new ActividadInvalidaException(this);
        }
    }*/

    @Override
    public String toString() {
        return String.format("sector = %s subSector = %s rama = %s subRama = %s claseAct = %s",
                sector, subSector, rama, subRama, claseAct);
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
        if (hassubsector) {
            sb.append(" and ").append("SUBSECTOR_ACTIVIDAD_ID='").append(subSector).append("'");
        }
        if (hasrama) {
            sb.append(" and ").append("RAMA_ACTIVIDAD_ID='").append(rama).append("'");
        }
        if (hassubrama) {
            sb.append(" and ").append("SUBRAMA_ACTIVIDAD_ID='").append(subRama).append("'");
        }
        if (hasclaseAct) {
            sb.append(" and ").append("CLASE_ACTIVIDAD_ID='").append(claseAct).append("'");
        }
        return sb.toString();
    }

    public ActividadRowItem getRowItem() {
        String field = null;
        String val = null;

        ActividadRowItem ari = new ActividadRowItem();

        field = "SECTOR_ACTIVIDAD_ID";
        val = "'" + sector + "'";
        ari.setSqlField(field);
        ari.setSqlValue(val);
        ari.setGsonData("SECTOR ACTIVIDAD", sector);

        if (hassubsector) {
            field = "SUBSECTOR_ACTIVIDAD_ID";
            val = "'" + subSector + "'";
            ari.setSqlField(field);
            ari.setSqlValue(val);
            ari.setGsonData("SUBSECTOR ACTIVIDAD", subSector);
        }
        if (hasrama) {
            field = "RAMA_ACTIVIDAD_ID";
            val = "'" + rama + "'";//sector + subSector + rama;
            ari.setSqlField(field);
            ari.setSqlValue(val);
            ari.setGsonData("RAMA ACTIVIDAD", rama);
        }
        if (hassubrama) {
            field = "SUBRAMA_ACTIVIDAD_ID";
            val = "'" + subRama + "'"; //sector + subSector + rama + subRama;
            ari.setSqlField(field);
            ari.setSqlValue(val);
            ari.setGsonData("SUBRAMA ACTIVIDAD", subRama);

        }
        if (hasclaseAct) {
            field = "CLASE_ACTIVIDAD_ID";
            val = "'" + claseAct + "'";// sector + subSector + rama + subRama + claseAct;
            ari.setSqlField(field);
            ari.setSqlValue(val);
            ari.setGsonData("CLASE ACTIVIDAD", claseAct);
        }

        return ari;

        /*if (field != null && val != null) {
        return new ActividadRowItem(field, val);
        } else {
        return null;
        }*/
    }
}
