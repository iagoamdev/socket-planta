import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        // Definição do endereço e porta do servidor
        final String HOST = "127.0.0.1";
        final int PORT = 64532;

        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            // Log: Conexão ao servidor estabelecida
            System.out.println("Conectado ao servidor");

            // Loop para enviar mensagens ao servidor e receber respostas
            while (true) {
                System.out.print("Digite a mensagem que deseja enviar: ");
                String message = scanner.nextLine();
                if ("sair".equalsIgnoreCase(message)) {
                    System.out.println("Encerrando conexão com o servidor.");
                    break;
                }
                // Log: Envio da mensagem ao servidor
                out.println(message);
                System.out.println("Mensagem enviada: " + message);

                // Leitura da resposta do servidor
                String response = in.readLine();
                if (response != null) {
                    System.out.println("Resposta do servidor: " + response);
                } else {
                    System.out.println("Conexão com o servidor foi perdida.");
                    break;
                }
            }
        } catch (IOException e) {
            // Log: Tratamento de erros de I/O
            System.err.println("Erro de I/O: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
