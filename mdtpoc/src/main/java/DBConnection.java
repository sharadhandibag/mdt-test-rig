import java.io.IOException;
import java.sql.*;

public class DBConnection {

    public static void main(String [] args) throws Exception{
        //Process proc = Runtime.getRuntime().exec("/home/mdt-worker/echo-hello.sh");
        //System.out.println(proc);

        //String[] cmd = { "bash", "echo-hello.sh", "/home/mdt-worker"};
        //Runtime.getRuntime().exec(cmd);

//        ProcessBuilder pb = new ProcessBuilder("/home/mdt-worker/echo-hello.sh");
//        try {
//            Process p;
//            p = pb.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//

      /*  String[] cmdScript = new String[]{"/bin/bash", "/home/mdt-worker/echo-hello.sh"};
        Process procScript = Runtime.getRuntime().exec(cmdScript);*/


        PreparedStatement ps = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mdt_poc","root","zim");
            ps = con.prepareStatement("insert into parameters values(?,?)");
            stmt = con.createStatement();
            int runid = 1;
            String primScript = "/home/mdt-worker/Downloads/scripts/uov_prim_";
            String testScript = "/home/mdt-worker/Downloads/scripts/uov_test_";

            rs = stmt.executeQuery("select * from tr_details where runid = "+runid);
            while(rs.next()){
                String[] uovList = rs.getString(3).split(",");
                for(String s:uovList){
                    int returnCode = ps.executeUpdate("truncate table parameters");
                    if(returnCode == 0){
                        ps.setInt(1,runid);
                        ps.setInt(2,Integer.parseInt(s));
                        int i = ps.executeUpdate();
                        primScript = primScript + runid + "_" + s + ".sh";
                        triggerScript(primScript);
                        //System.out.print(primScript);
                        testScript = testScript + runid + "_" + s + ".sh";
                        triggerScript(testScript);
                       // System.out.print(i+"  ");
                    }
                }
            }
            con.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void triggerScript(String scriptName){
        String[] cmdScript = new String[]{"/bin/bash", scriptName};
        try {
            Process procScript = Runtime.getRuntime().exec(cmdScript);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
