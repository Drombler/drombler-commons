
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.commons.fx.samples.docking;


import org.drombler.commons.fx.docking.DockablePane;



public class SampleEditorPane extends DockablePane {

//    private final SimpleContextContent contextContent = new SimpleContextContent();
//    private final SimpleContext context = new SimpleContext(contextContent);
//    private final Sample sample;
//    @FXML
//    private TextField nameField;
//    @FXML
//    private ImageView coloredCircleImageView;
//    @FXML
//    private ImageView redRectangleImageView;
//    @FXML
//    private ImageView yellowRectangleImageView;
//    @FXML
//    private ImageView blueRectangleImageView;
//    private final Map<ColoredRectangle, ImageView> coloredRectangleImageViews = new EnumMap<>(ColoredRectangle.class);
//    private ObjectProperty<ColoredCircle> coloredCircle = new SimpleObjectProperty<>(this, "coloredCircle");
//    private final ObservableSet<ColoredRectangle> coloredRectangles = FXCollections.observableSet(EnumSet.noneOf(
//            ColoredRectangle.class));
//
//    public SampleEditorPane(Sample sample) throws IOException {
//        // Set a writeable context
//        setContext(context);
//
//        loadFXML();
//        this.sample = sample;
//
//        // Add the sample to the context, so Views can see it
//        contextContent.add(sample);
//
//        // Add a ColoredCircleManager to the context to enable the ColoredCircle actions.
//        contextContent.add(new ColoredCircleManager() {
//            @Override
//            public ColoredCircle getColoredCircle() {
//                return coloredCircle.get();
//            }
//
//            @Override
//            public void setColoredCircle(ColoredCircle coloredCircle) {
//                SampleEditorPane.this.coloredCircle.set(coloredCircle);
//                coloredCircleImageView.setImage(coloredCircle.getImage());
//            }
//        });
//
//        // Add a ColoredRectangleManager to the context to enable the ColoredRectangle actions.
//        contextContent.add(new ColoredRectangleManager() {
//            @Override
//            public ObservableSet<ColoredRectangle> getColoredRectangles() {
//                return coloredRectangles;
//            }
//        });
//
//        initColoredRectangleImageViewsMap();
//
//        nameField.setText(sample.getName());
//        coloredCircle.set(sample.getColoredCircle());
//        coloredCircleImageView.setImage(sample.getColoredCircle().getImage());
//        coloredRectangles.addAll(sample.getColoredRectangles());
//        initColoredRectangleImageViews();
//
//        titleProperty().bind(nameField.textProperty());
//
//        // Mark this Editor as modified if any control has been modified
//        nameField.textProperty().addListener(new ModifiedListener());
//        coloredCircle.addListener(new ModifiedListener());
//        coloredRectangles.addListener(new SetChangeListener<ColoredRectangle>() {
//            @Override
//            public void onChanged(Change<? extends ColoredRectangle> change) {
//                if (change.wasAdded()) {
//                    setColoredRectangleImage(change.getElementAdded());
//                } else if (change.wasRemoved()) {
//                    coloredRectangleImageViews.get(change.getElementRemoved()).setImage(null);
//                }
//                markModified();
//            }
//        });
//
//    }
//
//    private void loadFXML() throws IOException {
//        FXMLLoaders.loadRoot(this);
//    }
//
//    private void initColoredRectangleImageViewsMap() {
//        coloredRectangleImageViews.put(ColoredRectangle.RED, redRectangleImageView);
//        coloredRectangleImageViews.put(ColoredRectangle.YELLOW, yellowRectangleImageView);
//        coloredRectangleImageViews.put(ColoredRectangle.BLUE, blueRectangleImageView);
//    }
//
//    public Sample getSample() {
//        return sample;
//    }
//
//
//    private void setColoredRectangleImage(ColoredRectangle coloredRectangle) {
//        coloredRectangleImageViews.get(coloredRectangle).setImage(coloredRectangle.getImage());
//    }
//
//    private void initColoredRectangleImageViews() {
//        for (ColoredRectangle coloredRectangle : ColoredRectangle.values()) {
//            if (coloredRectangles.contains(coloredRectangle)) {
//                setColoredRectangleImage(coloredRectangle);
//            }
//        }
//    }

    
}
