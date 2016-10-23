package org.truechain.msgprocess;

import org.truechain.core.Peer;
import org.truechain.message.Message;

public interface MessageProcess {

	MessageProcessResult process(Message message, Peer peer);
}
