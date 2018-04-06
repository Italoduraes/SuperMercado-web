

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

/**
 * Servlet implementation class ServletDetalheProduto
 */
@WebServlet("/detalheProduto")
public class ServletDetalheProduto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletDetalheProduto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//recuperar parametros
		String id = request.getParameter("id");
		
		//obter objeto de resposta
		PrintWriter out = response.getWriter();
		
		//conectar ao banco de dados
		
		//começar a montar HTML
				out.println("<html><head><title>Listar Produtos</title></head>");
				out.println("<body>");
		
				//ArrayList<String> produtos = new ArrayList<String>();
				
		try {
			//referenciar o driver JDBC
			Class.forName("com.mysql.jdbc.Driver");
			
			//Criar String de conexao
			String url = "jdbc:mysql://localhost/supermercado";
			String username = "root";
			String password = "root";
			
			//Realizar conexao com o BD
			Connection conexao = DriverManager.getConnection(url, username, password);
			
			//criando o SQL - Jeito HORIIVEL
			//String sql = "Select nomeusuario, senhausuario from usuario " +
			//"where nomeusuario = '" + usuario + "' and senhausuario = '" + senha + "'";
			
			//Criando SQL - Jeito MELHOR
			//String sql = "select * from produto";
			String sql = "select * from produto where idproduto=" + id;
			
			//preparar o SQL para envio ao DB
			PreparedStatement ps = conexao.prepareStatement(sql);
			//passar o valor de usuario
			//ps.setString(1, id);
			//passar o valor de senha
			
			
			//Executando SQL
			ResultSet rs = ps.executeQuery();
			
			//verificar se usuario e senha estao corretos
			while (rs.next()) {
				
				int idProduto = rs.getInt("idproduto");
				String nomeProduto = rs.getString("nomeproduto");
				String descProduto = rs.getString("descricaoproduto");
				String fabriProduto = rs.getString("fabricanteproduto");
				String precoProduto = rs.getString("precoproduto");
				
				
				out.print("id: " + idProduto + "<br/>" + "nome: " + nomeProduto + "<br/>" + "descrição: " + descProduto + "<br/>" + "fabricante: " + fabriProduto + "<br/>" + "preço: " + precoProduto + "<br/>" + "<br/>");
				
				//produtos.add(nomeProduto + "#" + descProduto + "#" + fabriProduto + "#" + precoProduto);
				}
			
			//for (String produto : produtos) {
				//String parameters[] = produto.split("#");
				
				//out.println("Nome: " + parameters[0] + "Descrição: " + parameters[1] + "Fabricante: " + parameters[2] + "Preço: " + parameters[3] + "<br>");
			//}
			
			//fechar ResultSet
			rs.close();
			//fechar o PrepareStatment
			ps.close();
			//fechar o Connection
			conexao.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			//mostrar o erro
			e.printStackTrace();
		}
		
		//fechar o HTML
		out.println("</body></html>");
	}

}