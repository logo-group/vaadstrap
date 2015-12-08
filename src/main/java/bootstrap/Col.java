package bootstrap;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

/**
 * @author seb
 *
 */
public class Col extends CssLayout {

	public Col() {
	}
	
	public Col setColStyles(BootstrapStyle...styles){
		StringBuilder sb = new StringBuilder();
		for (BootstrapStyle style : styles) {
			sb.append(style.getStyleName()).append(' ');
		}
		setStyleName(sb.toString());
		return this;
	}
	
	@Override
	public void addComponent(Component c) {
		c.setWidth(100, Unit.PERCENTAGE);
		super.addComponent(c);
	}
	
	@Override
	public void addComponentAsFirst(Component c) {
		c.setWidth(100, Unit.PERCENTAGE);
		super.addComponentAsFirst(c);
	}
}