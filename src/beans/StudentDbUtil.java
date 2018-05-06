package beans;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDbUtil {
    private static StudentDbUtil dbUtil;
    private DataSource source;

    private StudentDbUtil() throws NamingException {
        setDataSource();
    }

    static StudentDbUtil getInstance() throws NamingException {
        if(dbUtil == null){
            dbUtil = new StudentDbUtil();
        }
        return dbUtil;
    }

    private void setDataSource() throws NamingException {
        Context context = new InitialContext();
        source = (DataSource) context.lookup("java:comp/env/jdbc/tomcat-connection");
    }

    List<Student> getStudents() throws SQLException {
        return doQuery(null);
    }

    List<Student> addStudent(Student student) throws SQLException {
        return doQuery("INSERT INTO student(first_name,last_name,email) VALUE ('" +
                student.getName() + "', '" + student.getLastName() + "', '" + student.getEmail() + "')");
    }

    List<Student> deleteStudent(int id) throws SQLException {
        return doQuery("DELETE FROM student WHERE id =" + id);
    }

    List<Student> updateStudent(Student student) throws SQLException {
        return doQuery("UPDATE student SET first_name ='" + student.getName() +
                "', last_name ='" + student.getLastName() + "', email ='" + student.getEmail() +
                "' WHERE id =" + student.getId());
    }

    private List<Student> doQuery(String sqlQuery) throws SQLException {
        List<Student> students = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;

        try {
            connection = source.getConnection();
            connection.setAutoCommit(false);

            statement = connection.createStatement();

            if (sqlQuery != null) {
                //Executing current Query
                statement.executeUpdate(sqlQuery);
            }
            //Getting Students from db
            set = statement.executeQuery("SELECT * FROM student");
            connection.commit();

            while (set.next()){
                Student student = new Student();
                student.setId(set.getInt("id"));
                student.setName(set.getString("first_name"));
                student.setLastName(set.getString("last_name"));
                student.setEmail(set.getString("email"));

                students.add(student);
            }

        } finally {
            close(connection,statement,set);
        }
        return students;
    }

    private void close(Connection connection, Statement statement, ResultSet set) {
        try {
            if (connection != null)
                connection.close();
            if (statement != null)
                statement.close();
            if (set !=null)
                set.close();
        }catch (SQLException exc){
            exc.getErrorCode();
        }
    }
}
