
import javafx.scene.Node;
import javafx.scene.Parent;

public class ScaleNodes {
    public final static double scalingFactor = 1;

    public static void setScale(Parent node, String styleClassStr) {
        setScaleDeep(node, scalingFactor, styleClassStr);

        node.setScaleY(1);
        node.setScaleX(1);
    }
    public static void resetScale(Parent node) {
        node.setScaleX(1);
        node.setScaleY(1);

        if (!node.getChildrenUnmodifiable().isEmpty()) {
            for (Node nodes: node.getChildrenUnmodifiable()) {
                try {
                    Parent parent = (Parent) nodes;
                    resetScale(parent);
                } catch (ClassCastException e) {
                    System.out.println("Node skipped");
                }
            }
        }
    }

    private static void setScaleDeep(Parent node, final double scalingFactor, String styleClassStr) {

        for (String styleClass : node.getStyleClass()) {
            if (styleClass.equals(styleClassStr)) {
                node.setScaleX(scalingFactor);
                node.setScaleY(scalingFactor);
                break;
            }
        }

        if (!node.getChildrenUnmodifiable().isEmpty()) {
            for (Node nodes: node.getChildrenUnmodifiable()) {
                try {
                    Parent parent = (Parent) nodes;
                    setScaleDeep(parent, scalingFactor, styleClassStr);
                } catch (ClassCastException e) {
                    System.out.println("Node skipped");
                }
            }
        }
    }
}
