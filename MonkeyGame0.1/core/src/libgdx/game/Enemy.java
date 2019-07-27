package libgdx.game;
        
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
//import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

public class Enemy extends Actor
{
        public World world;
        public Body cuerpo;
        public Fixture cuerpo_fixture;
        PolygonShape cuerpo_forma;
        
        public TextureRegion region;
        public Rectangle boundary;
        
        //moviento
        boolean muevete_derecha;
        boolean muevete_izquierda;
        float limite_derecho;
        float limite_izquierdo;
        Vector2 posicion;
        
        public float velocityX;
        public float velocityY;
        
        //valores que se tiene que inicializar de nuevo
        public int cant_vidas;
        public boolean colision;
        public boolean vivo;
        public float maxSpeed;
        public boolean horizontal_vertical;
        
        private Texture hombre_der;
        private Texture hombre_izq;
        private Texture hombre_arri;
        private Texture hombre_abaj;
        
    public void __init__(float posicion_x, float posicion_y, int velocidad, float limite_izquierdo, float limite_derecho, int h_o_v)
    {
            cant_vidas=3;
            vivo=true;
            
            //nuevos limites del enemigo
            this.limite_izquierdo= limite_izquierdo;
            this.limite_derecho= limite_derecho;
            //movimiento horizontal=true, vertical=false
            if (h_o_v == 1)
            horizontal_vertical=true;
            else
            horizontal_vertical=false;
            
            //posicion nueva
            cuerpo.setTransform((posicion_x+ this.getOriginX()) /Constants.pixelametro,(posicion_y+ this.getOriginY()) /Constants.pixelametro,0);
            
            //asiganacion de la nueva velocidad
            setSpeed(velocidad/Constants.pixelametro);
            posicion.x=(posicion_x+ this.getOriginX())/Constants.pixelametro;
            posicion.y=(posicion_y+ this.getOriginY())/Constants.pixelametro;
                   
    }   
    public Enemy(World world, String nombre, float posicion_x, float posicion_y, int velocidad, float limite_izquierdo, float limite_derecho, boolean h_o_v, Texture t)
    {        
        super();
                region = new TextureRegion();
                boundary = new Rectangle();
                cant_vidas=3;
                vivo= true;
                
                //limites del enemigo
                this.limite_izquierdo= limite_izquierdo;
                this.limite_derecho= limite_derecho;
                //----variables para el movimiento------------------------------
                muevete_derecha=true; //que tambien se usa para arriba y abajo
                muevete_izquierda=false;
                //movimiento horizontal=true, vertical=false
                horizontal_vertical=h_o_v;
                
                setTexture(t);
                setPosition(posicion_x, posicion_y);
                crear_player(world, nombre);
                setSpeed(velocidad/Constants.pixelametro);//velocidad calculada en metros por segundo, aqui hay 300pixeles por segundo
                
                //esta variable posicion sera usada para ubicar al enemigo en el mapa de box2d con un set transforme
                //se guardan sus variables iniciales para que cuando el jugador golpee al enemigo el enemigo no pierda su camino guia
                //aquí se guardan las posiciones reales para usarlos en el mapa de box2d
                posicion = new Vector2();
                posicion.x=(posicion_x+ this.getOriginX())/Constants.pixelametro;
                posicion.y=(posicion_y+ this.getOriginY())/Constants.pixelametro;
    }
    public void setTexture(Texture t)
    {
        int w = t.getWidth();
        int h = t.getHeight();
        setWidth( w );
        setHeight( h );
        region.setRegion( t );
        setSize(region.getRegionWidth(), region.getRegionHeight());
        setOrigin(getWidth()/2, getHeight()/2);
    }
    public Rectangle getBoundingRectangle()
    {
        boundary.set( getX(), getY(), getWidth(), getHeight());
        return boundary;
    }
    public void act(float dt)
    {
        velocityX=0.00f;
        velocityY=0.00f;
        super.act( dt );

        if(horizontal_vertical)
            movimiento_horizontal();
        else
            movimiento_vertical();
        
    }       
        
