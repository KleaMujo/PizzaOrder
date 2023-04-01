package dao;

import models.Menu;
import models.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class MenuDao extends BaseDao {
    public MenuDao () {
        // Do this call to initialise database
        getFactory();
    }
    public void save(Menu student) {
        Transaction transaction = null;
        try (Session session = getFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(student);
            transaction.commit();
        }
    }

    public void saveIfAbsent(Menu student) {
        Transaction transaction;
        try (Session session = getFactory().openSession()) {
            transaction = session.beginTransaction();
            Optional<Menu> dbItem = session.createQuery("from Menu m where m.name = :name", Menu.class)
                    .setParameter("name", student.getName()).stream().findFirst();
            //nqs pica nuk ndodhet ne tabele ateher shtoje
            if (dbItem.isEmpty()) {
                session.persist(student); //shtimi
            }

            transaction.commit();
        }
    }

    public List<Menu> getMenus() {
        try (Session session = getFactory().openSession()) {
            return session.createQuery("from Menu", Menu.class).list();
        }
    }

    public Optional<Menu> getById(int id) {
        try (Session session = getFactory().openSession()) {
            return session.createQuery("from Menu o where o.id = :id", Menu.class)
                    .setParameter("id", id)
                    .stream().findFirst();
        }
    }
}
