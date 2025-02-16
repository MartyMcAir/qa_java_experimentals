package dev;

import dev.spark.UserController;

public class SparkApplication {
    public static void main(String[] args) {
        UserController userController = new UserController();
        userController.registerRoutes();
        System.out.println("🚀 Spark Java API запущен на порту 8080");
    }
}

