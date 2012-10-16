
package edu.uwm.cs361.sequencediagram;

import org.jhotdraw.draw.connector.Connector;
import org.jhotdraw.draw.decoration.ArrowTip;
import java.awt.*;
import static org.jhotdraw.draw.AttributeKeys.*;
import org.jhotdraw.draw.*;

/**
 * AssociationFigure.
 */
public class MessageFigure extends LineConnectionFigure {

    /** Creates a new instance. */
    public MessageFigure() {
        set(STROKE_COLOR, new Color(0x000099));
        set(STROKE_WIDTH, 1d);
        set(END_DECORATION, new ArrowTip());

        setAttributeEnabled(END_DECORATION, false);
        setAttributeEnabled(START_DECORATION, false);
        setAttributeEnabled(STROKE_DASHES, false);
        setAttributeEnabled(FONT_ITALIC, false);
        setAttributeEnabled(FONT_UNDERLINE, false);
    }

    /**
     * Checks if two figures can be connected. Implement this method
     * to constrain the allowed connections between figures.
     */
    @Override
    public boolean canConnect(Connector start, Connector end) {
        /*if ((start.getOwner() instanceof LifelineFigure)
                && (end.getOwner() instanceof LifelineFigure)) {

            LifelineFigure sf = (LifelineFigure) start.getOwner();
            LifelineFigure ef = (LifelineFigure) end.getOwner();

            // Disallow multiple connections to same dependent
            if (ef.getPredecessors().contains(sf)) {
                return false;
            }

            // Disallow cyclic connections
            return !sf.isDependentOf(ef);
        }*/

        return true;
    }

    @Override
    public boolean canConnect(Connector start) {
        return (start.getOwner() instanceof ObjectLifelineFigure);
    }

    /**
     * Handles the disconnection of a connection.
     * Override this method to handle this event.
     */
    @Override
    protected void handleDisconnect(Connector start, Connector end) {
/*        LifelineFigure sf = (LifelineFigure) start.getOwner();
        LifelineFigure ef = (LifelineFigure) end.getOwner();

        sf.removeDependency(this);
        ef.removeDependency(this);*/
    }

    /**
     * Handles the connection of a connection.
     * Override this method to handle this event.
     */
    @Override
    protected void handleConnect(Connector start, Connector end) {
 /*       LifelineFigure sf = (LifelineFigure) start.getOwner();
        LifelineFigure ef = (LifelineFigure) end.getOwner();

        sf.addDependency(this);
        ef.addDependency(this);*/
    }

    @Override
    public MessageFigure clone() {
        MessageFigure that = (MessageFigure) super.clone();

        return that;
    }

    @Override
    public int getLayer() {
        return 1;
    }

    @Override
    public void removeNotify(Drawing d) {
  /*      if (getStartFigure() != null) {
            ((LifelineFigure) getStartFigure()).removeDependency(this);
        }
        if (getEndFigure() != null) {
            ((LifelineFigure) getEndFigure()).removeDependency(this);
        }*/
        super.removeNotify(d);
    }
}
