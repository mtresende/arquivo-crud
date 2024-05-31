import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Create {
    public static void cadastrar(String nome, String idade, String cpf, String cep) {
        boolean jaCadastrado = false;
        String arquivo = "dados.txt";

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] campos = linha.split(", ");
                if (campos.length == 4 && campos[2].equals(cpf)) {
                    jaCadastrado = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!jaCadastrado) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
                writer.write(nome + ", " + idade + ", " + cpf + ", " + cep);
                writer.newLine();
                System.out.println("Dados cadastrados!");
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        } else {
            System.out.println("CPF já está cadastrado.");
        }
    }
}
