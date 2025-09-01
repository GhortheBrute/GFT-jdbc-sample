import org.flywaydb.core.Flyway;
import persistance.EmployeeAuditDAO;
import persistance.EmployeeDAO;
import persistance.entity.EmployeeEntity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private final static EmployeeDAO employeeDAO = new EmployeeDAO();

    private final static EmployeeAuditDAO employeeAuditDAO = new EmployeeAuditDAO();

    public static Flyway getFlyway() {
        return Flyway.configure().dataSource("jdbc:mysql://localhost/jdbcsample", System.getenv("DB_MYSQL_USER"), System.getenv("DB_MYSQL_PASSWORD"))
                .baselineOnMigrate(true)
                .outOfOrder(true)
                .load();
    }

    public static void migrar () {
        getFlyway().migrate();
    }

    public static void reparar () {
        getFlyway().repair();
    }

    public static void adicionarDados () {
        final OffsetDateTime now = OffsetDateTime.now();
        final List<String> names = List.of("Jesus", "João", "Maria");
        final List<BigDecimal> salaries = List.of(new BigDecimal("7500"), new BigDecimal("5500"), new BigDecimal("6000"));
        final List<OffsetDateTime> birthdates = List.of(now.minusYears(35), now.minusYears(48), now.minusYears(50));

        int size = names.size();
        for (int i = 0; i < size; i++) {
            EmployeeEntity employee = newEmployee(names.get(i), salaries.get(i), birthdates.get(i));
            employeeDAO.insert(employee);
        }
    }

    private static EmployeeEntity newEmployee(String name, BigDecimal salary, OffsetDateTime birthdate) {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setName(name);
        employee.setSalary(salary);
        employee.setBirthdate(birthdate);
        return employee;
    }


    public static void main(String[] args) {


        //Main.reparar();
//        Main.migrar();
//        adicionarDados();

//        Flyway flyway = Flyway.configure().dataSource("jdbc:mysql://localhost/jdbcsample", System.getenv("DB_MYSQL_USER"), System.getenv("DB_MYSQL_PASSWORD")).load();
//        flyway.repair();
//        flyway.migrate();

//        var insert = new EmployeeEntity();
//        insert.setName("Sophie");
//        insert.setSalary(new BigDecimal("140000.00"));
//        insert.setBirthdate(OffsetDateTime.now().minusYears(30));
////        System.out.println(insert);
//        employeeDAO.insertWithProcedure(insert);
////        System.out.println(insert);

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