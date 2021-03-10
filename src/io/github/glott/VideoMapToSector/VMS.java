package io.github.glott.VideoMapToSector;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.WindowEvent;
import java.io.File;

public class VMS
{
    private static JFrame frame;
    protected JPanel panel;
    protected JButton inButton;
    protected JButton outButton;
    protected JTextField outDirectory;
    protected JTextField inDirectory;
    protected JButton convertButton;
    protected JButton closeButton;
    protected JTextField outputLines;
    protected JTextField outputMS;

    private File fileIn;
    private File fileOut;

    public VMS()
    {
        inButton.addActionListener(e ->
        {
            fileIn = chooseFile(false);
            if (fileIn != null) inDirectory.setText(fileIn.getAbsolutePath());
        });

        outButton.addActionListener(e ->
        {
            fileOut = chooseFile(true);
            if (fileOut != null) outDirectory.setText(fileOut.getAbsolutePath());
        });

        closeButton.addActionListener(e ->
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));

        convertButton.addActionListener(e ->
        {
            MapToSector mts = new MapToSector();
            String fin = mts.convert(fileIn, fileOut);
            outputLines.setText(fin.split(" ")[0]);
            outputMS.setText(fin.split(" ")[1]);
        });

    }

    public static void main(String[] args)
    {
        frame = new JFrame("Video Map To Sector File");
        frame.setContentPane(new VMS().panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private File chooseFile(boolean directory)
    {
        JFileChooser fc = new JFileChooser();
        if (fileIn != null) fc.setCurrentDirectory(fileIn.getParentFile());
        else if (fileOut != null) fc.setCurrentDirectory(fileOut.getAbsoluteFile());
        else fc.setCurrentDirectory(new File(System.getProperty("user.home") + File.separator + "Documents"));
        if (directory) fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        else
        {
            fc.setFileFilter(new FileNameExtensionFilter("XML File (*.xml)", "xml"));
            fc.setAcceptAllFileFilterUsed(false);
        }
        int choice = fc.showOpenDialog(frame);
        if (choice == JFileChooser.APPROVE_OPTION) return fc.getSelectedFile();
        return null;
    }
}
