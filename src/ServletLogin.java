

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletLogin
 */
@WebServlet("/login")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//recuperar parametros
	
	String usuario = request.getParameter("usuario");
	String senha = request.getParameter("senha");
	
	// obter objeto de resposta 
	PrintWriter out = response.getWriter();
	
	// começar objeto HTML
	out.println("<html><head><title>Resultado do Login</title></head>");
	out.println("<body>");
	
	
	//conectar no banco de dados
	
	try {
		//referenciar o driver JDBC
		Class.forName("com.mysql.jdbc.Driver");
		
		//Criar strings de conexão
		String url= "jdbc:mysql://localhost/superMercado";
		String username ="root";
		String password = "root";
		
		// Realizar conexao com o BD
		Connection conexao = DriverManager.getConnection(url, username , password);
		// Criando sql
		//String sql= "select nomeusuario, senhausuario from usuario" +
		//		"where nomeusuario = '" + usuario + "' and senhausuario = '" + senha + "'";
		
		//Criando o SQL - jeito melhor 
		String sql= "select nomeusuario, senhausuario from usuario where nomeusuario = ? and senhausuario = ? ";
		
		// Preparar o SQL para envio ao BD
		PreparedStatement ps= conexao.prepareStatement(sql);
		// Passar o valor de usuario
		ps.setString(1, usuario);
		// Passar o valor de senha 
		ps.setString(2, senha);
		
		// Executando o SQL
		ResultSet rs= ps.executeQuery();
		
		//verificar se usuario e senha estao corretos
		
		if (rs.first()) {
			out.println("<h1>Login com sucesso!</h1>");
		}else {
			out.println("<h1>Login sem sucesso! </h1>");
		}
		
		// fechar o ResultSet
		rs.close();
		//fechar o PrepareStatemnt
		ps.close();
		//fehcar o connection
		conexao.close();
		
		
		
		
	} catch (ClassNotFoundException | SQLException e) {
		// Mostra o erro
		e.printStackTrace();
	}
	

	
	//fechar o hmtl
	out.println("</body></html>");
	}

}
