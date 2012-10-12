package edu.uwm.cs361.classdiagram;

import static org.jhotdraw.draw.AttributeKeys.END_DECORATION;
import static org.jhotdraw.draw.AttributeKeys.FONT_ITALIC;
import static org.jhotdraw.draw.AttributeKeys.FONT_UNDERLINE;
import static org.jhotdraw.draw.AttributeKeys.START_DECORATION;
import static org.jhotdraw.draw.AttributeKeys.STROKE_COLOR;
import static org.jhotdraw.draw.AttributeKeys.STROKE_DASHES;
import static org.jhotdraw.draw.AttributeKeys.STROKE_WIDTH;

import java.awt.Color;

import org.jhotdraw.draw.decoration.ArrowTip;

public class CompositionFigure extends AssociationFigure {

  /** Creates a new instance. */
  public CompositionFigure() {
      set(STROKE_COLOR, new Color(0x000099));
      set(STROKE_WIDTH, 1d);
      //TODO Set end decoration to be a filled diamond tip
      set(END_DECORATION, new ArrowTip());

      setAttributeEnabled(END_DECORATION, false);
      setAttributeEnabled(START_DECORATION, false);
      setAttributeEnabled(STROKE_DASHES, false);
      setAttributeEnabled(FONT_ITALIC, false);
      setAttributeEnabled(FONT_UNDERLINE, false);
  }
}
