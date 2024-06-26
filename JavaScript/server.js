const net = require('net'); // Importação do módulo de rede do Node.js

const HOST = '127.0.0.1'; // Endereço onde o servidor irá escutar
const PORT = 64532; // Porta onde o servidor irá escutar

const server = net.createServer((sock) => { // Criação do servidor
    console.log(`CONNECTED: ${sock.remoteAddress}:${sock.remotePort}`); // Log: Nova conexão estabelecida

    sock.on('data', (data) => { // Evento para receber dados do cliente
        console.log(`DATA ${sock.remoteAddress}: ${data}`); // Log: Dados recebidos do cliente
        sock.write(data); // Envia os dados de volta para o cliente
    });

    sock.on('close', () => { // Evento para lidar com o fechamento da conexão com o cliente
        console.log(`CLOSED: ${sock.remoteAddress} ${sock.remotePort}`); // Log: Conexão fechada com o cliente
    });
});

server.listen(PORT, HOST, () => { // Inicia o servidor e começa a escutar por conexões
    console.log(`Server listening on ${HOST}:${PORT}`); // Log: Servidor escutando no endereço e porta especificados
});
