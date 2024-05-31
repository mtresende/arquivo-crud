import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Scanner;

public class Update {
    public static void atualizar(File arquivo, Scanner sc){
        String[] cadastro = new String[4];
        boolean cpfValido = false;
        String consultCpf = "0";

        // Armazena os dados no array
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Nome: " + cadastro[0]);
        System.out.println("Idade: " + cadastro[1]);
        System.out.println("CPF: " + cadastro[2]);
        System.out.println("CEP: " + cadastro[3]);

        int opcaoDesejada = 0;

        // Solicita qual informação será alterada
        while (opcaoDesejada <= 0 || opcaoDesejada >= 5) {
            System.out.println("Escolha o dado a ser alterado: ");
            System.out.println("1 - Nome");
            System.out.println("2 - Idade");
            System.out.println("3 - CPF");
            System.out.println("4 - CEP");
            System.out.print("Digite o número da opção desejada: ");
            opcaoDesejada = sc.nextInt();

            if (opcaoDesejada <= 0 || opcaoDesejada >= 5) {
                System.out.println("Opção inválida!");
            }
        }

        sc.nextLine();

        // Valida os dados e substitui o valor antigo pelo novo através do índice do array
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
                        System.out.print("CEP inválido, digite novamente: ");
                        novoCEP = sc.nextLine();
                    } while (novoCEP.length() != 8);
                }

                cadastro[3] = novoCEP;
                break;
        }

        File tempArquivo = new File("temp.txt");

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempArquivo))) {

            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] campos = linha.split(", ");
                if (campos.length == 4 && campos[2].equals(consultCpf)) {
                    // Não escreve a linha original
                } else {
                    writer.write(linha);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.delete(arquivo.toPath());
            Files.move(tempArquivo.toPath(), arquivo.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Transformando o array em String
        String dadosNovos = Arrays.toString(cadastro);
        dadosNovos = dadosNovos.replace("[", "").replace("]", "");

        // Escrevendo os dados novos
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.write(dadosNovos);
            writer.newLine(); // Adiciona uma nova linha
            System.out.println("Dados cadastrados!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
