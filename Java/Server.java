import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        final int PORT = 64532;
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            // Log: Servidor está escutando na porta especificada
            System.out.println("Servidor na escuta na porta " + PORT);

            // Loop principal para aceitar conexões
            while (true) {
                System.out.println("Aguardando conexões...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                // Criação de um novo thread para lidar com a conexão do cliente
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            // Log: Tratamento de erros de I/O
            System.err.println("Erro de I/O: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine;
            // Loop para ler mensagens do cliente e enviar respostas
            while ((inputLine = in.readLine()) != null) {
                // Log: Recebimento da mensagem do cliente
                System.out.println("Recebido: " + inputLine);

                // Log: Envio da resposta ao cliente
                String response = "Servidor recebeu: " + inputLine;
                out.println(response);
                System.out.println("Resposta enviada: " + response);
            }
        } catch (IOException e) {
            // Log: Tratamento de erros de I/O
            System.err.println("Erro de I/O: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Fechamento da conexão com o cliente
                clientSocket.close();
                System.out.println("Cliente desconectado: " + clientSocket.getInetAddress());
            } catch (IOException e) {
                // Log: Erro ao fechar a conexão com o cliente
                System.err.println("Erro ao fechar a conexão com o cliente: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
