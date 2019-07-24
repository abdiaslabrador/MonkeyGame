package libgdx.game;
        
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;



public class MonkeyLevel extends BaseScreen
{   
    //----MUSICA-------------------------
    Music music_nivel;  //tiene dispose
    //----MAPA---------------------------
    private final int tileSize = 64;
    private final int tileCountWidth =  13;//el de prueba  es de 30;
    private final int tileCountHeight = 11;//el de prueba es de 30;
    private static int vidas_mapa=3; //máximo vidas que va a tener el mono en el mapa

    //cantidades a escojer de las listas
    private static int cant_enemigos_1_nivel=11;
    private static int cant_vidas=1;   //va ha ver solo una vida en el mapa que va a reaparecer dado un tiempo
    private static int cant_frutas=3;  //cantidad de frutas que van a aparecer en el mapa
    private static int cant_objetos=19;  //cantidad de frutas que van a aparecer en el mapa
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
    //private Label tiempolabel;
    private Label vidaslabel;
    private Label nivelabel;
    private Label frutaslabel;
    private Label mono_muerto;
    private Label mono_siguiente_nivel;
    
    //----ACCIONES------------------------
    private Action fadeInColorCycleForever;
    private Action fadein;
    private float tiempo_resurreccion;
    //---BOXD2----------------------------
    public Body cuerpo;
    public Fixture cuerpo_fixture;
    PolygonShape cuerpo_forma;
    
    public Body cuerpo_prueba;
    public Fixture cuerpo_fixture_prueba;
    PolygonShape cuerpo_forma_prueba;
    
    //---pruebas--
    public float tiempo_cambio;
    //las posiciones  aleatorias de las frutas
    //posicion x, posicion y, si ocupado o no
    public int m_frutas[][]={{192,0,0}, {128,639,0}, {768,640,0}, {768,64,0}};
    
    //las posiciones  aleatorias de las vidas
    //posicion x, posicion y, si ocupado o no, y tiempo de reaparicion
    public int m_vidas[][]={{143, 216,0, 5}, {783, 24,0, 5}};
    
    //las posiciones de los enemigos movimiento patrulla
    //posicion x, y, velocidad, limite_izquierdo, limite_derecho, h_o_v
    public int m_enemigos[][]={{64, 576, 300,64, 256, 1}, {128, 384, 200, 128, 320, 1}, {192, 192, 100,128, 320, 1},
                               {448, 576, 200, 448, 576, 1}, {64, 192, 300, 192, 320, 0}, {384, 64, 200, 64, 256, 0}, {384, 0, 300, 384, 576, 1},
                               {576, 257, 300, 257, 448, 0}, {640, 0, 200, 0, 192, 0}, {768, 192, 300, 192, 384,0},
                               {68, 508, 250, 68, 450, 1}
                              };
    
    //las posiciones de los enemigos movimiento circular
    //posicion_x, posicion_y, velocidad, diametro
    public int m_enemigos_2[][]={{456, 494,3, 70}, {1152, 640, 2, 200}, {161, 1380, 3, 64}, {1439, 1761, 3, 64}};
    
    //float posicion_x, float posicion_y
     public int m_objetos[][]={/*troncos*/{70, 644}, {133,0}, {326, 644}, {326, 580},
                                          {581, 131}, {645, 644}, {645, 580}, {710, 580},
                                          {519,73}, {581,73},
                               /*piedras*/{196, 261}, {196, 69}, {260, 261}, {260, 132},
                                          {260, 68}, {709, 133}, {709, 68}, {709, 4},
                               /*arboles*/{79, 72}
                              };
    
    MonkeyLevel(Game game, World w, Monkey mono, ArrayList<Enemy>lista_enemigos, ArrayList<Enemy_2>lista_enemigos_2, ArrayList<Fruits> lista_frutas, ArrayList<Vidas> lista_vidas, ArrayList<StaticObjects>lista_objetos)
    {
        
        super(game, w, mono, lista_enemigos, lista_enemigos_2, lista_frutas, lista_vidas, lista_objetos);
        mono.__init__(0,0); //960 896
        
        for (int i = 0; i < cant_frutas; i++) {
           lista_frutas.get(i).__init__(m_frutas);
        }
        
        for (int i = 0; i < cant_vidas; i++) {
            //int m[][], int posiciones_disponibles, int tiempo
            lista_vidas.get(i).__init__(m_vidas, cant_vidas, m_vidas[i][3]);
        }
        for (int i = 0; i < cant_enemigos_1_nivel; i++) {
            //posicion x, y, velocidad, limite_izquierdo, limite_derecho, h_o_v
            lista_enemigos.get(i).__init__(m_enemigos[i][0], m_enemigos[i][1], m_enemigos[i][2], m_enemigos[i][3], m_enemigos[i][4], m_enemigos[i][5]);
        }
        
        for (int i = 0; i < cant_objetos; i++) {
            //posicion x, y, velocidad, limite_izquierdo, limite_derecho, h_o_v
            lista_objetos.get(i).__init__(m_objetos[i][0], m_objetos[i][1]);
        }
        
    }
    
