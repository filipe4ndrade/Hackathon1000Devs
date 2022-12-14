package entities;

import java.util.Properties;
import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.Multipart;
import javax.mail.internet.MimeMultipart;
import javax.mail.BodyPart;
import javax.mail.internet.MimeBodyPart;
import javax.activation.FileDataSource;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Transport;

public class MailCSV {

	public MailCSV() {}

	public static void enviarEmail(String destinatario, String diretorioUsuario) {
		Properties propriedades = System.getProperties();

		String de = "xxxxxx@gmail.com";
		String para = destinatario;

		// Definindo propriedades:
		propriedades.put("mail.smtp.host", "smtp.gmail.com");
		propriedades.put("mail.smtp.port", "465");
		propriedades.put("mail.smtp.ssl.enable", "true");
		propriedades.put("mail.smtp.auth", "true");

		Session sessao = Session.getInstance(propriedades, new Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(de, "yyyyyy");
			}
		} );

		try {
			MimeMessage mensagem = new MimeMessage(sessao);
			Multipart multi = new MimeMultipart();
			BodyPart textoMensagem = new MimeBodyPart();
			BodyPart anexoMensagem = new MimeBodyPart();
			
			String arquivo = diretorioUsuario;
			DataSource fonte = new FileDataSource(arquivo);

			// Construindo a parte com texto do e-mail:
			mensagem.setFrom(new InternetAddress(de));
			mensagem.addRecipient(Message.RecipientType.TO, new InternetAddress(para));
			mensagem.setSubject("Selecionamos estas vagas exclusivamente para você!");
			textoMensagem.setText("Vagas de \"Desenvolvedor de Software\": ");

			// Construindo a parte com anexo do e-mail:
			anexoMensagem.setDataHandler(new DataHandler(fonte));
			anexoMensagem.setFileName(arquivo.substring(arquivo.indexOf("V")));

			// Juntando as 2 partes:
			multi.addBodyPart(textoMensagem);
			multi.addBodyPart(anexoMensagem);
			mensagem.setContent(multi);

			System.out.println("Enviando... \n");
			Transport.send(mensagem);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
