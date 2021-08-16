package components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import utilities.Resource;

public class BookLevel extends DynamicButton{
    public static final int POS_ROW_ONE = 371;
    public static final int POS_ROW_TWO = 172;
    public static final int POS_X = 56;
    Texture numberImg;
    int index;


    public BookLevel(String src, int x, int y, int index) {
        super(src, x, y);

        numberImg = new Texture(Resource.getNumberBook(index+1));
        this.index = index;
    }

    public Texture getNumberImg(){
        return  this.numberImg;
    }


    public void paintNumber(SpriteBatch batch) {
        int y = this.getY() + 84 - (numberImg.getHeight() /4);
        int x=0;

        if(index == 9)
            x = (int) (this.getX() + (this.getWidth() / 2)) - (int)(numberImg.getWidth()*0.58);
        else if(index > 0 )
            x = (int) (this.getX() + (this.getWidth() / 2)) - (int)(numberImg.getWidth()*0.65);
        else
            x = (int) (this.getX() + (this.getWidth() / 2)) - (int)(numberImg.getWidth()*0.9);

        batch.draw(numberImg, x, y, numberImg.getWidth(), numberImg.getHeight());
    }
}
