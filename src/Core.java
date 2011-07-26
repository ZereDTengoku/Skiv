import java.awt.*;

public abstract class Core {
    
    private boolean running;
    private static DisplayMode modes[] = {
        new DisplayMode(800,600,32,70),
        new DisplayMode(800,600,24,70),
        new DisplayMode(800,600,16,70),
        new DisplayMode(640,480,32,70),
        new DisplayMode(640,480,24,70),
        new DisplayMode(640,480,16,70)
    };
    
    protected ScreenManager screenManager;
    
    //call init and starts loop
    public void run() {
        try {
            init();
            gameLoop();
        } finally {
            screenManager.restoreScreen();
        }
    }
    
    //init sets to full screen
    protected void init() {
        running = true;
        
        screenManager = new ScreenManager();
        DisplayMode dm = screenManager.findFirstCompatibleMode(modes);
        if(dm == null) {
            System.out.println("Last attempt of figuring out DisplayMode");
            dm = new DisplayMode(800, 600, DisplayMode.BIT_DEPTH_MULTI, DisplayMode.REFRESH_RATE_UNKNOWN);
        }
        
        if(!screenManager.setFullScreen(dm)) {
            System.out.println("Couldn't figure out DisplayMode. Shutting Down...");
            stop();
        }
        
        Window w = screenManager.getFullScreenWindow();
        w.setFont(new Font("Arial", Font.PLAIN, 20));
        
        w.setBackground(Color.BLACK);
        w.setForeground(Color.WHITE);
    }
    
    //main loop
    protected void gameLoop() {
        long startTime = System.currentTimeMillis();
        long acumulatedTime = startTime;
        
        while(running) {
            long timePassed = System.currentTimeMillis() - acumulatedTime;
            acumulatedTime += timePassed;
            
            update(timePassed);
            
            Graphics2D g = screenManager.getGraphics();
            draw(g);
            g.dispose();
            screenManager.update();
            
            try {
                Thread.sleep(20);
            } catch(Exception e) {
                System.out.println(e.toString());
                System.out.println(e.getCause());
            }
        }
    }
    
    //stops game
    public void stop() {
        running = false;
    }
    
    //update animation
    protected void update(long timePassed) {}
    
    //draws to the screen (needs to be overriden)
    protected abstract void draw(Graphics2D g);
    
    
}