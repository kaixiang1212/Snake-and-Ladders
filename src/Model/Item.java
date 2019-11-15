package Model;

public class Item extends Entity {
	
	public enum ItemType {
		  SKIPTURN,				// Skip next player's turn
		  EXTRAROLL,			// Roll an extra dice
		  SLOWDOWN,				// Slowdown the next player piece (on the board) for a few turns by halving their rolls
		  IMMUNITY,				// Get temporary snake immunity
		  ROLLBACK,				// Roll a dice to move the leading player back
		  DOUBLE,				// Double the number on the dice
		  SWITCHEROO,			// Switch the functionality of the snakes and the ladders for the next player piece (on the board) for a few turns
		  ANTIDOTE,				// Use when poisoned by a snake
		  REVERSE				// Reverse the order of play
	}
	
	private ItemType itemType;
	private int frequency;		// Relative item frequency
	private int expiryCounter;	// Number of turns until the item despawns. Set to -1 for no expiry
	
	public Item(int x, int y, ItemType itemType, int frequency, int exp) {
		super(x, y, Type.ITEM);
		this.itemType = itemType;
		this.frequency = frequency;
		this.expiryCounter = exp;
	}
	
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}
	
	public ItemType getItemType() {
		return itemType;
	}
	
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	public int getFrequency() {
		return frequency;
	}
	
	public int getExpiry() {
		return expiryCounter;
	}
	
	public void decrementExpiry() {
		expiryCounter--;
	}

}
