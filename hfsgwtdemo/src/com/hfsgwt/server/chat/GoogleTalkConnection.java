package com.hfsgwt.server.chat;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class GoogleTalkConnection extends XMPPConnection {

	public GoogleTalkConnection() throws XMPPException {
		super(new ConnectionConfiguration("talk.google.com", 5222, "gmail.com"));
	}
}
