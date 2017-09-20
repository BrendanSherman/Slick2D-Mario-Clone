import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;

import static java.lang.Thread.sleep;

public class MarioClone extends BasicGame
{
    private Box box1;
    public Image marioRight;
    public Image marioLeft;
    private Image bg;
    private int marioX;
    private int marioY;
    private Rectangle marioRectangle = new Rectangle(marioX, marioY, 96, 128);
    private Music song = new Music("resources/music/song1.ogg");
    public String MarioCurrentDir = "right";
    private int jumpStage = 0;
    private boolean upJumping = false;
    private Sound jumpSound = new Sound("resources/music/smb_jump-small.wav");

    public MarioClone(String gamename, int x, int y) throws SlickException {
        super(gamename);
        this.marioX = x;
        this.marioY = y;
    }

    public void init() throws SlickException{
        // loads sprites (inc. Mario, the background, and blocks.)
        box1 = new Box("resources/images/blocks/questionMarkBlock1.png", 500, 952);
        marioRight = new Image("resources/images/marioFacingRight.png");
        marioLeft = new Image("resources/images/marioFacingLeft.png");
        bg = new Image("resources/images/background1.jpg");
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        song.play();
        song.loop();
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        marioRectangle.setX(marioX);
        marioRectangle.setY(marioY);

        if (marioY < 930 && jumpStage == 0){ //gravity
            marioY+=4;
        }

        if(box1.leftCollision(marioRectangle)){
            marioX--;
        }


        Input input = gc.getInput();
        if(input.isKeyDown(Input.KEY_RIGHT)){
            marioX += 2;
            MarioCurrentDir = "right";
        }

        if(input.isKeyDown(Input.KEY_LEFT)){
            marioX -= 2;
            MarioCurrentDir = "left";
        }

        if (input.isKeyDown(Input.KEY_SPACE) && jumpStage == 0){
            jumpStage++;
            jumpSound.play();
        }

        if(jumpStage > 0) {
            if (jumpStage >= 1 && jumpStage < 60) {
                marioY -= 4;
                jumpStage++;
            } else if (jumpStage >= 60 && jumpStage < 120) {
                marioY += 4;
                jumpStage++;
            }
            if (jumpStage == 120) {
                jumpStage = 0;
                marioY -= 4;
            }
        }
        init();

    }

    public void keyPressed(GameContainer gc, int key, char c) throws SlickException{
        super.keyPressed(key, c);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        init();
        //draws the background
        bg.draw(0,0);
        //draws the ground (i think)
        g.setColor(Color.green);
        g.fillRect(0, 1050, 1920, 30);
        //draws Mario
        marioRight.draw(marioX, marioY);

    }

    public static void main(String[] args)
    {
        try // Creates a new AppContainer and sets resolution, update interval, fullscreen status, and target framerate.
        {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new MarioClone("Game", 30, 930));
            appgc.setDisplayMode(1920, 1080, false);
            appgc.setMinimumLogicUpdateInterval(5);
            appgc.setMaximumLogicUpdateInterval(5);
            appgc.setTargetFrameRate(60);
            appgc.start();

        }
        catch (SlickException ex) //Generates a logger if AppContainer fails to start
        {
            Logger.getLogger(MarioClone.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}