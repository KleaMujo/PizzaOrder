package dao;

import models.Menu;
import models.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class OrderDao extends BaseDao {
    public Optional<Order> getOrderById(int id) {
        try (Session session = getFactory().openSession()) {
            return session.createQuery("from Order o where o.id = :id", Order.class)
                    .setParameter("id", id)
                    .stream().findFirst();
        }
    }

    public Order create(Order order) {
        Transaction transaction = null;
        try (Session session = getFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(order);
            transaction.commit();
            return order;
        }
    }
}
