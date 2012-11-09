package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.Util;
import edu.uwm.cs361.classdiagram.ConnectionFigure;

public class EditRoleAction extends AssociationFigureAction
{
		public static final String ID = "actions.editRoleAction";
		
		public EditRoleAction ( ConnectionFigure c )
		{
			super ( ID, c );
			UMLApplicationModel.getProjectResources().configureAction(this, ID);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = UMLApplicationModel.prompt( "actions.editRoleAction.prompt", "Edit Role name" );
			
			if ( name == null )
				{
					Util.dprint( "User canceled" );
					return;
				}
			
			int index = name.indexOf( ':' );
			
			if ( index == -1 ) _data.setRoles ( name, "" );
			else
				{
					String role_a = name.substring( 0, index);
					String role_b = name.substring( index + 1 );
					_data.setRoles( role_a, role_b );
				}
		}
}
