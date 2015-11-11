package tablealias.utils.comparators;

import java.util.Comparator;
import tablealias.xmldata.Server;
import tablealias.dto.BuscableIdentificableDto;
import tablealias.xmldata.FieldFunction;
import tablealias.xmldata.Table;

/**
 *
 * @author INEGI
 */
public class Comparators {

    public static class TableComparators{
        static private class ByNameComparator implements Comparator<Table>{
            public int compare(Table o1, Table o2) {
                return o1.getName().compareTo(o2.getName());
            }
        }
        static private class BySchemaComparator implements Comparator<Table>{
            public int compare(Table o1, Table o2) {
                return o1.getSchema().compareTo(o2.getSchema());
            }
        }
        static private class ByServerComparator implements Comparator<Table>{
            public int compare(Table o1, Table o2) {
                return o1.getServer().compareTo(o2.getServer());
            }
        }

        public static Comparator<Table> getComparatorByName(){
            return new ByNameComparator();
        }
        public static Comparator<Table> getComparatorBySchema(){
            return new BySchemaComparator();
        }
        public static Comparator<Table> getComparatorByServer(){
            return new ByServerComparator();
        }
    }

    public static class ServerComparators{
        static private class ByAliasComparator implements Comparator<Server>{
            public int compare(Server o1, Server o2) {
                int alias = o1.getAlias().compareTo(o2.getAlias());                
                int ip = o1.getServer().compareTo(o2.getServer());
                int port = o1.getPort().compareTo(o2.getPort());
                if(alias == 0 && ip == 0 && port == 0)
                    return 0;
                else
                    return -1;
            }
        }

        public static Comparator<Server> getComparatorByAlias(){
            return new ByAliasComparator();
        }

    }

    public static class FieldComparators{
        static private class ByFunctionOrderComparator implements Comparator<FieldFunction>{

            public int compare(FieldFunction o1, FieldFunction o2) {
                return o2.getOrder() - o1.getOrder();
            }

        }

        public static Comparator<FieldFunction> getComparatorByFunctionOrder(){
            return new ByFunctionOrderComparator();
        }
    }

    public static class BuscablesIdentificablesComparators{
        static private class ByAliasUsuarioComparator implements Comparator<BuscableIdentificableDto>{

            public int compare(BuscableIdentificableDto o1, BuscableIdentificableDto o2) {
                return o1.getAlias().compareTo(o2.getAlias());
            }

        }

        public static Comparator<BuscableIdentificableDto> getComparatorByAliasUsuario(){
            return new ByAliasUsuarioComparator();
        }
    }

}
