package application;

import java.io.IOException;

import java.util.Scanner;
import entities.InspecionarPagina;
import entities.MailCSV;

public class Program {

	public static void opcaoCompartilharArquivo(String diretorioUsuario) {
		Scanner input = new Scanner(System.in);

		char resposta;
		String destinatario;

		while (true) {
			System.out.print("Deseja compartilhar esse arquivo por Gmail? (S/N) - ");
			resposta = input.next().charAt(0);

			input.nextLine(); // <-- limpa o buffer

			if (resposta == 'S' || resposta == 's') {
				destinatario = pedirEmailUsuario();

				MailCSV.enviarEmail(destinatario, diretorioUsuario);
				System.out.println("E-mail enviado com sucesso!");

				break;
			} else if (resposta == 'N' || resposta == 'n') break;
			  else {
				System.out.println("Resposta inválida! Tente outra vez. \n");
			}
		}

		input.close();
	}

	public static String pedirEmailUsuario() {
		Scanner input = new Scanner(System.in);

		System.out.print("Insira o Gmail para envio do arquivo - ");
		return input.nextLine();
	}

	public static void main(String[] args) throws IOException {
		String diretorioUsuario = InspecionarPagina.criarArquivo();
		System.out.println("Arquivo .CSV gerado com sucesso! \n");

		opcaoCompartilharArquivo(diretorioUsuario);
	}
}
