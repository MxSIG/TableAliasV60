package tablealias.dinue.helpers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import mx.inegi.dtweb.connection.ConnectionManager;
import tablealias.xmldata.Server;
import tablealias.dto.ParamConsultaDto;
import tablealias.sqlworkers.helpers.TSearchConvierte;
import tablealias.utils.Validaciones;

/**
 *
 * @author INEGI
 */
public class DinueOptionalParams implements DinueSqlCreator {

    private Map<String, String> params;
    private Map<String, String> paramsDef;
    private final String NOMBRE_ESTABLECIMIENTO = "NOMBRE_ESTABLECIMIENTO_TS";
    private final String CALLE = "CALLE_TS";
    private final String COLONIA = "COLONIA_TS";
    private final String CODIGO_POSTAL = "CODIGO_POSTAL";
    private final String CENTRO_COMERCIAL = "CENTRO_COMERCIAL_TS";
    private final String NOM_PROPIETARIO_RAZON_SOCIAL = "NOM_PROPIETARIO_RAZON_SOCIAL_TS";
    private final String ACTIVIDAD_ESPECIFICA = "ACTIVIDAD_ESPECIFICA";

    private void setOptionalParams() {
        paramsDef.put(NOMBRE_ESTABLECIMIENTO, "nombre");
        paramsDef.put(CALLE, "calle");
        paramsDef.put(COLONIA, "colonia");
        paramsDef.put(CODIGO_POSTAL, "cp");
        paramsDef.put(CENTRO_COMERCIAL, "corredor");
        paramsDef.put(NOM_PROPIETARIO_RAZON_SOCIAL, "razonsocial");
        paramsDef.put(ACTIVIDAD_ESPECIFICA, "aespecifica");
    }

    private boolean isValid(String paramName, String value) {
        if (paramName.equalsIgnoreCase(CODIGO_POSTAL)) {
            return Validaciones.isNumber(value);
        } else {
            return value.trim().length() > 0;
        }
    }

    public DinueOptionalParams(HttpServletRequest request, Server srvr) throws SQLException {
        params = new LinkedHashMap<String, String>();
        paramsDef = new LinkedHashMap<String, String>();
        String vars = request.getParameter("vars");
        String aesp = request.getParameter("aespecifica");
        setOptionalParams();

        if (vars != null && vars.length() > 0) {
            Connection conn = ConnectionManager.getConnection(srvr);
            TSearchConvierte tsconv = new TSearchConvierte();
            Set<String> keys = paramsDef.keySet();
            Iterator<String> it = keys.iterator();
            String[] vals = vars.split("[|]");
            /*if (vars.indexOf(",", 2) > 1) {
                vals = vars.split(",");
            }*/
            for (int x = 0; x < vals.length; x++) {
                if (x < keys.size()) {
                    String paramName = it.next();
                    if (isValid(paramName, vals[x])) {

                        params.put(paramName, tsconv.processString(conn, vals[x], "convierte") ); //vals[x]);
                    }
                }
            }
            ConnectionManager.closeConnection(conn);
        }
        if(aesp!=null && !aesp.isEmpty() && !aesp.equalsIgnoreCase("0")){
            params.put(ACTIVIDAD_ESPECIFICA, aesp);
        }

        /*for (Map.Entry<String, String> en : paramsDef.entrySet()) {
        if (request.getParameter(en.getValue()) != null) {
        String value = request.getParameter(en.getValue()).trim();
        params.put(en.getKey(), value);
        }
        }*/
    }

    public void populateDto(ParamConsultaDto dto) {
        if (params.get(NOMBRE_ESTABLECIMIENTO) != null) {
            dto.setNombreEstablecimiento(params.get(NOMBRE_ESTABLECIMIENTO));
        }
        if (params.get(CALLE) != null) {
            dto.setCalle(params.get(CALLE));
        }
        if (params.get(COLONIA) != null) {
            dto.setColonia(params.get(COLONIA));
        }
        if (params.get(CODIGO_POSTAL) != null) {
            dto.setCodigoPostal(params.get(CODIGO_POSTAL));
        }
        if (params.get(CENTRO_COMERCIAL) != null) {
            dto.setCorredorIndustrial(params.get(CENTRO_COMERCIAL));
        }
        if (params.get(NOM_PROPIETARIO_RAZON_SOCIAL) != null) {
            dto.setRazonSocial(params.get(NOM_PROPIETARIO_RAZON_SOCIAL));
        }
        if (params.get(ACTIVIDAD_ESPECIFICA) != null) {
            dto.setActividadEspecifica(params.get(ACTIVIDAD_ESPECIFICA));
        }
    }

    /**
     * @return the params
     */
    public Map<String, String> getParams() {
        return params;
    }

    public String getSql() {
        return new OptionalParamsWhereGenerator(this).getWhere();
    }

    public boolean isEmpty() {
        return params.isEmpty();
    }
}
