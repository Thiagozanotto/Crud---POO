package br.com.CrudPo.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory fabrica = criarFabricaDeSessoes();

	public static SessionFactory getfabrica() {
		return fabrica;
	}

	public static SessionFactory criarFabricaDeSessoes() {
		try {
			Configuration config = new Configuration().configure("hibernate.cfg.xml");
			SessionFactory fb = config.buildSessionFactory();
			return fb;
		} catch (Throwable e) {
			System.err.println("A conex�o com o banco n�o pode ser estabelecida" + e);
			throw new ExceptionInInitializerError(e);
		}
	}
}
