import java.sql.*;

public class demo {

    public static void main(String[] args) {

        String url = "jdbc:mysql://127.0.0.1:3306/forjdbc";
        String username = "root";
        String password = "1234";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);

            
            CallableStatement csInsert =
                    con.prepareCall("{call insertLearner(?, ?, ?, ?)}");

            csInsert.setInt(1, 9);
            csInsert.setString(2, "Raj");
            csInsert.setInt(3, 20);
            csInsert.setDouble(4, 25000);

            int rows = csInsert.executeUpdate();

            if (rows > 0) {
                System.out.println("Record inserted successfully!");
                System.out.println("Name = Raj");
                System.out.println("Age = 20");
                System.out.println("Salary = 20000");
            }


            CallableStatement csSelect =
                    con.prepareCall("{call selectLearner()}");

            ResultSet rs = csSelect.executeQuery();
            System.out.println("record selected successfully");
            System.out.println("ID\tName\tAge\tSalary");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + "\t" +
                                rs.getString("name") + "\t" +
                                rs.getInt("age") + "\t" +
                                rs.getDouble("salary")
                );
            }




            CallableStatement csUpdate =
                    con.prepareCall("{call updateLearnerSalary(?,?)}");
            System.out.println("record updated sucessfuly");
            csUpdate.setInt(1, 580);
            csUpdate.setDouble(2, 40000);
            csUpdate.executeUpdate();


            CallableStatement csDelete =
                    con.prepareCall("{call deleteLearner(?)}");

            int deleteId = 580;
            csDelete.setInt(1, deleteId);

            int column = csDelete.executeUpdate();

            if (column > 0) {
                System.out.println("id " + deleteId + " is deleted");
            } else {
                System.out.println("id " + deleteId + " not found");
            }

            csDelete.close();
            con.close();




        } catch (Exception e) {
            System.out.println(e);
        }
    }
}