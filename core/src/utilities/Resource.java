package utilities;

public class Resource {
    public static final String BG_MAIN_MENU = "main_menu.png";
    public static final String BG_CREDITS_SCREEN = "screen_credits.png";
    public static final String BG_LEVEL_MENU = "level_menu.png";
    public static final String BG_GAME_SCREEN = "screen_game.png";
    public static final String GAME_OVER = "game_over.png";
    public static final String BTN_PLAY = "btn_play.png";
    public static final String BTN_CREDITS = "btn_credits.png";
    public static final String BTN_QUIT = "btn_quit.png";
    public static final String BTN_BACK = "btn_back.png";
    public static final String BOOK_ALL_STARS = "book_3_star.png";
    public static final String BOOK_LOCK = "book_lock.png";

    public static String getBookStar(int numStars){
        if(numStars==0)
            return "book.png";

        return  "book_" + numStars + "_star.png";
    }

    public static String getLevelComplete(int numStars){
        return  "level_completed_" + numStars + "_stars.png";
    }

    public static String getNumberBook(int index){
        return  "number_" + index + ".png";
    }

}
