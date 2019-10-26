package Sneks_and_Ladders;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Entity {
	
	
	// IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;
    private String entityName;   // entity identifier
	private BooleanProperty isVisible;
	private StringProperty image;
	
    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     */
    public Entity(int x, int y, String name) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.entityName = name;
        this.isVisible =  new SimpleBooleanProperty(true);
        this.image =  new SimpleStringProperty("N/A");
    }

    public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
        return y;
    }

    public int getY() {
        return y().get();
    }

    public int getX() {
        return x().get();
    }
    
    public void setX(int x) {
		this.x.set(x);
	}

	public void setY(int y) {
		this.y.set(y);
	}

	//TO MAKE GRAPHIC DISAPPEAR
	public void Invisible() {
		System.out.println("REMOVED : " + getEntityName());
		this.isVisible.setValue(false);
	}
	public BooleanProperty getIsVisible() {
		return isVisible;
	}
	//TO MAKE GRAPHIC CHANGE
	public void setImage(String string) {
		this.image.setValue(string);
	}
	public StringProperty getImage() {
		return this.image;
	}
    
    public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(this.getClass() != obj.getClass()) return false;
		Entity e = (Entity) obj;
		if(this.getX() == e.getX() && this.getY() == e.getY() && this.getEntityName() == e.getEntityName()) {
			return true;
		}
		else return false;
	}
    
}
