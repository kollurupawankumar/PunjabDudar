package com.punjab.springmvc.others;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.sql2o.*;

public class Ingredient {
  private int mId;
  private String mName;
  private String mUnit;
  private int mDesiredOnHand;
  private int mShelfLifeDays;

  public Ingredient(String name, String unit, int desiredOnHand, int shelfLifeDays) {
    mName = name;
    mUnit = unit;
    mDesiredOnHand = desiredOnHand;
    mShelfLifeDays = shelfLifeDays;
  }

  public int getId() {
    return mId;
  }

  public String getName() {
    return mName;
  }

  public String getUnit() {
    return mUnit;
  }

  public int getDesiredOnHand() {
    return mDesiredOnHand;
  }

  public int getShelfLifeDays() {
    return mShelfLifeDays;
  }

  @Override
  public boolean equals(Object otherIngredient) {
    if(!(otherIngredient instanceof Ingredient)) {
      return false;
    } else {
      Ingredient newIngredient = (Ingredient) otherIngredient;
      return this.getId() == newIngredient.getId() &&
             this.getName().equals(newIngredient.getName()) &&
             this.getUnit().equals(newIngredient.getUnit()) &&
             this.getDesiredOnHand() == newIngredient.getDesiredOnHand() &&
             this.getShelfLifeDays() == newIngredient.getShelfLifeDays();
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO dumdar.ingredients (name, unit, desired_on_hand, " +
                   "shelf_life_days) VALUES (:name, :unit, :desired_on_hand, " +
                   ":shelf_life_days)";
      mId = (int) con.createQuery(sql, true)
        .addParameter("name", mName)
        .addParameter("unit", mUnit)
        .addParameter("desired_on_hand", mDesiredOnHand)
        .addParameter("shelf_life_days", mShelfLifeDays)
        .executeUpdate()
        .getKey();
    }
  }

  public static Ingredient find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName, unit AS mUnit, " +
                   "desired_on_hand AS mDesiredOnHand, shelf_life_days AS " +
                   "mShelfLifeDays FROM dumdar.ingredients WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Ingredient.class);
    }
  }

  public static List<Ingredient> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName, unit AS mUnit, " +
                   "desired_on_hand AS mDesiredOnHand, shelf_life_days AS " +
                   "mShelfLifeDays FROM dumdar.ingredients ORDER BY name ASC";
      return con.createQuery(sql).executeAndFetch(Ingredient.class);
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM dumdar.ingredients WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", mId)
        .executeUpdate();
    }
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM dumdar.dishes_ingredients " +
                          "WHERE ingredient_id = :id";
      con.createQuery(sql)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public void update(String name, String unit, int desiredOnHand, int shelfLifeDays) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE dumdar.ingredients SET name = :name, unit = :unit, " +
                   "desired_on_hand = :desiredOnHand, shelf_life_days = " +
                   ":shelfLifeDays WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", mId)
        .addParameter("name", name)
        .addParameter("unit", unit)
        .addParameter("desiredOnHand", desiredOnHand)
        .addParameter("shelfLifeDays", shelfLifeDays)
        .executeUpdate();
    }
  }

  public List<Inventory> getInventories() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT inventories.id AS mId, inventories.ingredient_id AS mIngredientId, inventories.current_on_hand AS mCurrentOnHand, inventories.delivery_date AS mDeliveryDate, inventories.expiration_date AS mExpirationDate FROM dumdar.inventories inventories INNER JOIN dumdar.ingredients ingredients ON (ingredients.id = inventories.ingredient_id) WHERE inventories.ingredient_id = :id ORDER BY inventories.expiration_date ASC";
      return con.createQuery(sql)
        .addParameter("id", mId)
        .executeAndFetch(Inventory.class);
    }
  }

  public List<Dish> getAllDishes() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT dishes.id AS mId, dishes.name AS mName, dishes.category AS mCategory FROM dumdar.dishes INNER JOIN dishes_ingredients ON (dishes.id = dishes_ingredients.dish_id) WHERE ingredient_id = :id";
      return con.createQuery(sql)
        .addParameter("id", mId)
        .executeAndFetch(Dish.class);
    }
  }

  public String getMostRecentExpiration() {
    String expirationDate = "3000-12-31";
    for (Inventory inventory : this.getInventories()) {
      if (LocalDate.parse(inventory.getExpirationDate()).isBefore(LocalDate.parse(expirationDate)) && inventory.getCurrentOnHand() > 0) {
        expirationDate = inventory.getExpirationDate();
      }
    }
    if (LocalDate.parse(expirationDate).isBefore(LocalDate.parse("3000-12-31")))
    {
      return expirationDate;
    } else {
      return "";
    }
  }

  public int getIngredientAmountForDish(int dishId) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT ingredient_amount FROM dumdar.dishes_ingredients WHERE dish_id = :dish_id AND ingredient_id = :ingredient_id";
      return con.createQuery(sql)
        .addParameter("dish_id", dishId)
        .addParameter("ingredient_id", mId)
        .executeScalar(Integer.class);
    }
  }

  public int getTotalOnHand() {
    int totalOnHand = 0;
    for (Inventory inventory : this.getInventories()) {
      totalOnHand += inventory.getCurrentOnHand();
    }
    return totalOnHand;
  }

  public void decrement(int amount) {
    int totalUsed = 0;
    while (totalUsed < amount) {
      for (Inventory inventory : this.getInventories()) {
        for (int i = inventory.getCurrentOnHand(); i > 0 && totalUsed < amount; i--) {
          totalUsed++;
          inventory.update(inventory.getCurrentOnHand() - 1);
        }
      }
    }
  }

  public boolean isLessThanOrEqualToTwentyPercent() {
    if ((double)this.getTotalOnHand()/this.getDesiredOnHand() <= .2) {
      return true;
    } else {
      return false;
    }
  }


}
