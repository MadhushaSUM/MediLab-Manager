

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class ButtonAnimation {
    public static SequentialTransition transitionByFading(final Button btn) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(100), btn);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(100), btn);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        SequentialTransition seqTransition = new SequentialTransition();
        seqTransition.getChildren().addAll(fadeOut, fadeIn);
        return seqTransition;
    }
}
