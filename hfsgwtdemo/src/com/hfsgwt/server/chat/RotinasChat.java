package com.hfsgwt.server.chat;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import com.hfsgwt.client.componentes.chat.ChatMensagem;
import com.hfsgwt.client.componentes.chat.ChatUsuario;
import com.hfsgwt.server.ServicoException;

public final class RotinasChat {
	private static Logger log = Logger.getLogger(RotinasChat.class);
	private static RotinasChat instancia;

	private ChatMensagem mensagem;
	//private XMPPConnection conexao;

	private RotinasChat() {
		super();
		//mensagem = new ChatMensagem();
	}

	public static RotinasChat getInstancia() {
		if (instancia == null) {
			instancia = new RotinasChat();
		}
		return instancia;
	}

	private Chat criarChat(XMPPConnection conexao, String usuario) {
		Chat chat = conexao.getChatManager().createChat(usuario,
				new MessageListener() {
					public void processMessage(Chat chat, Message message) {
						mensagem.setUsuarioOrigem(message.getFrom());
						mensagem.setUsuarioDestino(message.getTo());
						mensagem.setAssunto(message.getSubject());
						mensagem.setMensagem(message.getBody());
						log.info("Chat Participante: " + chat.getParticipant()
								+ ", Mensagem recebida -> "
								+ mensagem.toString());
					}
				});
		return chat;
	}

	public void enviarMensagem(XMPPConnection conexao, String usuarioDestino, String mensagem)
			throws ServicoException {
		try {
			Chat chat = this.criarChat(conexao, usuarioDestino);
			chat.sendMessage(mensagem);
		} catch (XMPPException e) {
			throw new ServicoException(log,
					"Erro ao enviar mensagem pelo chat. " + e.getMessage());
		}
	}

	public void enviarMensagem(XMPPConnection conexao, String usuarioOrigem, ChatMensagem mensagem)
			throws ServicoException {
		try {
			Chat chat = this.criarChat(conexao, usuarioOrigem);
			
			Message mens = new Message();
			mens.setFrom(mensagem.getUsuarioOrigem());
			mens.setTo(mensagem.getUsuarioDestino());
			mens.setSubject(mensagem.getAssunto());
			mens.setBody(mensagem.getMensagem());
						
			chat.sendMessage(mens);
		} catch (XMPPException e) {
			throw new ServicoException(log,
					"Erro ao enviar mensagem pelo chat. " + e.getMessage());
		}
	}

//	public Message formataMensagem(String mensagem) {
//		Message message = new Message();
//		// message.setType(Type.chat);
//		message.setProperty("favoriteColor", new Color(0, 0, 255));
//		message.setProperty("favoriteNumber", 4);
//		message.setBody(mensagem);
//		return message;
//	}

	public ChatUsuario[] getUsuarios(XMPPConnection conexao) {		
		Roster roster = conexao.getRoster();
		Collection<RosterEntry> entries = roster.getEntries();
		ChatUsuario[] usuarios = new ChatUsuario[entries.size()];
		int i = 0;
		for (RosterEntry entry : entries) {
			usuarios[i] = new ChatUsuario(entry.getName(), entry.getUser());
			i++;
		}
		roster.addRosterListener(new RosterListener() {
			public void entriesAdded(Collection<String> addresses) {
				log.info("Usuários adicionados: " + addresses.toString());
			}

			public void entriesDeleted(Collection<String> addresses) {
				log.info("Usuários excluídos: " + addresses.toString());
			}

			public void entriesUpdated(Collection<String> addresses) {
				log.info("Usuários atualizados: " + addresses.toString());
			}

			public void presenceChanged(Presence presence) {
				log.info("Presence modificado: " + presence.getFrom() + " "
						+ presence);
			}
		});
		return usuarios;
	}

	public XMPPConnection conectaGoogleTalk(ChatUsuario usuario) throws ServicoException {
		try {
			XMPPConnection conexao = new GoogleTalkConnection();
			// ProxyInfo pi = new ProxyInfo(ProxyType.HTTP, pHost, pPort, pUser,
			// pPass)
			// ConnectionConfiguration cfg = new
			// ConnectionConfiguration("talk.google.com",5223,"gmail.com");
			// cfg.setCompressionEnabled(true);
			// cfg.setSASLAuthenticationEnabled(true);
			// cfg.setSecurityMode(SecurityMode.required);
			// XMPPConnection connection = new XMPPConnection(cfg);
			conexao.connect();
			conexao.login(usuario.getUsuario(), usuario.getSenha());
			return conexao;
		} catch (XMPPException e) {
			throw new ServicoException(log,
					"Erro ao conectar no chat do GoogleTalk. " + e.getMessage());
		}
	}

	public void desconecta(XMPPConnection conexao) {
		conexao.disconnect();
	}

	public ChatMensagem getMensagem() {
		return mensagem;
	}

	// // Create a packet filter to listen for new messages from a particular
	// // user. We use an AndFilter to combine two other filters.
	// PacketFilter filter = new AndFilter(new PacketTypeFilter(Message.class),
	// new FromContainsFilter("mary@jivesoftware.com"));
	// // Assume we've created an XMPPConnection name "connection".
	//
	// // First, register a packet collector using the filter we created.
	// PacketCollector myCollector = connection.createPacketCollector(filter);
	// // Normally, you'd do something with the collector, like wait for new
	// packets.
	//
	// // Next, create a packet listener. We use an anonymous inner class for
	// brevity.
	// PacketListener myListener = new PacketListener() {
	// public void processPacket(Packet packet) {
	// // Do something with the incoming packet here.
	// }
	// };
	// // Register the listener.
	// connection.addPacketListener(myListener, filter)

}
