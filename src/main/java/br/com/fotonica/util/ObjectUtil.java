package br.com.fotonica.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectUtil {

	/**
	 * Serializa uma entidade em um array de bytes
	 * 
	 * @author Talison
	 * @param instance Entidade
	 * @return Array de bytes referente a entidade
	 * @throws IOException 
	 */
	public static byte[] convertToBinary(Object instance) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		try {
			oos.writeObject(instance);
		} catch (IOException e) {
			throw e;
		} finally {
			if (oos != null) {
				oos.close();
			}
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		return bais.readAllBytes();
	}

	/**
	 * Converte o array de bytes armazenados nessa entidade em um objeto
	 * 
	 * @author Talison
	 * @return Objeto referente ao array de bytes armazenado
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object convertToObject(byte[] bytes) throws IOException, ClassNotFoundException {
		Object obj = null;
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
			obj = ois.readObject();
		} catch (IOException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			if (ois != null) {
				ois.close();
			}
		}

		return obj;
	}

}
