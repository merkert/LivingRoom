/**
 * Created by merkelu on 24.04.2017.
 */
public class Scene implements Runnable{

    private final Window window;
    private Logic logic;
    public Scene(String title, int width, int height)
    {
        window = new Window(title,width,height);
        logic = new Logic();

    }

    public void init() throws Exception {
        window.init();
        logic.init();
    }

    @Override
    public void run(){
        try{
            init();
            loop();
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    public void loop() throws InterruptedException {
        long currentTime = System.currentTimeMillis();
        while(!window.windowShouldClose()){
            Thread.sleep(5);
            if(System.currentTimeMillis()-currentTime >= 1000)
            {
                logic.addtime();
                currentTime = System.currentTimeMillis();
            }
            render();
        }
    }
    public void render(){
        window.update();
        logic.render(window);
    }
}
