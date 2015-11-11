/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.dataformatters;

import javax.servlet.http.HttpServletRequest;
import tablealias.dataformatters.DataFormatter;
import tablealias.sqlworkers.QueryWorker;
import tablealias.utils.HTTPEncodingFormat;
import tablealias.xmldata.Field;
import tablealias.xmldata.Table;

/**
 *
 * @author INEGI
 */
public class HtmlFormatter<T> implements DataFormatter<T> {

    private QueryWorker queryWorker;
    private boolean hasErrors;
    private StringBuilder sbError;
    private HttpServletRequest request;

    public void setQueryWorker(QueryWorker queryHandler) {
        this.queryWorker = queryHandler;
        hasErrors = false;
    }

    private String getTableHeader(Table table) {
        StringBuilder sb = new StringBuilder();
        for (Field f : table.getFields()) {
            sb.append("<th>").append(f.getAliasName()).append("</th>");
        }
        return sb.toString();
    }

    private String getTableBody(Table[] tables) {
        StringBuilder sb = new StringBuilder();
        for (Table table : tables) {
            sb.append("<tr>");
            for (Field f : table.getFields()) {
                sb.append("<td align='center'>").append(f.getValue().trim()).append("</td>");
            }
            sb.append("</tr>");
        }
        return sb.toString();
    }

    public T getData() throws Exception {
        Table[] data = (Table[])queryWorker.doQuery();
        StringBuilder sb = null;
        if (queryWorker.hasErrors()) {
            hasErrors = true;
            sbError = new StringBuilder();
            request.setAttribute("exito", false);//para el statisticianfilter sepa q no hubo registros en la consulta
            sbError.append(queryWorker.getErrorMsg());
        } else {
            hasErrors = false;            
            if (data != null && data.length > 0) {
                request.setAttribute("exito", true);
                sb = new StringBuilder();
                sb.append("<table width='50%' border='1'>");
                sb.append(getTableHeader(data[0]));
                sb.append(getTableBody(data));
                sb.append("</table>");
            }
        }
        return (T) (sb == null ? "" : sb.toString());
    }

    public String getErrorMsg() {
        return sbError == null ? "" : sbError.toString();
    }

    public boolean hasErrors() {
        return hasErrors;
    }

    public String getContentType() {
        return HTTPEncodingFormat.getHTMLFormatWEncoding();
    }

    public void setHttpServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}
