package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);
      userService.deleteAllUsers();

      User user1 = new User("User1", "Lastname1", "user1@mail.ru");
      Car car1 = new Car("Toyota", 12345);
      user1.setCar(car1);

      User user2 = new User("User2", "Lastname2", "user2@mail.ru");
      Car car2 = new Car("Honda", 67890);
      user2.setCar(car2);

      User user3 = new User("User3", "Lastname3", "user3@mail.ru");
      Car car3 = new Car("Ford", 54321);
      user3.setCar(car3);

      User user4 = new User("User4", "Lastname4", "user4@mail.ru");

      userService.add(user1);
      userService.add(user2);
      userService.add(user3);
      userService.add(user4);

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = " + user.getId());
         System.out.println("First Name = " + user.getFirstName());
         System.out.println("Last Name = " + user.getLastName());
         System.out.println("Email = " + user.getEmail());

         Car car = user.getCar();
         if (car != null) {
            System.out.println("Car Model = " + car.getModel());
            System.out.println("Car Series = " + car.getSeries());
         } else {
            System.out.println("User has no car.");
         }

         System.out.println();
      }
      User foundUser = userService.getUserByCarModelAndSeries("Toyota", 12345);
      if (foundUser != null) {
         System.out.println("Found User with Toyota: " + foundUser.getFirstName() + " " + foundUser.getLastName());
      } else {
         System.out.println("User not found.");
      }
      context.close();
   }
}