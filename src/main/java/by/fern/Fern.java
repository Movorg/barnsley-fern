package by.fern;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;

class Fern extends JFrame {

    int counter;
    static List<ColorPoint> points;
    static Color pointColor;
    static JSlider pointSize;
    static JSlider speedThread;
    static DrawingPane pane;

    JLabel jCounter = new JLabel(String.valueOf(0));

    void count() {
        String str;
        counter += 1;
        str = Integer.toString(counter);
        jCounter.setText(str);
    }

    StartGame startGame;

    Fern() {
        setTitle("Barnsley Fern");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(900, 800));

        points = new CopyOnWriteArrayList<>();
        pointColor = Color.GREEN;
        pane = new DrawingPane();

        JPanel topPanel = new JPanel();
        JButton colorButton = new JButton();
        colorButton.setBackground(pointColor);
        colorButton.addActionListener(e -> {
            pointColor = JColorChooser.showDialog(Fern.this,
                    "Choose a color:", pointColor);
            colorButton.setBackground(pointColor);
        });

        colorButton.setPreferredSize(new Dimension(30, 30));
        pointSize = new JSlider(1, 3, 1);
        pointSize.setPreferredSize(new Dimension(80, 30));
        speedThread = new JSlider(-50, -1, -50);
        speedThread.setPreferredSize(new Dimension(80, 30));

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            startGame = new StartGame();
            Thread thr = new Thread(startGame);
            thr.start();
        });

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> {
            startGame.stop();
            points.clear();
            pane.repaint();
        });

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e -> startGame.suspended());

        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(e -> startGame.resume());

        topPanel.add(new JLabel("Color:"));
        topPanel.add(colorButton);
        topPanel.add(new JLabel("Point size:"));
        topPanel.add(pointSize);
        topPanel.add(new JLabel("Speed:"));
        topPanel.add(speedThread);
        topPanel.add(startButton);
        topPanel.add(stopButton);
        topPanel.add(pauseButton);
        topPanel.add(continueButton);
        topPanel.add(new JLabel("Number of points:"));
        topPanel.add(jCounter);
        add(pane);
        add(topPanel, BorderLayout.NORTH);
        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Fern().setVisible(true));
    }

    static class DrawingPane extends JPanel {
        public DrawingPane() {
            setBorder(new LineBorder(Color.GRAY));
            setBackground(new Color(22,27,34));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for (ColorPoint p : points) {
                g2.setColor(p.getColor());
                int x = p.getX();
                int y = p.getY();
                int size = p.getSize();
                g2.fillRect(x, y, size, size);
            }
        }
    }

    static class ColorPoint {
        private final int x;
        private final int y;
        private final int size;
        private final Color color;

        public ColorPoint(int x, int y, int size, Color color) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.color = color;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Color getColor() {
            return color;
        }

        public int getSize() {
            return size;
        }
    }

    class StartGame extends JComponent implements Runnable {

        volatile boolean suspend;
        volatile boolean stop;

        synchronized void suspended() {
            suspend = true;
        }

        synchronized void resume() {
            suspend = false;
            notify();
        }

        synchronized void stop() {
            stop = true;
            counter = -1;
        }

        StartGame() {
            suspend = false;
            stop = false;
        }

        @Override
        public void run() {

            double x = 0;
            double y = 0;

            try {
                for (int i = 0; i < 50000; i++) {
                    double nextX, nextY;
                    count();
                    synchronized (this) {
                        while (suspend) {
                            JOptionPane.showMessageDialog(null,
                                    "The stream is paused!",
                                    "Message!",
                                    JOptionPane.WARNING_MESSAGE);
                            wait();
                        }
                        if (stop) break;
                    }
                    double p = Math.random();
                    if (p <= 0.01) {
                        nextX = 0;
                        nextY = 0.16 * y;
                    } else if (p <= 0.85) {
                        nextX = 0.85 * x + 0.04 * y;
                        nextY = -0.04 * x + 0.85 * y + 1.6;
                    } else if (p <= 0.93) {
                        nextX = 0.2 * x - 0.26 * y;
                        nextY = 0.23 * x + 0.22 * y + 1.6;
                    } else {
                        nextX = -0.15 * x + 0.28 * y;
                        nextY = 0.26 * x + 0.24 * y + 0.44;
                    }

                    x = nextX;
                    y = nextY;

                    ColorPoint point = new Fern.ColorPoint((int) ((x + 3) * 700 / 5), (int) (700 - y * 700 / 11),
                            Fern.pointSize.getValue(), Fern.pointColor);
                    points.add(point);
                    pane.repaint();
                    Thread.sleep(Fern.speedThread.getValue() * (-1));
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null,
                    "The program has finished working!",
                    "Message!",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}

