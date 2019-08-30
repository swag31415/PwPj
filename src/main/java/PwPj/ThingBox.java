package PwPj;

import java.io.Serializable;

/**
 * Just a wrapper of sorts for Objects that makes thwm Serializeable
 * 
 * @author swag31415
 */
public class ThingBox implements Serializable {

    private static final long serialVersionUID = -1920932889211266896L;

    private Object thing;

    public ThingBox(Object thing) {
        this.thing = thing;
    }

    public Object getThing() {
        return this.thing;
    }
}