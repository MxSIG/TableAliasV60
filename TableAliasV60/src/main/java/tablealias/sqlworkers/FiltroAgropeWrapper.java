package tablealias.sqlworkers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

/**
 *
 * @author INEGI
 */
public class FiltroAgropeWrapper {

    private static final String SQL_ID =
            //"SELECT last_value FROM censoagropecuario.filtroagro_id_seq";
            "SELECT nextval('censoagropecuario.filtroagro_id_seq')";
    private static final String SQL_INSERT =
            "INSERT INTO censoagropecuario.filtroagro(ID, AREASGEO,CLAVEINT,FILTRO,FILTROWHEN) VALUES (?, ?, ?, ?, ? )";
    private static final String SQL_UPDATE =
            "UPDATE censoagropecuario.filtroagro SET AREASGEO = ?,CLAVEINT = ?,FILTRO = ?, FILTROWHEN = ? WHERE ID = ?";
    private static final String SQL_SELECT =
            "SELECT claveint, color FROM censoagropecuario.tipos_productos_unicos";
    private static final Map< String, String > colorsMap;

    private Connection conn;

    static{
        colorsMap = new HashMap< String, String >();
    }

    public FiltroAgropeWrapper( Connection conn ) {
        this.conn = conn;
    }

    public static void setColorsMap( Connection connection ) throws SQLException {
        if( colorsMap.isEmpty() ){
            QueryRunner qr = new QueryRunner();
            synchronized( colorsMap ){
                qr.query( connection, SQL_SELECT, new ColorSetHandler() );
            }
        }
    }

    public Long escribeFiltro( String filtro ) throws SQLException{
        List< String > data = calculateData( filtro );
        Long[] result = null;
        QueryRunner qr = new QueryRunner();
        result = qr.query(conn, SQL_ID, new RSHimpl());
        Long id = result[ result.length - 1 ];
        qr.update(conn, SQL_INSERT, id, data.get( 0 ), data.get( 1 ), data.get(2), data.get( 3 ) );
        return id;
    }

    public Long actualizaFiltro(String filtroId, String filtro ) throws SQLException {
        Long id = Long.parseLong( filtroId );
        List< String > data = calculateData( filtro );
        QueryRunner qr = new QueryRunner();
        qr.update( conn, SQL_UPDATE, data.get( 0 ), data.get( 1 ), data.get( 2 ), data.get( 3 ), id );
        return id;
    }

    private List< String > calculateData( String filtro ){
        List< String > areasGeo = new ArrayList<String>();
        Set< String > clavesInt = new TreeSet<String>();
        Map< String, List < String > > filtroMap = new TreeMap< String, List< String > >();
        StringBuilder sb = new StringBuilder();
        String[] filtros = filtro.split( ";" );
        String areaGeo = null;
        for( String f : filtros ){
            areaGeo = f.split( "\\|" )[ 0 ];
            areasGeo.add( areaGeo );
            List< String > claves = Arrays.asList( f.split( "\\|" )[ 1 ].split( "," ) );
            clavesInt.addAll( claves );
            for( String cveCultivo : claves ){
                cveCultivo = "'" + cveCultivo + "'";
                if( filtroMap.get( cveCultivo ) == null ){
                    filtroMap.put( cveCultivo, new ArrayList< String >() );
                }
                List< String > cveEntList = filtroMap.get( cveCultivo );
                cveEntList.add( "'" + areaGeo + "'" );
                filtroMap.put( cveCultivo, cveEntList );
            }
            sb.append( "( CVE_ENT = '" ).append( areaGeo );
            sb.append( "' AND spvector @@ to_tsquery( 'spanish','" );
            sb.append( claves.get( 0 ) );
            for( int i = 1; i< claves.size(); i++ ){
                sb.append( "|" ).append( claves.get( i ) );
            }
            sb.append( "') ) OR " );
        }
        String filtroWhen = getFiltroWhen( filtroMap );
        sb.delete( sb.length() -4, sb.length() );
        List< String > data = new ArrayList< String >();
        data.add( areasGeo.toString().replace( "[", "" ).replace( "]", "" ).replace( " ", "" ) );
        data.add( clavesInt.toString().replace( "[", "" ).replace( "]", "" ).replace( " ", "" ) );
        data.add( sb.toString() );
        data.add( filtroWhen );
        return data;
    }

    private String getFiltroWhen( Map< String, List< String > > filtroMap ){
        StringBuilder sb = new StringBuilder();
        for( Map.Entry< String, List< String > > entry : filtroMap.entrySet() ){
            sb.append( "WHEN CVE_ENT IN(" );
            sb.append( entry.getValue().toString().replace( "[", "" ).replace( "]", "" ).replace( " ", "" ) );
            sb.append( ") AND spvector @@ plainto_tsquery('spanish',").append( entry.getKey() );
            sb.append( ") THEN " ).append( colorsMap.get( entry.getKey() ) ).append( " " );
        }
        return sb.toString();
    }

    private class RSHimpl implements ResultSetHandler<Long[]>{
        public Long[] handle(ResultSet rs) throws SQLException {
            if( !rs.next() ){
                return null;
            }
            int cols = rs.getMetaData().getColumnCount();
            Long[] result = new Long[ cols ];
            for( int i = 0; i < cols; i++ ){
                result[ i ] = (Long) rs.getObject( i + 1 );
            }
            return result;
        }

    }

    private static class ColorSetHandler implements ResultSetHandler< Map< String, String > >{

        public Map< String, String > handle(ResultSet rs) throws SQLException {
            while( rs.next() ){
                colorsMap.put( "'" + rs.getString( "claveint") + "'", "'" + rs.getString( "color" ) + "'" );
            }
            return colorsMap;
        }

    }
}
//http://almagesto.inegi.gob.mx/cgi-bin/mapserv.fcgi?map=/opt/map/mdm5vectorAgro.map&LAYERS=c0-199%2Cc102%2Cc100%2Cc101&TRANSPARENT=true&FORMAT=image%2Fpng&SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&STYLES=&EXCEPTIONS=application%2Fvnd.ogc.se_xml&SRS=EPSG%3A4326&DENUE=true&BBOX=-103.66872846678,21.525343762877,-101.07465360398,22.603506127729&WIDTH=1280&HEIGHT=532