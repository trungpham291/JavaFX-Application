package tutorial.javafxapplication;

import java.sql.*;
import java.util.ArrayList;

public class StudentsRepository {

    private final String MYSQL_CONNECTION = "jdbc:mysql://localhost:3306/java_application";
    private final String MYSQL_USER = "root";
    private final String MYSQL_PASSWORD = "";

    public ArrayList<Student> findAll(){
        ArrayList<Student> students = new ArrayList<Student>();
        try {
            Connection connection = DriverManager.getConnection(MYSQL_CONNECTION, MYSQL_USER, MYSQL_PASSWORD);
            String STRING_MYSQL = "SELECT * FROM students";
            PreparedStatement preparedStatement = connection.prepareStatement(STRING_MYSQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setAge(resultSet.getInt("age"));
                student.setAddress(resultSet.getString("address"));
                students.add(student);
            }
            System.out.println("Successfully loaded students");
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    public Student findById(long id){
        Student student = null;
        try {
            Connection connection = DriverManager.getConnection(MYSQL_CONNECTION, MYSQL_USER, MYSQL_PASSWORD);
            String STRING_MYSQL = "SELECT * FROM students WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(STRING_MYSQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                student = new Student();
                student.setId(resultSet.getLong("id"));
                student.setName(resultSet.getString("name"));
                student.setAge(resultSet.getInt("age"));
                student.setAddress(resultSet.getString("address"));
            }

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return student;
    }

    public Student save(Student student){
        try{
            Connection connection = DriverManager.getConnection(MYSQL_CONNECTION, MYSQL_USER, MYSQL_PASSWORD);
            String STRING_MYSQL = "INSERT INTO students (name, age, address) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(STRING_MYSQL);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getAge());
            preparedStatement.setString(3, student.getAddress());
            preparedStatement.execute();

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return student;
    }

    public Student update(Student student){
        try {
            Connection connection = DriverManager.getConnection(MYSQL_CONNECTION, MYSQL_USER, MYSQL_PASSWORD);
            String STRING_MYSQL = "update students set name = ?, age = ?, address = ? where id = ?";
            PreparedStatement preparedStatement =connection.prepareStatement(STRING_MYSQL);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getAge());
            preparedStatement.setString(3, student.getAddress());
            preparedStatement.setLong(4, student.getId());
            preparedStatement.execute();
            System.out.println("successfully updated student");
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return student;
    }

    public Student delete(long id){
        try {
            Connection connection = DriverManager.getConnection(MYSQL_CONNECTION, MYSQL_USER, MYSQL_PASSWORD);
            String SQL_String = "delete from students where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_String);

            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            System.out.println("success delete");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void main(String[] args) {
        StudentsRepository repository = new StudentsRepository();
        Student student = new Student();
        student.setName("nguyen");
        student.setAge(20);
        student.setAddress("ha noi");
        repository.save(student);
    }
}
