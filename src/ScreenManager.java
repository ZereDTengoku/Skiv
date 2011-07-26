import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;


public class ScreenManager {
    private GraphicsDevice graphicsDevice;
    
    public ScreenManager() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsDevice = env.getDefaultScreenDevice();
    }
    
    //compares modes passed to the ones you graphic card supports
    public DisplayMode findFirstCompatibleMode(DisplayMode modes[]) {
        DisplayMode goodModes[] = getCompatibleDisplayModes();
        for(int i = 0; i < modes.length; i++) {
            for(int j = 0; j < goodModes.length; j++) {
                if(displayModesMatch(modes[i], goodModes[j])) {
                    return modes[i];
                }
            }
        }
        return null;
    }
    
    
    
    //set full screen
    public boolean setFullScreen(DisplayMode dm) {
        JFrame f = new JFrame();
        f.setUndecorated(true);
        f.setIgnoreRepaint(true);
        f.setResizable(false);
        graphicsDevice.setFullScreenWindow(f);
        
        if(dm != null && graphicsDevice.isDisplayChangeSupported()) {
            try {
                graphicsDevice.setDisplayMode(dm);
            } catch(Exception e) {
                System.out.println("Couldn't set display mode " + e.toString());
                return false;
            }
        }
        f.createBufferStrategy(2);
        return true;
    }
    
    //get out of full screen
    public void restoreScreen() {
        Window w = graphicsDevice.getFullScreenWindow();
        if(w != null) {
            w.dispose();
        }
        graphicsDevice.setFullScreenWindow(null);
    }
    
    //get graphics (used for bufferStrategy)
    public Graphics2D getGraphics() {
        Window w = graphicsDevice.getFullScreenWindow();
        if(w != null) {
            BufferStrategy bufferStrategy = w.getBufferStrategy();
            return (Graphics2D)bufferStrategy.getDrawGraphics();
        } else {
            return null;
        }
    }
    
    //update display (used for buffer Strategy)
    public void update() {
        Window w = graphicsDevice.getFullScreenWindow();
        if(w != null) {
            BufferStrategy bufferStrategy = w.getBufferStrategy();
            if(!bufferStrategy.contentsLost()) {
                bufferStrategy.show();
            }
        }
    }
    
    //get current display mode
    public DisplayMode getCurrentDisplayMode() {
        return graphicsDevice.getDisplayMode();
    }
    
    //returns full screen window
    public Window getFullScreenWindow() {
        return graphicsDevice.getFullScreenWindow();
    }
    
    //get width of window
    public int getWidth() {
        Window w = graphicsDevice.getFullScreenWindow();
        if(w != null) {
            return w.getWidth();
        } else {
            return 0;
        }
    }
    
    //get height of window
    public int getHeight() {
        Window w = graphicsDevice.getFullScreenWindow();
        if(w != null) {
            return w.getHeight();
        } else {
            return 0;
        }
    }
    
    //get all compatible DM
    private DisplayMode[] getCompatibleDisplayModes() {
        return graphicsDevice.getDisplayModes();
    }
    
    //check if two modes match eachother
    private boolean displayModesMatch(DisplayMode m1, DisplayMode m2) {
        if(m1.getWidth() != m2.getWidth() || m1.getHeight() != m2.getHeight()) {
            return false;
        }
        if(m1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && m2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && m1.getBitDepth() != m2.getBitDepth()) {
            return false;
        }
        if(m1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && m2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && m1.getRefreshRate() != m2.getRefreshRate()) {
            return false;
        }
        return true;
    }
}