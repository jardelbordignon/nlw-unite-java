# NLW Unite Java

## Visão Geral

A aplicação `nlw-unite-java` é um sistema de gerenciamento de eventos e participantes, desenvolvido com o framework Spring Boot. Ela permite aos usuários criar eventos, registrar participantes e realizar check-ins, facilitando a organização e gestão de eventos.

## Arquitetura

A aplicação segue uma arquitetura monolítica, com uma divisão clara entre a camada de serviço e a camada de controle.

- **Camada de Serviço**: Inclui serviços para gerenciar eventos, participantes e check-ins.
- **Camada de Controle**: Inclui controladores para eventos e participantes, expondo endpoints HTTP para interação.

## Funcionalidades Principais

- **Criação de Eventos**: Permite aos usuários criar eventos, especificando detalhes como título, descrição e número máximo de participantes.
- **Registro de Participantes**: Permite aos usuários registrar participantes em eventos, fornecendo informações como nome e e-mail.
- **Check-ins de Participantes**: Permite aos participantes realizar check-ins em eventos, indicando sua presença.
- **Gestão de Participantes**: Oferece funcionalidades para a gestão de participantes, incluindo a visualização de detalhes dos participantes e a realização de check-ins.

## Tecnologias Utilizadas

- **Spring Boot**: Framework para desenvolvimento de aplicações Java.
- **JPA/Hibernate**: Para persistência de dados, utilizando o padrão JPA com o Hibernate como implementação.
- **Lombok**: Para reduzir a verbosidade do código Java.
- **Jakarta Persistence API (JPA)**: Para mapeamento objeto-relacional (ORM).

