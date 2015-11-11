/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.dinue.helpers;

/**
 *
 * @author INEGI
 */
class AreaGeoWhereGenerator  implements WhereGenerator{
    private final AreasGeo areasGeo;
    private boolean isEntidadesNull;
    private boolean isMunicipiosNull;
    private boolean isLocalidadesNull;

    public AreaGeoWhereGenerator(AreasGeo areas) {
        this.areasGeo = areas;
    }
    
    private String getEntidadesIn() {
        isEntidadesNull = true;
        StringBuilder sb = new StringBuilder();
        sb.append("ENTIDAD_ID in (");
        for (AreaGeoParser agp : areasGeo) {
            if (!agp.hasMunicipio() && !agp.hasLocalidad() && agp.isValid()) {
                isEntidadesNull = false;
                sb.append("'").append(agp.getEstado()).append("',");
            }
        }
        return sb.substring(0, sb.length() - 1) + ")";

    }

    private String getMunicipiosIn() {
        isMunicipiosNull = true;        
            StringBuilder sb = new StringBuilder();
            sb.append("MUNICIPIO_ID in (");
            for (AreaGeoParser agp : areasGeo) {
                if (agp.hasMunicipio() && !agp.hasLocalidad() && agp.isValid()) {
                    isMunicipiosNull = false;
                    sb.append("'").append(agp.getEstado()).append(agp.getMunicipio()).append("',");
                }
            }
            return sb.substring(0, sb.length() - 1) + ")";        
    }

    private String getLocalidaesIn() {
        isLocalidadesNull = true;        
            StringBuilder sb = new StringBuilder();
            sb.append("LOCALIDAD_ID in (");
            for (AreaGeoParser agp : areasGeo) {
                if (agp.hasLocalidad() && agp.isValid()) {//&& !agp.hasLocalidad())
                    isLocalidadesNull = false;
                    sb.append("'").append(agp.getEstado()).append(agp.getMunicipio()).append(agp.getLocalidad()).append("',");
                }
            }
            return sb.substring(0, sb.length() - 1) + ")";        
    }

    public String getWhere(){        
        String ents = getEntidadesIn();
        String locs = getLocalidaesIn();
        String muns = getMunicipiosIn();
        StringBuilder sb1 = new StringBuilder();
        int cont = 0;
        if(!isEntidadesNull){
            sb1.append(ents);
            cont = 1;
        }
        if (!isMunicipiosNull) {
            if(cont == 1)
                sb1.append(" or ").append(muns);
            else{
                cont = 1;
                sb1.append(muns);
            }
        }
        if (!isLocalidadesNull) {
            if(cont == 1)
                sb1.append(" or ").append(locs);
            else
                sb1.append(locs);
        }
        //"sqlazo = " + sb1.toString());
        /*for (ParserSqlCreator psc : areasGeo) {
            sb.append("(").append(psc.toSql()).append(")").append(" or ");
        }
        return "(" + sb.substring(0, sb.length() - 4) + ")";*/
        return "(" + sb1.toString() + ")";

    }



}
