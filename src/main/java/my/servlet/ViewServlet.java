/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ADMIN
 */
public class ViewServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            String data="";
            try {
                //1. nạp driver
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                //System.out.println("Nap Driver ok");
                //2. thiết lập kết nối
                conn = DriverManager.getConnection("jdbc:sqlserver://MSI;databaseName=dbo_user", "admin", "admin");
                //System.out.println("Ket noi ok");
                //3.
                ps = conn.prepareStatement("insert into users(name, password, email, country) values (?,?,?,?)");
               
                
                rs = ps.executeQuery();
                
                data += "<table>";
                data +="<tr><th>Id</th></th>Name</th><th>Password</th><th>Email</th><th>Country</th><th>Edit</th><th>Delete</th></tr>";
                while (rs.next()){
                    data +="<tr>";
                    data +="<td>" + rs.getInt(1) + "</td>";
                    data +="<td>" + rs.getString(2) + "</td>";
                    data +="<td>" + rs.getString(3) + "</td>";
                    data +="<td>" + rs.getString(4) + "</td>";
                    data +="<td>" + rs.getString(5) + "</td>";
                    data +="<td><a href = EditServlet?id=" + rs.getInt(1) + ">Edit</a></td>";
                    data +="<td><a href = DeleteServlet?id=" + rs.getInt(1) + ">Delete</a></td>";
                }
                data +="</table>";
                //4. thi han truy vấn
                int kq = ps.executeUpdate();
                //5. Xử lý kết quả trả về
                if (kq > 0) {
                    out.print("<h2>Thêm User thành công</h2>");
                } else {
                    out.print("<h2>Thêm thất bại</h2>");
                }
                //6. đóng kết nối
                conn.close();

            } catch (Exception e) {

                System.out.println("Loi:" + e.toString());
                out.print("<h2>Thêm thất bại</h2>");
            }
            //chèn nội dung của trang index.html vào phản hồi kết quả
            request.getRequestDispatcher("index.html").include(request, response);

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "ViewServlet to display users";
    }
    
    

}
