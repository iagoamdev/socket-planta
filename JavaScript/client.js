const net = require('net'); // Importação do módulo de rede do Node.js
const readline = require('readline'); // Importação do módulo readline para entrada de usuário

const rl = readline.createInterface({ // Criação de interface readline para entrada de usuário
    input: process.stdin, // Entrada padrão (teclado)
    output: process.stdout // Saída padrão (console)
});

const HOST = '127.0.0.1'; // Endereço do servidor
const PORT = 64532; // Porta do servidor

const client = new net.Socket(); // Criação de um novo socket cliente

client.connect(PORT, HOST, () => { // Conexão ao servidor
    console.log('CONNECTED TO: ' + HOST + ':' + PORT); // Log: Conexão estabelecida com sucesso

    rl.addListener('line', line => { // Evento para ler a entrada do usuário
        if (line.toLowerCase() === 'sair') { // Se o usuário digitar 'sair', encerra a conexão
            client.end();
        } else {
            client.write(line); // Envia a linha digitada pelo usuário para o servidor
        }
    });
});

client.on('data', (data) => { // Evento para receber dados do servidor
    console.log('DATA: ' + data); // Log: Dados recebidos do servidor
});

client.on('close', () => { // Evento para lidar com o fechamento da conexão
    console.log('Connection closed'); // Log: Conexão fechada
    rl.close(); // Fecha a interface readline
});
