<?php
// Definição do endereço e porta do servidor
$host = '127.0.0.1';
$port = 64532;

// Criação do socket
$socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
if ($socket === false) {
    die("socket_create() failed: " . socket_strerror(socket_last_error()) . "\n");
}
echo "Socket criado com sucesso.\n";

// Conexão ao servidor
$result = socket_connect($socket, $host, $port);
if ($result === false) {
    die("socket_connect() failed: " . socket_strerror(socket_last_error($socket)) . "\n");
}
echo "Conectado ao servidor em $host:$port.\n";

// Loop para enviar mensagens ao servidor e receber respostas
while (true) {
    $message = readline("Digite a mensagem que deseja enviar: ");
    if (strtolower($message) == 'sair') {
        break;
    }

    // Envio da mensagem ao servidor
    socket_write($socket, $message, strlen($message));
    echo "Mensagem enviada: $message\n";

    // Leitura da resposta do servidor
    $response = socket_read($socket, 1024);
    if ($response === false) {
        echo "socket_read() failed: " . socket_strerror(socket_last_error($socket)) . "\n";
    } else {
        echo "Resposta do servidor: $response\n";
    }
}

socket_close($socket);
echo "Cliente encerrado.\n";
?>
