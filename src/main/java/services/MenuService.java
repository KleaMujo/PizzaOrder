package services;

import dao.MenuDao;
import models.Menu;

import java.util.List;
import java.util.Optional;

public class MenuService {
    protected MenuDao menuDao;
    public MenuService(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    public void initMenu() {
        Menu margarita = new Menu();
        margarita.setPrice(300);
        margarita.setName("Margarita");
        menuDao.saveIfAbsent(margarita);

        Menu hamMushroom = new Menu();
        hamMushroom.setPrice(500);
        hamMushroom.setName("Proshut, Kerpurdha");
        menuDao.saveIfAbsent(hamMushroom);

        Menu vegetarian = new Menu();
        vegetarian.setPrice(450);
        vegetarian.setName("Vegjetariane");
        menuDao.saveIfAbsent(vegetarian);
    }

    public List<Menu> getMenu() {
        return menuDao.getMenus();
    }

    public void printMenu() {
        List<Menu> menus = getMenu();
        System.out.println("---------------------- Pizzeria SDA Menu ----------------------");
        menus.forEach(o -> System.out.printf("%s - %s - %s%n", o.getId(), o.getName(), o.getPrice()));
        System.out.println("---------------------------------------------------------------");
    }

    public Optional<Menu> getById(int productId) {
        return menuDao.getById(productId);
    }
}
