/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libgdx.game;

/**
 *
 * @author Familia
 */
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Color;

public class BaseActor extends Actor
{
	public TextureRegion region;
	public Rectangle boundary;
	public float velocityX;
	public float velocityY;
	public BaseActor()
	{
		super();
		region = new TextureRegion();
		boundary = new Rectangle();
		velocityX = 0;
		velocityY = 0;
                //setSize(region.getRegionWidth(), region.getRegionHeight());
	}
	public void setTexture(Texture t)
	{
		int w = t.getWidth();
		int h = t.getHeight();
		setWidth( w );
		setHeight( h );
		region.setRegion( t );
                setOrigin(this.getWidth()/2, this.getHeight()/2);
                setSize(region.getRegionWidth(), region.getRegionHeight());
	}
	public Rectangle getBoundingRectangle()
	{
		boundary.set( getX(), getY(), getWidth(), getHeight());
		return boundary;
	}
	public void act(float dt)
	{
		super.act( dt );
		moveBy( velocityX * dt, velocityY * dt );
	}
	public void draw(Batch batch, float parentAlpha)
	{       
		Color c = getColor();
		batch.setColor(c.r, c.g, c.b, c.a);
		if ( isVisible() )
		batch.draw( region, getX(), getY(), getOriginX(),getOriginY(),getWidth(), 
			        getHeight(), getScaleX(),getScaleY(), getRotation() );
	}
        
}
