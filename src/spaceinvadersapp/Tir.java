package spaceinvadersapp;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Tir {
    private int posX;
    private int posY;
    private int speeed = 10;
    private int size = 18;
    private Image img;
    private int direction; 
    private boolean detruit=false;
    
    public Tir(int posX, int posY, Image img, int direction){
        this.posX = posX;
        if (direction == 1)
            this.posY = posY-speeed*2;
        else
             this.posY = posY;
        this.direction = direction;
        this.img = img;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getSpeeed() {
        return speeed;
    }

    public int getSize() {
        return size;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setSpeeed(int speeed) {
        this.speeed = speeed;
    }

    public void setSize(int size) {
        this.size = size;
    }
    public void setdetruit()
    {
    	detruit=true;
    }
    public void update(GraphicsContext gc){
    	if(detruit)
    	{
    		 gc.setFill(Color.grayRgb(20));
             gc.fillRect(this.getPosX() , this.getPosY() + this.getSpeeed(), this.getSize(), this.getSize()*2);
    	}
    	else
    	{
    		 if (direction == 1)
    	            posY -= speeed;
    	        else posY += speeed;
    	}
       
    }
    
    public void affiche(GraphicsContext gc){
        if (direction == 1){
            gc.setFill(Color.grayRgb(20));
            gc.fillRect(this.getPosX() , this.getPosY() + this.getSpeeed(), this.getSize(), this.getSize()*2);
            gc.drawImage(img, posX, posY, size, size*2);
        }else{
            gc.setFill(Color.grayRgb(20));
            gc.fillRect(this.getPosX(), this.getPosY() - speeed, this.getSize(), this.getSize());
            gc.drawImage(img, posX, posY, size, size*2);
        }
    }
    
    public boolean collision(Alien alien){
        return false;
    }
}
