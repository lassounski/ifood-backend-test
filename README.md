## Start
`$mvn clean install spring-boot:run`
aplicação na porta 8080
`$curl localhost:8080/weather?city=campinas`
JSON com informações do tempo para campinas
- Cache Caffeine segura por 10 segundos todas requisições para uma mesma cidade (cache de vida curta)
- Cache de Fallback (vida longa) guarda as cidades ja pesquisadas por um tempo maior para fallback


Acompanhe o log do servidor executando estes:

    1 - curl localhost:8080/weather?city=campinas -> feita requisicao para serviço terceiro
    2 - curl localhost:8080/weather?city=campinas -> retorna a cidade (cache curta)
    3 - 10 segundos após
    4 - curl localhost:8080/weather?city=campinas -> nova requisicao para serviço terceiro atualizando cache curta para mais 10 segundos
    5 - desconectar da internet
    6 - até 60 minutos depois
    7 - curl localhost:8080/weather?city=campinas -> retorna a cidade (cache longa)
    8 - curl localhost:8080/weather?city=unknown -> Gracefull Shutdown Message

# iFood Backend Java Test

Este é um projeto base para demonstração de um micro-serviço RESTful fictício.

Recomenda-se gastar entre 4 a 6 horas para realizar essas tarefas. As duas primeiras são as mais importantes, 
enquanto que as duas últimas são opcionais, para quem conseguir terminar antes do previsto.

Esse projeto foi iniciado a partir do [Spring Initializr][SpringInitializr]. É o boilerplate padrão do Spring 1.5.2 
com [Spring Boot][SpringBoot] (torna o micro-serviço executável, sem necessidade de deploy para um servidor).

Crie um *fork* deste repositório e siga as tarefas abaixo.

##Tarefas

### 1. Crie um endpoint para retornar informações sobre o serviço

Para testar rapidamente se nosso serviço está funcionando, crie na classe `About` um endpoint `GET /about` que retorna
uma mensagem confirmando que nosso serviço está funcionando e recebendo requisições.


### 2. Integração com OpenWeatherMap

Vamos usar o OpenWeatherMap para obter informações do clima de uma cidade.

Primeiro, faça um registro rápido no [OpenWeatherMaps][OpenWeather] para obter uma API Key.

Agora, crie um endpoint `GET /weather` no nosso micro-serviço que aceite o parâmetro `city` e retorne os detalhes do
tempo na cidade (ensolarado, nublado, etc) e detalhes meteorológicos (temperatura, pressão, etc), utilizando 
requisições HTTP para o OpenWeatherMaps.

Estruture a resposta da maneira que achar mais organizada. 

Sugestão: Pesquise alguma biblioteca de requisições HTTP robusta, que trate erros e serialização/deserialização 
automaticamente. 


### 3. Cache

Pesquise alguma biblioteca de cache para tornar nosso micro-serviço mais rápido.

Adicione cache na chamada ao servidor do OpenWeatherMaps.


### 4. Tolerância a falhas

Se por algum motivo o serviço do OpenWeatherMaps estiver indisponível, não deveríamos deixar que nosso micro-serviço 
seja afetado.

Para isso, podemos adicionar uma biblioteca de controle de falhas chamada Hystrix.

Nessa etapa, adiciona e configure o [Hystrix][HystrixRepo] para tornar nosso micro-serviço mais robusto.

Em caso de falha... o que poderia acontecer para que o nosso endpoint não retorne apenas uma resposta de erro genérica?


[OpenWeather]: https://openweathermap.org/appid
[FeignRepo]: https://github.com/OpenFeign/feign
[HystrixRepo]: https://github.com/Netflix/Hystrix
[SpringInitializr]: https://start.spring.io/
[SpringBoot]: https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#getting-started