package libgdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
    
    Label instrucciones;
    LabelStyle style;
    BitmapFont font;
    SpriteBatch batch;
    BaseActor fondo;
    Action color_instruc;
    Music music_menu;
    
    MonkeyMenu(Game game, World w, Monkey mono, ArrayList<Enemy>lista_enemigos, ArrayList<Enemy_2>lista_enemigos_2, ArrayList<Fruits> lista_frutas, ArrayList<Vidas> lista_vidas, ArrayList<StaticObjects>lista_objetos)
    {   
        super(game, w, mono, lista_enemigos, lista_enemigos_2, lista_frutas, lista_vidas, lista_objetos);
        
    }
    
    @Override
    public void create()
    {
       inic_acciones();
        batch= new SpriteBatch();
        fondo= new BaseActor();
        fondo.setTexture(new Texture(Gdx.files.internal("menu_inicio.png")));
        fondo.setPosition(0, 0);
        fondo.setSize(fondo.getWidth(), fondo.getHeight());
        mainStage.addActor(fondo);
        
        font= new BitmapFont();
        style = new LabelStyle(font, Color.BLUE);
        String texto=" Press S to start, P for paused game ";
        instrucciones = new Label(texto, style);
        instrucciones.setPosition(25, 20);
        instrucciones.setFontScale(2);
        instrucciones.addAction(color_instruc);
        uiStage.addActor(instrucciones);            
        music_menu=  (Gdx.audio.newMusic(Gdx.files.internal("music_menu.mp3")));
        music_menu.setVolume(0.2f);
        music_menu.play();
        music_menu.setLooping(true);
        
    }
    public void show() {
    
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
    }
   public boolean keyDown(int keycode) {
       if(keycode == Keys.S)
       {    
           game.setScreen((new MonkeyLevel(game, world, mono, lista_enemigos, lista_enemigos_2, lista_frutas, lista_vidas, lista_objetos)));
       }
        return false;
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
