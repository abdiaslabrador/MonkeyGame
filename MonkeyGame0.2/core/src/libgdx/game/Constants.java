/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libgdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author Familia
 */
public class Constants {
    //NOTA en el juego 1 metro equivale a 64 pixelez
    public static final float pixelametro= 64f; //son 64 pero se le restan para una mejor exactitud 
                                                //a la hora de calcular los pixeles se multiplica metros* pixeles
                                                //a la hora de cualcular metros se divide los pixeles entre metros
    public static final float velocidad= 100; //para unavelocidad general si se desea
    public static final float medio_objeto=0.5f;
    
}