    public void draw(Batch batch, float parentAlpha)
    {       
        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a);
        Vector2 center = cuerpo.getWorldCenter();
        setPosition(Constants.pixelametro*center.x - this.getOriginX(), Constants.pixelametro*center.y - this.getOriginY());
        if ( isVisible() )
        batch.draw( region, getX(), getY(), getOriginX(),getOriginY(),getWidth(), getHeight(), getScaleX(),getScaleY(), getRotation() );
    }
    public void detach()
    {
        cuerpo.destroyFixture(cuerpo_fixture);
        world.destroyBody(cuerpo); 
    }
    public void crear_player(World W, String Tag)
    {
            world = W;
            BodyDef def = new BodyDef();
            def.position.set((getX()+ this.getOriginX()) /Constants.pixelametro, (getY() + this.getOriginY())/Constants.pixelametro);//piscion
            def.type=BodyDef.BodyType.DynamicBody; //cuerpo dinamico
            cuerpo=world.createBody(def); //se añade al mundo
            
            cuerpo_forma = new PolygonShape(); //es una caja              
            cuerpo_forma.setAsBox((this.getWidth()/2)/Constants.pixelametro, (this.getHeight()/2)/Constants.pixelametro);//colocar colocar la mitad en medidas de metro, cmienza en el centro
            cuerpo_fixture= cuerpo.createFixture(cuerpo_forma, 20);//es una fixture
            cuerpo_fixture.setUserData(Tag);
            cuerpo_forma.dispose();//se borra
    }
    public void movimiento_horizontal()
    {
        if(muevete_derecha){
            //setTexture(Constants.hombre_der); 
        	setTexture(hombre_der);
            if((Constants.pixelametro*cuerpo.getPosition().x - this.getOriginX()) + maxSpeed   < limite_derecho )
                     {
                           velocityX+=maxSpeed;
                           setVelocity(velocityX, velocityY);
                     }
                     else
                     {
                         setVelocity(velocityX, velocityY);
                         cuerpo.setTransform((limite_derecho + getOriginX())/ Constants.pixelametro, posicion.y,0); //aseguro que llegue al limite
                         muevete_derecha=false;
                         muevete_izquierda=true;
                     }
             }
       else{
            setTexture(hombre_izq);  

                     if((Constants.pixelametro*cuerpo.getPosition().x - this.getOriginX()) - maxSpeed > limite_izquierdo) 
                     {     velocityX-=maxSpeed;
                           setVelocity(velocityX, velocityY);
                     }
                     else
                     {
                         setVelocity(velocityX, velocityY);
                         cuerpo.setTransform((limite_izquierdo + getOriginX())/ Constants.pixelametro, posicion.y,0);
                         muevete_derecha=true;
                         muevete_izquierda=false;
                     }
             }
             cuerpo.setTransform(cuerpo.getPosition().x, posicion.y,0);
    }
    public void movimiento_vertical()
    {
        if(muevete_derecha){
           setTexture(hombre_arri); 
                         if((Constants.pixelametro*cuerpo.getPosition().y - this.getOriginY()) + maxSpeed   < limite_derecho )
                         {     velocityY+=maxSpeed;
                               setVelocity(velocityX, velocityY);
                         }
                         else
                         {
                             setVelocity(velocityX, velocityY);
                             cuerpo.setTransform(posicion.x, (limite_derecho + getOriginY())/ Constants.pixelametro,0); //aseguro que llegue al limite
                             muevete_derecha=false;
                             muevete_izquierda=true;
                         }
                 }
        else{
            setTexture(hombre_abaj); 
                         if((Constants.pixelametro*cuerpo.getPosition().y - this.getOriginY()) - maxSpeed > limite_izquierdo){
                              velocityY-=maxSpeed;
                               setVelocity(velocityX, velocityY);
                         }
                         else
                         {
                             setVelocity(velocityX, velocityY);
                             cuerpo.setTransform(posicion.x, (limite_izquierdo + getOriginY())/ Constants.pixelametro,0); //aseguro que llegue al limite
                             muevete_derecha=true;
                             muevete_izquierda=false;
                         }
        }
        cuerpo.setTransform(posicion.x, cuerpo.getPosition().y,0); //aseguro que llegue al limite
    }
    public float getSpeed()
    { return this.maxSpeed;}
    public void setSpeed(float s)
    { this.maxSpeed=s; }
    
    public void setVelocity(float vx, float vy)
    { cuerpo.setLinearVelocity(vx,vy); }
    
    public void colision(Monkey m){
        Rectangle frect = m.getBoundingRectangle();
        Rectangle monorect =  this.getBoundingRectangle();
        
        if(frect.overlaps(monorect))
            colision=true;
    } 
    public void set_texture_hombre_der(Texture imagen)
    {
    	hombre_der=imagen;
    }
    public void set_texture_hombre_izq(Texture imagen)
    {
    	hombre_izq=imagen;
    }
    public void set_texture_hombre_arr(Texture imagen)
    {
    	hombre_arri=imagen;
    }
    public void set_texture_hombre_aba(Texture imagen)
    {
    	hombre_abaj=imagen;
    }
}

