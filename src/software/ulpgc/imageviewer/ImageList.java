package software.ulpgc.imageviewer;

import java.util.ArrayList;
import java.util.List;

public class ImageList {
    private final List<String> ids = new ArrayList<>();

    public ImageList(List<String> initial) {
        ids.addAll(initial);
    }

    public int add(List<String> newIds) {
        int first = ids.size();
        ids.addAll(newIds);
        return first;
    }

    public boolean isEmpty() {
        return ids.isEmpty();
    }

    public Image imageAt(int i) {
        return new Image() {
            @Override
            public String id() {
                return ids.get(i);
            }

            @Override
            public Image next() {
                return imageAt((i + 1) % ids.size());
            }

            @Override
            public Image prev() {
                return imageAt(i > 0 ? i - 1 : ids.size() - 1);
            }
        };
    }
}
