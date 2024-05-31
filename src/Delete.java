import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class Delete {
    public static void deletar(String cpf) {

        boolean cpfEncontrado = false;
        File arquivoTemp = new File("temp.txt");
        File arquivo = new File("dados.txt");

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo));
        BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTemp))) {

        String linha;
        while ((linha = leitor.readLine()) != null) {
            String[] campos = linha.split(", ");
            if (campos.length == 4 && campos[2].equals(cpf)) {
                System.out.println("Usuário com CPF " + cpf + " foi deletado.");
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
            System.out.println("Usuário com CPF " + cpf + " não encontrado.");
            try {
                Files.delete(arquivoTemp.toPath());
            } catch (IOException e) {
                e.getMessage();
            }
        } else {
            try {
            Files.delete(arquivo.toPath());
            Files.move(arquivoTemp.toPath(), arquivo.toPath());
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }
}
