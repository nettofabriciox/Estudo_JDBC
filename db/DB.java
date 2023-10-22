package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {

	
	private static Connection conexao = null;
	
	public static Connection pegarConexao() {
		if(conexao == null) {
			try {
			Properties props = lerDados();
			String url = props.getProperty("dburl");
			conexao = DriverManager.getConnection(url, props);
			}
			catch(SQLException e) {
				throw new DbExcecao(e.getMessage());
			}
		}
		return conexao;
	}
	
	public static void closeConexao() {
		if(conexao != null) {
			try {
				conexao.close();
			}
			catch (SQLException e) {
				throw new DbExcecao(e.getMessage());
			}
		}
	}
	private static Properties lerDados() {
		try (FileInputStream fs = new FileInputStream("db.propriedades")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new DbExcecao(e.getMessage());
		}
	}
}
