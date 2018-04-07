

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javafx.util.converter.FloatStringConverter;

/**
 * Servlet implementation class ServletIncluirProduto
 */
@WebServlet("/IncluirProduto")
public class ServletIncluirProduto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletIncluirProduto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>Listar Produtos</title></head><body>");

		String nome = request.getParameter("nome");
		String valor = request.getParameter("valor");
		String fabricante= request.getParameter("fabricante");
		String descricao= request.getParameter("descricao");
		
		String query= "INSERT INTO produto ( nomeproduto, precoproduto, fabricanteproduto, descricaoproduto) VALUES (' " + nome + "','" + valor + "','" + fabricante + "','" + descricao +"')"; 
		
		
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
			
			
			// Preparar o SQL para envio ao BD
			PreparedStatement st= conexao.prepareStatement(query);
			
			st.executeUpdate(query);
			
			out.print("O produto foi inserido com sucesso<br/><a href=\"produtos\" >voltar</a>");
			
		} catch (Exception e) {
			// Mostra o erro
			e.printStackTrace();
		}
		

		
		//fechar o hmtl
		out.println("</body></html>");
		}

		
	}


