import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int opcao = 0;
        
        File arquivo = new File("dados.txt");
        
        try {
            arquivo.createNewFile();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro");
            e.printStackTrace();
        }
        
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
                boolean jaCadastrado = false;
                
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
                
                try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
                    String linha;
                    while ((linha = leitor.readLine()) != null) {
                        String[] campos = linha.split(", ");
                        if (campos.length == 4 && campos[2].equals(cpf)) {
                            jaCadastrado = true;
                            break;
                        }
                    }
                }
                
                if (!jaCadastrado) {
                
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
                            writer.write(nome + ", " + idade + ", " + cpf + ", " + cep);
                            writer.newLine(); // Adiciona uma nova linha
                            System.out.println("Dados cadastrados!");
                        } catch (IOException e) {
                            e.getMessage();
                        }
                    } else {
                        System.out.println("CPF já está cadastrado.");
                    }
                
                break;
            case 2:
                System.out.print("Digite o CPF do usuário a ser consultado: ");
                String cpfConsulta = sc.nextLine();
                boolean encontrado = false;
                
                try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
                    String linha;
                    while ((linha = leitor.readLine()) != null) {
                        String[] campos = linha.split(", ");
                        if (campos.length == 4 && campos[2].equals(cpfConsulta)) {
                            System.out.println("Usuário encontrado:");
                            System.out.println("Nome: " + campos[0]);
                            System.out.println("Idade: " + campos[1]);
                            System.out.println("CPF: " + campos[2]);
                            System.out.println("CEP: " + campos[3]);
                            encontrado = true;
                            break;
                        }
                    }
                    
                    if (!encontrado) {
                        System.out.println("Usuário com CPF " + cpfConsulta + " não encontrado.");
                    }
                } catch (IOException e) {
                    e.getMessage();
                }
            
                break;
            case 3:
                System.out.print("Digite o CPF do usuário a ser deletado: ");
                String cpfDeletado = sc.nextLine();
                boolean cpfEncontrado = false;

                File arquivoTemp = new File("temp.txt");

                try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo));
                BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTemp))) {

                String linha;
                while ((linha = leitor.readLine()) != null) {
                    String[] campos = linha.split(", ");
                    if (campos.length == 4 && campos[2].equals(cpfDeletado)) {
                        System.out.println("Usuário com CPF " + cpfDeletado + " foi deletado.");
                        cpfEncontrado = true; // Marca que o CPF foi encontrado
                    } else {
                        writer.write(linha);
                        writer.newLine();
                    }
                }
                } catch (IOException e) {
                    System.err.println("Ocorreu um erro ao deletar o usuário por CPF: " + e.getMessage());
                }

            if (!cpfEncontrado) {
                System.out.println("Usuário com CPF " + cpfDeletado + " não encontrado.");
            } else {
                try {
                Files.delete(arquivo.toPath());
                Files.move(arquivoTemp.toPath(), arquivo.toPath());
                } catch (IOException e) {
                    e.getMessage();
                }
            }
                    
                break;
            case 4:
                String[] cadastro = new String[4];
                boolean cpfValido = false;
                String consultCpf = "0";

                while (!cpfValido) {
                    System.out.print("Digite o CPF do usuário a ser atualizado: ");
                    consultCpf = sc.nextLine();

                    try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
                        String linha;
                        while ((linha = reader.readLine()) != null) {
                            String[] dados = linha.split(", ");
                            if (dados.length == 4 && dados[2].equals(consultCpf)) {
                                cadastro[0] = dados[0];
                                cadastro[1] = dados[1];
                                cadastro[2] = dados[2];
                                cadastro[3] = dados[3];
                                cpfValido = true;
                            }
                        }
                        
                        if (!cpfValido) {
                            System.out.println("CPF inválido!");
                        }
                    }
                }
                
                System.out.println("Nome: " + cadastro[0]);
                System.out.println("Idade: " + cadastro[1]);
                System.out.println("CPF: " + cadastro[2]);
                System.out.println("CEP: " + cadastro[3]);

                int opcaoDesejada = 0;

                while (opcaoDesejada <= 0 || opcaoDesejada >= 5) {
                    System.out.println("Escolha o dado a ser alterado: ");
                    System.out.println("1 - Nome");
                    System.out.println("2 - Idade");
                    System.out.println("3 - CPF");
                    System.out.println("4 - CEP");
                    System.out.print("Digite o número da opção desejada: ");
                    opcaoDesejada = sc.nextInt();

                    if (opcaoDesejada <= 0 || opcaoDesejada >= 5){
                        System.out.println("Opção inválida!");
                    }
                }
                
                sc.nextLine();

                switch (opcaoDesejada) {
                    case 1:
                        System.out.print("Digite o novo nome: ");
                        String novoNome = sc.nextLine();

                        cadastro[0] = novoNome;
                        break;

                    case 2:
                        System.out.print("Digite a nova idade: ");
                        String novaIdade = sc.nextLine();

                        cadastro[1] = novaIdade;
                        break;

                    case 3:
                        System.out.print("Digite seu CPF: ");
                        String novoCPF = sc.nextLine();
                
                        if (novoCPF.length() != 11) {
                            do {
                                System.out.print("CPF inválido, digite novamente: ");
                                novoCPF = sc.nextLine();
                            } while (novoCPF.length() != 11);
                        }

                        cadastro[2] = novoCPF;
                        break;

                    case 4:
                    System.out.print("Digite seu CEP: ");
                    String novoCEP = sc.nextLine();
            
                    if (novoCEP.length() != 8) {
                        do {
                            System.out.print("CPF inválido, digite novamente: ");
                            novoCEP = sc.nextLine();
                        } while (novoCEP.length() != 8);
                    }

                        cadastro[3] = novoCEP;
                        break;
                }
                
                boolean validCpf = false;
                File tempArquivo = new File("temp.txt");

                try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempArquivo))) {

                String linha;
                while ((linha = leitor.readLine()) != null) {
                    String[] campos = linha.split(", ");
                    if (campos.length == 4 && campos[2].equals(consultCpf)) {
                        validCpf = true; // Marca que o CPF foi encontrado
                    } else {
                        writer.write(linha);
                        writer.newLine();
                    }
                }
                } catch (IOException e) {
                    System.err.println("Ocorreu um erro ao deletar o usuário por CPF: " + e.getMessage());
                }

                if (!validCpf) {
                    System.out.println("Usuário com CPF " + consultCpf + " não encontrado.");
                } else {
                    try {
                    Files.delete(arquivo.toPath());
                    Files.move(tempArquivo.toPath(), arquivo.toPath());
                    } catch (IOException e) {
                        e.getMessage();
                    }
                }

                String dadosNovos = Arrays.toString(cadastro);
                dadosNovos = dadosNovos.replace("[", "").replace("]", "");

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
                    writer.write(dadosNovos);
                    writer.newLine(); // Adiciona uma nova linha
                    System.out.println("Dados cadastrados!");
                } catch (IOException e) {
                    e.getMessage();
                }

            break;
        }
        sc.close();
        
    }
}