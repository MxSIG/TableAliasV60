/**
 * 
 */
package tablealias.web.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tablealias.dto.GeometriaCvegeoDto;
import tablealias.dto.GeometriaDto;
import tablealias.service.GeometriaService;
import tablealias.utils.FieldQueryDataCleaner;
import tablealias.utils.ProyectoInvalido;
import tablealias.utils.TablasServidor;
import tablealias.web.ResponseFactory;
import tablealias.web.ResponseFactory.CustomResponse;
import tablealias.xmldata.Field;
import tablealias.xmldata.Server;
import tablealias.xmldata.Table;
import tablealias.xmldata.TableFields;

/**
 * @author INEGI
 *
 */
@Controller
@RequestMapping("geometria")
public class GeometriaController {

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private GeometriaService service;

	@RequestMapping(value = "punto", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody CustomResponse processByPoint(
			@Valid @RequestBody GeometriaDto dto,
			final HttpServletRequest request) {

		TablasServidor ts = (TablasServidor) servletContext
				.getAttribute("tablasServidor");
		Server server = null;
		Table table = null;

		String point = dto.getPoint();
		String alias = dto.getAlias();
		String project = dto.getProject();

		CustomResponse response = null;

		try {
			if (ts.tableExists(alias, project)) {
				server = ts.getFoundServer();
				table = ts.getFoundTableClone();

				String sql = sqlIntersect(table);

				try {
					Object geometry = service.findGeometryByPoint(sql, point,
							server);
					response = ResponseFactory.successfulResponse(geometry);
				} catch (Exception e) {
					String error = "Internal service error!";
					response = ResponseFactory.unsuccessfulResponse(error);
				}

			} else {
				String error = "Table " + alias + " not found.";
				response = ResponseFactory.unsuccessfulResponse(error);
			}
		} catch (ProyectoInvalido e) {
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping(value = "cvegeo", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody CustomResponse processByCvegeo(
			@Valid @RequestBody GeometriaCvegeoDto dto,
			final HttpServletRequest request) {

		TablasServidor ts = (TablasServidor) servletContext
				.getAttribute("tablasServidor");
		Server server = null;
		Table table = null;

		String cvegeo = dto.getCvegeo();
		String alias = dto.getAlias();
		String project = dto.getProject();

		CustomResponse response = null;

		try {
			if (ts.tableExists(alias, project)) {
				server = ts.getFoundServer();
				table = ts.getFoundTableClone();

				String sql = sqlCvegeo(table);

				try {
					Object geometry = service.findGeometryByPoint(sql, cvegeo,
							server);
					response = ResponseFactory.successfulResponse(geometry);
				} catch (Exception e) {
					String error = "Internal service error!";
					response = ResponseFactory.unsuccessfulResponse(error);
				}

			} else {
				String error = "Table " + alias + " not found.";
				response = ResponseFactory.unsuccessfulResponse(error);
			}
		} catch (ProyectoInvalido e) {
			e.printStackTrace();
		}

		return response;
	}

	private String sqlIntersect(Table table) {

		TableFields tfields = table.getFields();
		List<Field> fields = tfields.getFields();
		String projection = table.getProy();

		StringBuilder sb = new StringBuilder("select ");

		for (Field field : fields) {

			String fieldAlias = FieldQueryDataCleaner.normalize(field
					.getAliasName());

			sb.append(field.getSqlName());
			sb.append(" as ");
			sb.append(fieldAlias);
			sb.append(", ");

		}

		if (table.getGeomName().equalsIgnoreCase("the_geom")
				&& projection.equalsIgnoreCase("900913")) {
			sb.append("ST_AsText(Simplify(");
			sb.append(table.getGeomName());
			sb.append(", area(");
			sb.append(table.getGeomName());
			sb.append(")/100000000000))");
			sb.append(" as ");
			sb.append(table.getGeomName());
			sb.append(", ");
			sb.deleteCharAt(sb.lastIndexOf(","));
			sb.append("from ");
			sb.append(table.getSchema());
			sb.append(".");
			sb.append(table.getName());
			sb.append(" where ST_Intersects(");
			sb.append(table.getGeomName());
			sb.append(", ST_GeomFromText(?, 900913)) limit 1");
		} else if (table.getGeomName().equalsIgnoreCase("the_geom")
				&& projection.equalsIgnoreCase("4326")) {
			sb.append("ST_AsText(Simplify(");
			sb.append(table.getGeomName());
			sb.append(", area(");
			sb.append(table.getGeomName());
			sb.append(")/100000))");
			sb.append(" as ");
			sb.append(table.getGeomName());
			sb.append(", ");
			sb.deleteCharAt(sb.lastIndexOf(","));
			sb.append("from ");
			sb.append(table.getSchema());
			sb.append(".");
			sb.append(table.getName());
			sb.append(" where ST_Intersects(");
			sb.append(table.getGeomName());
			sb.append(", ST_GeomFromText(?, 4326)) limit 1");
		}

		String sql = sb.toString();
		System.out.println(sql);

		return sql;

	}

	private String sqlCvegeo(Table table) {

		TableFields tfields = table.getFields();
		List<Field> fields = tfields.getFields();
		String projection = table.getProy();

		StringBuilder sb = new StringBuilder("select ");

		for (Field field : fields) {

			String fieldAlias = FieldQueryDataCleaner.normalize(field
					.getAliasName());

			sb.append(field.getSqlName());
			sb.append(" as ");
			sb.append(fieldAlias);
			sb.append(", ");

		}

		if (table.getGeomName().equalsIgnoreCase("the_geom")
				&& projection.equalsIgnoreCase("900913")) {
			sb.append("ST_AsText(Simplify(");
			sb.append(table.getGeomName());
			sb.append(", area(");
			sb.append(table.getGeomName());
			sb.append(")/100000000000))");
			sb.append(" as ");
			sb.append(table.getGeomName());
			sb.append(", ");
			sb.deleteCharAt(sb.lastIndexOf(","));
			sb.append("from ");
			sb.append(table.getSchema());
			sb.append(".");
			sb.append(table.getName());
			sb.append(" where cvegeo = ? limit 1");
		} else if (table.getGeomName().equalsIgnoreCase("the_geom")
				&& projection.equalsIgnoreCase("4326")) {
			sb.append("ST_AsText(Simplify(");
			sb.append(table.getGeomName());
			sb.append(", area(");
			sb.append(table.getGeomName());
			sb.append(")/100000))");
			sb.append(" as ");
			sb.append(table.getGeomName());
			sb.append(", ");
			sb.deleteCharAt(sb.lastIndexOf(","));
			sb.append("from ");
			sb.append(table.getSchema());
			sb.append(".");
			sb.append(table.getName());
			sb.append(" where cvegeo = ? limit 1");
		}

		String sql = sb.toString();
		System.out.println(sql);

		return sql;

	}

}
