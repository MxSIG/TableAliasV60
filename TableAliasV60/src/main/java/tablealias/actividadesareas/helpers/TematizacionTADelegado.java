package tablealias.actividadesareas.helpers;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import tablealias.sqlworkers.ConsultaWrapper;
import tablealias.utils.TablasServidor;
import tablealias.xmldata.Table;
import tablealias.xmldata.TableFields;
import tablealias.xmldata.Tables;
import dtweb.temas.delegate.TematizacionDelegate;
import dtweb.temas.dto.DatosTema;
import dtweb.temas.dto.EstratificacionData;
import dtweb.temas.dto.Rango;
import dtweb.temas.dto.Tema;
import dtweb.temas.statistics.StatisticsDataGenerator;
import excelcreate.delegate.DelegadoExcelCreator;
import excelcreate.dto.Data;
import excelcreate.dto.ExcelFillePageDTO;
import excelcreate.dto.Headers;

/**
 * 
 * @author INEGI
 */
public class TematizacionTADelegado {

	TematizacionDelegate del = new TematizacionDelegate();

	public DatosTema getDatosTema(String page, String count, String x,
			String y, String res, String id, String pgrafica, String mapaId,
			String year, Connection conn) throws Exception {
		// System.out.println("id: " + id + " idtema: " + mapaId);
		return del.getDatosTema(page, count, x, y, res, id, pgrafica, mapaId,
				year, conn);
	}

	public Tema getTema(String id, String metodo, String estratos,
			String clases, Connection conn) throws Exception {
		return del.getTema(id, metodo, estratos, clases, conn);
	}

	public long writeTematizacion(String[] colores, String[] inicio,
			String[] fin, String[] activo, String idtema, String idmapa,
			String metodo, String estratos, String nivel, String variables,
			String geo, Connection conn) throws Exception {
		return del.writeTematizacionSingle(colores, inicio, fin, activo,
				idtema, idmapa, metodo, estratos, nivel, variables, geo, conn);
	}

	public long getRangosMVMapNoUrlConnection(String muestras, String estratos,
			Connection conn, String proyName,
			List<EstratificacionData> datosEstratos) throws Exception {
		return del.getRangosfromCsvNoUrlConnection(muestras, estratos, conn,
				proyName, datosEstratos);
	}

	public long getRangosMVMap(String muestras, String estratos, String url,
			Connection conn, String proyName) throws Exception {
		return del.getRangosfromCsv(muestras, estratos, url, conn, proyName);
	}

	public List<Rango> getRangos(String metodo, String estratos, String nivel,
			String variables, String geo) throws Exception {
		return del.getRangosFromDBF(metodo, estratos, nivel, variables, geo);
	}

	public Tema getRangos(String idtema, String metodo, String estratos,
			String clases, Connection conn) throws Exception {
		return del.getRangos(idtema, metodo, estratos, clases, conn);
	}

	public List<Rango> getRangosMV(String idtema, String metodo,
			String estratos, String clases, Connection conn) throws Exception {
		return del.getRangosMV(idtema, metodo, estratos, clases, conn);
	}

	public List<Rango> getRangosIndefinidos() {
		return del.getUndefinedRanges();
	}

	public StatisticsDataGenerator getStatistics(String metodo,
			String estratos, String nivel, String variables, String geo)
			throws Exception {
		return del
				.getStatisticsFromDBF(metodo, estratos, nivel, variables, geo);
	}

	public StatisticsDataGenerator getStatistics(String idtema, String metodo,
			String estratos, String clases, Connection conn) throws Exception {
		return del.getStatistics(idtema, metodo, estratos, clases, conn);
	}

	public void updateRangosOcurrencias(String idtema, String metodo,
			String estratos, String clases, List<Rango> rangos, Connection conn)
			throws Exception {
		del.updateRangosOcurrencias(idtema, metodo, estratos, clases, rangos,
				conn);
	}

	public List<Rango> updateRangosOcurrencias(String idtema, String[] colores,
			String[] inicio, String[] fin, String metodo, String estratos,
			String clases, Connection conn) throws Exception {
		List<Rango> misrangos = new LinkedList<Rango>();
		int i = 0;
		for (String c : colores) {
			Rango r1 = new Rango();
			r1.setClavesGeo(null);
			r1.setFin(Double.parseDouble(fin[i]));
			r1.setInicio(Double.parseDouble(inicio[i++]));
			r1.setOcurrencias(0);
			r1.setColor(null);
			misrangos.add(r1);
		}
		updateRangosOcurrencias(idtema, metodo, estratos, clases, misrangos,
				conn);
		return misrangos;
	}

