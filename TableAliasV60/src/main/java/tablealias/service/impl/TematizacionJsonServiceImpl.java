package tablealias.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import mx.inegi.dtweb.connection.ConnectionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tablealias.actividadesareas.helpers.TematizacionTADelegado;
import tablealias.dto.ExitoDTO;
import tablealias.dto.TematizacionParam;
import tablealias.service.TematizacionJsonService;
import tablealias.utils.RegistraErrorWebService;
import tablealias.utils.TablasServidor;
import tablealias.xmldata.Server;

import com.google.gson.Gson;

import dtweb.temas.dto.Tema;
import dtweb.temas.ws.exception.StratificationException;

@Service
public class TematizacionJsonServiceImpl implements TematizacionJsonService {
	@Autowired
	ServletContext context;

	@Override
	public Object json(TematizacionParam param, HttpServletRequest req) {
		TematizacionTADelegado del = new TematizacionTADelegado();
		TablasServidor tablasServidor = (TablasServidor) context
				.getAttribute("tablasServidor");
		List<ExitoDTO> listaSalida = new LinkedList<ExitoDTO>();
		String salida;
		Gson gson = new Gson();
		Tema temaError = null;
		Connection conn = null;
		String tema = param.getTema();
		String mapa = param.getMapa();
		String Especial = "";
		ExitoDTO exi = new ExitoDTO();
		exi.setTema(tema);
		if (mapa != null) {
			exi.setMapa(mapa);
		}
		try {
			tablasServidor.tableExists("tematizacion", "mdm6");
			Server server = tablasServidor.getFoundServer();
			conn = ConnectionManager.getConnectionW(server);
			exi.setMapa(del.getRangosMVMapNoUrlConnection(param.getMuestras(),
					param.getEstratos(), conn, param.getProyName(),
					param.getDatosEstratos())
					+ "");
			exi.setOperacion("Actualiza mapa");
			exi.setEstatus("exito");
			listaSalida.add(exi);
			if (!Especial.equalsIgnoreCase("")) {
				exi.setEspecial(Especial);
			}
			return listaSalida;

		} catch (SQLException es) {

			es.printStackTrace();
			temaError = new Tema();
			temaError.setErrorMsg(es.getMessage());
			salida = gson.toJson(temaError);
			String additionalData = String
					.format("\"totalFields\":\"%d\",%n \"aliasUsuario\":\"%s\",%n \"currentPage\":\"%d\", %n \"camposTotales\":\"%s\", %n \"esTotales\":\"%s\" ",
							0, "", 1, "", "");
			StringBuilder sb;
			if (salida != null) {
				sb = new StringBuilder(salida);
				sb.insert(2, additionalData);
			} else {
				sb = new StringBuilder(additionalData);
			}
			salida = sb.toString();
			es.printStackTrace(System.err);
		} catch (StratificationException es) {
			es.printStackTrace();
			RegistraErrorWebService.RegistraError(req, es,
					"beatriz.lopez@inegi.org.mx");
			temaError = new Tema();
			temaError.setErrorMsg(es.getMessage());
			salida = gson.toJson(temaError);
			String additionalData = String
					.format("\"totalFields\":\"%d\",%n \"aliasUsuario\":\"%s\",%n \"currentPage\":\"%d\", %n \"camposTotales\":\"%s\", %n \"esTotales\":\"%s\", ",
							0, "", 1, "", "");
			StringBuilder sb = new StringBuilder(salida);
			sb.insert(2, additionalData);
			salida = sb.toString();
			es.printStackTrace(System.err);
		} catch (Exception e) {
			e.printStackTrace();
			RegistraErrorWebService.RegistraError(req, e,
					"beatriz.lopez@inegi.org.mx");
			temaError = new Tema();
			temaError.setErrorMsg(e.getMessage());
			salida = gson.toJson(temaError);
			String additionalData = String
					.format("\"totalFields\":\"%d\",%n \"aliasUsuario\":\"%s\",%n \"currentPage\":\"%d\", %n \"camposTotales\":\"%s\", %n \"esTotales\":\"%s\", ",
							0, "", 1, "", "");
			StringBuilder sb = new StringBuilder(salida);
			sb.insert(2, additionalData);
			salida = sb.toString();
			e.printStackTrace(System.err);
		} finally {
			if (conn != null) {
				try {
					ConnectionManager.closeConnection(conn);
				} catch (SQLException ex) {
					ex.printStackTrace(System.err);
				}
			}
		}
		return salida;
	}
}
