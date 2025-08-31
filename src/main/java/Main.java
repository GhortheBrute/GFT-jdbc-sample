import org.flywaydb.core.Flyway;
import persistance.entity.EmployeeDAO;

public class Main {

    private final static EmployeeDAO employeeDAO = new EmployeeDAO();

    public static Flyway getFlyway() {
        return Flyway.configure().dataSource("jdbc:mysql://localhost/jdbcsample", System.getenv("DB_MYSQL_USER"), System.getenv("DB_MYSQL_PASSWORD")).load();
    }

    public static void migrar () {
        getFlyway().migrate();
    }

    public static void reparar () {
        getFlyway().repair();
    }

    public static void main(String[] args) {

//        Main.migrar();
//        Main.reparar();

//        Flyway flyway = Flyway.configure().dataSource("jdbc:mysql://localhost/jdbcsample", System.getenv("DB_MYSQL_USER"), System.getenv("DB_MYSQL_PASSWORD")).load();
//        flyway.repair();
//        flyway.migrate();

//        var employee = new EmployeeEntity();
//        employee.setName("Miguel");
//        employee.setSalary(new BigDecimal("2000.00"));
//        employee.setBirthdate(OffsetDateTime.now().minusYears(49));
//        System.out.println(employee);
//        employeeDAO.insert(employee);
//        System.out.println(employee);

        employeeDAO.findAll().forEach(System.out::println);

//        var updateEmployee = new EmployeeEntity();
//        updateEmployee.setName("João");
//        updateEmployee.setSalary(new BigDecimal("3000.00"));
//        updateEmployee.setBirthdate(OffsetDateTime.now().minusYears(48));
//        updateEmployee.setId(2L);
//
//        System.out.println("Atualizando");
//        employeeDAO.update(updateEmployee);
//
//        System.out.println("Listando");
//        employeeDAO.findAll().forEach(System.out::println);
//
//        System.out.println("Buscando pelo ID");
//        System.out.println(employeeDAO.findById(1L));
//
//        System.out.println("Deletando");
//        employeeDAO.delete(4L);
//
//        System.out.println("Listando");
//        employeeDAO.findAll().forEach(System.out::println);
    }
}