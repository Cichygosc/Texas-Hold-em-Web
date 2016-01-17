package models;

import models.msgs.*;
import models.msgs.NewCard;
import play.Logger;
import play.mvc.*;
import play.libs.*;
import play.libs.F.*;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;
import akka.actor.*;
import static akka.pattern.Patterns.ask;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import static java.util.concurrent.TimeUnit.*;

public class PlayerClient extends UntypedActor {

	protected WebSocket.In<JsonNode> in;
	protected WebSocket.Out<JsonNode> out;
	private HumanPlayer player;
	private ActorRef controller;

	public PlayerClient(String name, WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out, ActorRef controller) {
		this.in = in;
		this.out = out;
		player = new HumanPlayer(name, false);
		this.controller = controller;

		in.onMessage(new Callback<JsonNode>() {
			@Override
			public void invoke(JsonNode event) {
				try {
					String message = event.get("nr").toString();
					message = message.substring(1, message.length() - 1);
					controller.tell(new Info(message, player.getName()), getSelf());
				} catch (Exception e) {
					Logger.error("invokeError");
				}
			}
		});

		in.onClose(new Callback0() {
			@Override
			public void invoke() {
				controller.tell(new Quit(player.getName()), getSelf());
			}
		});
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Info) {
			Info info = (Info) msg;
			ObjectNode event = Json.newObject();
			event.put("message", "[ " + info.getName() + " ] : " + info.getText());
			out.write(event);
		} else if (msg instanceof Seat) {
			player.takeASeat(((Seat) msg).getSeat());
		} else if (msg instanceof Money) {
			player.getPlayerPot().setMoney(((Money) msg).getMoney());
		} else if (msg instanceof NewCard) {
			Card card = new Card(((NewCard) msg).getNumber(), ((NewCard) msg).getSuit());
			if (((NewCard) msg).getName().equals("BOARD"))
				player.getHand().addCardOnBoard(card);
			else
				player.getHand().addCardToHand(card);
		} else if (msg instanceof Winners) {
			Winners winners = (Winners) msg;
			ObjectNode event = Json.newObject();
			event.put("message", "[ " + winners.getName() + " ] : " + winners.getMessage());
			out.write(event);
			player.getHand().newGame();
			player.getPlayerPot().newGame();
		} else if (msg instanceof Turn) {
			Turn turn = (Turn) msg;
			ObjectNode event = Json.newObject();
			getSelf().tell(
					new Info("Your turn. Money " + turn.getPlayerMoney() + ". Table pot " + turn.getPot(), "GAME"),
					getSelf());
			getSelf().tell(new Info("Your cards: " + player.getHand().getCardFromHand(0).toString() + ", "
					+ player.getHand().getCardFromHand(1).toString(), "GAME"), getSelf());
			getSelf().tell(new Info("Cards on board: ", "GAME"), getSelf());
			for (Card card : player.getHand().getBoardCards())
				getSelf().tell(new Info(card.toString(), "GAME"), getSelf());
			getSelf().tell(new Info("Available moves:", "GAME"), getSelf());
			String message = "";
			String buttons[] = turn.getButtons().split(" ");
			for (String button : buttons) {
				message = "";
				if (button.equals("Call"))
					message = "Call " + turn.getCall();
				else if (button.equals("Raise"))
					message = "Raise " + turn.getRaise() + "-" + turn.getMaxRaise();
				else if (button.equals("Bet"))
					message = "Bet " + turn.getRaise() + "-" + turn.getMaxRaise();
				else if (button.equals("AllIn"))
					message = "AllIn";
				else if (button.equals("Fold"))
					message = "Fold";
				else if (button.equals("Check"))
					message = "Check";
				getSelf().tell(new Info(message, "GAME"), getSelf());
			}
		} else {
			unhandled(msg);
		}
	}

	public HumanPlayer getPlayer() {
		return player;
	}
}
