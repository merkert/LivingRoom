/**
 * Created by merkelu on 24.04.2017.
 */
public class Main {

    public static void main(String[] args){
        try {
            Scene scene = new Scene("Living Room", 1024, 840);
            scene.run();
        }catch(Exception ex){
            ex.printStackTrace();
            System.exit(-1);
        }
    }
}
