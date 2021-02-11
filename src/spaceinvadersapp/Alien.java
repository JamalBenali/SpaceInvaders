package spaceinvadersapp;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Alien extends Fusee{
    private static int vitesse = 5;
    private static int direction = 1;
    private Image IMAGE_TIR = new Image("/Images/bullet2.png");

    public Alien(int posX, int posY, int size, Image img) {
        super(posX, posY, size, img);
    }

    public static int getVitesse() {
        return vitesse;
    }

    public static int getDirection() {
        return direction;
    }
    
    public void setDetruit(boolean detruit) {
        this.detruit = detruit;
    }

    public static void setVitesse(int vitesse) {
        Alien.vitesse = vitesse;
    }

    public static void setDirection(int direction) {
        Alien.direction = direction;
    }
    
    public void affiche(GraphicsContext gc){
        if(detruit){
            if(dureeExplosition < 20){
            	gc.setFill(Color.grayRgb(20));
                gc.fillRect(this.getPosX(), this.getPosY(), this.getSize(), this.getSize());
                gc.drawImage(boom, posX, posY, this.getSize(), this.getSize());
                dureeExplosition++;
            }
            else
            	{
            	img = null;
            	dureeExplosition++;
            	}
        }else{
            if(img == null){
                gc.setFill(Color.grayRgb(20));
                gc.fillRect(this.getPosX(), this.getPosY(), this.getSize(), this.getSize());
            }
            else gc.drawImage(img, posX, posY, this.getSize(), this.getSize());
        }
    }
    
    public Tir tir(){
        return new Tir(getPosX(),getPosY() + 50,IMAGE_TIR, 0);
    }
    
    public void update(GraphicsContext gc){
        if(direction == 1){
            gc.setFill(Color.grayRgb(20));
            gc.fillRect(this.getPosX(), this.getPosY() - 15, this.getSize(), this.getSize() + 15);
            this.setPosX(this.getPosX()+vitesse);
            affiche(gc);
        }else{
            gc.setFill(Color.grayRgb(20));
            gc.fillRect(this.getPosX(), this.getPosY() - 15, this.getSize(), this.getSize() + 15);
            this.setPosX(this.getPosX()-vitesse);
            affiche(gc);
        }
    }
    
}
