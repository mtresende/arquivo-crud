import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Read {
    public static void consultar(String cpf) {
        boolean jaCadastrado = false;
        String arquivo = "dados.txt";

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
                    String linha;
                    while ((linha = leitor.readLine()) != null) {
                        String[] campos = linha.split(", ");
                        if (campos.length == 4 && campos[2].equals(cpf)) {
                            System.out.println("Usuário encontrado:");
                            System.out.println("Nome: " + campos[0]);
                            System.out.println("Idade: " + campos[1]);
                            System.out.println("CPF: " + campos[2]);
                            System.out.println("CEP: " + campos[3]);
                            jaCadastrado = true;
                            break;
                        }
                    }
                    
                    if (!jaCadastrado) {
                        System.out.println("Usuário com CPF " + cpf + " não encontrado.");
                    }
                } catch (IOException e) {
                    e.getMessage();
                }
    }
}
