package Model;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Entity {
	
	public enum Type {
		  PLAYER,
		  ITEM,
		  LADDER,
		  SNAKE,
	}
	
	// IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    protected IntegerProperty x, y;
    //protected String entityName;   // entity identifier
    protected Type type;
    protected BooleanProperty isVisible;
    protected StringProperty image;
	
    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     */
    public Entity(int x, int y, Type type) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.type = type;
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
		System.out.println("REMOVED : " + getEntityType());
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
    
    public Type getEntityType() {
		return type;
	}
    
    public String getEntityName() {
		return type.toString();
	}


	public void setEntityType(Type type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(this.getClass() != obj.getClass()) return false;
		Entity e = (Entity) obj;
		if(this.getX() == e.getX() && this.getY() == e.getY() && this.getEntityType() == e.getEntityType()) {
			return true;
		}
		else return false;
	}
    
}
