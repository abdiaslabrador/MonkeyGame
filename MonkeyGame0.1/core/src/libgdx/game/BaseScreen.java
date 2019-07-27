package libgdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import java.util.ArrayList;
/**
 *
 * @author Familia
 */
public abstract class BaseScreen implements Screen, InputProcessor {
    protected OrthographicCamera cameragame;
    protected Box2DDebugRenderer b2dr;
    protected Viewport gameport;
    protected MonkeyGame game;
    protected Stage mainStage;
    protected Stage uiStage;
    public final int windowHeight = 480;
    public final int windowWidth = 640;
    private boolean paused;
    
    public World world;
    public Monkey mono;
    public ArrayList<Enemy> lista_enemigos;
    public ArrayList<Enemy_2> lista_enemigos_2;
    public ArrayList<Fruits> lista_frutas;
    public ArrayList<Vidas> lista_vidas;
    public ArrayList<StaticObjects> lista_objetos;
    public Json_data json;
    
//input procesor se encarga de manejar diferentes entras, se puede manejar entradas de diferentes maneras
//lo puede manejas las que heredan del basescreen o los ecenarios, es dependiendo del caso
    BaseScreen(MonkeyGame game) {
        cameragame= new OrthographicCamera(/*1920/Constants.pixelametro, 1920/Constants.pixelametro*/); //este viewport es colocado con 30 para que en Boxd2 se vea más cerca
        gameport = new FitViewport(30, 30, cameragame); //esto controla 
        b2dr= new Box2DDebugRenderer();
        
        paused = false;
        this.game = game;
        this.world= game.world;//new World(new Vector2(0,0), true);
        mainStage = new Stage(new FitViewport(windowWidth, windowHeight));
        uiStage = new Stage(new FitViewport(windowWidth, windowHeight));
        InputMultiplexer im = new InputMultiplexer(this, uiStage, mainStage);
        Gdx.input.setInputProcessor(im);
        
        this.mono=game.mono;
        this.lista_enemigos=game.lista_enemigos;
        this.lista_enemigos_2=game.lista_enemigos_2;
        this.lista_frutas=game.lista_frutas;
        this.lista_vidas=game.lista_vidas;
        this.lista_objetos=game.lista_objetos;
        
        
        
        create();
    }

    public abstract void create();

    public abstract void update(float dt);

    public void render(float dt) {

        uiStage.act(dt);
        
        if (!isPaused()) {
            
            mainStage.act(dt);
            update(dt);
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //b2dr.render(world, cameragame.combined);
        mainStage.draw();
        uiStage.draw();

    }

// pause methods
    public Boolean isPaused()
    {return paused;}

    public void setPaused(boolean b) 
    { paused = b; }

    public void togglePaused() 
    {paused = !paused;}

    public void resize(int width, int height) {
       mainStage.getViewport().update(width, height, true);
       uiStage.getViewport().update(width, height, true);
      //gameport.update(width, height);
    }
    //Screen interface
    
    public void show() {}
    public void pause() { }
    public void resume() { }
    public void hide() {
        world.dispose();
        mainStage.dispose();
        uiStage.dispose();
        
    }
    public void dispose() { }

//Inputprocessor methods
    public boolean keyDown(int keycode) {
        return false;
    }

    public boolean keyUp(int keycode) {
        return false;
    }

    public boolean keyTyped(char c) {
        return false;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    public boolean scrolled(int amount) {
        
        return false;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }
}