    public void create(){
        
        //---pruebas
        tiempo_cambio=5.00f;
        //mainStage.setDebugAll(true);
        //---MUSICA---------------------------------
        music_nivel=(Gdx.audio.newMusic(Gdx.files.internal("music_nivel1.mp3")));
        music_nivel.setVolume(0.2f);

        //---MAPA-----------------------------------
        mapWidth= tileSize*tileCountWidth;
        mapHeight= tileSize*tileCountHeight;
        tiempo_resurreccion=3;
        
         //limites del mapa
        crear_limites(15.0f, -0.1f, 15.0f, 0.1f, "limite abajo");
        crear_limites(-0.1f, 15.0f, 0.1f, 15.0f, "limite izquierdo");
        crear_limites(15.0f, 11.1f, 15.0f, 0.1f, "limite superior");
        crear_limites(13.1f, 15.0f, 0.1f, 15.0f, "limite derecho");      
        
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
                       if(mono.cant_vidas<3)
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
        
        music_nivel.dispose();
        mainStage.dispose();
        uiStage.dispose();
    }

    public void show(){
        //---ACTORES-----------------------------------
        grama=new BaseActor();       
        grama.setTexture(new Texture(Gdx.files.internal("nivel_1.png")));
        grama.setPosition(0, 0);
        grama.setSize(mapWidth, mapHeight);
        mainStage.addActor(grama);
        
        
        music_nivel.play();
        music_nivel.setLooping(true);
        
        
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
        winTex.setTexture(new Texture(Gdx.files.internal("you-win.png")));
        winTex.setPosition(windowHeight/2,windowHeight/2);        
        winTex.setVisible(false);
        winTex.addAction(fadeInColorCycleForever);
        uiStage.addActor(winTex);
        win=false;
        
        gameoverTex = new BaseActor();
        gameoverTex.setTexture(new Texture(Gdx.files.internal("gameover.png")));
        gameoverTex.setPosition(windowHeight/2,windowHeight/2);        
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
        
        this.mono_siguiente_nivel= new Label(" Press N to go to nex level", new LabelStyle(new BitmapFont(), Color.BLUE));
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
        music_nivel.stop();
        cuerpo.destroyFixture(cuerpo_fixture);
        world.destroyBody(cuerpo);
        mainStage.clear();
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
                    mono.cuerpo.setTransform(0+0.5f, 0+0.5f, mapHeight);

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

   public boolean keyDown(int keycode) {
       if(keycode == Keys.P)
           togglePaused();
        return false;
    }
    public boolean scrolled(int amount){
        
        return false;
    }
       //para limites iniciales 
    public void crear_limites(float posicion_x, float posicion_y, float centro_x, float centro_y, String nombre)
   { 
       crear_cuerpo(posicion_x, posicion_y);
       crear_fixture(centro_x, centro_y, nombre);
   }
   public void crear_cuerpo(float x, float y)
   {
       //limite de abajo
      BodyDef def = new BodyDef();
        def.position.set(x, y);// 15.0f, -0.1f
        def.type=BodyDef.BodyType.StaticBody; //cuerpo dinamico
        cuerpo=world.createBody(def); //se añade al mundo
   }
   public void crear_fixture(float x, float y, String nombre)
   {
       cuerpo_forma = new PolygonShape(); //es una caja              
       cuerpo_forma.setAsBox(x, y);//15.0f, 0.1f colocar colocar la mitad en medidas de metro, cmienza en el centro
       cuerpo_fixture= cuerpo.createFixture(cuerpo_forma, 1);//es una fixture
       cuerpo_fixture.setUserData(nombre);
       
   }
   
   //cuerpos estaticos
   public void crear_objetoestatico(float posicion_x, float posicion_y, float ancho_x, float ancho_y,  String nombre)
   { 
       crear_cuerpo_estatico(posicion_x, posicion_y, ancho_x, ancho_y);
       crear_fixture_estatico(ancho_x, ancho_y, nombre);
   }
   public void crear_cuerpo_estatico(float x, float y, float ancho_x, float ancho_y)
   {
       //limite de abajo
      BodyDef def = new BodyDef();
        def.position.set((x+ ancho_x/2) /Constants.pixelametro, (y + ancho_y/2)/Constants.pixelametro);// 15.0f, -0.1f
        def.type=BodyDef.BodyType.StaticBody; //cuerpo dinamico
        cuerpo=world.createBody(def); //se añade al mundo
   }
   public void crear_fixture_estatico(float ancho_x, float ancho_y, String nombre)
   {
       cuerpo_forma = new PolygonShape(); //es una caja              
       cuerpo_forma.setAsBox((ancho_x/2)/Constants.pixelametro, (ancho_y/2)/Constants.pixelametro);//15.0f, 0.1f colocar colocar la mitad en medidas de metro, cmienza en el centro
       cuerpo_fixture= cuerpo.createFixture(cuerpo_forma, 1);//es una fixture
       cuerpo_fixture.setUserData(nombre);
   }
   public void mono_muerto()
   {   gameoverTex.setVisible(true);
       mono_muerto.setVisible(true);
       
       if(Gdx.input.isKeyPressed(Keys.S))
          {   
              game.setScreen((new MonkeyLevel(game, world, mono, lista_enemigos, lista_enemigos_2, lista_frutas, lista_vidas, lista_objetos)));
          }
      if(Gdx.input.isKeyPressed(Keys.M))
           game.setScreen((new MonkeyMenu(game, world, mono, lista_enemigos, lista_enemigos_2, lista_frutas, lista_vidas, lista_objetos)));
   }
   public void mono_gano()
   {    
       winTex.setVisible(true);
       mono_siguiente_nivel.setVisible(true);
       if(Gdx.input.isKeyPressed(Keys.N))
          {   
              game.setScreen((new MonkeyMenu(game, world, mono, lista_enemigos, lista_enemigos_2, lista_frutas, lista_vidas, lista_objetos)));
          }

   }
}
    