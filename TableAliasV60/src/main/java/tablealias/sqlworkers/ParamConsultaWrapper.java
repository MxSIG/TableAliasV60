package tablealias.sqlworkers;

import dtweb.denue.dto.ActividadSearch;
import dtweb.denue.dto.GeoSearch;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.inegi.dtweb.connection.ConnectionManager;
import tablealias.xmldata.Server;
import mx.org.inegi.dtweb.denue.delegate.SearchDelegated;
import oracle.sql.TIMESTAMP;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import tablealias.dinue.helpers.Actividades;
import tablealias.dinue.helpers.AreasGeo;
import tablealias.dinue.helpers.DenuePK;
import tablealias.dinue.helpers.DinueOptionalParams;
import tablealias.dinue.helpers.Estratos;
import tablealias.dinue.helpers.EstratosEnum;
import tablealias.dto.DenueTable;
import tablealias.dto.ParamConsultaDto;
import tablealias.dto.ParamConsultaTotal;
import tablealias.sqlworkers.interfaces.ParamConsultaWrapperInterface;
import tablealias.utils.DenueWhereCreator;
import tablealias.utils.TablasServidor;

/**
 *
 * @author INEGI
 */
public class ParamConsultaWrapper implements ParamConsultaWrapperInterface{

    private final Server oracleServer;
    private final Server postgresServer;
    private ParamConsultaDto dto;
    private AreasGeo areasGeo;
    private Estratos estratos;
    private Actividades actividades;
    private String errorMsg;
    private DinueOptionalParams optParams;
    private DenuePK denuePK;
    private final String acts;
    private final String ag;
    private final String ests;
    private Integer totales;

    public ParamConsultaWrapper(TablasServidor tablas, String acts, String ag, String ests) {
        this.oracleServer = tablas.getServer("oracledenuepro"); // desarrollo : oracledenuedes ;  produccion: oracledenuepro
        this.postgresServer = tablas.getServer("servidorsote");
        this.acts = acts;
        this.ag = ag;
        this.ests = ests;
    }

    public String getErrorMsj() {
        return errorMsg;
    }

    private Object[] getParams(Integer totales, int idconsulta) throws SQLException {
        List l = new ArrayList();
        l.add(idconsulta);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sdf.format(new Date(System.currentTimeMillis()));
        TIMESTAMP ts = new oracle.sql.TIMESTAMP(date);
        l.add(ts);
        l.add(totales);
        l.add(dto.getUbicacionGeografica());
        l.add(dto.getActividadEconomica());
        l.add(dto.getEstratoPersonal());
        l.add(dto.getNombreEstablecimiento());
        l.add(dto.getCalle());
        l.add(dto.getColonia());
        l.add(dto.getCodigoPostal());
        l.add(dto.getCorredorIndustrial());
        l.add(dto.getRazonSocial());
        l.add(dto.getFiltro());
        l.add(dto.getActividadEspecifica());
        return l.toArray();
    }

    private void inserta(Connection conn, Integer totales, int idconsulta) throws SQLException {
        QueryRunner qr = new QueryRunner();
        String sql = "INSERT INTO DiNUE_DGG.param_consulta(id_consulta, FECHA, TOTAL_REGISTROS, UBICACION_GEOGRAFICA, ACTIVIDAD_ECONOMICA,"
                + " ESTRATO_PERSONAL, NOMBRE_ESTABLECIMIENTO, CALLE, COLONIA, CODIGO_POSTAL, CORREDOR_INDUSTRIAL, RAZON_SOCIAL, FILTRO, ACTIVIDAD_ESPECIAL ) "
                + "VALUES (?, ?,?,?,?,?,?,?,?,?,?,?,?,?)";
        qr.update(conn, sql, getParams(totales, idconsulta));
    }

    private Integer getTotales(Connection conn) throws SQLException {
        QueryRunner qr = new QueryRunner();
        String sqlWhere = DenueWhereCreator.getWhere(areasGeo, estratos, actividades, optParams);
        dto.setFiltro(sqlWhere.replaceAll("'", "''"));
        String sql = "select count(*) as total from "+DenueTable.getEsquema()+"."+DenueTable.getTabla()+" ";
        ParamConsultaTotal total = null;
        if (sqlWhere != null && sqlWhere.length() > 0) {
            sql = sql + " where " + sqlWhere;
            ResultSetHandler rsh = new BeanHandler(ParamConsultaTotal.class);
            total = (ParamConsultaTotal) qr.query(conn, sql, rsh);
        } else {
            total = new ParamConsultaTotal();
            total.setTotal(4374600);
        }
        return total.getTotal();
    }

    private DenuePK getDenuePK(Connection conn) {
        DenuePK denueDto = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT PARAMCONSULTA_SEQ.nextval from dual");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                denueDto = new DenuePK();
                denueDto.setId(new Integer(rs.getInt(1)));
                rs.close();
                stmt.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return denueDto;
    }

