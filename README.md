# web-api-exemplo
Exemplo de uma Web API e um Client, inspirados nos conceitos de REST, e HATEOAS.

Fui um exemplo elaborado mediante a questão: "Como criar um client em Java para uma API Restful?". O Resultado desse projeto 
é apenas uma implementação possível, das muitas, para essa questão. 

=====
A idéia é que o servidor gere recursos que contenham links indicando os próximos "estados" possiveis.

Nesse exemplo, temos um tipo de recurso, o Filme, em dois estados possíveis: Filme (inidividual) e Vários Filmes (coletivo).
No servidor, ambos são representados pela classe "FilmeRecursos", que nos retornos configura os links de maneira adequada.

No Cliente, temos classes de modelo semelhantes do servidor (Filme ApiLink, Recurso<T>, Recursos<T>, etc). no package "estados",
temos os elmentos que interagem com o servidor:

FilmeState: Estado com links para FilmesState e exclusivos de uma entidade Filme;
FilmesState: Estado inicial da API, que pode retornar uma lista de FilmeState ou uma lista de Filmes, se necessário.

A comunicação com o servidor, o uso de links dos resultados nos requests, a instanciação dos serviços baseado nos Rels, são feitos
pela classe FilmeServices. 

Em ambos os estados, cada método de interação (implementados com algumas SMI do Java 8) retorna um "State" novo, com os resultados
da interação. Os métodos de interação sã osempre retornados com envelopados com a classe Optional, também do Java 8. Dessa forma é
possível avaliar sempre se um método de interação está disponível (o que depende do que a API te retorna nos links).

Dessa forma, a API cliente em cada interação troca estados definidos da API, possibilitando uma melhor abordagem ao utilizá-la
em outras camadas.

O uso de inglês/português nesse projeto é um problema; a semantica das funções do java 8 também. Portanto, pretendo melhorar
esses conceitos futuramente. 

