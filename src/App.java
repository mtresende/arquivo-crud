import java.io.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        
        boolean running = true;        
        File arquivo = new File("dados.txt");
        
        try {
            arquivo.createNewFile();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro");
            e.printStackTrace();
        }
        
        while (running){
            int opcao = 0;
            while (opcao <= 0 || opcao >= 5) {
                System.out.println("ARQUIVO CRUD");
                System.out.println("1 - Cadastrar usuário");
                System.out.println("2 - Consultar usuário");
                System.err.println("3 - Deletar um usuário");
                System.out.println("4 - Atualizar um cadastro");
                System.out.print("Digite o número da opção desejada: ");
                opcao = sc.nextInt();
                sc.nextLine();
                
                if (opcao <= 0 || opcao >= 5){
                    System.out.println("Opção inválida!");
                }
            }
            
            switch (opcao) {
                case 1:
                    String nome, idade, cpf, cep;
                    
                    System.out.print("Digite o nome a ser cadastrado: ");
                    nome = sc.nextLine();
                    System.out.print("Digite a idade: ");
                    idade = sc.nextLine();
                    System.out.print("Digite o CPF: ");
                    cpf = sc.nextLine();
                    
                    if (cpf.length() != 11) {
                        do {
                            System.out.print("CPF inválido, digite novamente: ");
                            cpf = sc.nextLine();
                        } while (cpf.length() != 11);
                    }
                    
                    System.out.print("Digite o CEP: ");
                    cep = sc.nextLine();
                    
                    if (cep.length() != 8) {
                        do {
                            System.out.print("CEP inválido, digite novamente: ");
                            cep = sc.nextLine();
                        } while (cep.length() != 8);
                    }
                    
                    Create.cadastrar(nome, idade, cpf, cep);
                    
                    break;
                case 2:
                    System.out.print("Digite o CPF do usuário a ser consultado: ");
                    cpf = sc.nextLine();
                    
                    Read.consultar(cpf);
                    break;
                case 3:
                    System.out.print("Digite o CPF do usuário a ser deletado: ");
                    cpf = sc.nextLine();
                    
                    Delete.deletar(cpf);
                    break;
                case 4:
                    Update.atualizar(arquivo, sc);
                break;
            }
            
            opcao = 0;
            int retornar = 0;
            while (retornar <= 0 || retornar >= 3) {
                System.out.println("Deseja retornar ao menu?");
                System.out.println("1 - Sim");
                System.out.println("2 - Não");
                System.out.print("Digite o número da opção desejada: ");
                retornar = sc.nextInt();

                if (retornar <= 0 || retornar >= 3) {
                    System.out.println("Opção inválida");
                } else if (retornar == 1) {
                    running = true;
                } else if (retornar == 2) {
                    running = false;
                }
            }
        }
        sc.close();
    }
}