

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

import com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter;

/**
 * Servlet implementation class ServletLogin
 */
@WebServlet("/produtos")
public class ServletProduto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletProduto() {
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
	
	ArrayList<String> produtos = new ArrayList<String>();
	
	
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
		String sql= "select * from produto";
		
		// Preparar o SQL para envio ao BD
		PreparedStatement ps= conexao.prepareStatement(sql);
		
		
		
		// Executando o SQL
		ResultSet rs= ps.executeQuery();
		
		//verificar se usuario e senha estao corretos
		
		while(rs.next()) {
			String nome = rs.getString("nomeproduto");
			//String descricao = rs.getString("descricaoproduto");
			//String fabricante = rs.getString("fabricanteproduto");
			//String preço = rs.getString("preçoproduto");
			int idProduto = rs.getInt("idproduto");
			
			
			out.print(idProduto + ". " + "<a href=\"detalheProduto?id="+ idProduto + "\">" + nome + "</a> " + " " + "<br/>");
			//produtos.add(nome + "#" + descricao + "#" + fabricante + "#" + preço);
		}
		
		for (String produto : produtos) {
			String parameters[] = produto.split("#");
			
			out.println("Nome:  " + parameters[0] + "  Descricao: " + parameters[1] + "  Fabricante: " + parameters[2]+ "  Preços: " + parameters[3]+ "<br>");
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