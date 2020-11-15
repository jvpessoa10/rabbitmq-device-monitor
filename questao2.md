# Atividade 2 - João Victor Pessoa  

### Introdução
Esta aplicação ilustra o monitoramento dos dados de um dispositivo, sendo composto por tres módulos
 principais:

* **Equipment**: Representa o equipamento que está continuamente emitindo novos valores
, utilizando o proprio Scheduler do spring para isso.
* **Dashboard**: Consome os valores emitidos pelo equipamento
* **History**: Consome e "Registra" os valores emitidos pelo equipamento

### Arquitetura

O sistema utiliza o padrão publisher-subscriber (de forma assíncrona), onde um
dos módulos representa o dispositivo emitindo novos valores, e os outros, que estão observando,
recebem as atualizações.

O RabbitMQ foi escolhido como o broker de mensagens pela sua boa integração com o framework
Spring; O facil gerenciamento utilizando o CloudAMQP; E o sistema de exchange, que facilita
o  direcionamento das mensagens nas filas.

Para esta solução, é necessário que tanto o histórico quanto o dashboard recebam as
atualizaçõs emitidas pelo equipamento. E para isso, o sistema utiliza-se de exchanges do tipo fanout, onde
as novas mensagens serão adicionadas em todas as filas que estiverem vinculadas à exchange
responsável. 

O módulo do equipamento é responsavel por declarar as filas para a qual ele quer enviar as
 atualizações (de historico e do dashboard), e realizar os bindinds dessas filas com uma
 exchange definida. Assim, sempre que ouver uma atualização nos valores, tanto o componente de
  historico, quanto o dashboard receberão as mensagens em suas proprias filas , e forma assíncrona.
  
Outro ponto a destacar é a serialização/deserialização das mensagens, que é feita de forma
interna pelo proprio Spring AMQP, bastando apenas declarar um Bean do tipo MessageConverter
 especifico
para json (ex: Jackson2JsonMessageConverter). E então, criar um POJO que represente a estrutura
 da mensagem a ser enviada (Ver classe DeviceUpdateMessage). Assim, a conversão será feita
 automaticamente pelo Spring AMQP.

### Considerações
Essa abordagem é eficiente para evitar pontos unicos de falha, tipicos de sistemas monol
íticos. E alem disso, diminui o acoplamento entre os componentes do sistema. 

Como melhorias, podemos considerar a centralização de algumas propriedades de configurações, que
 se repetiram entre os módulos (Nome de filas, de exchange, hostname do RabbitMQ, etc ).
