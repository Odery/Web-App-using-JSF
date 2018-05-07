package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean(name = "studentController")
@SessionScoped
public class StudentControllerBean {
    public String searchLName;
    public String indexUrl = "index.xhtml";

    private StudentDbUtil dbUtil;
    private List<Student> students;
    private Logger logger = Logger.getLogger(getClass().getName());

    public StudentControllerBean() throws NamingException {
        dbUtil = StudentDbUtil.getInstance();
    }

    public List<Student> getStudents(){
        return students;
    }

    public String addStudent(Student student) {
        try {
            students = dbUtil.addStudent(student);
            return "index?faces-redirect=true";
        } catch (Exception exc) {
            logger.log(Level.SEVERE, "Error adding student to db!", exc);
            return null;
        }
    }

    public void deleteStudent(int id) {
        try {
            students = dbUtil.deleteStudent(id);
        } catch (Exception exc) {
            logger.log(Level.SEVERE, "Error deleting student from db!", exc);
        }
    }

    public String updateStudent(Student student) {
        try {
            students = dbUtil.updateStudent(student);
            return "index?faces-redirect=true";
        } catch (Exception exc) {
            logger.log(Level.SEVERE, "Error updating student!", exc);
            return null;
        }
    }

    public void loadStudents() {
        try {
            students = dbUtil.getStudents(searchLName);
        }catch (Exception exc){
            logger.log(Level.SEVERE,"Error loading students from db!",exc);

        }
    }

    public String loadStudent(int id){
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            Map<String,Object> map = context.getRequestMap();
            map.put("student",getStudent(id));

            return "updateStudent.xhtml";
        }catch (Exception exc){
            logger.log(Level.SEVERE,"Error loading user by id!",exc);
            return null;
        }
    }

    private Student getStudent(int id) {
        Student student = null;

        for (Student temp : students){
            if (temp.getId()==id){
                student = temp;
                break;
            }
        }

        return student;
    }

    public String getSearchLName() {
        return searchLName;
    }

    public void setSearchLName(String searchLName) {
        this.searchLName = searchLName;
    }
}
