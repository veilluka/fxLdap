package gui;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.AbstractPropertyEditor;

public class NumberSliderEditor extends AbstractPropertyEditor<Number, Slider> {

    public NumberSliderEditor(PropertySheet.Item property, Slider control)
    {
        super(property, control);
    }

    public NumberSliderEditor(PropertySheet.Item item)
    {
        this(item, new Slider());
    }

    @Override
    public void setValue(Number n) {
        this.getEditor().setValue(n.doubleValue());
    }

    @Override
    protected ObservableValue<Number> getObservableValue() {
        return this.getEditor().valueProperty();
    }

}