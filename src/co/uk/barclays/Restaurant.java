package co.uk.barclays;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Restaurant {
    private int id;
    private String name;
    private String imageURL;

    public static ArrayList<Restaurant> all = new ArrayList<>();

    public static void init() {
        try {
            Statement createTable = DB.conn.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS restaurants (id INTEGER PRIMARY KEY, name TEXT, imageURL TEXT);");
            // create a query to check if restaurants are in table
            Statement getRestaurants = DB.conn.createStatement();
            // excecute and capture the results
            ResultSet restaurants = getRestaurants.executeQuery("SELECT * FROM restaurants;");
            while (restaurants.next()) {
                // create instances of a restaurant from data name etc
                int id = restaurants.getInt(1);
                String name = restaurants.getString(2);
                String imageURL = restaurants.getString(3);
                new Restaurant(id, name, imageURL);

            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    // created a new restaurant constructor
    public Restaurant(String name, String imageURL) {
        this.name = name;
        this.imageURL = imageURL;

        try {
            PreparedStatement insertRestaurant = DB.conn
                    .prepareStatement("INSERT INTO restaurants (name, imageURL) VALUES (?, ?);");
            insertRestaurant.setString(1, this.name);
            insertRestaurant.setString(2, this.imageURL);
            insertRestaurant.executeUpdate();
            this.id = insertRestaurant.getGeneratedKeys().getInt(1);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        Restaurant.all.add(this);
    }
    //create a diff constructor rec id,name & string
    //this constructor makes the java object/instance
    //don't need to do anything in the database
    public Restaurant(int id, String name, String imageURL){
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        Restaurant.all.add(this);
    }

    public int getId() {
        return this.id;
    }
}
