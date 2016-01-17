import static org.junit.Assert.*;
import org.junit.Test;
import models.msgs.NewCard;
import models.Deck;

public class SomeTest {

    @Test
    public void deckTest() {
		Deck d = new Deck();
		NewCard c = d.giveCard(0);
		assertEquals(c.getRank(),"2");
    }
	
	@Test
    public void otherTest() {

    }


}
