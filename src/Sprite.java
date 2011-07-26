import java.awt.Image;

public class Sprite {
    private Image image;
    private float x;
    private float y;
    private float velocityX;
    private float velocityY;
    
    public Sprite(Image image) {
        this.image = image;
        x = 0;
        y = 0;
        velocityX = 0;
        velocityY = 0;
    }
    
    //change position
    public void update(long timePassed) {
        x += velocityX * timePassed;
        y += velocityY * timePassed;
    }
    
    //get x position
    public float getX() {
        return x;
    }
    
    //get y position
    public float getY() {
        return y;
    }
    
    //set x position
    public void setX(float x) {
        this.x = x;
    }
    
    //set y position
    public void setY(float y) {
        this.y = y;
    }
    
    //get image width
    public int getWidth() {
        return image.getWidth(null);
    }
    
    //get image height
    public int getHeight() {
        return image.getHeight(null);
    }
    
    //get horizontal velocity
    public float getVelocityX() {
        return velocityX;
    }
    
    //get vertical velocity
    public float getVelocityY() {
        return velocityY;
    }
    
    //set horizontal velocity
    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }
    
    //set vertical velocity
    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }
    
    //get sprite image
    public Image getImage() {
        return image;
    }
}