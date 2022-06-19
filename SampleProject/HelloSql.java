import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class HelloSql {
    public static void main(String[] args) {
        try{
            /**
              ドライバーを読み込む必要は無くなりました。
              よって、以下のコードは不要です。
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            **/
            String url = "jdbc:mysql://localhost/ncsj2022";
            String user = "root";
            String pass = "";

            Connection con = DriverManager.getConnection(url,user,pass);
            System.out.println("Connection : OK");

            String sql = "select * from members";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String s1 = rs.getString(1);
                String s2 = rs.getString(2);
                String s3 = rs.getString(3);

                System.out.println(s1 + " " + s2 + " " + s3);
            }

            stmt.close();
            con.close();
        }
        catch(SQLException e){
            System.out.println(e.toString());
        }
    }    
}
