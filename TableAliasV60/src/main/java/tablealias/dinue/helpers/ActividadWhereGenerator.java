/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.dinue.helpers;

/**
 *
 * @author INEGI
 */
class ActividadWhereGenerator implements WhereGenerator{
    private final Actividades activs;
    private boolean isSectorNull;
    private boolean isSubSectorNull;
    private boolean isRamaNull;
    private boolean isSubRamaNull;
    private boolean isClaseActNull;
    
    public ActividadWhereGenerator(Actividades acts) {
        this.activs = acts;
    }

    private String getSectorIn(){
        isSectorNull = true;
        StringBuilder sb = new StringBuilder();
        sb.append("SECTOR_ACTIVIDAD_ID in (");
        for(ActividadParser ap: activs){
            if(!ap.hasSubsector() && !ap.hasRama() && !ap.hasSubRama() && !ap.hasClaseAct() && ap.isValid()){
                isSectorNull = false;
                if (ap.getSector().equals("30")){
                    sb.append("'").append("31','32','33").append("',");
                }else if (ap.getSector().equals("47")){
                    sb.append("'").append("48','49").append("',");
                }else{
                    sb.append("'").append(ap.getSector()).append("',");
                }
            }
        }
        return sb.substring(0, sb.length() - 1) + ")";
    }

    private String getSubSectorIn(){
        isSubSectorNull = true;
        StringBuilder sb = new StringBuilder();
        sb.append("SUBSECTOR_ACTIVIDAD_ID in (");
        for(ActividadParser ap: activs){
            if(!ap.hasRama() && !ap.hasSubRama() && !ap.hasClaseAct() && ap.isValid() && ap.getSubSector() != null){
                isSubSectorNull = false;
                sb.append("'").append(ap.getSubSector()).append("',");
                //sb.append("'").append(ap.getSector()).append(ap.getSubSector()).append("',");
            }
        }
        return sb.substring(0, sb.length() - 1) + ")";
    }

    private String getRamaIn(){
        isRamaNull = true;
        StringBuilder sb = new StringBuilder();
        sb.append("RAMA_ACTIVIDAD_ID in (");
        for(ActividadParser ap: activs){
            if(!ap.hasSubRama() && !ap.hasClaseAct() && ap.isValid() && ap.getRama() != null){
                isRamaNull = false;
                sb.append("'").append(ap.getRama()).append("',");
                //sb.append("'").append(ap.getSector()).append(ap.getSubSector()).append(ap.getRama()).append("',");
            }
        }
        return sb.substring(0, sb.length() - 1) + ")";
    }

    private String getSubRamaIn(){
        isSubRamaNull = true;
        StringBuilder sb = new StringBuilder();
        sb.append("SUBRAMA_ACTIVIDAD_ID in (");
        for(ActividadParser ap: activs){
            if(!ap.hasClaseAct() && ap.isValid() && ap.getSubRama() != null){
                isSubRamaNull = false;
                sb.append("'").append(ap.getSubRama()).append("',");
                //sb.append("'").append(ap.getSector()).append(ap.getSubSector()).append(ap.getRama()).append(ap.getSubRama()).append("',");
            }
        }
        return sb.substring(0, sb.length() - 1) + ")";
    }

    private String getClaseActIn(){
        isClaseActNull = true;
        StringBuilder sb = new StringBuilder();
        sb.append("CLASE_ACTIVIDAD_ID in (");
        for(ActividadParser ap: activs){
            if(ap.hasClaseAct() && ap.isValid() && ap.getClaseAct() != null){
                isClaseActNull = false;
                //sb.append("'").append(ap.getSector()).append(ap.getSubSector()).append(ap.getRama()).append(ap.getSubRama()).append(ap.getClaseAct()).append("',");
                sb.append("'").append(ap.getClaseAct()).append("',");
            }
        }
        return sb.substring(0, sb.length() - 1) + ")";
    }

    public String getWhere(){
        String secs = getSectorIn();
        String subsecs = getSubSectorIn();
        String rama = getRamaIn();
        String subrama = getSubRamaIn();
        String claseAct = getClaseActIn();
        StringBuilder sb1 = new StringBuilder();
        int cont = 0;
        if(!isSectorNull){
            sb1.append(secs);
            cont = 1;
        }
        if (!isSubSectorNull) {
            if(cont == 1)
                sb1.append(" or ").append(subsecs);
            else{
                cont = 1;
                sb1.append(subsecs);
            }
        }
        if (!isRamaNull) {
            if(cont == 1)
                sb1.append(" or ").append(rama);
            else{
                cont = 1;
                sb1.append(rama);
            }
        }
        if (!isSubRamaNull) {
            if(cont == 1)
                sb1.append(" or ").append(subrama);
            else{
                cont = 1;
                sb1.append(subrama);
            }
        }
        if (!isClaseActNull) {
            if(cont == 1)
                sb1.append(" or ").append(claseAct);
            else{
                cont = 1;
                sb1.append(claseAct);
            }
        }
        /*StringBuilder sb = new StringBuilder();
        for (ParserSqlCreator psc : activs) {
            sb.append("(").append(psc.toSql()).append(")").append(" or ");
        }
        return "(" + sb.substring(0, sb.length() - 4) + ")";*/
        return "(" + sb1.toString() + ")";
        
    }



}
