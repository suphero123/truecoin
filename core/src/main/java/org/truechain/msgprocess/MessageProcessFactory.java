package org.truechain.msgprocess;

import org.truechain.message.Message;

/**
 * 消息处理
 * @author ln
 *
 */
public interface MessageProcessFactory {

	MessageProcess getFactory(Message message);
}
