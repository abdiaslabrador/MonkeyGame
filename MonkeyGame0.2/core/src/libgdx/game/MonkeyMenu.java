package libgdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;

public class MonkeyMenu extends BaseScreen{
    
    //BaseActor fondo;
    Label teclas;
    Label flechas;
    Label pause_;
    Label instrucciones;
    LabelStyle style;
    BitmapFont font;
    Action color_instruc;


    SpriteBatch batch;
    BaseActor fondo;
    
    Music music_menu;
    
    MonkeyMenu(MonkeyGame game)
    {   
        super(game);
    }
    
    @Override
    public void create()
    {
       inic_acciones();
        batch= new SpriteBatch();
        fondo= new BaseActor();
        fondo.setTexture((Texture)game.get_manager().get("menu_inicio/menu_inicio.png"));
        fondo.setPosition(0, 0);
        fondo.setSize(fondo.getWidth(), fondo.getHeight());
        mainStage.addActor(fondo);
        
        teclas= new Label("keys to play:", new LabelStyle(new BitmapFont(), Color.YELLOW));
        teclas.setPosition(53, 124);
        teclas.setFontScale(1.5f);
        uiStage.addActor(teclas);

        flechas= new Label("* Move: <, >, v, ^ (common arrows to move)", new LabelStyle(new BitmapFont(), Color.YELLOW));
        flechas.setPosition(75, 95);
        flechas.setFontScale(1.3f);
        uiStage.addActor(flechas);

        pause_= new Label("* Pause: P", new LabelStyle(new BitmapFont(), Color.YELLOW));
        pause_.setPosition(75, 70);
        pause_.setFontScale(1.3f);
        uiStage.addActor(pause_);

        font= new BitmapFont();
        style = new LabelStyle(font, Color.BLUE);
        String texto="Press S to start the game";
        instrucciones = new Label(texto, style);
        instrucciones.setPosition(45, 20);
        instrucciones.setFontScale(2);
        instrucciones.addAction(color_instruc);
        uiStage.addActor(instrucciones); 

        music_menu=  (Music)game.get_manager().get("menu_inicio/music_menu.mp3");
        music_menu.setVolume(1);
        music_menu.play();
        music_menu.setLooping(true);
        
    }
     public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {

                if (keyCode == Input.Keys.S) {
                    game.setScreen(new MonkeyLevel(game));
                }

                return true;
            }
        });
    }
    public void inic_acciones()
    {
        color_instruc =  Actions.forever(
                        Actions.sequence(
                                Actions.color(new Color(0.5f, 0.5f, 0.5f, 1), 1),
                                Actions.delay( 0.5f ),
                                Actions.color(new Color(1, 1, 1, 1), 1)
                        )
                );
    }
    @Override
    public void hide()
    {
        music_menu.stop();
        mainStage.clear();
        Gdx.input.setInputProcessor(null);

    }
    
    public void update(float dt) {}
     @Override
    public void pause(){}
     @Override
    public void resume(){}
    
    
    @Override
    public void dispose(){
        font.dispose();
        music_menu.dispose();
        mainStage.dispose();
        uiStage.dispose();
    }
    

   
}
