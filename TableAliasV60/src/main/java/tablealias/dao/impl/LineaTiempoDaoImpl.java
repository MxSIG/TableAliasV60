package tablealias.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import mx.inegi.dtweb.connection.ConnectionManager;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import tablealias.dao.LineaTiempoDao;
import tablealias.dto.LineaTiempoDto;
import tablealias.xmldata.Server;

@Repository
public class LineaTiempoDaoImpl implements LineaTiempoDao {

	@Override
	public List<LineaTiempoDto> getLineaTiempo(String tableName, Server server,
			String extent) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select to_date(to_char(registro2, 'YYYY/MM/DD'), 'YYYY/MM/DD') as date from ")
				.append(tableName)
				.append(" where ST_Intersects(ST_GeomFromText(?,900913), the_geom)")
				.append(" group by registro2 order by 1");
		Connection connection = ConnectionManager.getConnectionW(server);
		ResultSetHandler<List<LineaTiempoDto>> h = new BeanListHandler<LineaTiempoDto>(
				LineaTiempoDto.class);
		/*
		 * ListHandler<LineaTiempoDto> lh = new ListHandler<LineaTiempoDto>( new
		 * ResultSetConverter<LineaTiempoDto>() {
		 * 
		 * @Override public LineaTiempoDto convert(ResultSet rs) throws
		 * SQLException { String registro2 = rs.getString(1); LineaTiempoDto dto
		 * = new LineaTiempoDto(); dto.setTime(registro2); return dto; } });
		 */
		QueryRunner runner = new QueryRunner();
		List<LineaTiempoDto> result = null;
		try {
			result = runner.query(connection, sql.toString(), h, extent);
		} finally {
			ConnectionManager.closeConnection(connection);
		}
		return result;
	}
}
