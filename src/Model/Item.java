package Model;

public class Item extends Entity {
	
	public enum ItemType {
		  SKIPTURN,				// Skip next player's turn
		  EXTRAROLL,			// Roll an extra dice (preserved if you roll a 6)
		  SLOWDOWN,				// Slow down the next closest piece on the board for a few turns by halving their rolls
		  SHIELD,				// Temporarily protects against power-ups
		  ROLLBACK,				// Roll a dice to move the first player on the board backwards
		  DOUBLE,				// Double the result of the next roll
		  SWAP,					// Swap positions with the first player on the board
		  ANTIDOTE,				// Heals snake poison and gives temporary snake immunity
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
