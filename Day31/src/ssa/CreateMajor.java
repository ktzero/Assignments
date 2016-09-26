package ssa;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateMajor {

	SessionFactory factory;
	
	public CreateMajor() {}
		// TODO Auto-generated method stub
	
	public void openFactory()
	{
		factory = new Configuration()
			.configure("hibernate.cfg.xml")
			.addAnnotatedClass(Major.class)
			.buildSessionFactory();
	}
	
	public void closeFactory(){	factory.close();	}
	
		
	public void insertMajor(String desc, int sat)
	{
			Session session = factory.getCurrentSession();
			session.beginTransaction();
			Major maj = new Major(desc, sat);
			session.save(maj);
			session.getTransaction().commit();
			System.out.printf("Major %s has been inserted "
					+ "with reqiured SAT of %d\n", desc, sat);
	}
	
	public void updateMajor(String oldDesc, String newDesc)
	{
//		session = factory.getCurrentSession();
//		session.beginTransaction();
//		Major maj = session.get(Major.class, 8);
//		maj.setReq_sat(1345);
//		session.getTransaction().commit();
		
		Session session= factory.getCurrentSession();
		session.beginTransaction();
		session.createQuery("update Major set description='" + newDesc
				+ "' where description  ='" + oldDesc + "'").executeUpdate();
		session.getTransaction().commit();
		
		System.out.printf("Major description has been updated "
				+ "to %s\n", newDesc);
	}
	
	public void updateMajor(int majorID, String newDesc)
	{	
		Session session= factory.getCurrentSession();
		session.beginTransaction();
		session.createQuery(String.format("update Major set description=% where id = %d",
				newDesc, majorID)).executeUpdate();
		session.getTransaction().commit();
		
		System.out.printf("Major description has been updated "
				+ "to %s\n", newDesc);
	}
	
	public void deleteMajor(String desc)
	{
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session = factory.getCurrentSession();
		session.beginTransaction();
		session.createQuery("delete from Major where description  ='" + desc + "'").executeUpdate();
		session.getTransaction().commit();
		
		System.out.printf("Major %s has been deleted\n", desc);
	}
	
	public void displayAll()
	{
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<Major> majors = session.createQuery("from Major").list();
		
		for(Major maj : majors)
			System.out.println(maj);
	}
}
			
			

