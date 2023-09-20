package hiber.service;

import hiber.dao.UserDao;
import hiber.model.User;
import jakarta.persistence.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

   private final UserDao userDao;

   public UserServiceImp(UserDao userDao, SessionFactory sessionFactory) {
      this.userDao = userDao;
      this.sessionFactory = sessionFactory;
   }

   @Transactional
   @Override
   public void add(User user) {
      userDao.add(user);
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> listUsers() {
      return userDao.listUsers();
   }
   @Transactional
   @Override
   public void deleteAllUsers(){
      userDao.deleteAllUsers();
   }

   private final SessionFactory sessionFactory;
   @Transactional(readOnly = true)
   @Override
   public User getUserByCarModelAndSeries(String model, int series) {
      String hql = "FROM User u WHERE u.car.model = :model AND u.car.series = :series";
      Query query = sessionFactory.getCurrentSession().createQuery(hql);
      query.setParameter("model", model);
      query.setParameter("series", series);

      return (User) query.getSingleResult();
   }
}
