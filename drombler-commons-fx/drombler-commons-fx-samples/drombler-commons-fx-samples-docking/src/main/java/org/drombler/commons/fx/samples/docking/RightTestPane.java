
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.commons.fx.samples.docking;


import java.util.EnumMap;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.SetChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.drombler.commons.context.ActiveContextSensitive;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.ContextEvent;
import org.drombler.commons.context.ContextListener;
import org.drombler.commons.context.SimpleContext;
import org.drombler.commons.context.SimpleContextContent;
import org.drombler.commons.fx.docking.DockablePane;


public class RightTestPane extends DockablePane implements ActiveContextSensitive {

    private static final String FXML_EXTENSION = ".fxml";

    private final SimpleContextContent contextContent = new SimpleContextContent();
    private final SimpleContext context = new SimpleContext(contextContent);
    private Context activeContext;
    @FXML
    private Label nameLabel;
    @FXML
    private ImageView coloredCircleImageView;
    @FXML
    private ImageView redRectangleImageView;
    @FXML
    private ImageView yellowRectangleImageView;
    @FXML
    private ImageView blueRectangleImageView;
    private final Map<ColoredRectangle, ImageView> coloredRectangleImageViews = new EnumMap<>(ColoredRectangle.class);
    private final ColoredCircleChangeListener coloredCircleChangeListener = new ColoredCircleChangeListener();
    private final ColoredRectanglesSetChangeListener coloredRectanglesSetChangeListener = new ColoredRectanglesSetChangeListener();
    private Sample sample;

//    public RightTestPane() throws IOException {
//        setContext(context);
//        loadFXML();
//        initColoredRectangleImageViewsMap();
//    }

//    private void loadFXML() throws IOException {
//        FXMLLoaders.loadRoot(this);
//    }

    private void initColoredRectangleImageViewsMap() {
        coloredRectangleImageViews.put(ColoredRectangle.RED, redRectangleImageView);
        coloredRectangleImageViews.put(ColoredRectangle.YELLOW, yellowRectangleImageView);
        coloredRectangleImageViews.put(ColoredRectangle.BLUE, blueRectangleImageView);
    }

    @Override
    public void setActiveContext(Context activeContext) {
        this.activeContext = activeContext;
        this.activeContext.addContextListener(Sample.class, new ContextListener() {
            @Override
            public void contextChanged(ContextEvent event) {
                RightTestPane.this.contextChanged();
            }
        });
        contextChanged();
    }

    private void contextChanged() {
        Sample newSample = activeContext.find(Sample.class);
        if ((sample == null && newSample != null) || (sample != null && !sample.equals(newSample))) {
            if (sample != null) {
                unregister();
            }
            sample = newSample;
            if (sample != null) {
                register();
            }
        }
    }

    private void unregister() {
        contextContent.remove(sample);

        nameLabel.textProperty().unbind();
        nameLabel.setText(null);

        sample.coloredCircleProperty().removeListener(coloredCircleChangeListener);
        coloredCircleImageView.setImage(null);

        sample.getColoredRectangles().removeListener(coloredRectanglesSetChangeListener);
        resetColoredRectangleImageViews();
    }

    private void register() {
        nameLabel.textProperty().bind(sample.nameProperty());

        sample.coloredCircleProperty().addListener(coloredCircleChangeListener);
        configureColoredCircleImageView(sample.getColoredCircle());

        initColoredRectangleImageViews();
        sample.getColoredRectangles().addListener(coloredRectanglesSetChangeListener);

        contextContent.add(sample);
    }

    private void configureColoredCircleImageView(ColoredCircle coloredCircle) {
        coloredCircleImageView.setImage(coloredCircle.getImage());
    }

    private void setColoredRectangleImage(ColoredRectangle coloredRectangle) {
        coloredRectangleImageViews.get(coloredRectangle).setImage(coloredRectangle.getImage());
    }

    private void initColoredRectangleImageViews() {
        for (ColoredRectangle coloredRectangle : ColoredRectangle.values()) {
            if (sample.getColoredRectangles().contains(coloredRectangle)) {
                setColoredRectangleImage(coloredRectangle);
            }
        }
    }

    private void resetColoredRectangleImageViews() {
        for (ColoredRectangle coloredRectangle : ColoredRectangle.values()) {
            coloredRectangleImageViews.get(coloredRectangle).setImage(null);
        }
    }

    private class ColoredCircleChangeListener implements ChangeListener<ColoredCircle> {

        @Override
        public void changed(ObservableValue<? extends ColoredCircle> ov, ColoredCircle oldeValue, ColoredCircle newValue) {
            configureColoredCircleImageView(newValue);
        }
    }

    private class ColoredRectanglesSetChangeListener implements SetChangeListener<ColoredRectangle> {

        @Override
        public void onChanged(SetChangeListener.Change<? extends ColoredRectangle> change) {
            if (change.wasAdded()) {
                setColoredRectangleImage(change.getElementAdded());
            } else if (change.wasRemoved()) {
                coloredRectangleImageViews.get(change.getElementRemoved()).setImage(null);
            }
        }
    }
}
