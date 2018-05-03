package beans;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDbUtil {
    private static StudentDbUtil dbUtil;
    private DataSource source;

    private StudentDbUtil() throws NamingException {
        setDataSource();
    }

    public static StudentDbUtil getInstance() throws NamingException {
        if(dbUtil == null){
            dbUtil = new StudentDbUtil();
        }
        return dbUtil;
    }

    private void setDataSource() throws NamingException {
        Context context = new InitialContext();
        source = (DataSource) context.lookup("java:comp/env/jdbc/tomcat-connection");
    }

    public List<Student> getStudents() throws SQLException {
        List<Student> students = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            connection = source.getConnection();
            statement = connection.prepareStatement("SELECT * FROM student");
            set = statement.executeQuery();

            while (set.next()){
                Student student = new Student();
                student.setId(set.getInt("id"));
                student.setName(set.getString("first_name"));
                student.setLastName(set.getString("last_name"));
                student.setEmail(set.getString("email"));

                students.add(student);
            }
        }catch (SQLException exc){
            throw exc;
        }finally {
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
