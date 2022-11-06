# Plataforma que Busca Vagas para Desenvolvedores na Internet e Disponibiliza para os Usuários

O primeiro Hackathon da segunda turma do programa 1000Devs, trouxe o desafio de implementarmos um código em Java que conseguisse trazer informações objetivas das vagas de emprego de desenvolvedor. Nosso código tem como saída uma planilha em CSV com as principais informações das vagas no diretório que o usuário inserir, além de dar a opção para o usuário receber por email.

Buscando agilidade, optamos por fazer a busca em apenas um site de plataforma de empregos: Catho, e as informações que conseguimos extrair da inspeção do html da página foram: nome do cargo, nome da empresa, salário, local de trabalho, quantidade de vagas e link da oferta. O filtro de busca das vagas foi limitado a 3 opções: Junior, Pleno e Senior, que o usuário faz a escolha. Toda esta primeira implementação foi realizada através da biblioteca Jsoup. 
