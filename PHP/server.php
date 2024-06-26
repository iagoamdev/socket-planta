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

// Vinculação do socket ao endereço e porta
$result = socket_bind($socket, $host, $port);
if ($result === false) {
    die("socket_bind() failed: " . socket_strerror(socket_last_error($socket)) . "\n");
}
echo "Socket vinculado a $host:$port.\n";

// Configuração do socket para escutar conexões
$result = socket_listen($socket, 5);
if ($result === false) {
    die("socket_listen() failed: " . socket_strerror(socket_last_error($socket)) . "\n");
}
echo "Servidor na escuta em $host:$port.\n";

// Loop principal para aceitar e processar conexões
while (true) {
    echo "Aguardando conexões...\n";
    $client = socket_accept($socket);
    if ($client === false) {
        echo "socket_accept() failed: " . socket_strerror(socket_last_error($socket)) . "\n";
        break;
    }
    echo "Cliente conectado.\n";

    // Loop para ler e responder dados do cliente
    while (true) {
        $data = socket_read($client, 1024);
        if ($data === false) {
            echo "socket_read() failed: " . socket_strerror(socket_last_error($client)) . "\n";
            break;
        }
        $data = trim($data);
        if (!empty($data)) {
            echo "Recebido: $data\n";
            $response = "Servidor recebeu: " . $data;
            socket_write($client, $response, strlen($response));
            echo "Resposta enviada: $response\n";
        }
    }

    socket_close($client);
    echo "Cliente desconectado.\n";
}
socket_close($socket);
echo "Servidor encerrado.\n";
?>
