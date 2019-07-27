package libgdx.game;

import java.io.*;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Json_data {
	private int mono_xy[];
	private int m_frutas[][];
	private int m_vidas[][];
	private int m_enemigos[][];
	private int m_objetos[][];
	
	private int vidas_mapa;
	private int cant_frutas;
	private int cant_vidas;
	private int cant_enemigos;
	private int cant_objetos;
	
	Long v;
	JSONParser parser;
	JSONObject json;
	
	public Json_data(String ruta)
	{
		m_frutas= new int[4][3];
		m_vidas= new int[2][4];
		m_enemigos= new int[11][6];
		m_objetos= new int[19][2];
		mono_xy= new int[2];
		
		parser = new JSONParser();
        
        try (FileReader reader = new FileReader(ruta)) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            
            set_matrix_frutas(jsonObject);
            set_matrix_vidas(jsonObject);
            set_matrix_enemigos(jsonObject);
            set_matrix_objetos(jsonObject);
            
            set_mono_xy(jsonObject);
            
            set_vidas_mapa( jsonObject);
            set_cant_frutas( jsonObject);
            set_cant_vidas( jsonObject);
            set_cant_enemigos( jsonObject);
            set_cant_objetos( jsonObject);
       /*
            for(int k=0; k < m_objetos.length; k++)
            {
                for(int c=0; c < m_objetos[0].length; c++)
                {
                    System.out.print(m_objetos[k][c]+" ");
                }
                System.out.println();
            }
       */ 
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
	}
	private void set_mono_xy(JSONObject jsonObject)
	{
		JSONArray  mono_array = (JSONArray) jsonObject.get("mono");
		
            json =  (JSONObject) mono_array.get(0);
              v = (Long)json.get("x");
            mono_xy[0]= v.intValue();
            
            v = (Long)json.get("y");
            mono_xy[1]= v.intValue();
            
	}
	public int[] get_mono_xy()
	{
		return mono_xy;
	}
	
	private void set_vidas_mapa(JSONObject jsonObject)
	{
		v = (Long)jsonObject.get("vidas_mapa");
		vidas_mapa= v.intValue();
	}
	public int get_vidas_mapa()
	{
		return vidas_mapa;
	}
	
	private void set_cant_frutas(JSONObject jsonObject)
	{
		v = (Long)jsonObject.get("cant_frutas");
		cant_frutas= v.intValue();
	}
	public int get_cant_frutas()
	{
		return cant_frutas;
	}
	
	private void set_cant_vidas(JSONObject jsonObject)
	{
		v = (Long)jsonObject.get("cant_vidas");
		cant_vidas= v.intValue();
	}
	public int get_cant_vidas()
	{
		return cant_vidas;
	}
	
	private void set_cant_enemigos(JSONObject jsonObject)
	{
		v = (Long)jsonObject.get("cant_enemigos");
		cant_enemigos= v.intValue();
	}
	public int get_cant_enemigos()
	{
		return cant_enemigos;
	}
	
	private void set_cant_objetos(JSONObject jsonObject)
	{
		v = (Long)jsonObject.get("cant_objetos");
		cant_objetos= v.intValue();
	}
	public int get_cant_objetos()
	{
		return cant_objetos;
	}
	
	
	private void set_matrix_frutas(JSONObject jsonObject)
	{
		JSONArray  frutas = (JSONArray) jsonObject.get("m_frutas");
		for(int i=0; i<frutas.size();i++)
        {
            json =  (JSONObject) frutas.get(i);
            v = (Long)json.get("x");
            m_frutas[i][0]= v.intValue();
            
            v = (Long)json.get("y");
            m_frutas[i][1]= v.intValue();
            
            v =(Long)json.get("ocupado");
            m_frutas[i][2]= v.intValue();
        }
		 
	}
	public int[][] get_matrix_frutas()
	{
		return m_frutas;
	}
	
	private void set_matrix_vidas(JSONObject jsonObject)
	{
		JSONArray  vidas = (JSONArray) jsonObject.get("m_vidas");

        for(int i=0; i<vidas.size();i++)
        {
            json =  (JSONObject) vidas.get(i);
            v = (Long)json.get("x");
            m_vidas[i][0]= v.intValue();
            
            v = (Long)json.get("y");
            m_vidas[i][1]= v.intValue();
            
            v =(Long)json.get("ocupado");
            m_vidas[i][2]= v.intValue();

            v =(Long)json.get("reaparicion");
            m_vidas[i][3]= v.intValue();
        }
	}
	public int[][] get_matrix_vidas()
	{
		return m_vidas;
	}
	
	private void set_matrix_enemigos(JSONObject jsonObject)
	{
		JSONArray  enemigos = (JSONArray) jsonObject.get("m_enemigos");

        for(int i=0; i<enemigos.size();i++)
        {
            json =  (JSONObject) enemigos.get(i);
            v = (Long)json.get("x");
            m_enemigos[i][0]= v.intValue();
            
            v = (Long)json.get("y");
            m_enemigos[i][1]= v.intValue();
            
            v =(Long)json.get("velocidad");
            m_enemigos[i][2]= v.intValue();

            v =(Long)json.get("limite_izquierdo");
            m_enemigos[i][3]= v.intValue();
            
            v =(Long)json.get("limite_derecho");
            m_enemigos[i][4]= v.intValue();
            
            v =(Long)json.get("h_o_v");
            m_enemigos[i][5]= v.intValue();
        }
	}
	public int[][] get_matrix_enemigos()
	{
		return m_enemigos;
	}
	
	private void set_matrix_objetos(JSONObject jsonObject)
	{
		JSONArray  objetos = (JSONArray) jsonObject.get("m_objetos");

        for(int i=0; i<objetos.size();i++)
        {
            json =  (JSONObject) objetos.get(i);
            v = (Long)json.get("x");
            m_objetos[i][0]= v.intValue();
            
            v = (Long)json.get("y");
            m_objetos[i][1]= v.intValue();            
        }
	}
	public int[][] get_matrix_objetos()
	{
		return m_objetos;
	}
}
