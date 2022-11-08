package entities;

import java.io.IOException;
import java.util.InputMismatchException;

import java.io.FileWriter;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class InspecionarPagina {

	private static final int totalPaginas = 9;

	private static final int vagasPorPagina = 40;

	public InspecionarPagina() {}

	public static String obterSenioridadeEscolhida() {
		Scanner input = new Scanner(System.in);

		System.out.println("Quais vagas de desenvolvimento queres buscar? ");
		System.out.println("1 - Junior");
		System.out.println("2 - Pleno");
		System.out.println("3 - Senior");

		do {
			try {
				System.out.print("Digite aqui: ");
				int indiceMenu = input.nextInt();

				System.out.print("\n");

				switch (indiceMenu) {
					case 1:
						return "Junior";
					case 2:
						return "Pleno";
					case 3:
						return "Senior";
					default:
						return "";
				}
			} catch (InputMismatchException e) {
				System.out.println("Valor precisa ser inteiro! \n");

				input.nextLine();
			}
		} while (true);
	}

	public static String obterDiretorioUsuario() {
		Scanner input = new Scanner(System.in);

		System.out.print("Insira o diretorio em que deseja salvar o arquivo .CSV (Ex. D:\\\\Pasta\\\\) - ");
		return input.nextLine();
	}

	public static Document obterCodigoHTML(int numeroPagina, String busca) throws IOException {
		numeroPagina++;

		return Jsoup.connect("https://www.catho.com.br/vagas/desenvolvedor-" + busca + "/?q=Desenvolvedor%20"
				                                                     + busca + "&page=" + numeroPagina).get();
	}

	public static Elements obterDataNoCatho(Document codigoHTML) {
		return codigoHTML.getElementById("search-result").getElementsByTag("article");
	}

	public static boolean testarSeVagaNAOTemSalario(Element data) {
		String salario = obterSalario(data);

		if (salario.equals("")) return true;
		else return false;
	}

	public static boolean testarSeVagaNAOTemLocal(Element data) {
		String local = data.getElementsByClass("sc-iyvyFf kIwChr").text();

		if (local.equals("")) return true;
		else return false;
	}

	public static String obterCargo(Element data) {
		return data.getElementsByTag("h2").text();
	}

	public static String obterEmpresa(Element data) {
		String empresa = data.getElementsByTag("p").text();

		if (empresa.contains("Por que?")) {
			empresa = empresa.substring(0, empresa.indexOf("P"));
		}

		if (empresa.contains(",")) {
			empresa = empresa.replace(",", "");
		}

		return empresa;
	}

	public static String obterSalario(Element data) {
		String salario = data.getElementsByClass("sc-esjQYD kYLuC").text();

		if (salario.contains(",")) {
			salario = salario.replace(",", ".");
		}

		return salario;
	}

	public static String obterLocal(Element data) {
		String local = data.getElementsByClass("sc-iyvyFf kIwChr").text();
		local = local.substring(0, local.indexOf("("));

		return local;
	}

	public static String obterQuantidadeVagas(Element data) {
		String quantidadeVagas = data.getElementsByTag("strong").text();
		quantidadeVagas = quantidadeVagas.substring(0, quantidadeVagas.indexOf(":"));

		return quantidadeVagas;
	}

	public static String obterURL(Element data) {
		Element link = data.select("a").first();
		String URL = link.attr("href");

		return URL;
	}

	public static String criarArquivo() throws IOException {
		int numeroVaga = 1;

		String senioridade = obterSenioridadeEscolhida();
		String diretorioUsuario = obterDiretorioUsuario();

		diretorioUsuario += ("Vagas" + senioridade + ".csv");
		FileWriter montarArquivo = new FileWriter(diretorioUsuario, true);
		montarArquivo.write("Vaga,Cargo,Empresa,Salario,Local,Quantidade de Vagas,Link de Acesso\n");

		System.out.println("Gerando... \n");

		for (int numeroPagina = 0; numeroPagina < totalPaginas; numeroPagina++) {
			Document codigoHTML;
			Elements informacoesColetadas;
			Element data;

			codigoHTML = obterCodigoHTML(numeroPagina, senioridade);
			informacoesColetadas = obterDataNoCatho(codigoHTML);

			for (int indice = 0; indice < vagasPorPagina; indice += 2) {
				String cargo, empresa, salario, local, quantidadeVagas, URL;
				boolean vagaSemSalario, vagaSemLocal;

				data = informacoesColetadas.get(indice);

				vagaSemSalario = testarSeVagaNAOTemSalario(data);
				vagaSemLocal = testarSeVagaNAOTemLocal(data);

				if (vagaSemSalario || vagaSemLocal) continue;

				cargo = obterCargo(data);
				empresa = obterEmpresa(data);
				salario = obterSalario(data);
				local = obterLocal(data);
				quantidadeVagas = obterQuantidadeVagas(data);
				URL = obterURL(data);

				montarArquivo.write((numeroVaga++) + "," + cargo + "," + empresa + "," + salario + ","
						                   + local + "," + quantidadeVagas + "," + URL + "\n");
			}
		}

		montarArquivo.close();

		return diretorioUsuario;
	}
}
