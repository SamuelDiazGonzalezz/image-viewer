package software.ulpgc.imageviewer.swing;

import software.ulpgc.imageviewer.ImageList;
import software.ulpgc.imageviewer.ImagePresenter;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        ImagePresenter presenter = new ImagePresenter(frame.getImageDisplay());
        ImageList images = new ImageList(List.of("red", "green", "blue", "yellow", "orange"));
        presenter.show(images.imageAt(0));
        frame.onPrev(presenter::prev);
        frame.onNext(presenter::next);
        frame.onAdd(files -> {
            int first = images.add(files.stream().map(File::getAbsolutePath).toList());
            presenter.show(images.imageAt(first));
        });
        frame.setVisible(true);
    }
}
