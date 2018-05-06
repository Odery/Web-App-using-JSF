package beans;

public class Main {
    public static void main(String[] args) {
        Student student = new Student("Steve","Rock","kamu@d.cc");
        student.setId(22);
        System.out.println("UPDATE student SET first_name ='" + student.getName() +
                "', last_name ='" + student.getLastName() + "', email ='" + student.getEmail() +
                "' WHERE id =" + student.getId());
    }
}
