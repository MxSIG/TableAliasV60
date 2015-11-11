package tablealias.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import mx.inegi.dtweb.connection.ConnectionManager;
import tablealias.dto.BuscableIdentificableDto;
import tablealias.utils.comparators.Comparators;
import tablealias.xmldata.Server;
import tablealias.xmldata.Table;

/**
 * 
 * @author INEGI
 */
public class TablasServidor {

	TreeMap<Server, List<Table>> tableServerPool;
	private Table foundTable;
	private Server foundServer;
	Set<String> proyectos;

	public TablasServidor() {
		tableServerPool = new TreeMap<Server, List<Table>>(
				Comparators.ServerComparators.getComparatorByAlias());
	}

	public List<Table> getTablesByServerAlias(String serverAlias) {
		for (Map.Entry<Server, List<Table>> entry : tableServerPool.entrySet()) {
			if (entry.getKey().getAlias().equalsIgnoreCase(serverAlias))
				return entry.getValue();
		}
		return null;
	}

	public boolean tableExists(String alias) {
		for (Map.Entry<Server, List<Table>> entry : tableServerPool.entrySet()) {
			for (Table t : entry.getValue()) {
				if (t.getAlias().equalsIgnoreCase(alias)
						|| t.getName().equalsIgnoreCase(alias)) {
					foundTable = t;
					foundServer = entry.getKey();
					foundServer.setDbName(foundTable.getDatabaseName());
					return true;
				}
			}
		}
		return false;
	}

	public boolean tableExists(String alias, String proyName)
			throws ProyectoInvalido {
		boolean exito = false;
		if (proyName != null && proyName.trim().length() > 0) {
			for (Map.Entry<Server, List<Table>> entry : tableServerPool
					.entrySet()) {
				for (Table t : entry.getValue()) {
					if ((t.getAlias().equalsIgnoreCase(alias) || t.getName()
							.equalsIgnoreCase(alias))
							&& isProyectoValido(proyName)) {
						foundTable = t;
						foundServer = entry.getKey();
						foundServer.setDbName(foundTable.getDatabaseName());
						return true;
					}
				}
			}
			return false;
		} else {
			exito = tableExists(alias);
		}
		return exito;
	}

	public Server getServerByTable(String table) {
		Server srvr = null;
		for (Map.Entry<Server, List<Table>> entry : tableServerPool.entrySet()) {
			for (Table t : entry.getValue()) {
				if (t.getAlias().equalsIgnoreCase(table)
						|| t.getName().equalsIgnoreCase(table)) {
					srvr = entry.getKey();
					srvr.setDbName(t.getDatabaseName());
					return srvr;
				}
			}
		}
		return null;
	}

	public Server getServer(String server) {
		for (Map.Entry<Server, List<Table>> entry : tableServerPool.entrySet()) {
			Server s = entry.getKey();
			if (s.getAlias().equalsIgnoreCase(server)) {
				return s;
			}
		}
		return null;
	}

	//
	public void openConnections() {
		for (Map.Entry<Server, List<Table>> entry : tableServerPool.entrySet()) {
			Server s = entry.getKey();
			try {
				// s.setDbName(entry.getValue().get(0).getDatabaseName());
				Connection conn = ConnectionManager.getConnection(s);
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	void setProyectos(Set<String> proyectos) {
		this.proyectos = proyectos;
	}

	public boolean isProyectoValido(String proyecto) throws ProyectoInvalido {
		if (proyecto == null)
			return false;
		if (proyectos.contains(proyecto)) {
			return true;
		} else {
			throw new ProyectoInvalido(proyecto + " no valido");
		}
	}

	public void setGeolocatorTableFromProyecto(String proyecto) {
		for (Map.Entry<Server, List<Table>> entry : tableServerPool.entrySet()) {
			for (Table t : entry.getValue()) {
				if (t.getAlias().toLowerCase().startsWith("geolocator")
						|| t.getName().toLowerCase().startsWith("geolocator")) {
					if (t.getProyectos().contains(proyecto)) {
						foundTable = t;
						foundServer = entry.getKey();
						break;
					}
				}
			}
		}
	}

	/**
	 * @return the foundTable
	 */
	public Table getFoundTableClone() {
		return (Table) foundTable.clone();
	}

	/**
	 * @return the foundTable
	 */
	public Table getFoundTable() {
		return foundTable;
	}

	/**
	 * @return the foundServer
	 */
	public Server getFoundServer() {
		return foundServer;
	}

	public List<BuscableIdentificableDto> getBuscablesIdentificables(
			String proyecto) {
		List<BuscableIdentificableDto> data = new ArrayList<BuscableIdentificableDto>();
		for (Map.Entry<Server, List<Table>> entry : tableServerPool.entrySet()) {
			for (Table t : entry.getValue()) {
				StringBuilder sb = new StringBuilder();
				List<String> proyectos = t.getProyectos();
				if (proyectos.contains(proyecto.toLowerCase())) {
					if (t.isBuscable() || t.isIdentificable()
							|| t.isBuffereable()) {
						BuscableIdentificableDto dto = new BuscableIdentificableDto();
						if (t.isBuscable()) {
							sb.append("B");
						}
						if (t.isIdentificable()) {
							sb.append("I");
						}
						if (t.isBuffereable()) {
							sb.append("A");
						}
						dto.setTipo(sb.toString());
						dto.setNombre(t.getAlias());
						dto.setAlias(t.getAliasUsuario());
						data.add(dto);
					}
				}
			}
		}
		Collections.sort(data, Comparators.BuscablesIdentificablesComparators
				.getComparatorByAliasUsuario());
		return data;
	}
}
