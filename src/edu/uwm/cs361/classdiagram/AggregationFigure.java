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

import edu.uwm.cs361.Util;
import edu.uwm.cs361.settings.Style;

public class AggregationFigure extends AssociationFigure {

	private static Color for_color = Color.black;
	
  /** Creates a new instance. */
  public AggregationFigure() {
      set(STROKE_COLOR, for_color);
      set(STROKE_WIDTH, 1d);
      //TODO Set end decoration to be an empty diamond tip
      set(END_DECORATION, new ArrowTip());

      setAttributeEnabled(END_DECORATION, false);
      setAttributeEnabled(START_DECORATION, false);
      setAttributeEnabled(STROKE_DASHES, false);
      setAttributeEnabled(FONT_ITALIC, false);
      setAttributeEnabled(FONT_UNDERLINE, false);
  }
  
  static { config(); }
	private static void config ( )
	{
		Style s = Style.get( "AssociationFigure" );
		if ( s == null ) return;
		
		int val = s.getInt( "forground-color", -1);
		if ( val != -1 ) for_color = new Color ( val );
		
		Util.dprint( "AssociationFigure.forground-color:" + for_color );
	}

}
