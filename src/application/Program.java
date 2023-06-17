package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import entities.Aluno;
import jdbc.AlunoJDBC;

public class Program {

    public static void main(String[] args) {

        try {

            int opcao = 0;
            Scanner console = new Scanner(System.in);
            AlunoJDBC alunoJDBC = new AlunoJDBC();

            do {
                System.out.println("####### Menu #######"
                        + "\n1 - Cadastrar"
                        + "\n2 - Listar"
                        + "\n3 - Alterar"
                        + "\n4 - Excluir"
                        + "\n5 - Sair");
                System.out.println("\n\tOpção:");
                opcao = Integer.parseInt(console.nextLine());

                switch (opcao) {
                    case 1:
                        System.out.println("\n ### Cadastrar Aluno ### \n\r");

                        Aluno aluno = new Aluno();
                        System.out.print("Nome: ");
                        aluno.setNome(console.nextLine());

                        System.out.print("Sexo: ");
                        aluno.setSexo(console.nextLine());

                        System.out.print("Data de Nascimento (dd-mm-aaaa): ");
                        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        aluno.setDt_nasc(LocalDate.parse(console.nextLine(), formato));

                        alunoJDBC.salvar(aluno);
                        System.out.println("\nCadastro do aluno realizado com sucesso!\n");
                        break;

                    case 2:
                        System.out.println("\n ### Listar Alunos ### \n\r");
                        List<Aluno> alunos = alunoJDBC.listar();

                        if (alunos.isEmpty()) {
                            System.out.println("Não há alunos cadastrados.\n");
                        } else {
                            for (Aluno a : alunos) {
                                System.out.println("Nome: " + a.getNome());
                                System.out.println("Sexo: " + a.getSexo());
                                System.out.println("Data de Nascimento: " + a.getDt_nasc());
                                System.out.println();
                            }
                        }
                        break;

                    case 3:
                    	System.out.println("\n ### Alterar Aluno ### \n\r");

                    	System.out.print("Informe o ID do aluno que deseja alterar: ");
                    	int idAlterar = Integer.parseInt(console.nextLine());

                    	Aluno alunoAlterar = new Aluno();
                    	alunoAlterar.setId(idAlterar);

                    	System.out.print("Novo nome: ");
                    	alunoAlterar.setNome(console.nextLine());

                    	System.out.print("Novo sexo: ");
                    	alunoAlterar.setSexo(console.nextLine());

                    	System.out.print("Nova data de Nascimento (dd-mm-aaaa): ");
                    	DateTimeFormatter formatoAlterar = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    	alunoAlterar.setDt_nasc(LocalDate.parse(console.nextLine(), formatoAlterar));

                    	alunoJDBC.alterar(alunoAlterar);
                    	
                    	break;
                    	

                    case 4:
                        System.out.println("\n ### Excluir Aluno ### \n\r");

                        System.out.print("Informe o ID do aluno que deseja excluir: ");
                        int idExcluir = Integer.parseInt(console.nextLine());

                        Aluno alunoExcluir = new Aluno();

                        if (alunoExcluir == null) {
                            System.out.println("Aluno não encontrado.\n");
                        } else {
                            alunoJDBC.apagar(idExcluir);
                            System.out.println("Aluno excluído com sucesso!\n");
                        }
                        break;

                    case 5:
                        System.out.println("Encerrando o programa...");
                        break;

                    default:
                        System.out.println("Opção inválida. Digite novamente.\n");
                        break;
                }

            } while (opcao != 5);

        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
    }
}
