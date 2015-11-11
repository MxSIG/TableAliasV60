/**
 * 
 */
package tablealias.dao.impl;

import java.sql.Connection;

import mx.inegi.dtweb.connection.ConnectionManager;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.stereotype.Repository;

import tablealias.dao.ImssDao;
import tablealias.dto.FrenteManzana;
import tablealias.dto.NumeroExterior;
import tablealias.xmldata.Server;

/**
 * @author INEGI
 *
 */
@Repository
public class ImssDaoImpl implements ImssDao {

	@Override
	public FrenteManzana findFrenteManzana(String point, Server server)
			throws Exception {
		String sql = "select nomvial as vialidad, tipovial as tipoVialidad, nomref1 as entrevialidad1, nomref2 as entrevialidad2,"
				+ " tipovr1 as tipoEntrevialidad1, tipovr2 as tipoEntrevialidad2, nombre_loc as localidad, cve_loc as cveloc,"
				+ " nombre_mun as municipio, cve_mun as cvemun, nombre_ent as entidad, nomasen as asentamiento, cp as codigoPostal,"
				+ " ST_AsText(ST_Line_Interpolate_Point(ST_LineMerge(the_geom), ST_Line_Locate_Point(ST_LineMerge(the_geom), ST_GeomFromText(?, 900913)))) as punto,"
				+ " ST_Distance(ST_GeomFromText(?, 900913), the_geom) as distance"
				+ " from localidadesmzn.frentesdemanzana_mgn"
				+ " where ST_DWithin(ST_GeomFromText(?, 900913), the_geom, 40)"
				+ " order by distance" + " limit 1";
		Connection conn = ConnectionManager.getConnectionW(server);
		ResultSetHandler<FrenteManzana> rsh = new BeanHandler<FrenteManzana>(
				FrenteManzana.class);
		QueryRunner qr = new QueryRunner();
		FrenteManzana fm = null;
		try {
			fm = qr.query(conn, sql, rsh, point, point, point);
		} finally {
			ConnectionManager.closeConnection(conn);
		}
		return fm;
	}

	@Override
	public NumeroExterior findNumeroExterior(String point, Server server)
			throws Exception {
		String sql = "select numextnum as numeroExterior, numextalf as numeroExteriorAlf, numextant as numeroAnterior,"
				+ " nomvial as vialidad, tipovial as tipoVialidad, nomref1 as entrevialidad1, nomref2 as entrevialidad2,"
				+ " tipovr1 as tipoEntrevialidad1, tipovr2 as tipoEntrevialidad2, nombre_loc as localidad, cve_loc as cveloc,"
				+ " nombre_mun as municipio, cve_mun as cvemun, nombre_ent as entidad, nomasen as asentamiento, cp as codigoPostal,"
				+ " ST_Distance(ST_GeomFromText(?, 900913), the_geom) as distance,"
				+ " ST_AsText(the_geom) as punto"
				+ " from localidadesmzn.numeroexteriorurbano"
				+ " where ST_DWithin(ST_GeomFromText(?, 900913), the_geom, 40)"
				+ " order by distance" + " limit 1";
		Connection conn = ConnectionManager.getConnectionW(server);
		ResultSetHandler<NumeroExterior> rsh = new BeanHandler<NumeroExterior>(
				NumeroExterior.class);
		QueryRunner qr = new QueryRunner();
		NumeroExterior ne = null;
		try {
			ne = qr.query(conn, sql, rsh, point, point);
		} finally {
			ConnectionManager.closeConnection(conn);
		}
		return ne;
	}

}
