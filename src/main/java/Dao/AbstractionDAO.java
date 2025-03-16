package Dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Connection.ConnectionFactory;
/**
 * Clasa generica pentru operatii CRUD (create, read, update, delete) pe obiecte de tip T.
 * @param <T> tipul obiectelor cu care lucreaza clasa
 */
public class AbstractionDAO<T> {
    // Logger pentru inregistrarea mesajelor
    protected static final Logger LOGGER = Logger.getLogger(AbstractionDAO.class.getName());

    // Tipul generic T cu care lucreaza clasa
    private final Class<T> type;
    /**
     * Constructorul clasei care initializeaza tipul obiectului T.
     */

    @SuppressWarnings("unchecked")
    public AbstractionDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    // Metoda pentru crearea interogarii SELECT
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE ").append(field).append(" =?");
        return sb.toString();
    }
    /**
     * Creeaza si returneaza o lista cu toate obiectele din tabela asociata clasei T.
     * @return lista de obiecte T
     */

    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + type.getSimpleName();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
    /**
     * Cauta si returneaza un obiect T dupa id-ul sau.
     * @param id id-ul obiectului cautat
     * @return obiectul T gasit sau null daca nu exista
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            List<T> objects = createObjects(resultSet);
            if (!objects.isEmpty()) {
                return objects.get(0);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
    /**
     * Cauta si returneaza un obiect T dupa numele sau.
     * @param name numele obiectului cautat
     * @return obiectul T gasit sau null daca nu exista
     */

    public T findByName(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("name");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            resultSet = statement.executeQuery();

            List<T> objects = createObjects(resultSet);
            if (!objects.isEmpty()) {
                return objects.get(0);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findByName " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
    // Metoda pentru crearea obiectelor T din ResultSet
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        Constructor<?>[] ctors = type.getDeclaredConstructors();
        Constructor<?> ctor = null;
        for (Constructor<?> c : ctors) {
            if (c.getGenericParameterTypes().length == 0) {
                ctor = c;
                break;
            }
        }
        try {
            while (resultSet.next()) {
                if (ctor != null) {
                    ctor.setAccessible(true);
                    T instance = (T) ctor.newInstance();
                    for (Field field : type.getDeclaredFields()) {
                        field.setAccessible(true);
                        String fieldName = field.getName();
                        Object value = resultSet.getObject(fieldName);
                        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                        Method method = propertyDescriptor.getWriteMethod();
                        if (method != null) {
                            method.invoke(instance, value);
                        }
                    }
                    list.add(instance);
                }
            }
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                 | InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * Insereaza un obiect T in baza de date.
     * @param t obiectul T de inserat
     * @return obiectul T inserat sau null daca a aparut o eroare
     */
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionFactory.getConnection();
            String query = createInsertQuery();
            statement = connection.prepareStatement(query);
            setPreparedStatementParameters(statement, t, true);
            statement.executeUpdate();
            return t;
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }


    /**
     * Actualizeaza un obiect T existent in baza de date.
     * @param t obiectul T de actualizat
     * @return obiectul T actualizat sau null daca a aparut o eroare
     */
    public T update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionFactory.getConnection();
            String query = createUpdateQuery();
            statement = connection.prepareStatement(query);
            setPreparedStatementParameters(statement, t, false);

            Field idField = getIdField();
            if (idField != null) {
                idField.setAccessible(true);
                Object idValue = idField.get(t);
                statement.setObject(type.getDeclaredFields().length, idValue);
            }
            statement.executeUpdate();
            return t;
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }


    /**
     * Sterge un obiect T din baza de date.
     * @param t obiectul T de sters
     * @return obiectul T sters sau null daca a aparut o eroare
     */
    public T delete(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionFactory.getConnection();
            String query = createDeleteQuery();
            statement = connection.prepareStatement(query);
            Field idField = getIdField();
            if (idField != null) {
                idField.setAccessible(true);
                Object idValue = idField.get(t);
                statement.setObject(1, idValue);
            }
            statement.executeUpdate();
            return t;
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
    // Metoda pentru crearea interogarii INSERT
    private String createInsertQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append(" (");

        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            sb.append(field.getName()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(") VALUES (");
        for (Field field : fields) {
            sb.append("?,");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }


    // Metoda pentru crearea interogarii UPDATE
    private String createUpdateQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");

        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            if (!field.getName().equalsIgnoreCase("id")) {
                sb.append(field.getName()).append(" = ?,");
            }
        }
        sb.deleteCharAt(sb.length() - 1); // remove last comma
        sb.append(" WHERE id = ?");
        return sb.toString();
    }
    // Metoda pentru crearea interogarii DELETE
    private String createDeleteQuery() {
        return "DELETE FROM " + type.getSimpleName() + " WHERE id = ?";
    }
    // Metoda pentru setarea parametrilor Statement pentru INSERT si UPDATE
    private void setPreparedStatementParameters(PreparedStatement statement, T t, boolean includeId) throws SQLException, IllegalAccessException {
        Field[] fields = type.getDeclaredFields();
        int index = 1;
        for (Field field : fields) {
            if (includeId || !field.getName().equalsIgnoreCase("id")) {
                field.setAccessible(true);
                Object value = field.get(t);
                statement.setObject(index++, value);
            }
        }
    }

    // Metoda pentru obtinerea campului ID al clasei T
    private Field getIdField() {
        for (Field field : type.getDeclaredFields()) {
            if (field.getName().equalsIgnoreCase("id")) {
                return field;
            }
        }
        return null;
    }

}
