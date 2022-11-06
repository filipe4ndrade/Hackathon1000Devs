package application;

import java.io.IOException;

import entities.InspecionarPagina;

public class Program {

	public static void main(String[] args) throws IOException {
		InspecionarPagina inspecao = new InspecionarPagina();


		inspecao.criarArquivo(inspecao.obterMenuBusca(), inspecao.obterDiretorioUsuario());

		System.out.println("Arquivo .CSV gerado com sucesso!");

	}
}