    private void setGeoDescription(Connection conn) throws Exception {
        SearchDelegated sd = new SearchDelegated(conn);
        StringBuilder sb = new StringBuilder();
        for (String s : ag.split(",")) {
            List<GeoSearch> list = sd.getGeoByClave(s);
            if (list != null && list.size() > 0) {
                GeoSearch as = list.get(0);
                sb.append("(").append(as.getClave()).append(") ").append(as.getDescripcion()).append("| ");
            }
        }
        sb.delete(sb.length() - 2, sb.length());
        dto.setUbicacionGeografica(sb.toString());
    }

    private void setActDescription(Connection conn) throws Exception {
        SearchDelegated sd = new SearchDelegated(conn);
        StringBuilder sb = new StringBuilder();
        for (String s : acts.split(",")) {
            if (s.equals("30")) {
                sb.append("(").append("31-33").append(") ").append("INDUSTRIAS MANUFACTURERAS").append("| ");
            }else if (s.equals("47")){
                sb.append("(").append("48-49").append(") ").append("TRANSPORTES, CORREOS Y ALMACENAMIENTO").append("| ");
            }else{
                List<ActividadSearch> list = sd.getActividades(s);
                if (list != null && list.size() > 0) {
                    ActividadSearch as = list.get(0);
                    sb.append(as.getDescripcion()).append("| ");
                }
            }
        }
        sb.delete(sb.length() - 2, sb.length());
        dto.setActividadEconomica(sb.toString());
    }

    private void setEstratosDescription(Connection conn) throws SQLException {
        SearchDelegated sd = new SearchDelegated(conn);
        StringBuilder sb = new StringBuilder();
        EstratosEnum[] ee1 = EstratosEnum.values();
        for (String s : ests.split(",")) {
            //EstratosEnum ee = EstratosEnum.valueOf(s);
            for (EstratosEnum e : ee1) {
                if (e.getId() == Integer.parseInt(s)) {
                    sb.append(e.getDescripcion()).append(" y ");
                    break;
                }
            }
        }
        sb.delete(sb.length() - 3, sb.length());
        dto.setEstratoPersonal(sb.toString());
    }

    public boolean save() throws Exception {
        boolean exito = false;
        dto = new ParamConsultaDto();

        postgresServer.setDbName("mdm6data");
        Connection postgresConn = ConnectionManager.getConnection(postgresServer);
        totales = null;
        if (postgresConn != null) {
            totales = getTotales(postgresConn);
        } else {
            errorMsg = "No se pudo establecer conexion a postgres " + postgresServer.getURL();
        }
        //oracleServer.setDbName("oracledenuepro");
        Connection oracleConn = ConnectionManager.getConnectionW(oracleServer);
        if (getTotales() > 0) {
            setActDescription(postgresConn);
            setGeoDescription(postgresConn);
            setEstratosDescription(postgresConn);
            optParams.populateDto(dto);
            if (oracleConn != null) {
                denuePK = getDenuePK(oracleConn);
                inserta(oracleConn, getTotales(), getDenuePK().getId());
                exito = true;
            } else {
                errorMsg = "No se pudo establecer conexion a oracle " + oracleServer.getURL();
            }
        } else {
            exito = false;
        }
        ConnectionManager.closeConnection(postgresConn);
        ConnectionManager.closeConnection(oracleConn);
        return exito;
    }

    /**
     * @return the dto
     */
    public ParamConsultaDto getDto() {
        return dto;


    }

    /**
     * @return the areasGeo
     */
    public AreasGeo getAreasGeo() {
        return areasGeo;


    }

    /**
     * @param areasGeo the areasGeo to set
     */
    public void setAreasGeo(AreasGeo areasGeo) {
        this.areasGeo = areasGeo;


    }

    /**
     * @return the estratos
     */
    public Estratos getEstratos() {
        return estratos;


    }

    /**
     * @param estratos the estratos to set
     */
    public void setEstratos(Estratos estratos) {
        this.estratos = estratos;


    }

    /**
     * @return the actividades
     */
    public Actividades getActividades() {
        return actividades;


    }

    /**
     * @param actividades the actividades to set
     */
    public void setActividades(Actividades actividades) {
        this.actividades = actividades;


    }

    /**
     * @return the optParams
     */
    public DinueOptionalParams getOptParams() {
        return optParams;


    }

    /**
     * @param optParams the optParams to set
     */
    public void setOptParams(DinueOptionalParams optParams) {
        this.optParams = optParams;


    }

    /**
     * @return the denuePK
     */
    public DenuePK getDenuePK() {
        return denuePK;

    }

    /**
     * @return the totales
     */
    public Integer getTotales() {
        return totales;
    }
}
