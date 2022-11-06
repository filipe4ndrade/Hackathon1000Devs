package application;

import java.io.IOException;
import java.util.Scanner;
import entities.InspecionarPagina;
import entities.MailCSV;

public class Program {

	public static void enviarGmail(String diretorioUsuario) {
		Scanner input = new Scanner(System.in);
		MailCSV mCsv = new MailCSV();
		String destinatario;
		char resposta;

		while (true) {
			System.out.print("Deseja compartilhar este arquivo por Gmail? (S/N) - ");
			resposta = input.next().charAt(0);

			input.nextLine(); // <-- limpa o buffer

			if (resposta == 'S' || resposta == 's') {
				System.out.print("Insira seu gmail para o envio do arquivo - ");
				destinatario = input.nextLine();

				mCsv.enviarEmail(destinatario, diretorioUsuario);
				System.out.println("E-mail enviado com sucesso!");

				break;

			} else if (resposta == 'N' || resposta == 'n')
				break;
			else {
				System.out.println("Resposta inválida! Tente outra vez. \n");
			}
		}

		input.close();
	}

	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(System.in);
		InspecionarPagina inspecao = new InspecionarPagina();

		String diretorioUsuario = inspecao.criarArquivo();
		System.out.println("Arquivo .CSV gerado com sucesso! \n");

		enviarGmail(diretorioUsuario);

		input.close();
	}
}
