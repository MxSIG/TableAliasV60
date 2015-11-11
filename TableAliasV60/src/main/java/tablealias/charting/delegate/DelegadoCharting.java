package tablealias.charting.delegate;

import dtweb.temas.dto.Data;
import dtweb.temas.dto.DatosTema;
import dtweb.temas.dto.Rango;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import mx.inegi.dgg.dtweb.helper.Configuracion;
import mx.inegi.dgg.dtweb.helper.Graficador;
import tablealias.actividadesareas.helpers.TematizacionTADelegado;

/**
 *
 * @author INEGI
 */
public class DelegadoCharting {

    private Configuracion config;

    public DelegadoCharting() {
        config = new Configuracion("Histograma", new Color(200, 200, 200),
                "png", "Ocurrencias", "Estratos", 400, 300);
    }

    public void generachart(String idtema, String metodo, String estratos, String clases, String[] colores, String[] inicio, String[] fin, OutputStream os, String tipo, String[] cuantos, Connection conn) throws Exception {
        List<Rango> misrangos = getRangesFromStrings(colores, inicio, fin, cuantos);
        System.out.println("tipo: " + tipo);
        if (misrangos.get(0).getOcurrencias() == 0) {
            TematizacionTADelegado del = new TematizacionTADelegado();
            del.updateRangosOcurrencias(idtema, metodo, estratos, clases, misrangos, conn);
        }
        int i = misrangos.size() - 1;
        for (Rango r : misrangos) {
            r.setColor(new dtweb.temas.dto.Color(colores[ i--]));
        }
        if ("dispersion".equalsIgnoreCase(tipo)) {
            TematizacionTADelegado del = new TematizacionTADelegado();
            String nullType = null;
            System.out.println("IDTEMA: " + idtema);
            DatosTema datosTema = del.getDatosTema("1", "101", nullType, nullType, nullType, idtema, "", "-11", null,conn);
            config.setName("Disperción");
            //config.setName("Histograma");
            //graficaHistograma(config, misrangos, os);
            graficaDispersion(config, datosTema.getDatos(), os);
        } else if ("ojiva".equalsIgnoreCase(tipo)) {
            config.setName("Ojiva");
            graficaOjiva(config, misrangos, os);
        } else {
            config.setName("Histograma");
            graficaHistograma(config, misrangos, os);
        }
    }

    private void graficaHistograma(Configuracion configuracion, List<Rango> rangos, OutputStream os) throws IOException {
        Graficador gr = new Graficador();
        gr.generaGraficaHistograma(configuracion, rangos, os);
    }

    private void graficaOjiva(Configuracion configuracion, List<Rango> rangos, OutputStream os) throws IOException {
        Graficador gr = new Graficador();
        gr.generaGraficaOjiva(configuracion, rangos, os);
    }

    private void graficaDispersion(Configuracion configuracion, List<Data> rangos, OutputStream os) throws IOException {
        Graficador gr = new Graficador();
        //System.out.println("Paso 1 intentando dispoersion");
        gr.generaGraficaDispercion(configuracion, rangos, os);
    }

    private List< Rango> getRangesFromStrings(String[] colors, String[] inits, String[] ends, String[] cuantos) {
        List< Rango> ranges = new LinkedList< Rango>();
        Integer rangesCount = Math.min(colors.length, inits.length);
        rangesCount = Math.min(rangesCount, ends.length);
        Rango range = null;
        int p = rangesCount - 1;
        for (int i = 0; i < rangesCount; i++) {
            if (colors[ i].isEmpty() || inits[ i].isEmpty() || ends[ i].isEmpty()) {
                range = new Rango("0 0 0", "0.0", "0.0");
            } else {
                range = new Rango(colors[ p--], inits[ i], ends[ i], cuantos[i]);
                System.out.println("Aki... " + range);
            }
            ranges.add(range);
        }
        if (!ranges.isEmpty()
                && (ranges.get(0).getFinD() > ranges.get(ranges.size() - 1).getFinD())) {
            Collections.reverse(ranges);//reverse if the list comes in descending order
        }
        return ranges;
    }
}
