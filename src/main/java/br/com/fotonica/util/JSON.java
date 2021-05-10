package br.com.fotonica.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

/**
 * Classe responsável por realizar conversões de strings no formato JSON para
 * objetos e vice-versa.
 *
 */
public class JSON {
	
	private static final ObjectMapper mapper = new ObjectMapper();

	/**
	 * Converte uma string JSON para um objeto de uma determinada classe.
	 * 
	 * @param <T>      Tipo de retorno
	 * @param jsonText JSON a se converter
	 * @param clazz    Tipo da classe
	 * @return Objeto da classe com dados do JSON
	 */
	public synchronized static <T> T toObject(String jsonText, Class<T> clazz) {
		Gson gson = new Gson();
		return gson.fromJson(jsonText, clazz);
	}

	/**
	 * Converte uma string JSON para um objeto de uma determinada classe com o formato de data à ser usado passado como parâmetro.
	 *
	 * @param <T>        Tipo de retorno
	 * @param jsonText   JSON a se converter
	 * @param dateFormat Formato da data que será usada
	 * @param clazz      Tipo da classe
	 * @return Objeto da classe com dados do JSON
	 */
	public synchronized static <T> T toObjectWithFormattedDate(String jsonText, String dateFormat, Class<T> clazz) {
		Gson gson = new GsonBuilder().setDateFormat(dateFormat).create();
		T t = null;
		try {
			t = gson.fromJson(jsonText, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * Converte uma string JSON para um objeto de uma determinada classe.
	 * @param jsonText JSON a se converter
	 * @return Objeto da classe com dados do JSON
	 */
	public synchronized static Map<String, Object> toMap(String jsonText) {
		Map<String, Object> map = null;
		try {
			map = mapper.readValue(jsonText, LinkedTreeMap.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * Converte um objeto em JSON
	 */
	public synchronized static String toJSON(Object object) {
		Gson gson = new Gson();
		return gson.toJson(object);
	}

	/**
	 * Converte um objeto correspondente a uma data em um JSON
	 * 
	 * @param object      Objeto a ser convertido
	 * @param formatoData Formato da data esperado
	 * @return JSON coma data formatada
	 */
	public synchronized static String toJSON(Object object, String formatoData) {
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat(formatoData);
		Gson gson = builder.create();
		return gson.toJson(object);
	}
	
	public static String fromFile(String path) {
		String content = "";
		BufferedReader objReader = null;
		try {
			String strCurrentLine;
			objReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), Charset.forName("UTF-8")));
			while ((strCurrentLine = objReader.readLine()) != null) {
				content += strCurrentLine;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (objReader != null)
					objReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return content;
	}

	/**
	 * Converte um JSON em uma lista de objetos
	 * 
	 * @param <T>      Tipo de objetos da lista
	 * @param jsonText JSON a ser convertido
	 * @param clazz    Tipo da classe
	 * @param params   Outros parametros String (ex.: dateFormat)
	 * @return Lista com objetos da classe com dados do JSON
	 * @throws JSONException
	 */
	public synchronized static <T> List<T> toList(String jsonText, Class<T> clazz, String... params) throws JSONException {
		String dateFormat = !List.of(params).isEmpty() ? params[0] : null;
        JSONArray jsonArray = new JSONArray(jsonText);
        List<T> result = new ArrayList<T>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            T object = null;
            if(dateFormat != null) {
                object = toObjectWithFormattedDate(jsonObject.toString(), dateFormat, clazz);   
            }
            else {
                object = toObject(jsonObject.toString(), clazz);
            }
            result.add(object);
        }
        return result;
    }
	
	public static String stringify(Object instance) {
		String str = toJSON(instance);
		JSONObject json = new JSONObject(str);
		return json.toString(2);
	}

}
