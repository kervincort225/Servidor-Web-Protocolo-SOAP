/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wskc.servicio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author kervi
 */
@WebService(serviceName = "Servicio")
public class Servicio {


    /**
     * Web service operation
     */
    @WebMethod(operationName = "insertTrx")
    public int insertTrx(@WebParam(name = "Data") String Data, @WebParam(name = "Tipo") int Tipo, @WebParam(name = "Empresa") int Empresa) {
        //TODO write your implementation code here:
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = null;
            Statement st;
            String Sql = "";
            try
            {
                //con = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-9C0ORO9O\\\\SQLEXPRESS:1433;databaseName=DB_GPS;","sa","Micrologica2014");
                        con = DriverManager.getConnection("jdbc:sqlserver://150.0.20.202;databaseName=DB_GPS","sa","Micrologica2014");
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                if (con!=null)
                    con.close();
                int c = 0;
                while (c==0)
                {
                    try
                    {
                        //con = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-9C0ORO9O\\\\SQLEXPRESS:1433;databaseName=DB_GPS;","sa","Micrologica2014");
                        con = DriverManager.getConnection("jdbc:sqlserver://150.0.20.202;databaseName=DB_GPS","sa","Micrologica2014");
                        c=1;
                    }
                    catch (SQLException s)
                    {
                        System.out.println("Err: "+s.toString());
                    }
                    Thread.sleep(15000);
                }
            }
            System.out.println("Llego hasta este punto 2");
            //Reestructuracion para enviar datos a base de datos dependiendo el tipo
             ArrayList<String> ListDatTransHec = new ArrayList<String>();
            ArrayList<String> ListDatStockHec = new ArrayList<String>();
            
            if(Tipo == 1) 
            {   
                //INSERTAR A BASE DE DATOS DE TABLA TRANSHECPOLL
               
                String[] ObjeDatos = Data.split("@");
                for (int i = 0; i < ObjeDatos.length; i++) {
                        ListDatTransHec.add(ObjeDatos[i]);
                }
                //Insertaremos a Tabla TRANSHECPOLL
                Sql = "insert into TransHec values ('"+ListDatTransHec.get(0)+"','"+ListDatTransHec.get(1)+"','"+ListDatTransHec.get(2)+"',"
                        + "'"+ListDatTransHec.get(3)+"','"+ListDatTransHec.get(4)+"','"+ListDatTransHec.get(5)+"','"+ListDatTransHec.get(6)+"','"+ListDatTransHec.get(7)+"',"
                        + "'"+ListDatTransHec.get(8)+"','"+ListDatTransHec.get(9)+"',"+Tipo+","+Empresa+",0)";   
                System.out.println("enviando datos a TRANSHECPOLL "+Sql);
            }else
            {
                 //INSERTAR A BASE DE DATOS DE TABLA STOCKHECPOLL
                
                String[] ObjeDatos2 = Data.split("@");
                for (int i = 0; i < ObjeDatos2.length; i++) {
                        ListDatStockHec.add(ObjeDatos2[i]);
                }
                //Insertaremos a Tabla STOCKHECPOLL
                Sql = "insert into StockHec values ('"+ListDatStockHec.get(0)+"','"+ListDatStockHec.get(1)+"','"+ListDatStockHec.get(2)+"',"
                        + "'"+ListDatStockHec.get(3)+"','"+ListDatStockHec.get(4)+"','"+ListDatStockHec.get(5)+"',"+Tipo+","+Empresa+",0)";
                
                System.out.println("enviando datos a STOCKHECPOLL "+Sql);
            }
            
            st = con.createStatement();
            st.executeUpdate(Sql);
            return 1;
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            return 0;
        }
    }
}
