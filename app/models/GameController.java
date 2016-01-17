package models;

import play.mvc.*;
import play.libs.*;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;
import akka.actor.*;
import static akka.pattern.Patterns.ask;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.*;
import java.util.Map.Entry;

import models.msgs.*;
import models.msgs.NewCard;
import static java.util.concurrent.TimeUnit.*;

public class GameController extends UntypedActor {

	// Default table for all players.
	// You can/should create many tables.
	static ActorRef instance = Akka.system().actorOf(Props.create(GameController.class));
	static PokerRoom pokerRoom = new PokerRoom(instance);

	// Members of this table
	static Map<String, ActorRef> members = new HashMap<String, ActorRef>();

	// Join the table
	public static void join(final String name, WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out)
			throws Exception {

		// Send the Join message to the table
		String result = (String) Await.result(ask(instance, new Join(name), 1000), Duration.create(1, SECONDS));

		if (!pokerRoom.getGameStarted()
				&& pokerRoom.getNumOfConnectedPlayers() < GameSettings.getInstance().getNumOfPlayers()
				&& "OK".equals(result)) {

			ActorRef player = Akka.system().actorOf(Props.create(PlayerClient.class, name, in, out, instance));

			members.put(name, player);
			pokerRoom.addPlayer(name);
			pokerRoom.startGame();
		}
	}

	public static void quit(final String username) throws Exception {

	}

	public void onReceive(Object message) throws Exception {

		if (message instanceof Join) {

			Join join = (Join) message;

			if (false) {
				// if username is already taken do sth
			} else {
				getSender().tell("OK", getSelf());
			}
		} else if (message instanceof Seat) {
			Seat seat = (Seat) message;
			int s = seat.getSeat();
			String name = seat.getPlayerName();
			String text = name + " has taken seat " + s;
			notifyAll(new Info(text, name));
			notifyPlayer(message, name);
		} else if (message instanceof Money) {
			int money = ((Money) message).getMoney();
			String name = ((Money) message).getName();
			notifyPlayer(message, name);
		} else if (message instanceof Turn) {
			notifyAll(new Info(((Turn) message).getName() + " turn", "GAME"));
			notifyPlayer(message, ((Turn) message).getName());
		} else if (message instanceof NewCard) {
			String name = ((NewCard) message).getName();
			if (name.equals("BOARD"))
				notifyAll(message);
			else
				notifyPlayer(message, name);
		} else if (message instanceof Winners) {
			notifyAll(message);
		} else if (message instanceof Info) {
			String text = ((Info) message).getText();
			String name = ((Info) message).getName();

			if (pokerRoom.getGameStarted() && pokerRoom.isPlayerTurn(name)) {
				try {
					String buttons = pokerRoom.getCurrentOptions().getButtons();
					if (text.startsWith("Call") && buttons.contains("Call")) {
						int money = pokerRoom.getCurrentOptions().getCall();
						pokerRoom.getCurrentPlayer().getPlayerPot().bet(money);
						pokerRoom.getDealer().addPot(money);
						if (pokerRoom.getCurrentPlayer().getPlayerPot().getMoney() == 0) {
							pokerRoom.getCurrentPlayer().getPlayerPot().allIn();
							pokerRoom.playerFoldOrAllIn();
						}
						String newText = "Player " + name + " call " + money;
						notifyAll(new Info(newText, "GAME"));
						pokerRoom.nextPlayerTurn();
					} else if ((text.startsWith("Raise") && buttons.contains("Raise"))
							|| (text.startsWith("Bet") && buttons.contains("Bet"))) {
						String line[] = text.split(" ");
						int money = Integer.parseInt(line[1]);
						pokerRoom.getCurrentPlayer().getPlayerPot().bet(money);
						pokerRoom.getDealer().setCurrentBet(money);
						if (pokerRoom.getCurrentPlayer().getPlayerPot().getMoney() == 0) {
							pokerRoom.getCurrentPlayer().getPlayerPot().allIn();
							pokerRoom.playerFoldOrAllIn();
						}
						String newText = "Player " + name + " raise " + money;
						notifyAll(new Info(newText, "GAME"));
						pokerRoom.nextPlayerTurn();
					} else if (text.startsWith("AllIn") && buttons.contains("AllIn")) {
						pokerRoom.getDealer().addPot(pokerRoom.getCurrentPlayer().getPlayerPot().getMoney());
						pokerRoom.getCurrentPlayer().getPlayerPot().allIn();
						pokerRoom.playerFoldOrAllIn();
						String newText = "Player " + name + " allin";
						notifyAll(new Info(newText, "GAME"));
						pokerRoom.nextPlayerTurn();
					} else if (text.startsWith("Fold") && buttons.contains("Fold")) {
						pokerRoom.getCurrentPlayer().getPlayerPot().fold();
						pokerRoom.playerFoldOrAllIn();
						String newText = "Player " + name + " fold";
						notifyAll(new Info(newText, "GAME"));
						pokerRoom.nextPlayerTurn();
					} else if (text.startsWith("Check") && buttons.contains("Check")) {
						String newText = "Player " + name + " check";
						notifyAll(new Info(newText, "GAME"));
						pokerRoom.nextPlayerTurn();
					} else
						notifyAll(new Info(text, name));
				} catch (Exception e) {
					notifyAll(new Info(text, name));
				}
			} else
				notifyAll(new Info(text, name));
		} else if (message instanceof Quit) {

			String name = ((Quit) message).getName();
			members.remove(name);
			pokerRoom.removePlayer(name);
			String text = "has left the game";
			notifyAll(new Info(text, name));

		} else {
			unhandled(message);
		}

	}

	// Private method for reusing the code.
	// Public methods for communication should be avoided in Akka.
	static private void notifyAll(Object msg) {
		for (ActorRef member : members.values()) {
			member.tell(msg, instance);
		}
	}

	static private void notifyPlayer(Object msg, String name) {
		for (Entry<String, ActorRef> entry : members.entrySet()) {
			if (entry.getKey().equals(name)) {
				entry.getValue().tell(msg, instance);
				break;
			}
		}
	}

}
