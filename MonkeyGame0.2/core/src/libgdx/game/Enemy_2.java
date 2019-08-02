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
import java.lang.Math;
public class Enemy_2 extends Actor
{
        public World world;
        public Body cuerpo;
        public Fixture cuerpo_fixture;
        PolygonShape cuerpo_forma;
        
        public TextureRegion region;
        public Rectangle boundary;
        
        //moviento
        Vector2 posicion;
        
        public float velocityX;
        public float velocityY;
        
        //valores que se tiene que inicializar de nuevo
        public int cant_vidas;
        public boolean colision;
        public boolean vivo;
        public float maxSpeed;
        public int diametro;
        private float posicion_x;
        private float posicion_y;
        private float _x;
        private float _y;
        private float acumulador;
        
        
    public void __init__(float posicion_x, float posicion_y, float velocidad, int diametro)
    {
            cant_vidas=3;
            vivo=true;
            this.posicion_x=posicion_x/Constants.pixelametro;
            this.posicion_y=posicion_y/Constants.pixelametro;
            //asiganacion de la nueva velocidad
            setSpeed(velocidad/100);
            this.diametro=diametro;
            cuerpo.setTransform(this.posicion_x, this.posicion_y, 0);
    }   
    public Enemy_2(World world, String nombre, float posicion_x, float posicion_y, float velocidad, int diametro, Texture t)
    {        
        super();
                region = new TextureRegion();
                boundary = new Rectangle();
                cant_vidas=3;
                vivo= true;
                
                
                setTexture(t);
                setPosition(posicion_x, posicion_y);
                crear_player(world, nombre);
                
                this.posicion_x=posicion_x/Constants.pixelametro;
                this.posicion_y=posicion_y/Constants.pixelametro;
                setSpeed(velocidad);
                this.diametro=diametro;
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
        super.act( dt );
        
        acumulador+=getSpeed();
        _x= posicion_x + (MathUtils.cos(acumulador) * diametro)/Constants.pixelametro;
        _y= posicion_y + (MathUtils.sin(acumulador) * diametro)/Constants.pixelametro;
        
        cuerpo.setTransform(_x,_y, 0);
        
        /*  System.out.println("(enemigo2) x: " +  this.getX() + " y: " +this.getY());
            System.out.println("(enemigo2 body) X: "+cuerpo.getPosition().x + " Y: "+cuerpo.getPosition().y);
            System.out.println("");
        */   
        
    }       
        
    public void draw(Batch batch, float parentAlpha)
    {       
        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a);
        Vector2 center = cuerpo.getWorldCenter();
        setPosition(Constants.pixelametro*center.x- this.getOriginX(), Constants.pixelametro*center.y - this.getOriginY());
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
    public void sacar_objeto(int x, int y)
    {
        cuerpo.setTransform(x, y,0); //aseguro que llegue al limite
    }  
}

