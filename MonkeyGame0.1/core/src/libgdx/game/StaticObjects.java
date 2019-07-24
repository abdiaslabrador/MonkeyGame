package libgdx.game;

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
import com.badlogic.gdx.physics.box2d.World;

public class StaticObjects{
    
        public World world;
        public Body cuerpo;
        public Fixture cuerpo_fixture;
        PolygonShape cuerpo_forma;
        public float maxSpeed;
        
        public float ancho_x;
        public float ancho_y;
                
    public void __init__(float posicion_x, float posicion_y)
    {
        cuerpo.setTransform((posicion_x+ (ancho_x/2)) /Constants.pixelametro,(posicion_y+ (ancho_y/2)) /Constants.pixelametro,0);
    }
    public StaticObjects(World world, String nombre, float posicion_x, float posicion_y, float ancho_x, float ancho_y)
	{
            this.ancho_x=ancho_x;
            this.ancho_y=ancho_y;
            crear_objeto(world, nombre, posicion_x,  posicion_y,  ancho_x,  ancho_y);
	}
    public void crear_objeto(World W, String Tag, float posicion_x, float posicion_y, float ancho_x, float ancho_y)
        {
            world = W;
            BodyDef def = new BodyDef();
            def.position.set((posicion_x+ ancho_x/2) /Constants.pixelametro, (posicion_x + ancho_y/2)/Constants.pixelametro);//posicion
            def.type=BodyDef.BodyType.StaticBody; //cuerpo dinamico
            cuerpo=world.createBody(def); //se añade al mundo
            
            cuerpo_forma = new PolygonShape(); //es una caja              
            cuerpo_forma.setAsBox((ancho_x/2)/Constants.pixelametro, (ancho_y/2)/Constants.pixelametro);//colocar colocar la mitad en medidas de metro, cmienza en el centro
            cuerpo_fixture= cuerpo.createFixture(cuerpo_forma, 1);//es una fixture
            cuerpo_fixture.setUserData(Tag);
            cuerpo_forma.dispose();//se borra
        }
    public void detach()
        {
            cuerpo.destroyFixture(cuerpo_fixture);
            world.destroyBody(cuerpo);
            
        }
}