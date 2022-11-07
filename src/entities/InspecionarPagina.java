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

	private static final int totalDataColetada = 40;

	public InspecionarPagina() {}

	public String obterMenuBusca() {
		Scanner input = new Scanner(System.in);

		System.out.println("Quais vagas de desenvolvimento queres buscar? ");
		System.out.println("1 - Junior");
		System.out.println("2 - Pleno");
		System.out.println("3 - Senior");
		do {
			try {
				System.out.print("Digite aqui: ");
				int indiceMenu = input.nextInt();

				System.out.println();

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

	public String obterDiretorioUsuario() {
		Scanner input = new Scanner(System.in);

		System.out.print("Insira o diretorio em que deseja salvar o arquivo .CSV (Ex. D:\\\\Pasta\\\\) - ");
		String diretorioUsuario = input.nextLine();

		return diretorioUsuario;
	}

	public static Document obterCodigoHTML(int numeroPagina, String busca) throws IOException {
		numeroPagina++;

		return Jsoup.connect("https://www.catho.com.br/vagas/desenvolvedor-" + busca + "/?q=Desenvolvedor%20" 
		                                                                     + busca + "&page=" + numeroPagina).get();
	}

	public static Elements obterDataNoCatho(Document codigoHTML) {
		return codigoHTML.getElementById("search-result").getElementsByTag("article");
	}

	public static boolean testarSeVagaTemSalario(Element data) {
		String salario = obterSalario(data);

		if (salario.equals(""))
			return true;
		else
			return false;
	}

	public static boolean testarSeVagaTemLocal(Element data) {
		String local = data.getElementsByClass("sc-iyvyFf kIwChr").text();

		if (local.equals(""))
			return true;
		else
			return false;
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
		URL = URL.substring(0, URL.indexOf("?"));

		return URL;
	}

	public String criarArquivo() throws IOException {
		String busca = obterMenuBusca();
		String diretorioUsuario = obterDiretorioUsuario();
		int indiceVaga = 1;

		diretorioUsuario += ("Vagas" + busca + ".csv");

		FileWriter montarArquivo = new FileWriter(diretorioUsuario, true);
		montarArquivo.write("Vaga,Cargo,Empresa,Salario,Local,Quantidade de Vagas,Link de Acesso\n");

		System.out.println("Processando... \n");

		for (int numeroPagina = 0; numeroPagina < totalPaginas; numeroPagina++) {
			Document codigoHTML;
			Elements dataColetada;
			Element dataFiltrada;

			codigoHTML = obterCodigoHTML(numeroPagina, busca);
			dataColetada = obterDataNoCatho(codigoHTML);

			for (int indice = 0; indice < totalDataColetada; indice += 2) {
				String cargo, empresa, salario, local, quantidadeVagas, URL;
				boolean vagaSemSalario, vagaSemLocal;

				dataFiltrada = dataColetada.get(indice);

				vagaSemSalario = testarSeVagaTemSalario(dataFiltrada);
				vagaSemLocal = testarSeVagaTemLocal(dataFiltrada);

				if (vagaSemSalario || vagaSemLocal) continue;

				cargo = obterCargo(dataFiltrada);
				empresa = obterEmpresa(dataFiltrada);
				salario = obterSalario(dataFiltrada);
				local = obterLocal(dataFiltrada);
				quantidadeVagas = obterQuantidadeVagas(dataFiltrada);
				URL = obterURL(dataFiltrada);

				montarArquivo.write( (indiceVaga++) + "," + cargo + "," + empresa + "," + salario + "," 
				                                    + local + "," + quantidadeVagas + "," + URL + "\n");
			}
		}

		montarArquivo.close();

		return diretorioUsuario;
	}

}