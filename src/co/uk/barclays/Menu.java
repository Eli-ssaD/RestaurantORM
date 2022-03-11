package co.uk.barclays;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.SQLException;

public class Menu {
    private int id;
    private int restaurantID;
    private String title;
    public static ArrayList<Menu> all = new ArrayList<>();

    public static void init() {
        try {
            Statement createTable = DB.conn.createStatement();
            createTable.execute(
                    "Create table if not exists menus (id integer primary key, restaurantID integer, Title text)");
            Statement getMenu = DB.conn.createStatement();
            ResultSet menus = getMenu.executeQuery("select*from menus");
            while (menus.next()) {
                int id = menus.getInt(1);
                int restaurant_ID = menus.getInt(2);
                String title = menus.getString(3);
                new Menu(id, restaurant_ID, title);
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public Menu(Integer restaurantID, String title) {
        this.restaurantID = restaurantID;
        this.title = title;
        try {
            PreparedStatement insertMenu = DB.conn
                    .prepareStatement("Insert into menus (restaurantID,title) values(?,?)");
            insertMenu.setInt(1, this.restaurantID);
            insertMenu.setString(2, this.title);
            insertMenu.executeUpdate();
            this.id = insertMenu.getGeneratedKeys().getInt(1);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        Menu.all.add(this);
    }

    public Menu(int id, int restaurantID, String title) {
        this.id = id;
        this.restaurantID = restaurantID;
        this.title = title;
        Menu.all.add(this);
    }

    public String getTitle() {
        return title;
    }

    public int getID() { return this.id; 
    }
}