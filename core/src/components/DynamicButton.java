package components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import screens.game_screens.Utils.GameAssets;

public class DynamicButton {
    private Texture imageBtn;
    private Rectangle shapeBtn;
    private int x, y, width, height;

    public DynamicButton(String src, int x, int y) {
        imageBtn = new Texture(src);

        if(x == -1)
            this.x = (Gdx.graphics.getWidth() / 2) - (imageBtn.getWidth() /2);
        else
            this.x = x;

        if(y == -1)
            this.y = (Gdx.graphics.getHeight() / 2) - (imageBtn.getHeight() /2);
        else
            this.y = y;

        this.width = imageBtn.getWidth();
        this.height = imageBtn.getHeight();

        shapeBtn = new Rectangle(x, y, width, height);
    }

    public boolean isPressed(){
        int posX = Gdx.input.getX();
        int posY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if(Gdx.input.justTouched()){
            if( (posX > (getX()*1.05))
                && (posX < (getX() + getWidth()*0.9))
            && (posY > getY()*1.15)
            && (posY < (getY() + getHeight()*0.9)) ){
                GameAssets.sound12.setVolume(GameAssets.sound12.play(), 3f);
                return true;
            }
        }

        return  false;
    }

    public Texture getButton(){
        return imageBtn;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void dispose(){
        imageBtn.dispose();
    }
}
