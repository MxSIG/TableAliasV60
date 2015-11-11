package tablealias.dao;

import java.sql.SQLException;
import java.util.List;

import tablealias.dto.LineaTiempoDto;
import tablealias.xmldata.Server;

public interface LineaTiempoDao {

	List<LineaTiempoDto> getLineaTiempo(String tableName, Server server,
			String extent) throws SQLException;

}
