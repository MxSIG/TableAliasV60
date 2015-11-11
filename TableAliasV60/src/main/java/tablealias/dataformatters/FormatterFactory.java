package tablealias.dataformatters;

import javax.servlet.http.HttpServletRequest;
import tablealias.utils.*;

/**
 *
 * @author INEGI
 */
public class FormatterFactory {

    public static <T> DataFormatter<T> getFormatter(ResultFormat format, HttpServletRequest request) {
        DataFormatter<T> formatter = null;        
        switch (format) {
            case HTML:
                formatter = new HtmlFormatter<T>();
                break;
            case JSON:
                formatter = new JSONFormatter<T>();
                break;
            case DINUE:
                formatter = new DinueFormatter<T>();
                break;
            case BUFFER:
                formatter = new BufferFormatter<T>();
                break;
        }
        if(formatter != null)
            formatter.setHttpServletRequest(request);
        return formatter;
    }
}
