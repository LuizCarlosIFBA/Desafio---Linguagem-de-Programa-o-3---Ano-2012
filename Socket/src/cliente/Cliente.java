package cliente;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Scanner;

public class Cliente implements Runnable {
   
	private Socket socket;

	private BufferedReader in;
	private PrintStream out;

	private boolean inicializado;
	private boolean executando;

	private Thread thread;

	private void open(String endereco, int porta) throws Exception {
		try {
			socket = new Socket(endereco, porta);

			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintStream(socket.getOutputStream());

			inicializado = true;
		} catch (Exception e) {
			System.out.println(e);
			close();
			throw e;
		}
	}

	private void close() {
		if (in != null) {
			try {
				in.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		if (out != null) {
			try {
				out.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		if (socket != null) {
			try {
				socket.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		in = null;
		out = null;
		socket = null;

		inicializado = false;
		executando = false;

		thread = null;

	}

	public void start() {
		if (!inicializado || executando) {
			return;
		}

		executando = true;
		thread = new Thread(this);
		thread.start();
	}

	public void stop() throws Exception {
		executando = false;

		if (thread != null) {
			thread.join();
		}
	}

	public boolean isExecutando() {
		return executando;
	}

	public void send(String mensagem) {
		out.println(mensagem);
	}

	@Override
	public void run() {

		while (executando) {
			try {
				socket.setSoTimeout(2500);

				String mensagem = in.readLine();

				if (mensagem == null) {
					break;
				}

				System.out.println("Mensagem enviada pelo servidor: " + mensagem);

			} catch (SocketTimeoutException e) {
				// Ignorar
			} catch (Exception e) {
				System.out.println(e);
				break;
			}
		}

		close();

	}

	ArrayList<String> mensagem = new ArrayList();
	public static void main(String[] args) throws Exception {

		Cliente cliente = new Cliente();

		System.out.println("Conexão estabelecida com sucesso ...");

		cliente.start();

		Scanner scanner = new Scanner(System.in);
		String msg;
		while (true) {
			System.out.println("Cadastro de\n" + "   Anuncios de Eletronicos \n" + "   (\n"
					+ "   Para Terminar Digite # na descrição do Anuncio\n" + "   )\n" + "   ");

			System.out.print("Entre com a descricão do anuncio:  ");
			msg = scanner.nextLine();
			mensagem.add(msg);
			if (!cliente.isExecutando()) {
				break;
			}

			cliente.send(msg);

			if ("#".equals(msg)) {
				System.out.print("FIM");
				break;			   
			}else System.out.print("OK"); 

		}

		System.out.println("Encerrando cliente ...");

		cliente.stop();

	}

}

