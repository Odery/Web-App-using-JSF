package beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean(name = "studentController")
@SessionScoped
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
