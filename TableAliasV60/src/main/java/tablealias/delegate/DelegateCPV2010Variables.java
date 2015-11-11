package tablealias.delegate;

import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.List;
import searchcp2010data.dto.DatosGeoCp;
import searchcp2010data.helpper.ParserFacade;
import tablealias.xmldata.Field;
import tablealias.xmldata.Server;
import tablealias.xmldata.Table;
import tablealias.xmldata.TableFields;

/**
 *
 * @author INEGI
 */
public class DelegateCPV2010Variables {

    Server server = null;
    private int cuantos;

    public DelegateCPV2010Variables(Server server) throws SQLException {
        this.server = server;
    }

    public String searchData(String criterio, Table table) throws SQLException {
        ParserFacade pf = new ParserFacade(server);
        List<DatosGeoCp> datosPoblacion = pf.getKeys(criterio);
        Table[] data = null;
        if (datosPoblacion != null && datosPoblacion.size() > 0) {
            data = new Table[datosPoblacion.size()];
            int i = 0;
            for (DatosGeoCp cp : datosPoblacion) {
                data[i] = (Table) table.clone();
                populateData(data[i], cp);
                i++;
            }
            cuantos=datosPoblacion.size();
        }
        return data == null ? "" :formatData(data, datosPoblacion.size());
    }

    private void populateData(Table t, DatosGeoCp cp) throws SQLException {
        TableFields fields = t.getFields();
        for (int x = 0; x < fields.size(); x++) {
            //for(Field f: fields){
            Field f = fields.get(x);
            if (f.isBusquedaDisplay()) {
                if (f.getAliasName().equalsIgnoreCase("tipo")) {
                    f.setValue("Indicador");
                } else if (f.getAliasName().equalsIgnoreCase("nombre")) {
                    f.setValue("(" + cp.getCampo() + ") " + cp.getIndicador() + " en (" + cp.getClave() + ") " + cp.getNombre());
                } else if (f.getName().equalsIgnoreCase("referencia")) {
                    f.setValue(cp.getValor());
                } else if (f.getAliasName().equalsIgnoreCase("_metadato")) {
                    f.setValue(cp.getCampo());
                } else if (f.getAliasName().equalsIgnoreCase("Ubicacion")) {
                    f.setValue(cp.getUbicacion());
                } else if (f.getAliasName().equalsIgnoreCase("_coordenada")) {
                    f.setValue(cp.getCoordenadas());
                }
            }
        }
    }

    private String formatData(Table[] data, int totalRecords) {
        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder();
        sb.append(gson.toJson(data));
        String aliasUsuario = data[0].getAliasUsuario();
        String jsonAdditionalData = String.format("\"totalFields\":\"%d\",%n \"typeFields\":\"%s\",%n \"aliasUsuario\":\"%s\",%n \"currentPage\":\"%d\", %n \"camposTotales\":\"%s\", %n \"esTotales\":\"%s\", ",
                totalRecords, "Indicador", aliasUsuario, 1, "", false);
        sb.insert(2, jsonAdditionalData);//"\"totalFields\":\""+totalRecords+"\",");
        return sb == null ? "" : sb.toString();
    }

    /**
     * @return the cuantos
     */
    public int getCuantos() {
        return cuantos;
    }
}
