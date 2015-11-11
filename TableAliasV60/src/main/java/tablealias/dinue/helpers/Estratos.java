/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.dinue.helpers;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author INEGI
 */
public class Estratos implements DinueSqlCreator {

    private List<String> estrats;

    public Estratos(String values) {
        if (values.length() > 0) {
            estrats = Arrays.<String>asList(values.split(","));
        }
    }

    public String getSql() {
        BaseExcluder be = new EstratosExcluder(this);
        if (be.isExcluded() || (estrats != null && estrats.size() < 1)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();

        sb.append("ESTRATO_PERSONAL_OCUPADO_ID in (");
        for (String s : getEstrats()) {
            sb.append(s).append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(",")).append(")");
        return sb.toString();

    }

    public int size() {
        return getEstrats().size();
    }

    public boolean isEmpty() {
        return getEstrats().isEmpty();
    }

    /**
     * @return the estrats
     */
    public List<String> getEstrats() {
        return estrats;
    }
}
