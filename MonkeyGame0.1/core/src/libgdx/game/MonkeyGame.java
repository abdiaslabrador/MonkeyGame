package libgdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;


public class MonkeyGame extends Game{
    
    World world;Monkey mono;
    public ArrayList<Enemy> lista_enemigos;
    public ArrayList<Enemy_2> lista_enemigos_2;
    public ArrayList<Fruits> lista_frutas;
    public ArrayList<Vidas> lista_vidas;
    public ArrayList<StaticObjects> lista_objetos;
    public Fruits manzana;
    public Fruits uvas;
    public Fruits naranja;
    public Vidas vida1;
    public Vidas vida2;
    private Json_data json;
    private AssetManager manager;
   
   public AssetManager get_manager()
    {
        return manager;
    }
    public Json_data get_json_data()
    {
        return json;
    }
    public void set_json_data(String ruta)
    {
          json = new Json_data(ruta);
    }
    
    public void create()
    {
        manager = new AssetManager();
        manager.load("personajes/hombre_derecha.png", Texture.class);
        manager.load("personajes/hombre_izquierda.png", Texture.class);
        manager.load("personajes/hombre_arriba.png", Texture.class);
        manager.load("personajes/hombre_abajo.png", Texture.class);
        manager.load("personajes/mujer.png", Texture.class);
        manager.load("frutas/uvas.png", Texture.class);
        manager.load("frutas/naranja.png", Texture.class);
        manager.load("frutas/banana.png", Texture.class);
        manager.load("frutas/manzana.png", Texture.class);
        
        manager.load("menu_inicio/menu_inicio.png", Texture.class);
        manager.load("menu_inicio/music_menu.mp3", Music.class);
        
        manager.load("nivel1/nivel_1.png", Texture.class);
        manager.load("escenario/you-win.png", Texture.class);
        manager.load("escenario/gameover.png", Texture.class);
        manager.load("nivel1/music_nivel1.mp3", Music.class);
        
        manager.load("mono/mono_disparo.png", Texture.class);
        manager.load("mono/mono0.png", Texture.class);
        manager.load("mono/mono1.png", Texture.class);
        manager.load("mono/mono2.png", Texture.class);
        manager.load("mono/mono3.png", Texture.class);
        
        manager.finishLoading();
        
        world = new World(new Vector2(0,0), true);
        
        mono = new Monkey();
        lista_enemigos = new ArrayList<Enemy>();
        lista_enemigos_2 = new ArrayList<Enemy_2>();
        lista_frutas = new ArrayList<Fruits>();
        lista_vidas = new ArrayList<Vidas>();
        lista_objetos = new ArrayList<StaticObjects>();
        
        //ENEMIGOS
        //world, tags, posicion x, y, velocidad, limite inferior, limite superior, textura
        
        Enemy enemigo=new Enemy(world, "enemigos", -200, -200, 100,64, 128, true, (Texture)get_manager().get("personajes/hombre_derecha.png"));
        enemigo.set_texture_hombre_der((Texture)get_manager().get("personajes/hombre_derecha.png"));
        enemigo.set_texture_hombre_izq((Texture)get_manager().get("personajes/hombre_izquierda.png"));
        enemigo.set_texture_hombre_arr((Texture)get_manager().get("personajes/hombre_arriba.png"));
        enemigo.set_texture_hombre_aba((Texture)get_manager().get("personajes/hombre_abajo.png"));
        lista_enemigos.add(enemigo);
       
        Enemy enemigo2=new Enemy(world, "enemigos", -200, -200, 200, 64, 384, true, (Texture)get_manager().get("personajes/hombre_derecha.png"));
        enemigo2.set_texture_hombre_der((Texture)get_manager().get("personajes/hombre_derecha.png"));
        enemigo2.set_texture_hombre_izq((Texture)get_manager().get("personajes/hombre_izquierda.png"));
        enemigo2.set_texture_hombre_arr((Texture)get_manager().get("personajes/hombre_arriba.png"));
        enemigo2.set_texture_hombre_aba((Texture)get_manager().get("personajes/hombre_abajo.png"));
        lista_enemigos.add(enemigo2);
       
        Enemy enemigo3=new Enemy(world, "enemigos", -200, -200, 300, 64, 320, false, (Texture)get_manager().get("personajes/hombre_derecha.png"));
        enemigo3.set_texture_hombre_der((Texture)get_manager().get("personajes/hombre_derecha.png"));
        enemigo3.set_texture_hombre_izq((Texture)get_manager().get("personajes/hombre_izquierda.png"));
        enemigo3.set_texture_hombre_arr((Texture)get_manager().get("personajes/hombre_arriba.png"));
        enemigo3.set_texture_hombre_aba((Texture)get_manager().get("personajes/hombre_abajo.png"));
        lista_enemigos.add(enemigo3);
        
        Enemy enemigo4=new Enemy(world, "enemigos", -200, -200, 300, 0, 576, false, (Texture)get_manager().get("personajes/hombre_derecha.png"));
        enemigo4.set_texture_hombre_der((Texture)get_manager().get("personajes/hombre_derecha.png"));
        enemigo4.set_texture_hombre_izq((Texture)get_manager().get("personajes/hombre_izquierda.png"));
        enemigo4.set_texture_hombre_arr((Texture)get_manager().get("personajes/hombre_arriba.png"));
        enemigo4.set_texture_hombre_aba((Texture)get_manager().get("personajes/hombre_abajo.png"));
        lista_enemigos.add(enemigo4);
        
        Enemy enemigo5=new Enemy(world, "enemigos", -200, -200, 100,64, 128, true, (Texture)get_manager().get("personajes/hombre_derecha.png"));
        enemigo5.set_texture_hombre_der((Texture)get_manager().get("personajes/hombre_derecha.png"));
        enemigo5.set_texture_hombre_izq((Texture)get_manager().get("personajes/hombre_izquierda.png"));
        enemigo5.set_texture_hombre_arr((Texture)get_manager().get("personajes/hombre_arriba.png"));
        enemigo5.set_texture_hombre_aba((Texture)get_manager().get("personajes/hombre_abajo.png"));
        lista_enemigos.add(enemigo5);
        
        Enemy enemigo6=new Enemy(world, "enemigos", -200, -200, 200, 64, 384, true, (Texture)get_manager().get("personajes/hombre_derecha.png"));
        enemigo6.set_texture_hombre_der((Texture)get_manager().get("personajes/hombre_derecha.png"));
        enemigo6.set_texture_hombre_izq((Texture)get_manager().get("personajes/hombre_izquierda.png"));
        enemigo6.set_texture_hombre_arr((Texture)get_manager().get("personajes/hombre_arriba.png"));
        enemigo6.set_texture_hombre_aba((Texture)get_manager().get("personajes/hombre_abajo.png"));
        lista_enemigos.add(enemigo6);
       
        Enemy enemigo7=new Enemy(world, "enemigos", -200, -200, 300, 64, 320, false, (Texture)get_manager().get("personajes/hombre_derecha.png"));
        enemigo7.set_texture_hombre_der((Texture)get_manager().get("personajes/hombre_derecha.png"));
        enemigo7.set_texture_hombre_izq((Texture)get_manager().get("personajes/hombre_izquierda.png"));
        enemigo7.set_texture_hombre_arr((Texture)get_manager().get("personajes/hombre_arriba.png"));
        enemigo7.set_texture_hombre_aba((Texture)get_manager().get("personajes/hombre_abajo.png"));
        lista_enemigos.add(enemigo7);
        
        Enemy enemigo8=new Enemy(world, "enemigos", -200, -200, 300, 0, 576, false, (Texture)get_manager().get("personajes/hombre_derecha.png"));
        enemigo8.set_texture_hombre_der((Texture)get_manager().get("personajes/hombre_derecha.png"));
        enemigo8.set_texture_hombre_izq((Texture)get_manager().get("personajes/hombre_izquierda.png"));
        enemigo8.set_texture_hombre_arr((Texture)get_manager().get("personajes/hombre_arriba.png"));
        enemigo8.set_texture_hombre_aba((Texture)get_manager().get("personajes/hombre_abajo.png"));
        lista_enemigos.add(enemigo8);
        
        Enemy enemigo9=new Enemy(world, "enemigos", -200, -200, 300, 0, 576, false, (Texture)get_manager().get("personajes/hombre_derecha.png"));
        enemigo9.set_texture_hombre_der((Texture)get_manager().get("personajes/hombre_derecha.png"));
        enemigo9.set_texture_hombre_izq((Texture)get_manager().get("personajes/hombre_izquierda.png"));
        enemigo9.set_texture_hombre_arr((Texture)get_manager().get("personajes/hombre_arriba.png"));
        enemigo9.set_texture_hombre_aba((Texture)get_manager().get("personajes/hombre_abajo.png"));
        lista_enemigos.add(enemigo9);
        
        Enemy enemigo10=new Enemy(world, "enemigos", -200, -200, 300, 0, 576, false, (Texture)get_manager().get("personajes/hombre_derecha.png"));
        enemigo10.set_texture_hombre_der((Texture)get_manager().get("personajes/hombre_derecha.png"));
        enemigo10.set_texture_hombre_izq((Texture)get_manager().get("personajes/hombre_izquierda.png"));
        enemigo10.set_texture_hombre_arr((Texture)get_manager().get("personajes/hombre_arriba.png"));
        enemigo10.set_texture_hombre_aba((Texture)get_manager().get("personajes/hombre_abajo.png"));
        lista_enemigos.add(enemigo10);
        
        Enemy enemigo11=new Enemy(world, "enemigos", -200, -200, 300, 0, 576, false, (Texture)get_manager().get("personajes/hombre_derecha.png"));
        enemigo11.set_texture_hombre_der((Texture)get_manager().get("personajes/hombre_derecha.png"));
        enemigo11.set_texture_hombre_izq((Texture)get_manager().get("personajes/hombre_izquierda.png"));
        enemigo11.set_texture_hombre_arr((Texture)get_manager().get("personajes/hombre_arriba.png"));
        enemigo11.set_texture_hombre_aba((Texture)get_manager().get("personajes/hombre_abajo.png"));
        lista_enemigos.add(enemigo11);
        
        //----------------------------------------------------------------
        //world, nombre, posicion_x, posicion_y, velocidad, diametro, textura        
        Enemy_2 enemigo12= new Enemy_2(world, "enemigos",-200, -200, 0.03f, 32, (Texture)get_manager().get("personajes/mujer.png"));
        lista_enemigos_2.add(enemigo12);
        
        Enemy_2 enemigo13= new Enemy_2(world, "enemigos",-200, -200, 0.03f, 32, (Texture)get_manager().get("personajes/mujer.png"));
        lista_enemigos_2.add(enemigo13);
        
        Enemy_2 enemigo14= new Enemy_2(world, "enemigos",-200, -200, 0.03f, 32, (Texture)get_manager().get("personajes/mujer.png"));
        lista_enemigos_2.add(enemigo14);
        
        Enemy_2 enemigo15= new Enemy_2(world, "enemigos",-200, -200, 0.03f, 32, (Texture)get_manager().get("personajes/mujer.png"));
        lista_enemigos_2.add(enemigo15);
        //mono
        TextureRegion[] frames = new TextureRegion[4];
        for (int n = 0; n < 4; n++)
        {
            String fileName = "mono" + n + ".png";
            Texture texto = (Texture)get_manager().get("mono/"+ fileName);
            texto.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
            frames[n] = new TextureRegion( texto );
        }
        Array<TextureRegion> framesArray = new Array<TextureRegion>(frames);
        Animation<TextureRegion> anim = new Animation(0.1f, framesArray,Animation.PlayMode.LOOP_PINGPONG);
        mono.setAnimimation(anim);
        mono.setOrigin(mono.getWidth()/2, mono.getHeight()/2); 
        mono.setPosition(960, 896); //960,896
        mono.crear_player(world, "mono");

        //frutas
        manzana = new Fruits(world, "frutas",1856, 1856,(Texture)get_manager().get("frutas/manzana.png"));
        lista_frutas.add(manzana);
        
        uvas = new Fruits(world, "frutas",1856, 384,(Texture)get_manager().get("frutas/uvas.png"));
        lista_frutas.add(uvas);
        
        naranja = new Fruits(world, "frutas",0, 1600,(Texture)get_manager().get("frutas/naranja.png"));
        lista_frutas.add(naranja);
        
        //vidas
        vida1 = new Vidas(world, "vidas",-200, -200,(Texture)get_manager().get("frutas/banana.png"));
        lista_vidas.add(vida1);
            
        vida2 = new Vidas(world, "vidas",-200, -200,(Texture)get_manager().get("frutas/banana.png"));
        lista_vidas.add(vida2);
        
        //objetos
        //troncos
        //world, nombre, posicion_x, posicion_y, ancho_x, ancho_y)        
        StaticObjects tronco1= new StaticObjects(world, "objetos", -200, -200, 51, 40); //centro pequeño
        lista_objetos.add(tronco1);
        
        StaticObjects tronco2= new StaticObjects(world, "objetos", -200, -200, 51, 40); //centro pequeño
        lista_objetos.add(tronco2);
        
        StaticObjects tronco3= new StaticObjects(world, "objetos", -200, -200, 51, 40); //centro pequeño
        lista_objetos.add(tronco3);
        
        StaticObjects tronco4= new StaticObjects(world, "objetos", -200, -200, 51, 40); //centro pequeño
        lista_objetos.add(tronco4);
        
        StaticObjects tronco5= new StaticObjects(world, "objetos", -200, -200, 51, 40); //centro pequeño
        lista_objetos.add(tronco5);
        
        StaticObjects tronco6= new StaticObjects(world, "objetos", -200, -200, 51, 40); //centro pequeño
        lista_objetos.add(tronco6);
        
        StaticObjects tronco7= new StaticObjects(world, "objetos", -200, -200, 51, 40); //centro pequeño
        lista_objetos.add(tronco7);
        
        StaticObjects tronco8= new StaticObjects(world, "objetos", -200, -200, 51, 40); //centro pequeño
        lista_objetos.add(tronco8);
        
        StaticObjects tronco9= new StaticObjects(world, "objetos", -200, -200, 51, 40); //centro pequeño
        lista_objetos.add(tronco9);
        
        StaticObjects tronco10= new StaticObjects(world, "objetos", -200, -200, 51, 40); //centro pequeño
        lista_objetos.add(tronco10);
        
        //piedras
        StaticObjects pidras11= new StaticObjects(world, "objetos", -200, -200, 51, 57); 
        lista_objetos.add(pidras11);
        
        StaticObjects pidras12= new StaticObjects(world, "objetos", -200, -200, 51, 57); 
        lista_objetos.add(pidras12);
        
        StaticObjects pidras13= new StaticObjects(world, "objetos", -200, -200, 51, 57); 
        lista_objetos.add(pidras13);
        
        StaticObjects pidras14= new StaticObjects(world, "objetos", -200, -200, 51, 57); 
        lista_objetos.add(pidras14);
        
        StaticObjects pidras15= new StaticObjects(world, "objetos", -200, -200, 51, 57); 
        lista_objetos.add(pidras15);
        
        StaticObjects pidras16= new StaticObjects(world, "objetos", -200, -200, 51, 40); 
        lista_objetos.add(pidras16);
        
        StaticObjects pidras17= new StaticObjects(world, "objetos", -200, -200, 51, 40); 
        lista_objetos.add(pidras17);
        
        StaticObjects pidras18= new StaticObjects(world, "objetos", -200, -200, 51, 57); 
        lista_objetos.add(pidras18);
        
        //arboles
        StaticObjects arboles19= new StaticObjects(world, "objetos", -200, -200, 96, 116); 
        lista_objetos.add(arboles19);
        
        MonkeyMenu firts_prueba= new MonkeyMenu(this);
        setScreen(firts_prueba);

        
    }
    
    public void dispose()
    {
        world.dispose();
        manager.dispose();
        
    }
}
