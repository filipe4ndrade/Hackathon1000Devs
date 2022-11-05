package application;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Program {

	public static void main(String[] args) {

		System.out.println("=====Vagas Devs Junior no Catho=====");

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

					System.out.print("\nPublicacao " + (i + j) + "#:\n");
					String trabalho = info.getElementsByTag("a").text();
					System.out.println("Trabalho: " + trabalho);
					String empresa = info.getElementsByTag("p").text();
					if (empresa.length() - 1 == empresa.lastIndexOf("?")) {
						// Retirar o termo "Por que?"
						System.out.println("Empresa: " + empresa.substring(0, empresa.indexOf("P")));
					} else {
						System.out.println("Empresa: " + empresa);
					}
					String salario = info.getElementsByClass("sc-esjQYD kYLuC").text();
					System.out.println("Salario: " + salario);
					String vagas = info.getElementsByTag("strong").text();
					System.out.println("Quantidade de Vagas: " + vagas.substring(0, vagas.indexOf(":")));

				}
				j /= 20;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
