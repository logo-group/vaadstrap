package com.lbs.vaadin.bootstrap;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author seb
 */
public class Col extends CssLayout {

	private Style childrenStyle;
	private Map<Threshold, Integer> styleMap;

	public Col() {
	}

	public Col(Style... styles){
		this();
		addStyles(styles);
	}

	public Col(@Min(1) @Max(12) int sm,
						 @Min(1) @Max(12) int md) {
		this();
		addStyles(ColMod.get(Threshold.SM, sm),
				ColMod.get(Threshold.MD, md));
	}

	public Col(@Min(1) @Max(12) int xs,
						 @Min(1) @Max(12) int sm,
						 @Min(1) @Max(12) int md,
						 @Min(1) @Max(12) int lg) {
		this();
		addStyles(ColMod.get(Threshold.XS, xs),
				ColMod.get(Threshold.SM, sm),
				ColMod.get(Threshold.MD, md),
				ColMod.get(Threshold.LG, lg));
	}


	public Col clearStyles() {
		setStyleName("");
		return this;
	}

	public Col addStyles(Style... styles) {
		for (Style style : styles) {
			addStyleName(style.getStyleName());
		}
		return this;
	}

	/**
	 * @param childrenStyle   children style to apply, this is usually some margin, with {@link MarginMod}.
	 * @return this object
	 */
	public Col setChildrenStyle(Style childrenStyle) {
		return setChildrenStyle(childrenStyle, false);
	}

	/**
	 * @param childrenStyle   children style to apply, this is usually some margin, with {@link MarginMod}.
	 * @param applyToExisting if true, applies given style to all existing children
	 * @return this object
	 */
	public Col setChildrenStyle(Style childrenStyle, boolean applyToExisting) {
		this.childrenStyle = childrenStyle;
		if (applyToExisting) {
			for (int i = 0; i < getComponentCount(); i++) {
				Component component = getComponent(i);
				component.addStyleName(childrenStyle.getStyleName());
			}
		}
		return this;
	}

	/**
	 * Add components with builder pattern
	 * @param components components to add
	 * @return this
	 */
	public Col add(Component... components){
		addComponents(components);
		return this;
	}

	@Override
	public void addComponent(Component c) {
		c.setWidth(100, Unit.PERCENTAGE);
		if (childrenStyle != null) {
			c.addStyleName(childrenStyle.getStyleName());
		}
		super.addComponent(c);
	}

	@Override
	public void addComponentAsFirst(Component c) {
		c.setWidth(100, Unit.PERCENTAGE);
		if (childrenStyle != null) {
			c.addStyleName(childrenStyle.getStyleName());
		}
		super.addComponentAsFirst(c);
	}

	private Map<Threshold,Integer> extractStyles(String style) {
		styleMap = new HashMap<>();
		//TODO refactor
		if(style.contains(" ")) {
			String[] styles = style.split(" ");
			for(int i = 0; i< styles.length ; i++) {
				if(styles[i].startsWith("col-")){
					String extractedStyle = styles[i].substring(4);
					if(extractedStyle.contains("-")){
						String[] thresholdAnRatio = extractedStyle.split("-");
						Threshold threshold = null;
						if(thresholdAnRatio[0].equals("lg")){
							threshold = Threshold.LG;
						}
						else if(thresholdAnRatio[0].equals("md")){
							threshold = Threshold.MD;
						}
						else if(thresholdAnRatio[0].equals("sm")){
							threshold = Threshold.SM;
						}
						else if(thresholdAnRatio[0].equals("xs")){
							threshold = Threshold.XS;
						}
						if(threshold != null){
							try {
								Integer ratio = Integer.valueOf(thresholdAnRatio[1]);
								styleMap.put(threshold, ratio);
							}catch (Exception e) {
								//TODO implement
							}

						}
					}
				}
			}
		}

		return styleMap;
	}

	public Integer getThresholdRatio(Threshold threshold){
		if(styleMap == null) {
			extractStyles(getStyleName());
		}
		if(!styleMap.containsKey(threshold)) return 0;
		return styleMap.get(threshold);
	}
	public String generateStyleString(int xlRatio, int lgRatio, int mdRatio, int smRatio, int xsRatio, String additionalStyles)
	{

		return  "col-lg-" + (lgRatio > 12
				? 12
				: lgRatio) + " col-md-"
				+ (mdRatio > 12
				? 12
				: mdRatio)
				+ " col-sm-" + (smRatio > 12
				? 12
				: smRatio)
				+ " col-xs-"  + (xsRatio > 12
				? 12
				: xsRatio)+ " " + additionalStyles;
	}

}
