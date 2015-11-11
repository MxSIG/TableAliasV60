package tablealias.dinue.helpers;

/**
 *
 * @author INEGI
 */
public enum EstratosEnum {

    Uno(1){

        @Override
        public String getDescripcion() {
            return "0 a 5 personas";
        }

    }, Dos(2){

        @Override
        public String getDescripcion() {
            return "6 a 10 personas";
        }

    }, Tres(3){

        @Override
        public String getDescripcion() {
            return "11 a 30 personas";
        }

    }, Cuatro(4){

        @Override
        public String getDescripcion() {
            return "31 a 50 personas";
        }

    }, Cinco(5){

        @Override
        public String getDescripcion() {
            return "51 a 100 personas";
        }

    }, Seis(6){

        @Override
        public String getDescripcion() {
            return "101 a 250 personas";
        }

    }, Siete(7){

        @Override
        public String getDescripcion() {
            return "251 y mas personas";
        }

    };

    private int id;
    private String descripcion;



    private EstratosEnum(int id) {
        this.id = id;
    }

    public String getDescripcion(){
        return descripcion;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }





}
