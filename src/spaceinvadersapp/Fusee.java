package spaceinvadersapp;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

public class Fusee {
    protected int posX;
    protected int posY;
    private int size;
    private int score = 0;
    private int vie = 3;
    protected boolean explosion = false;
    protected boolean detruit = false;
    private boolean updatedLife = false;
    protected Image img;
    protected Image boom = new Image("/Images/explosion.png");
    private Image IMAGE_TIR = new Image("/Images/bullet.png");
    protected int dureeExplosition = 0;
    private final java.net.URL resource = getClass().getResource("/sound_effects/boom.mp3");
    private final Media media = new Media(resource.toString());
    private MediaPlayer mediaPlayer = new MediaPlayer((media));
    
    public Fusee(int posX,int posY, int size, Image img){
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.img = img;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getSize() {
        return size;
    }

    public int getScore() {
        return score;
    }
    
    public int getVie(){
        return vie;
    }

    public boolean isExplosion() {
        return explosion;
    }

    public boolean isDetruit() {
        return detruit;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setExplosion(boolean explosion) {
        this.explosion = explosion;
        if (!updatedLife){
            vie--;
            updatedLife = true;
        }
    }

    public void setDetruit(boolean detruit) {
        if (vie == 0)
        this.detruit = detruit;
    }

    public Tir tir(){
        return new Tir(getPosX() + getSize()/2,getPosY() - 30,IMAGE_TIR,1);
    }
    
    public void update(int a){
        score += a;
    }
    int getDureeExplosion()
    {
    	return dureeExplosition;
    }
    public void affiche(GraphicsContext gc){
        if(explosion){
            if(dureeExplosition < 10){
            	gc.setFill(Color.grayRgb(20));
                gc.fillRect(this.getPosX(), this.getPosY(), this.getSize(), this.getSize());
                gc.drawImage(boom, posX, posY, size, size);
                dureeExplosition++;
                
            }
            else{
                explosion = false;
                dureeExplosition = 0;
                updatedLife = false;
            }
        }else{
            gc.drawImage(img, posX, posY, size, size);
        }
    }
    public void moveRight(GraphicsContext gc,int width){
        if(this.getPosX() <= width - size - 20){
            gc.setFill(Color.grayRgb(20));
            gc.fillRect(this.getPosX(), this.getPosY(), this.getSize(), this.getSize());
            this.setPosX(this.getPosX()+10);
            affiche(gc);
        }
    }
    public void moveLeft(GraphicsContext gc,int width){
        if(this.getPosX() >= 20){
            gc.setFill(Color.grayRgb(20));
            gc.fillRect(this.getPosX(), this.getPosY(), this.getSize(), this.getSize());
            this.setPosX(this.getPosX()-10);
            affiche(gc);
        }
    }
    

    public boolean collision(Tir tir){
        if((tir.getPosX() >= getPosX() && tir.getPosX() <= (getPosX() + getSize())) && (tir.getPosY() <= (getSize() + getPosY())) && tir.getPosY() >= getPosY()){
            
            return true;
        }
        return false;
    }
}
