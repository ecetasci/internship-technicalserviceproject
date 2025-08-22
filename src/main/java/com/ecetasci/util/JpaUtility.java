package com.ecetasci.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtility {
	private static EntityManagerFactory emf;
	static {
		try {
			emf = Persistence.createEntityManagerFactory("pu_jpa");
		}
		catch (Exception e) {
			System.out.println("EntityManagerFactory creation failed"+e.getMessage());
		}
	}
	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	public static void closeEntityManagerFactory() {
		if(emf != null && emf.isOpen()) {
			emf.close();
		}
	}
}