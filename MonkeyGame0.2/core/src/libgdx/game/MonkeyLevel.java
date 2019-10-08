package libgdx.game;
        
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
// import com.badlogic.gdx.physics.box2d.Body;
// import com.badlogic.gdx.physics.box2d.BodyDef; 
// import com.badlogic.gdx.physics.box2d.Fixture;
// import com.badlogic.gdx.physics.box2d.PolygonShape;
// import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class MonkeyLevel extends BaseScreen
{   
    //----MUSICA-------------------------
    Music song1;  //tiene dispose
    Music song2;
    //----MAPA---------------------------
    private final int tileSize = 64;
    private final int tileCountWidth =  13;//el de prueba  es de 30;
    private final int tileCountHeight = 11;//el de prueba es de 30;
    private  int vidas_mapa; 

    //cantidades a escojer de las listas
    private  int cant_enemigos;
    private  int cant_enemigos2;
    private  int cant_vidas;   
    private  int cant_frutas;  
    private  int cant_objetos;
    
     //world game dimensions
    private float mapHeight;
    private float mapWidth;
    
    
    
    //----ACTORES------------------------
    private BaseActor grama;
    private BaseActor winTex;
    private BaseActor gameoverTex;
    private boolean win;
    //----LABELS--------------------------
    private float suma_dt_Label;
    private Label vidaslabel;
    private Label nivelabel;
    private Label frutaslabel;
    private Label mono_muerto;
    private Label mono_siguiente_nivel;
    
    //----ACCIONES------------------------
    private Action fadeInColorCycleForever;
    private Action fadein;
    private float tiempo_resurreccion;

    

    //las posiciones de los enemigos movimiento circular
    //posicion_x, posicion_y, velocidad, diametro
        
    MonkeyLevel(MonkeyGame game)
    { 
        super(game);
        
        //ubicaci�n del archivo
        // game.set_json_data("C:\\Users\\abdia_000\\Desktop\\versiones del juego\\MonkeyGame0.2\\core\\assets\\nivel1\\nivel1.json");
	//el que estaba ---> game.set_json_data("../core/assets/nivel1/nivel1.json");
        game.set_json_data("nivel1.json");
        //game.set_json_data("nivel1.json");
        json= game.get_json_data();
        vidas_mapa=json.get_vidas_mapa();     //máximo vidas que va a tener el mono en el mapa, practicamente es un label
        cant_enemigos=json.get_cant_enemigos();
        cant_vidas=json.get_cant_vidas();     //va ha ver solo una vida en el mapa que va a reaparecer dado un tiempo
        cant_frutas=json.get_cant_frutas();   //cantidad de frutas que van a aparecer en el mapa
        cant_objetos=json.get_cant_objetos(); //cantidad de frutas que van a aparecer en el mapa

           

        mono.__init__(json.get_mono_xy()[0], json.get_mono_xy()[1], vidas_mapa);
        
        for (int i = 0; i < cant_frutas; i++) {
            //las posiciones  aleatorias de las frutas
            //posicion x, posicion y, si ocupado o no
           lista_frutas.get(i).__init__(json.get_matrix_frutas());
        }
        
        for (int i = 0; i < cant_vidas; i++) {
            //posicion x, posicion y, si ocupado o no, y tiempo de reaparicion. cant_vidas de la "LISTA_VIDAS" (no del for, el del for indica los objetos de "Vidas" a tomar en cuenta)
            //indica el numero de posiciones disponible que dispone una vida para trasladarze
            lista_vidas.get(i).__init__(json.get_matrix_vidas(), json.get_matrix_vidas()[i][3]);
        }

        for (int i = 0; i < cant_enemigos; i++) {
            //las posiciones de los enemigos movimiento patrulla
            //posicion x, y, velocidad, limite_izquierdo, limite_derecho, h_o_v
            lista_enemigos.get(i).__init__(json.get_matrix_enemigos()[i][0], json.get_matrix_enemigos()[i][1], json.get_matrix_enemigos()[i][2], json.get_matrix_enemigos()[i][3], json.get_matrix_enemigos()[i][4], json.get_matrix_enemigos()[i][5]);
        }
        for (int i = 0; i < cant_enemigos2; i++) {
                // enemigos movimiento circular
                // posicion_x, posicion_y, velocidad, diametro
                lista_enemigos_2.get(i).__init__(json.get_matrix_enemigos2()[i][0], json.get_matrix_enemigos2()[i][1],
                        json.get_matrix_enemigos2()[i][2], json.get_matrix_enemigos2()[i][3]);
            }

        for (int i = 0; i < cant_objetos; i++) {
            //float posicion_x, float posicion_y
            /*troncos{70, 644}, {133,0}, {326, 644}, {326, 580},
                     {581, 131}, {645, 644}, {645, 580}, {710, 580},
                     {519,73}, {581,73},
              piedras{196, 261}, {196, 69}, {260, 261}, {260, 132},
                     {260, 68}, {709, 133}, {709, 68}, {709, 4},
              arboles{79, 72}*/
            lista_objetos.get(i).__init__(json.get_matrix_objetos()[i][0], json.get_matrix_objetos()[i][1]);
        }
}
    
    public void create(){
        //---pruebas
        //mainStage.setDebugAll(true);

        //---MUSICA---------------------------------
        song1=(Music)game.get_manager().get("nivel1/Miami Nights 1984 - Accelerated.mp3");
        song2=(Music)game.get_manager().get("nivel1/Roosevelt - Shadows.mp3");

        song1.setVolume(0.2f);
        song2.setVolume(0.2f);

        //---MAPA-----------------------------------
        mapWidth= tileSize*tileCountWidth;
        mapHeight= tileSize*tileCountHeight;
        tiempo_resurreccion=3;
        
        inic_acciones();
 
        world.setContactListener(new ContactListener(){
            public boolean areCollided(Contact contact, Object userA, Object userB) {
                return(contact.getFixtureA().getUserData().equals(userA) && contact.getFixtureB().getUserData().equals(userB)) ||
                        (contact.getFixtureA().getUserData().equals(userB) && contact.getFixtureB().getUserData().equals(userA));
            }
            
            @Override
            public void beginContact(Contact contact) {
                
                //To change body of generated methods, choose Tools | Templates.
                if(areCollided(contact, "frutas", "mono"))
                {  
                  
                        for (Fruits e : lista_frutas) 
                            {
                                e.colision(mono);
                            }
                }
                if(areCollided(contact, "enemigos", "mono"))
                {  
                    
                    if(mono.cant_vidas >1)
                    {
                        mono.golpeado=true;
                        mono.cant_vidas--;
                        //esta es la accion de aparicion del mono
                        mono.addAction(Actions.parallel(
                        Actions.alpha(0),
                        Actions.fadeIn(tiempo_resurreccion)));
                    }
                    else
                        mono.vivo=false;
                }
                if(areCollided(contact, "vidas", "mono"))
                {  
                       if(mono.cant_vidas<vidas_mapa)
                       {
                        for (Vidas e : lista_vidas) 
                            {
                                e.colision(mono);
                            }
                       }
                }
            }
                
            @Override
            public void endContact(Contact contact) {
                //To change body of generated methods, choose Tools | Templates.
                
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                //To change body of generated methods, choose Tools | Templates.
            }
            
        });
                
        
    }
    public void dispose() {
        song1.dispose();
        song2.dispose();
        mainStage.dispose();
        uiStage.dispose();
    }

    public void show(){

        //---ACTORES-----------------------------------
        grama=new BaseActor();       
        grama.setTexture((Texture)game.get_manager().get("nivel1/nivel_1.png"));
        grama.setPosition(0, 0);
        grama.setSize(mapWidth, mapHeight);
        mainStage.addActor(grama);
        
        song1.play();
        // song1.setLooping(true);
        song1.setOnCompletionListener(new Music.OnCompletionListener()
        {
	        	@Override
				public void onCompletion(Music music){
                    song2.play();
                }
        });
        song2.setOnCompletionListener(new Music.OnCompletionListener()
        {
	        	@Override
				public void onCompletion(Music music){
                    song1.play();
                }

        });
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {

            	if(keyCode == Keys.P)
            		togglePaused();
            		 return false;
            }
        });
        
        for (Fruits e : lista_frutas){
            mainStage.addActor(e);
        }
        
        for (Vidas e : lista_vidas){
            mainStage.addActor(e);
        }
        
        for (Enemy e : lista_enemigos) {
            mainStage.addActor(e);
        }

        mainStage.addActor(mono);

        winTex = new BaseActor();
        winTex.setTexture((Texture)game.get_manager().get("escenario/you-win.png"));
        winTex.setPosition((windowWidth/2)-(winTex.getWidth()/2),(windowHeight/2));        
        winTex.setVisible(false);
        winTex.addAction(fadeInColorCycleForever);
        uiStage.addActor(winTex);
        win=false;
        
        gameoverTex = new BaseActor();
        gameoverTex.setTexture((Texture)game.get_manager().get("escenario/you-lose.png"));
        gameoverTex.setPosition((windowWidth/2)-(gameoverTex.getWidth()/2),(windowHeight/2));        
        gameoverTex.setVisible(false);
        gameoverTex.addAction(fadeInColorCycleForever);
        uiStage.addActor(gameoverTex);
        
        
        this.nivelabel= new Label("NIVEL: 1-2", new LabelStyle(new BitmapFont(), Color.BLUE));
        nivelabel.setPosition(45, 440);
        nivelabel.setFontScale(2);
        uiStage.addActor(nivelabel);
        
        this.vidaslabel= new Label("VIDAS: 0-3", new LabelStyle(new BitmapFont(), Color.BLUE));
        vidaslabel.setPosition(242, 440);
        vidaslabel.setFontScale(2);
        uiStage.addActor(vidaslabel);
        
        this.frutaslabel= new Label("FRUTAS: 0-3", new LabelStyle(new BitmapFont(), Color.BLUE));
        frutaslabel.setPosition(440, 440);
        frutaslabel.setFontScale(2);
        uiStage.addActor(frutaslabel);
        
        this.mono_siguiente_nivel= new Label(" Press N to go to next level", new LabelStyle(new BitmapFont(), Color.BLUE));
        mono_siguiente_nivel.setPosition(25, 20);
        mono_siguiente_nivel.setFontScale(2);
        mono_siguiente_nivel.setVisible(false);
        uiStage.addActor(mono_siguiente_nivel);
        
        this.mono_muerto= new Label(" Press S to start again, M for main menu ", new LabelStyle(new BitmapFont(), Color.BLUE));
        mono_muerto.setPosition(25, 20);
        mono_muerto.setFontScale(2);
        mono_muerto.setVisible(false);
        uiStage.addActor(mono_muerto);
    }
    public void hide()
    {
        song1.stop();
        song2.stop();
        // cuerpo.destroyFixture(cuerpo_fixture);
        // world.destroyBody(cuerpo);
        mainStage.clear();
        mainStage.dispose();
        uiStage.dispose();
        Gdx.input.setInputProcessor(null);
    }
    //para eleminar un objeto de el box2d con los pjs  hay que eliminarlos primero de box
    //si no da error
    
    public void update(float dt) {
       mono.velocityX=0.00f;
       mono.velocityY=0.00f;

       if(mono.vivo && !win) {
                
                world.step(dt, 6, 2);
                //aqui elimina las frutas
                for (Fruits e : lista_frutas){
                    if(e.colision && !e.marcado)
                    {       mono.cant_frutas++;
                            e.marcado=true;
                            e.inactivo();
                    }
                }
                for (Vidas e : lista_vidas){
                    if(e.colision && !e.marcado)
                    {   
                           mono.cant_vidas++;
                            e.marcado=true;
                            e.inactivo();
                    }
                }
                
                //aqui mueve al mono si chocó con un enimigo
                if(mono.golpeado)
                {   mono.tiempo_resurreccion+=dt;
                	mono.cuerpo.setTransform((json.get_mono_xy()[0] + mono.getOriginX()) /Constants.pixelametro, (json.get_mono_xy()[1] + mono.getOriginX()) /Constants.pixelametro, 0);

                    if(mono.tiempo_resurreccion >tiempo_resurreccion)
                    {
                    mono.golpeado=false;
                    mono.tiempo_resurreccion=0.00f;
                    }
                }

                //process input
                if(!mono.golpeado)
                {
                    if(Gdx.input.isKeyPressed(Keys.RIGHT))
                    {     //Esto es la transformacion de pixeles a metros            ||||   esto es la velovidad en pixeles  |||  esto es el limite de arriba + la velovidad en pixeles                             
                           //la condicion dice si la posicion donde estoy más el aumento de la velocidad en pixeles es menor a el limite del mapa mas el el aumento de la velocidad
                            //entonces sumale velocidad, usamos el valor de velocidad para limitar
                        
                        mono.setRotation(90);
                            mono.velocityX+=mono.maxSpeed;
                    }
                    if(Gdx.input.isKeyPressed(Keys.LEFT))
                    {   
                        
                        mono.setRotation(270);
                            mono.velocityX-=mono.maxSpeed;
                    }
                    if(Gdx.input.isKeyPressed(Keys.UP))
                    {   mono.setRotation(180);
                            mono.velocityY+=mono.maxSpeed;
                    }   
                    if(Gdx.input.isKeyPressed(Keys.DOWN))
                    {   mono.setRotation(0);
                            mono.velocityY-=mono.maxSpeed;
                    }

                    if(mono.cant_frutas == cant_frutas)
                    {
                        win=true;
                    }
                }
                
                
                this.vidaslabel.setText("VIDAS: "+mono.cant_vidas+"-"+this.vidas_mapa);
                this.frutaslabel.setText("FRUTAS: "+mono.cant_frutas+"-"+this.cant_frutas);
                 

                Camera cam = mainStage.getCamera();
                //ajusta la camara sobre el jugador
                                        //x                               y                            z
                cam.position.set(mono.getX() + mono.getOriginY(), mono.getY() + mono.getOriginY(), 0);
                //cam.position.set(mono.cuerpo.getPosition().x, mono.cuerpo.getPosition().y, 0);
                // límites de la camara
                cam.position.x = MathUtils.clamp(cam.position.x, windowWidth/2, mapWidth-windowWidth/2);
                cam.position.y = MathUtils.clamp(cam.position.y, windowHeight/2, mapHeight-windowHeight/2);
                cam.update();
                
                //cameragame.position.x=mono.cuerpo.getPosition().x;
                //cameragame.position.y=mono.cuerpo.getPosition().y;
                //cameragame.update();
       }
       else if(win)
       {
           mono_gano();
       }
       else
       {
           mono_muerto();
       }
    }
    
    
    
    private void inic_acciones()
    {
       
        fadeInColorCycleForever = Actions.sequence(
                Actions.alpha(0),
               //Actions.show(),
               Actions.fadeIn(2),
                Actions.forever(
                        Actions.sequence(
                                Actions.color(new Color(0.5f, 0.5f, 0.5f, 1), 1),
                                Actions.color(new Color(1, 1, 1, 1), 1)
                        )
                )
                );        
    }

   // public boolean keyDown(int keycode) {
   //     if(keycode == Keys.P)
   //         togglePaused();
   //      return false;
   //  }
    public boolean scrolled(int amount){
        
        return false;
    }

   public void mono_muerto()
   {   gameoverTex.setVisible(true);
       mono_muerto.setVisible(true);
       
       if(Gdx.input.isKeyPressed(Keys.S))
          {   
              mueve_todo();
              game.setScreen((new MonkeyLevel(game)));
          }
      if(Gdx.input.isKeyPressed(Keys.M)){
           mueve_todo();
           game.setScreen((new MonkeyMenu(game)));
          }
    }
   public void mono_gano()
   {    
       winTex.setVisible(true);
       mono_siguiente_nivel.setVisible(true);
       if(Gdx.input.isKeyPressed(Keys.N))
          {   
              mueve_todo();
              game.setScreen((new MonkeyLevel2(game)));
          }
   }
   public void mueve_todo()
   {
        for (int i = 0; i < cant_frutas; i++) {
           game.lista_frutas.get(i).sacar_objeto(-200,-200);
        }
        
        for (int i = 0; i < cant_vidas; i++) {
            game.lista_vidas.get(i).sacar_objeto(-200,-200);
        }

        for (int i = 0; i < cant_enemigos; i++) {
            game.lista_enemigos.get(i).sacar_objeto(-200,-200);
        }
        for (int i = 0; i < cant_enemigos2; i++) {
                game.lista_enemigos_2.get(i).sacar_objeto(-200,-200);
            }

        for (int i = 0; i < cant_objetos; i++) {
            game.lista_objetos.get(i).sacar_objeto(-200,-200);
        }
   }

}
    