	public byte[] getDatosTema(String idtema, Connection conn) throws Exception {
		return del.getDatosTemaFile(idtema, conn);
	}

	public byte[] getDatosTema(String idtema, String tablasDescarga,
			String gid, TablasServidor tablasServidor) throws Exception {
		String[] tablasDes = tablasDescarga.split(",");
		Table table = null;
		Tables tables = new Tables();
		for (String s : tablasDes) {
			if (tablasServidor.tableExists(s, "scince")) {
				table = tablasServidor.getFoundTableClone();
			}
			if (table != null) {
				ConsultaWrapper qw = new ConsultaWrapper("", gid, "", false);
				qw.setServer(tablasServidor.getFoundServer());
				qw.setTable(table); // tablasServidor.getFoundTable()
				qw.setPolygon(null);
				qw.setPageToView(1);
				qw.setServerBuffer(tablasServidor.getServerByTable("c100"));
				Table[] data = (Table[]) qw.doQuery();
				tables.addTable(data[0]);
			}
			table = null;
		}

		// DatosExcel datosExcel = new DatosExcel("", "",
		// "Censo de Población y Vivienda 2010", null);
		String[] encabezados = { "Tema: Población" };
		String[] encabezados2 = { "Corte: Entidad Federativa" };
		String[] encabezados4 = { "Indicador", "Valor" };
		List<Headers> hs = new LinkedList<Headers>();
		hs.clear();

		ExcelFillePageDTO dto = new ExcelFillePageDTO();
		DelegadoExcelCreator del = new DelegadoExcelCreator();

		String[][] datas = null;
		// System.out.println("Empezanod....");
		for (Table mt : tables.getTables()) {
			TableFields tf = mt.getFields();
			datas = new String[tf.size()][3];
			// System.out.println("tamaño: " + datas.length + " otro " +
			// datas[0].length);

			// boolean band = false;
			// for (int i = 0; i < tf.size(); i++) {
			// if (tf.get(i).getValue().contains("|")) {
			// System.out.println("gg: " + tf.get(i).getValue());
			// band = true;
			// }
			// }
			// System.out.println("y la bandera : " + band);
			// if (band) {
			// datas = new String[tf.size()][3];
			// System.out.println("quedó en 3 s:" + tf.size());
			// }else{
			// //datas = new String[tf.size()][2];
			// System.out.println(" quedo en 2 ");
			// }

			Data mD = new Data(gid, "0");
			for (int i = 0; i < tf.size(); i++) {
				if (tf.get(i).getValue().contains("|")) {
					// System.out.println("++++++++++++++ valor:" +
					// tf.get(i).getValue() + " sizer:" + datas[i].length);
					String dl[] = tf.get(i).getValue().split("\\|");
					datas[i][1] = transformaDato(mD, dl[0]);
					datas[i][2] = transformaDato(mD, dl[1])
							+ (("NA".equalsIgnoreCase(dl[1])) ? "" : " %");
					// System.out.println(" y datas: " + datas[i][1] +" :: " +
					// datas[i][2]);
					encabezados4 = new String[] { "Indicador", "Absoluto",
							"Porcentual" };
				} else {
					datas[i][1] = tf.get(i).getValue();
				}
				datas[i][0] = tf.get(i).getAliasName();

			}
			hs.clear();
			Headers h = new Headers(encabezados);
			hs.add(h);
			h = new Headers(encabezados2);
			hs.add(h);
			h = new Headers(encabezados4);
			hs.add(h);

			// System.out.println("agregando: " + mt.getAliasUsuario());
			String corteYtematica[] = mt.getAliasUsuario().split("-");
			encabezados[0] = "Tematica: " + corteYtematica[1];
			encabezados2[0] = "Corte: " + corteYtematica[0];
			dto.setData(datas);
			dto.setName(corteYtematica[1]);
			dto.setData(datas);
			dto.setHeaders(hs);
			del.addPage(dto);
			del.addContent2Page(datas);
			String k[] = { "Fuente: Censo de Población y Vivienda 2010" };
			h = new Headers(k);
			del.addFooter(h);
			// System.out.println("Di primera vuelta");
		}
		del.closeSheet();
		System.out.println("Termine");
		return del.getZippedFileInBytes("cp2010");
	}

	private String transformaDato(Data mD, String dl) {
		String transformado;
		try {
			mD.setDato(dl);
			transformado = mD.getDato();
		} catch (Exception e) {
			transformado = dl;
		}
		return transformado;
	}
}
