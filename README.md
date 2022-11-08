# Plataforma que Busca Vagas para Desenvolvedores na Internet e Disponibiliza aos Usuários 💻

O primeiro Hackathon da segunda turma do programa 1000DEVs, trouxe o desafio de implementarmos um código em Java que conseguisse trazer informações objetivas das vagas de emprego para desenvolvedores. Nosso código tem como saída uma planilha em CSV com as principais informações das vagas no diretório que o usuário inserir.

Buscando agilidade, optamos por fazer a busca em apenas um site de plataforma de empregos: Catho, e as informações que conseguimos extrair da inspeção do html da página foram: nome do cargo, nome da empresa, salário, local de trabalho, quantidade de vagas e link da oferta. O filtro de busca das vagas foi limitado a 3 opções: Junior, Pleno e Senior, que o usuário faz a escolha. Toda essa primeira implementação foi realizada através da biblioteca Jsoup.

Também incorporamos a opção do usuário compartilhar o arquivo via Gmail, algo a se fazer possível com o auxílio da biblioteca JavaMail. Para isso, é preciso informar o e-mail do destinatário ao algoritmo, seja do usuário ou daquele a quem ele almeja mostrar a planilha. 

⚠️ - Caso queira se utilizar do código para ser o remetente nessa última implementação, siga os seguintes passos: ponha o Gmail desejado na variável "de", em MailCSV >> vá às configurações da conta Google desse e-mail, em Segurança >> desça até "Verificação em 2 etapas" e ative a funcionalidade >> volte para Segurança, clique em "Senhas de App" (logo abaixo da "Verificação em 2 etapas") e adicione uma senha a "E-mail" / "Computador Windows" >> será gerada a senha que se deve colocar na Session, PasswordAuthentication, MailCSV.
