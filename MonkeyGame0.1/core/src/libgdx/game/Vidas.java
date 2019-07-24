package libgdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
//import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Vidas extends BaseActor{
    
        public World world;
        public Body cuerpo;
        public Fixture cuerpo_fixture;
        PolygonShape cuerpo_forma;
        
        public float maxSpeed;
        //colision
        public boolean colision;
        public boolean marcado;
        //tiempo
        private float acumula_tiempo_volver;
        private int tiempo_volver;
        private boolean inactivo;
        //cambio de posicion
        private int m[][];
        private int posiciones_disponibles;
        //acciones
        public int num_posicion;
        public boolean ocupado;

        Action fadeout;
                                //x y y es pasado en pixeles
    public Vidas(World world, String nombre, float posicion_x, float posicion_y, Texture t)
    {      //los valores se cargan después de salir del constructor
        super();
                setTexture(t);
                setPosition(posicion_x, posicion_y);
                crear_player(world, nombre);
                inic_acciones();
                
    }
    //esta funcion sirve para cuando inicie un nivel inicializar todo
    public void __init__(int m[][], int posiciones_disponibles, int tiempo){
        //igualo la matriz con el proposito de usarlo en el act(dt)
        this.m=m;
        //busca una posicion en la matriz que no este acupada para ocuparla(marcala con 1)
        while (!ocupado){
            num_posicion=MathUtils.random(0,posiciones_disponibles);
            if (m[num_posicion][2]==0){
                   cuerpo.setTransform((m[num_posicion][0]+ this.getOriginX()) /Constants.pixelametro,(m[num_posicion][1]+ this.getOriginX()) /Constants.pixelametro,  0);
                   m[num_posicion][2]=1;
                   ocupado=true;
            }
        }
        colision=false;
        marcado=false;
        ocupado=false;
        tiempo_volver=tiempo;
        this.posiciones_disponibles=posiciones_disponibles;
        setVisible(true);
    }
    public void act(float dt)
    {
        super.act( dt );
        
        if(inactivo) // cuando está inactivo, comienza un conateo para luego volverlo a poner en el juego
        {
            acumula_tiempo_volver+=dt; //acumulador de tiempo
            if((int)acumula_tiempo_volver== tiempo_volver)
            {   
                cambiar(m); //le asigna la posicion
                
                acumula_tiempo_volver=0.00f;//coloco el tiempo 0 por si lo vuelve a tocar pueda correr de nuevo el contador
                //cambio el estado de las variables para que la vida pueda volverse a usar
                inactivo=false;
                colision=false;
                marcado=false;
                setVisible(true);
                
                
            }
        }
               /* System.out.println("(VIDAS) x: " +  this.getX() + " y: " +this.getY());
                System.out.println("(VIDAS BODY) X: "+cuerpo.getPosition().x + " Y: "+cuerpo.getPosition().y);*/
       
    }
    public void draw(Batch batch, float parentAlpha)
    {       
        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a);
        Vector2 center = cuerpo.getWorldCenter();
         setPosition(Constants.pixelametro*center.x - this.getOriginX(), Constants.pixelametro*center.y - this.getOriginY());
        if ( isVisible() )
        batch.draw( region, getX(), getY(), getOriginX(),getOriginY(),getWidth(), 
                    getHeight(), getScaleX(),getScaleY(), getRotation() );
    }
    public void crear_player(World W, String Tag)
        {
            world = W;
            BodyDef def = new BodyDef();
            def.position.set((getX()+ this.getOriginX()) /Constants.pixelametro, (getY() + this.getOriginY())/Constants.pixelametro);//piscion
            def.type=BodyDef.BodyType.StaticBody; //cuerpo dinamico
            cuerpo=world.createBody(def); //se añade al mundo
            
            cuerpo_forma = new PolygonShape(); //es una caja              
            cuerpo_forma.setAsBox((this.getWidth()/2)/Constants.pixelametro, (this.getHeight()/2)/Constants.pixelametro);//colocar colocar la mitad en medidas de metro, cmienza en el centro
            cuerpo_fixture= cuerpo.createFixture(cuerpo_forma, 1);//es una fixture
            cuerpo_fixture.setUserData(Tag);
            cuerpo_fixture.setSensor(true);
            cuerpo_forma.dispose();//se borra
        }
    public void colision(Monkey m){
            Rectangle vrect = m.getBoundingRectangle();
            Rectangle monorect =  this.getBoundingRectangle();
            
            if(vrect.overlaps(monorect))
                colision=true;
        }
    //esta funicion sirve para cuando colisione con el mono sacarlo momentaneamente
    public void inactivo()
    {
        cuerpo.setTransform(-200, -200, 0);
        setVisible(false);
        inactivo=true;
        //coloco la posicion donde estaba en 0 para voverla a utilizar
        m[num_posicion][2]=0;
    }
    
            
    public void cambiar(int m[][]){
        
           while (!ocupado){ //mientras no esté ocupado busca un puesto y luego lo coloca ocupado
               num_posicion=MathUtils.random(0,posiciones_disponibles); //elige unas de las casillas de la matriz y luego comprueba si no esta ocupada con el numero 1
               if (m[num_posicion][2]==0){ //0 indica que la casilla esta vacia
                   cuerpo.setTransform((m[num_posicion][0]+
                           this.getOriginX()) /Constants.pixelametro,
                           (m[num_posicion][1]+ this.getOriginX())
                                   /Constants.pixelametro,  0); //le asigna una nueva posicion si esta disponible
                   m[num_posicion][2]=1; // le coloca que ahora esta ocupado
                   ocupado=true; // le asgina true para salir del while
               }             
           }
           ocupado=false;//lo coloca false para volver a disponer de esta funcion
        }
    public void detach()
        {
            cuerpo.destroyFixture(cuerpo_fixture);
            world.destroyBody(cuerpo);
        }
    
    
    public void inic_acciones()
    {
        fadeout = Actions.parallel(
                Actions.alpha(1),
                Actions.fadeOut(3)
        );
    }
    public void comienza_fadeout()
    {
        this.addAction(fadeout);
    }
}

