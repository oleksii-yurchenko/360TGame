package org.yura.model.message.strategy;

import org.yura.model.Player;

/**
 * The {@code MessageStrategy} interface defines a strategy for handling messages for a player.
 */
public interface MessageStrategy {

    void start(Player player);
}
