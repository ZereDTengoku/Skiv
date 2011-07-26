import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Skiv extends Core implements KeyListener {
    
    private Sprite sprite;
    private Image player;
    private Image enemy;
    
    private boolean started = false;
    private boolean loose = false;
    
    //environment
    private String currentDir = System.getProperty("user.dir");
    private String fileSeparator = System.getProperty("file.separator");
    
    public static void main(String[] args) {
        new Skiv().run();
    }
    
    //init also call init from superclass (gotta fix this... it's just too messed up for me).
    public void init() {
        super.init();
        Window w = screenManager.getFullScreenWindow();
        w.setFocusTraversalKeysEnabled(false);
        w.addKeyListener(this);
        loadImages();
    }

    //draw method called from super class in main loop
    public synchronized void draw(Graphics2D g) {
        Window w = screenManager.getFullScreenWindow();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(w.getBackground());
        g.fillRect(0, 0, screenManager.getWidth(), screenManager.getHeight());
        g.setColor(w.getForeground());
        
        if(!loose) {
            if(!started) {
                g.drawString("Please... PLEASE... do not touch the red square!", 60, 300);
                g.drawString("Press enter to start", 30, 30);
            } else {
                g.drawImage(sprite.getImage(), Math.round(sprite.getX()), Math.round(sprite.getY()), null);
                g.drawImage(enemy, screenManager.getWidth() - enemy.getWidth(null), 0, null);
            }
        } else {
            g.drawString("I told you... Now press enter if you wanna try again", 30, 30);
        }
    }
    
    @Override
    public void update(long timePassed) {
        if(started) {
            sprite.update(timePassed);
            int width = screenManager.getWidth();
            int height = screenManager.getHeight();

            //What? You're out of the screen. Let me help you
            if(sprite.getX() < 0) {
                sprite.setX(0f);
            }
            if(sprite.getX() + sprite.getImage().getWidth(null) >= width) {
                sprite.setX((float)(width - sprite.getImage().getWidth(null)));
            }
            if(sprite.getY() < 0) {
                sprite.setY(0f);
            }
            if(sprite.getY() + sprite.getImage().getHeight(null) >= height) {
                sprite.setY((float)(height - sprite.getImage().getHeight(null)));
            }
            
            //Have you touched the red square?
            if((sprite.getX() + sprite.getImage().getWidth(null)) > (screenManager.getWidth() - enemy.getWidth(null)) 
                    && sprite.getY() <= enemy.getHeight(null)) {
                loose = true;
            }
        }
        
    }
    
    //keyPressed
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        //wanna exit?
        if(keyCode == KeyEvent.VK_ESCAPE) {
            stop();
        }
        
        //have you started game?
        if(keyCode == KeyEvent.VK_ENTER) {
            started = true;
            loose = false;
            sprite = new Sprite(player);
        }
        
        if(started) {
            //set movement
            if(keyCode == KeyEvent.VK_UP) {
                sprite.setVelocityY(-0.3f);
            }
            if(keyCode == KeyEvent.VK_DOWN) {
                sprite.setVelocityY(0.3f);
            }
            if(keyCode == KeyEvent.VK_RIGHT) {
                sprite.setVelocityX(0.3f);
            }
            if(keyCode ==KeyEvent.VK_LEFT) {
                sprite.setVelocityX(-0.3f);
            }
        }
        
    }
    
    //keyReleased
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        //set movement
        if(keyCode == KeyEvent.VK_UP) {
            sprite.setVelocityY(0f);
        }
        if(keyCode == KeyEvent.VK_DOWN) {
            sprite.setVelocityY(0f);
        }
        if(keyCode == KeyEvent.VK_RIGHT) {
            sprite.setVelocityX(0f);
        }
        if(keyCode ==KeyEvent.VK_LEFT) {
            sprite.setVelocityX(0f);
        }
    }
    
    //keyTyped
    public void keyTyped(KeyEvent e) {
        e.consume();
    }
    
    //load images
    private void loadImages() {
        player = new ImageIcon(currentDir +  fileSeparator + "square_blue.png").getImage();
        enemy = new ImageIcon(currentDir + fileSeparator + "square_red.png").getImage();
    }
}