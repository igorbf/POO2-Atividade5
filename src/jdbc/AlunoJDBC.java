package jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import entities.Aluno;

public class AlunoJDBC {

	Connection con;
	String sql;
	PreparedStatement pst;
	
	public void salvar(Aluno a) throws SQLException {
		
		try {
			con = db.getConexao();
			sql = "INSERT INTO aluno (nome, sexo, dt_nasc) VALUES (?,?, ?)";
			pst = con.prepareStatement(sql);
			pst.setString(1, a.getNome());
			pst.setString(2, a.getSexo());
			Date dataSql = Date.valueOf( a.getDt_nasc() );
			pst.setDate(3, dataSql);
			pst.executeUpdate();
			System.out.println("\nCadastro do aluno realizado com sucesso!");
			
		} catch (Exception e) {
			System.out.println(e);
		}
		finally {
			pst.close();
			db.closeConexao();
		}
	}
	
	public List<Aluno> listar() throws SQLException {
	    List<Aluno> alunos = new ArrayList<>();

	    try {
	        con = db.getConexao();
	        sql = "SELECT * FROM aluno";
	        pst = con.prepareStatement(sql);
	        ResultSet rs = pst.executeQuery();

	        while (rs.next()) {
	            Aluno aluno = new Aluno();
	            aluno.setNome(rs.getString("nome"));
	            aluno.setSexo(rs.getString("sexo"));
	            aluno.setDt_nasc(rs.getDate("dt_nasc").toLocalDate());
	            alunos.add(aluno);
	        }
	    } catch (Exception e) {
	        System.out.println(e);
	    } finally {
	        pst.close();
	        db.closeConexao();
	    }

	    return alunos;
	}

	
	public void apagar(int id) throws SQLException {
	    try {
	        con = db.getConexao();
	        sql = "DELETE FROM aluno WHERE id = ?";
	        pst = con.prepareStatement(sql);
	        pst.setInt(1, id);
	        pst.executeUpdate();
	        System.out.println("\nAluno excluído com sucesso!");
	    } catch (Exception e) {
	        System.out.println(e);
	    } finally {
	        pst.close();
	        db.closeConexao();
	    }
	}

	
	public void alterar(Aluno a) throws SQLException {
	    try {
	        con = db.getConexao();
	        sql = "UPDATE aluno SET nome = ?, sexo = ?, dt_nasc = ? WHERE id = ?";
	        pst = con.prepareStatement(sql);
	        pst.setString(1, a.getNome());
	        pst.setString(2, a.getSexo());
	        Date dataSql = Date.valueOf(a.getDt_nasc());
	        pst.setDate(3, dataSql);
	        pst.setInt(4, a.getId());

	        // Executa a consulta para buscar o aluno pelo ID
	        String sqlBusca = "SELECT * FROM aluno WHERE id = ?";
	        PreparedStatement pstBusca = con.prepareStatement(sqlBusca);
	        pstBusca.setInt(1, a.getId());
	        ResultSet rs = pstBusca.executeQuery();

	        // Verifica se o aluno existe no banco
	        if (rs.next()) {
	            pst.executeUpdate();
	            System.out.println("\nDados do aluno atualizados com sucesso!");
	        } else {
	            System.out.println("Aluno não encontrado.\n");
	        }

	        rs.close();
	        pstBusca.close();
	    } catch (Exception e) {
	        System.out.println(e);
	    } finally {
	        pst.close();
	        db.closeConexao();
	    }
	}
	}

