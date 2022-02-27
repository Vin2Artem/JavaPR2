package DAO;

public abstract class DAOFactory {

    public static final int SQLITE = 1;

    // Здесь будет метод для каждого DAO, который может быть создан.
    // Реализовывать эти методы должны конкретные генераторы.
    public abstract UserDAO getUserDAO();

    public static DAOFactory getDAOFactory(int whichFactory) {

        switch (whichFactory) {
            case SQLITE:
                return new SQLiteDAOFactory();
            default:
                return null;
        }
    }
}
