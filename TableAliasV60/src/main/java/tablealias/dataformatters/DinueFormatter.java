/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.dataformatters;

import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import tablealias.sqlworkers.DinueWrapper;
import tablealias.sqlworkers.QueryWorker;
import tablealias.utils.HTTPEncodingFormat;
import tablealias.utils.JSONExceptionMessage;
import tablealias.utils.NoRecordsFoundJSONError;
import tablealias.xmldata.Table;

/**
 *
 * @author INEGI
 */
public class DinueFormatter <T> implements DataFormatter<T>{

    private QueryWorker queryWorker;
    private boolean hasErrors;
    private StringBuilder sbError;
    private HttpServletRequest request;

    public void setQueryWorker(QueryWorker queryWorker) {
        this.queryWorker = queryWorker;
        hasErrors = false;
    }

    public T getData() throws Exception {
        Table[] data = (Table[])queryWorker.doQuery();

        StringBuilder sb = new StringBuilder();
        if (queryWorker.hasErrors()) {
            hasErrors = true;
            sbError = new StringBuilder();
            request.setAttribute("exito", false);//para el statisticianfilter sepa q no hubo registros en la consulta
            sbError.append(queryWorker.getErrorMsg());
        } else {
            hasErrors = false;
            if (data != null && data.length > 0) {
                request.setAttribute("exito", true);
                int totalRecords = queryWorker.getNumberOfRecords();
                Gson gson = new Gson();
                sb.append(gson.toJson(data));
                String aliasUsuario = data[0].getAliasUsuario();
                String jsonAdditionalData = String.format("\"totalFields\":\"%d\"," +
                        "%n \"currentPage\":\"%d\"," +
                        "%n \"aliasUsuario\":\"%s\"," +
                        "%n \"where\":\"%s\"," +
                        "%n \"extent\":\"%s\","
                        , totalRecords,queryWorker.getPageToView(),aliasUsuario, ((DinueWrapper)queryWorker).getWhere(), ((DinueWrapper)queryWorker).getExtent());
                sb.insert(2, jsonAdditionalData);//"\"totalFields\":\""+totalRecords+"\",");
            }
            else{
                Gson gson = new Gson();
                request.setAttribute("exito", false);//para el statisticianfilter sepa q no hubo registros en la consulta
                String aliasUsuario = queryWorker.getTable().getAliasUsuario();
                sb.append("["+gson.toJson(new NoRecordsFoundJSONError(aliasUsuario))+"]");//String.format("[{\"totalFields\":\"%d\",%n}]", 0)));
            }
        }
        String callback = request.getParameter( "callback" );
        if( callback != null && !callback.isEmpty() ){
            StringBuilder jsonp = new StringBuilder( callback );
            jsonp.append( "(" ).append( sb.toString() ).append( ")" );
            sb = jsonp;
        }
        return (T) (sb == null ? "" : sb.toString());
    }

    public String getErrorMsg() {
        Gson gson = new Gson();
        return gson.toJson(new JSONExceptionMessage(sbError == null ? "" : sbError.toString()));
    }

    public boolean hasErrors() {
        return hasErrors;
    }

    public String getContentType() {
        //return "application/json;charset=UTF-8";
        return HTTPEncodingFormat.getJsonFormatWEncoding();
    }

    public void setHttpServletRequest(HttpServletRequest request) {
        this.request = request;
    }

}
