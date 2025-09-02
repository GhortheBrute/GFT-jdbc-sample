import org.flywaydb.core.Flyway;
import persistance.ContactDAO;
import persistance.EmployeeAuditDAO;
import persistance.EmployeeDAO;
import persistance.entity.ContactEntity;
import persistance.entity.EmployeeEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import net.datafaker.Faker;

public class Main {

    private final static EmployeeDAO employeeDAO = new EmployeeDAO();

    private final static EmployeeAuditDAO employeeAuditDAO = new EmployeeAuditDAO();

    private final static Faker faker = new Faker(Locale.of("pt-BR"));

    private final static ContactDAO contactDAO = new ContactDAO();

    public static Flyway getFlyway() {
        return Flyway.configure().dataSource("jdbc:mysql://localhost/jdbcsample", System.getenv("DB_MYSQL_USER"), System.getenv("DB_MYSQL_PASSWORD"))
                .baselineOnMigrate(true)
                //.outOfOrder(true)
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
//        System.out.println("ANTES\n" + insert);
//        employeeDAO.insertWithProcedure(insert);
//        System.out.println("DEPOIS\n" + insert);



//        var lista = Stream.generate(() -> {
//            EmployeeEntity employee = new EmployeeEntity();
////            Faker faker = new Faker(Locale.forLanguageTag("pt-BR"));
//            employee.setName(faker.name().fullName());
//            employee.setSalary(new BigDecimal(faker.number().randomDouble(2, 1000, 10000)));
//            LocalDate birthDate = faker.date().birthdayLocalDate(18, 54);
//            OffsetDateTime birthday = birthDate.atStartOfDay().atOffset(ZoneOffset.UTC);
//            employee.setBirthdate(birthday);
//
//            return employee;
//        }).limit(4000).toList();
//        employeeDAO.insertBatch(lista);


        //System.out.println(teste.name().fullName());



//        employeeDAO.findAll().forEach(System.out::println);

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

        var contact = new ContactEntity();
        contact.setDescription("mirella@email.com");
        contact.setType("e-mail");
        contact.setEmployee(employeeDAO.findById(1L));
        contactDAO.insert(contact);
    }
}