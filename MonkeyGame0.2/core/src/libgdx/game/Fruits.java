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

public class Fruits extends BaseActor{
    
        public World world;
        public Body cuerpo;
        public Fixture cuerpo_fixture;
        PolygonShape cuerpo_forma;
        
        public float maxSpeed;
        public boolean colision;
        public boolean marcado;
        public int posicion_anterior;
        public int num_posicion;
        public boolean ocupado;
        
        //acciones
        Action rotacion;
        Action fadeout;
                                //x y y es pasado en pixeles
    public Fruits(World world, String nombre, float posicion_x, float posicion_y, Texture t)
	{      //los valores se cargan después de salir del constructor
		super();
                setTexture(t);
                setPosition(posicion_x, posicion_y);
                crear_player(world, nombre);
                inic_acciones();
                comienza_rotacion();
	}
    public void __init__(int m[][]){
        
        while (!ocupado){
            num_posicion=MathUtils.random(0,3);
            if (m[num_posicion][2]==0){
                   cuerpo.setTransform((m[num_posicion][0]+ this.getOriginX()) /Constants.pixelametro,(m[num_posicion][1]+ this.getOriginX()) /Constants.pixelametro,  0);
                   m[num_posicion][2]=1;
                   ocupado=true;
            }
        }
        colision=false;
        marcado=false;
        ocupado=false;
        setVisible(true);
    }
    public void act(float dt)
    {
        super.act( dt );
       //System.out.println("este es x: " +  this.getX() + "estes es y: " +this.getY());
       // System.out.println("ESTE ES body X: "+bodyB.getLinearVelocity().x + "ESTE ES bodyY: "+bodyB.getLinearVelocity().y);
       
    }
    public void draw(Batch batch, float parentAlpha)
	{       
		Color c = getColor();
		batch.setColor(c.r, c.g, c.b, c.a);
                setPosition((cuerpo.getPosition().x -0.5f)* Constants.pixelametro, (cuerpo.getPosition().y -0.5f)* Constants.pixelametro);
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
            cuerpo_forma.dispose();//se borra
        }
    public void colision(Monkey m){
            Rectangle frect = m.getBoundingRectangle();
            Rectangle monorect =  this.getBoundingRectangle();
            
            if(frect.overlaps(monorect))
                colision=true;
        }
    public void inactivo()
    {
        cuerpo.setTransform(-200, -200, 0);
        setVisible(false);
    }
    
    public void detach()
        {
            cuerpo.destroyFixture(cuerpo_fixture);
            world.destroyBody(cuerpo);
        }
    
    public void inic_acciones()
    {
        rotacion = Actions.parallel(
                Actions.alpha(1),
                Actions.forever(Actions.rotateBy(360, 1))
        );
        fadeout = Actions.fadeOut(3);
    }
    public void comienza_rotacion()
    {
        this.addAction(rotacion);
    }
    public void comienza_fadeout()
    {
        this.addAction(fadeout);
    }
    public void sacar_objeto(int x, int y)
    {
        cuerpo.setTransform(x, y,0); //aseguro que llegue al limite
    }
}
