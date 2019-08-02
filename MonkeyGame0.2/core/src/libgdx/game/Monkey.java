package libgdx.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

public class Monkey extends Actor
{
        public World world;
        public Body cuerpo;
        public Fixture cuerpo_fixture;
        CircleShape cuerpo_forma;
        
        public float elapsedTime;
        public TextureRegion region;
        public Rectangle boundary;
        public int areadecomprobacionWH=32, areadecomprobacionXY=16;
        private Animation<TextureRegion> anim;
        
        public float velocityX;
        public float velocityY;
        public float maxSpeed;
        
        public int cant_vidas;
        public int cant_frutas;
        public float tiempo_resurreccion;
        public boolean golpeado;
        public boolean vivo;
                
	public Monkey()
	{
		super();

                region = new TextureRegion();
                boundary = new Rectangle();
                elapsedTime = 0;
                maxSpeed=300/60;///1.67f; //velocidad calculada en metros por segundo, aqui hay 200pixeles por segundo                
	}
	
	//La posiciones son pasadas en pixeles
        public void __init__(float x, float y, int cant_vidas)
        {
            this.cant_frutas=0;
            this.cant_vidas=cant_vidas;
            vivo=true;
            cuerpo.setTransform((x+ this.getOriginX()) /Constants.pixelametro,(y+ this.getOriginX()) /Constants.pixelametro,0);
        }
                
	public void setTexture(Texture t)
	{
		int w = t.getWidth();
		int h = t.getHeight();
		setWidth( w );
		setHeight( h );
		region.setRegion( t );
                setSize(region.getRegionWidth(), region.getRegionHeight());
	}
        public void setAnimimation(Animation<TextureRegion> a) {

        setTexture(a.getKeyFrame(0).getTexture());
        anim = a;
        }
	public Rectangle getBoundingRectangle()
	{
		boundary.set( getX()-areadecomprobacionXY, getY()-areadecomprobacionXY, 
                        getWidth()+areadecomprobacionWH, getHeight()+areadecomprobacionWH);
		return boundary;
	}
	public void act(float dt)
	{
		super.act( dt );
                elapsedTime += dt;
                //System.out.println("ESTE ES MONOZ¿SPEED: " + maxSpeedX);
                
                 cuerpo.setLinearVelocity(velocityX, velocityY);
                 
                //System.out.println("(MONO) x: " +  this.getX() + " y: " +this.getY());
                //System.out.println("(MONO BODY) X: "+cuerpo.getPosition().x + " Y: "+cuerpo.getPosition().y);
                //System.out.println("");
                   
        
	}       
        
	public void draw(Batch batch, float parentAlpha)
	{       
	    Color c = getColor();
            batch.setColor(c.r, c.g, c.b, c.a);
            Vector2 center = cuerpo.getWorldCenter();
            setPosition(Constants.pixelametro*center.x - this.getOriginX(), Constants.pixelametro*center.y - this.getOriginY());
            region.setRegion(anim.getKeyFrame(elapsedTime));
	    if ( isVisible() )
            batch.draw( region, getX(), getY(), getOriginX(),getOriginY(),getWidth(),getHeight(), getScaleX(),getScaleY(), getRotation() );
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
            
            cuerpo_forma = new CircleShape(); //es una caja              
            cuerpo_forma.setRadius((this.getWidth()/2)/Constants.pixelametro);
            //cuerpo_forma.setAsBox((this.getWidth()/2)/Constants.pixelametro, (this.getHeight()/2)/Constants.pixelametro);//colocar colocar la mitad en medidas de metro, cmienza en el centro
            cuerpo_fixture= cuerpo.createFixture(cuerpo_forma, 1);//es una fixture
            cuerpo_fixture.setUserData(Tag);
            cuerpo_forma.dispose();//se borra
        }
       
        public float getSpeed()
        { return this.maxSpeed;}
        public void setSpeed(float s)
        { this.maxSpeed=s; }
        
        public void setVelocity(float vx, float vy)
        { cuerpo.setLinearVelocity(vx,vy); }
        public void setVelocity(Vector2 v)
        { cuerpo.setLinearVelocity(v); }
        
}

