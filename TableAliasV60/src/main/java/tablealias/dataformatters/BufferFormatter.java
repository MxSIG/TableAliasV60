/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.dataformatters;

import com.google.gson.Gson;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import tablealias.dto.BufferDto;
import tablealias.dto.Buffers;
import tablealias.sqlworkers.QueryWorker;
import tablealias.utils.HTTPEncodingFormat;
import tablealias.utils.JSONExceptionMessage;
import tablealias.utils.NoRecordsFoundJSONError;

/**
 *
 * @author INEGI
 */
public class BufferFormatter<T> implements DataFormatter<T>{

    private QueryWorker queryWorker;
    private boolean hasErrors;
    private StringBuilder sbError;
    private HttpServletRequest request;

    public void setQueryWorker(QueryWorker queryWorker) {
        this.queryWorker = queryWorker;
        hasErrors = false;
    }

    public T getData() throws Exception {
        List<BufferDto> data = (List<BufferDto>)queryWorker.doQuery();

        StringBuilder sb = new StringBuilder();
        if (queryWorker.hasErrors()) {
            hasErrors = true;
            sbError = new StringBuilder();
            request.setAttribute("exito", false);
            sbError.append(queryWorker.getErrorMsg());
        } else {
            hasErrors = false;
            if (data != null && data.size() > 0) {
                request.setAttribute("exito", true);
                Buffers buffers = new Buffers();
                buffers.setAliasUsuario(queryWorker.getTable().getAliasUsuario());
                buffers.setDatos(data);
                //int totalRecords = queryWorker.getNumberOfRecords();
                Gson gson = new Gson();
                sb.append(gson.toJson(buffers));
                //sb.deleteCharAt(0);
                //String jsonAdditionalData = String.format("\"totalFields\":\"%d\",%n", 0);//String.format("\"totalFields\":\"%d\",%n ", data.size());
                //sb.insert(1, jsonAdditionalData);//"\"totalFields\":\""+totalRecords+"\",");
                //sb.insert(0, "[");
            }
            else{
                request.setAttribute("exito", false);//para el statisticianfilter sepa q no hubo registros en la consulta
                Gson gson = new Gson();
                sb.append("["+gson.toJson(new NoRecordsFoundJSONError(queryWorker.getTable().getAliasUsuario()))+"]");//String.format("[{\"totalFields\":\"%d\",%n}]", 0)));
            }
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
