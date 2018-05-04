package beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean(name = "studentController")
@ApplicationScoped
public class StudentControllerBean {
    private StudentDbUtil dbUtil;
    private List<Student> students;
    private Logger logger = Logger.getLogger(getClass().getName());

    public StudentControllerBean() throws NamingException {
        dbUtil = StudentDbUtil.getInstance();
    }

    public List<Student> getStudents(){
        return students;
    }

    public void addStudent(Student student) {
        try {
            students = dbUtil.addStudent(student);
        } catch (Exception exc) {
            logger.log(Level.SEVERE, "Error adding student to db!", exc);

        }
    }

    public void deleteStudent(Student student) {
        try {
            students = dbUtil.addStudent(student);
        } catch (Exception exc) {
            logger.log(Level.SEVERE, "Error deleting student from db!", exc);

        }
    }

    public void updateStudent(Student student) {
        try {
            students = dbUtil.updateStudent(student);
        } catch (Exception exc) {
            logger.log(Level.SEVERE, "Error deleting student from db!", exc);

        }
    }

    public void loadStudents() {
        try {
            students = dbUtil.getStudents();
        }catch (Exception exc){
            logger.log(Level.SEVERE,"Error loading students from db!",exc);

        }
    }

    private void forwardExc(Exception exc){
        FacesMessage message = new FacesMessage(exc.getLocalizedMessage());
        FacesContext.getCurrentInstance().addMessage("Error loading students!",message);
    }
}
