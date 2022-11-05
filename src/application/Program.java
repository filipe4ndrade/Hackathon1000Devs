package application;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Program {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	
	public static void main(String[] args) {

		
		
		System.out.println(ANSI_YELLOW+"=====Vagas Devs Junior no Catho====="+ANSI_RESET);
		
		
		for (int j = 0; j < 10; j++) {
			try {
				Document url = Jsoup.connect(
						"https://www.catho.com.br/vagas/desenvolvedor-junior/?q=Desenvolvedor%20J%C3%BAnior&page="
								+ (j + 1))
						.get();
				Elements informacoes = url.getElementById("search-result").getElementsByTag("article");

				j *= 20;
				for (int i = 1; i <= 20; i++) {

					Element info = informacoes.get(i);

					System.out.print(ANSI_YELLOW+"\nPUBLICACAO " + (i + j) + "#:\n"+ANSI_RESET);
					String cidade = info.getElementsByClass("sc-iyvyFf kIwChr").text();
					String trabalho = info.getElementsByTag("a").text().replace(cidade, ""); //Substituindo Cidade por vazio, no Trabalho
					System.out.println(ANSI_YELLOW+"Trabalho: "+ANSI_RESET + trabalho);
					String empresa = info.getElementsByTag("p").text();
					if (empresa.length() - 1 == empresa.lastIndexOf("?")) {
						// Retirar o termo "Por que?"
						System.out.println(ANSI_YELLOW+"Empresa: " +ANSI_RESET+empresa.substring(0, empresa.indexOf("P")));
					}
					else if(empresa.indexOf(",")>0) {
						System.out.println(ANSI_YELLOW+"Empresa: " +ANSI_RESET+empresa.substring(0, empresa.indexOf(",")));
					}
					else {
					
						System.out.println(ANSI_YELLOW+"Empresa: " +ANSI_RESET+ empresa);
					}
					//Salario
					String salario = info.getElementsByClass("sc-esjQYD kYLuC").text();
					System.out.println(ANSI_YELLOW+"Salario: "+ANSI_RESET + salario);
					//Cidade
					if(cidade.indexOf("(")>0) {
					System.out.println(ANSI_YELLOW+"Cidade(s): "+ANSI_RESET + cidade.substring(0,cidade.indexOf("(")));
					}
					else {
						System.out.println(ANSI_YELLOW+"Cidade(s): "+ANSI_RESET + cidade);
					}
	                //Vagas
					String vagas = info.getElementsByTag("strong").text();
					System.out.println(ANSI_YELLOW+"Quantidade de Vagas: "+ANSI_RESET + vagas.substring(0, vagas.indexOf(":")));
					
					

				}
				j /= 20;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
		}

	}
}
