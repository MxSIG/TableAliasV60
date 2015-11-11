package tablealias.controlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.inegi.dtweb.connection.ConnectionManager;
import tablealias.actividadesareas.helpers.TematizacionTADelegado;
import tablealias.dto.EstratosW;
import tablealias.dto.ExitoDTO;
import tablealias.utils.HTTPEncodingFormat;
import tablealias.utils.RegistraErrorWebService;
import tablealias.utils.RequestHelper;
import tablealias.utils.TablasServidor;
import tablealias.xmldata.Field;
import tablealias.xmldata.Server;
import tablealias.xmldata.Table;
import tablealias.xmldata.TableFields;

import com.google.gson.Gson;

import dtweb.temas.dto.Data;
import dtweb.temas.dto.DatosTema;
import dtweb.temas.dto.Rango;
import dtweb.temas.dto.Tema;
import dtweb.temas.statistics.IStatistics;
import dtweb.temas.statistics.SUVStatisticsDTO;
import dtweb.temas.ws.exception.StratificationException;

/**
 * 
 * @author INEGI testing branch commit
 */
public class Tematizacion extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(HTTPEncodingFormat.getJsonFormatWEncoding());
		// PrintWriter out = response.getWriter();
		ServletOutputStream out = null;
		ServletContext context = getServletContext();
		TablasServidor tablasServidor = (TablasServidor) context
				.getAttribute("tablasServidor");
		Connection conn = null;
		String salida = null;
		byte[] exportFile = null;
		Tema temaError = null;
		String tema = request.getParameter("tema");
		Gson gson = new Gson();
		try {
			tablasServidor.tableExists("tematizacion", "mdm6");
			Server server = tablasServidor.getFoundServer();
			conn = ConnectionManager.getConnectionW(server);

			String tablas = request.getParameter("tablas");
			String gid = request.getParameter("gid");
			String mapa = request.getParameter("mapa");
			String metodo = request.getParameter("metodo");
			String estratos = request.getParameter("estratos");
			String clases = request.getParameter("clases");
			String colores = request.getParameter("colores");
			String inicio = request.getParameter("inicio");
			String fin = request.getParameter("fin");
			String activo = request.getParameter("activo");
			String operacion = request.getParameter("operacion");
			String pgrafica = request.getParameter("pgrafica");

			StringBuilder sb = new StringBuilder();
			List listaSalida = new LinkedList();
			EstratosW estratosW = new EstratosW();
			ExitoDTO exi = new ExitoDTO();
			String Especial = "";
			exi.setTema(tema);
			if (mapa != null) {
				exi.setMapa(mapa);
			}
			TematizacionTADelegado del = new TematizacionTADelegado();
			IStatistics stdg = null;
			boolean valida = false;
			if (operacion == null || operacion.isEmpty() || metodo == null
					|| metodo.isEmpty() || estratos == null
					|| estratos.isEmpty()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,
						"Invalid Parameters: OME");
				valida = true;
			} else if ("getTema".equalsIgnoreCase(operacion)) {
				if (tema == null) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST,
							"Invalid Parameters: Theme");
					valida = true;
				} else {
					String pagina = request.getParameter("pagina");
					String cuantos = request.getParameter("cuantos");
					String xPoint = request.getParameter("x");
					String yPoint = request.getParameter("y");
					String resolution = request.getParameter("res");
					String year = request.getParameter("year");

					DatosTema datosTema = del.getDatosTema(pagina, cuantos,
							xPoint, yPoint, resolution, tema, pgrafica, mapa,
							year, conn);
					int totalRecords = datosTema.getDatosTotales();
					List<Table> dataList = new ArrayList<Table>();
					Field f = null;
					for (Data data : datosTema.getDatos()) {
						TableFields tf = new TableFields();
						if (data.getDato().contains(";")) {
							String g[] = data.getDato().split(";");
							for (String m : g) {
								f = new Field("", data.getNombre());
								f.setValue(m);
								tf.addField(f);
							}
						} else {
							f = new Field("", data.getNombre());
							f.setValue(data.getDato());
							tf.addField(f);
						}
						if (year == null) {
							f = new Field("", "Ubicacion");
							f.setValue(data.getUbicacion());
							tf.addField(f);
							f = new Field("", "_coordenada");
							f.setValue(data.getCoordenadas());
							tf.addField(f);
						} else {
							f = new Field("", "_coordenada");
							f.setValue(data.getCoordenadas());
							tf.addField(f);

							f = new Field("", "Imagen");
							f.setValue(data.getImagen());
							tf.addField(f);
						}
						dataList.add(new Table("", "", "", "", "", "", "",
								null, tf, null, null));
					}

					sb.append(gson.toJson(dataList));
					String additionalData = String
							.format("\"totalFields\":\"%d\",%n \"aliasUsuario\":\"%s\",%n \"currentPage\":\"%d\", "
									+ "%n \"camposTotales\":\"%s\", %n \"esTotales\":\"%s\", ",
									totalRecords, "", datosTema.getPagina(),
									"", "");
					sb.insert(2, additionalData);// "\"totalFields\":\""+totalRecords+"\",");
				}
			} else if ("computeMethod".equalsIgnoreCase(operacion)) {
				if (tema == null || metodo == null || estratos == null) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST,
							"Invalid Parameters: TME");
					valida = true;
				} else if ("mv".equalsIgnoreCase(metodo)) {
					estratosW.setLista(del.getRangosMV(tema, metodo, estratos,
							clases, conn));
					List<Rango> r = estratosW.getLista();
					int cuantos = 0;
					for (Rango l : r) {
						cuantos += l.getOcurrencias();
					}
					stdg = new SUVStatisticsDTO("0", "", "", 0);
					// del.getStatistics(tema, metodo, estratos, clases, conn);
					estratosW.setListaIndefinida(del.getRangosIndefinidos());
					exi.setOperacion("Aplicar metodo " + metodo);
					exi.setEstatus("exito");
				} else {
					Tema t = del
							.getRangos(tema, metodo, estratos, clases, conn);
					Especial = t.getEspecial();
					estratosW.setLista(t.getTematizacion().getRangos());
					stdg = t.getStatistics();// del.getStatistics(tema, metodo,
												// estratos, clases, conn);
					// System.out.println("Aplique metodo, los datos Esta:" +
					// stdg);
					if (stdg == null) {
						stdg = del.getStatistics(tema, metodo, estratos,
								clases, conn);
					}
					estratosW.setListaIndefinida(del.getRangosIndefinidos());
					exi.setOperacion("Aplicar metodo " + metodo);
					exi.setEstatus("exito");
				}
			} else if ("applyMethod".equalsIgnoreCase(operacion)) {
				if (tema == null || colores == null || colores.isEmpty()
						|| inicio == null || inicio.isEmpty() || fin == null
						|| fin.isEmpty()) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST,
							"Invalid Parameters: TCIF");
					valida = true;
				} else {
					// DELEGADO, GET TEMA
					String[] colors = colores.split(",");
					String[] from = inicio.split(",");
					String[] to = fin.split(",");
					String[] activos = activo.split(",");
					String nivel = request.getParameter("nivel");
					String variables = request.getParameter("variables");
					String geo = request.getParameter("geo");
					if (mapa != null && !mapa.isEmpty() && !mapa.equals("-1")) {
						// delegado ACTUALIZAR MAPA
						del.writeTematizacion(colors, from, to, activos, tema,
								mapa, metodo, estratos, nivel, variables, geo,
								conn);
					} else {
						exi.setMapa(del.writeTematizacion(colors, from, to,
								activos, tema, mapa, metodo, estratos, nivel,
								variables, geo, conn)
								+ "");
					}
					// listaSalida = new LinkedList<ExitoDTO>();
					exi.setOperacion("Actualiza mapa");
					exi.setEstatus("exito");
				}
			} // ///esto es para tematizar desde un csv
			else if ("mapMethod".equalsIgnoreCase(operacion)) {
				if (request.getParameter("url") != null) {

					// exi.setMapa(( proyName

					exi.setMapa(del.getRangosMVMap(
							request.getParameter("muestras"),
							request.getParameter("estratos"),
							request.getParameter("url"), conn,
							request.getParameter("proyName"))
							+ "");
					exi.setOperacion("Actualiza mapa");
					exi.setEstatus("exito");

				}
			} else if ("countElements".equalsIgnoreCase(operacion)) {
				String[] colors = colores.split(",");
				String[] from = inicio.split(",");
				String[] to = fin.split(",");
				if (tema == null || colores == null || colores.isEmpty()
						|| inicio == null || inicio.isEmpty() || fin == null
						|| fin.isEmpty()) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST,
							"Invalid Parameters: TCIF2");
					valida = true;
				} else {
					exi.setMapa(mapa);
					exi.setTema(tema);
					exi.setOperacion("Calcula elementos en estrato");
					exi.setEstatus("exito");
					estratosW.setLista(del.updateRangosOcurrencias(tema,
							colors, from, to, metodo, estratos, clases, conn));
					estratosW.setListaIndefinida(del.getRangosIndefinidos());
				}
			} else if ("getTemaFile".equalsIgnoreCase(operacion)) {
				if (tema == null) {
					response.sendError(response.SC_BAD_REQUEST,
							"PARAMETROS NO VALIDOS");
					valida = true;
				} else {
					if (tablas != null && !tablas.isEmpty()) {
						// System.out.println("Exportar ficha");
						exportFile = del.getDatosTema(tema, tablas, gid,
								tablasServidor);
					} else {
						// System.out.println("Exportar indicador");
						exportFile = del.getDatosTema(tema, conn);
					}
				}
			} else if ("getTemaWs".equalsIgnoreCase(operacion)) {
				String nivel = request.getParameter("nivel");
				String variables = request.getParameter("variables");
				String geo = request.getParameter("geo");
				if (nivel == null || variables == null || geo == null
						|| metodo == null || estratos == null) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST,
							"Invalid Parameters: NVGME");
					valida = true;
				} else {
					/*
					 * estratosW.setLista(del.getRangos( metodo, estratos,
					 * nivel, variables, geo ) ); stdg = del.getStatistics(
					 * metodo, estratos, nivel, variables, geo );
					 */
					Tema t = del
							.getRangos(tema, metodo, estratos, clases, conn);
					estratosW.setLista(t.getTematizacion().getRangos());
					stdg = t.getStatistics();
					// System.out.println("Aplique metodo, los datos Esta 2:" +
					// stdg);
					if (stdg == null) {
						stdg = del.getStatistics(tema, metodo, estratos,
								clases, conn);
					}
					// estratosW.setListaIndefinida(del.getRangosIndefinidos());
					exi.setOperacion("Aplicar metodo " + metodo);
					exi.setEstatus("exito");
				}
			}

			if (stdg != null) {
				listaSalida.add(stdg);
			}
			if (!Especial.equalsIgnoreCase("")) {
				exi.setEspecial(Especial);
			}
			if (estratosW.getLista() != null) {
				listaSalida.add(estratosW);
			}
			salida = gson.toJson(listaSalida);
			if ("getTema".equalsIgnoreCase(operacion)) {
				salida = sb.toString();
			}

		} catch (SQLException es) {
			// RegistraErrorWebService.RegistraError(request, es);
			es.printStackTrace();
			temaError = new Tema();
			temaError.setErrorMsg(es.getMessage());
			salida = gson.toJson(temaError);
			String additionalData = String
					.format("\"totalFields\":\"%d\",%n \"aliasUsuario\":\"%s\",%n \"currentPage\":\"%d\", %n \"camposTotales\":\"%s\", %n \"esTotales\":\"%s\", ",
							"0", "", "1", "", "");
			StringBuilder sb = new StringBuilder(salida);
			sb.insert(2, additionalData);
			salida = sb.toString();
			es.printStackTrace(System.err);
		} catch (StratificationException es) {
			es.printStackTrace();
			RegistraErrorWebService.RegistraError(request, es,
					"beatriz.lopez@inegi.org.mx");
			temaError = new Tema();
			temaError.setErrorMsg(es.getMessage());
			salida = gson.toJson(temaError);
			String additionalData = String
					.format("\"totalFields\":\"%d\",%n \"aliasUsuario\":\"%s\",%n \"currentPage\":\"%d\", %n \"camposTotales\":\"%s\", %n \"esTotales\":\"%s\", ",
							"0", "", "1", "", "");
			StringBuilder sb = new StringBuilder(salida);
			sb.insert(2, additionalData);
			salida = sb.toString();
			es.printStackTrace(System.err);
		} catch (Exception e) {
			e.printStackTrace();
			RegistraErrorWebService.RegistraError(request, e,
					"beatriz.lopez@inegi.org.mx");
			temaError = new Tema();
			temaError.setErrorMsg(e.getMessage());
			salida = gson.toJson(temaError);
			String additionalData = String
					.format("\"totalFields\":\"%d\",%n \"aliasUsuario\":\"%s\",%n \"currentPage\":\"%d\", %n \"camposTotales\":\"%s\", %n \"esTotales\":\"%s\", ",
							"0", "", "1", "", "");
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
			RequestHelper rh = new RequestHelper(request);
			/*
			 * aki fue la otra modificación del exportador a excel, VA CODIGO
			 * QUE YA EXISTE
			 */
			if (exportFile != null) {
				response.setContentType("application/x-zip-compressed");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"datosCensalesTema" + tema
								+ ".zip\"");
				response.setContentLength(exportFile.length);
				out = response.getOutputStream();
				out.write(exportFile);
			} else {
				if (rh.supportsGzip()) {
					response.setHeader("Content-Encoding", "gzip");
					response.setContentType(HTTPEncodingFormat
							.getJsonFormatWEncoding());
					out = response.getOutputStream();
					GZIPOutputStream gzos = new GZIPOutputStream(out);
					gzos.write(salida.getBytes());
					gzos.close();
				} else {
					out = response.getOutputStream();
					out.print(salida);
				}
			}
			if (out != null) {
				out.close();
			}
		}
	}

	// <editor-fold defaultstate="collapsed"
	// desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 * 
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>
}
