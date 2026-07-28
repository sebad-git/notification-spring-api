package org.uy.sdm.notificator.modules.channels;

public interface Channel<T> {

	void send(T message);

}
