import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;

public class rainbow {
	private JFrame frame;
	private JFrame frame2;
	private myComponent component1;
	private myComponent2 component2;
	private Color magnifiedColor;

	public rainbow(int width, int height) {
		frame = new JFrame("Rainbow");
		component1 = new myComponent();
		frame.add(component1);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setVisible(true);
		frame2 = new JFrame("Zoom");
		component2 = new myComponent2();
		frame2.add(component2);
		frame2.setLocation(width, 0);
		frame2.setSize(100, 100);
		frame2.setResizable(true);
		frame2.setVisible(true);
	}

	class myComponent2 extends Component {
		private static final long serialVersionUID = -1076426184653991817L;
		public myComponent2() {
			magnifiedColor = Color.white;
		}
		public void paint(Graphics g) {
			frame2.setLocation(frame.getWidth(), 0);
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(magnifiedColor);
			g2.fill(new Rectangle2D.Float(0, 0, this.getWidth(), this
					.getHeight()));
		}
	}

	class myComponent extends Component implements MouseMotionListener {
		private static final long serialVersionUID = 3196502682326900862L;

		public myComponent() {
			addMouseMotionListener(this);
		}

		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			for (int i = 0; i <= this.getWidth(); ++i) {
				g2.setColor(getColor((float) i / this.getWidth()));
				g2.fill(new Rectangle2D.Float(i, 0, 1, this.getHeight() / 2));
				g2.setColor(getColor2((float) i / this.getWidth()));
				g2.fill(new Rectangle2D.Float(i, this.getHeight() / 2, 1, this
						.getHeight() / 2));
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			int x = e.getX();
			int y = e.getY();
			Color c;
			if (y < this.getHeight() / 2) {
				c = getColor((float) x / this.getWidth());
			} else if (y >= this.getHeight() / 2) {
				c = getColor2((float) x / this.getWidth());
			} else {
				System.out.println("Out of range");
				return;
			}
			System.out.println("RGB - " + c.getRed() + " " + c.getGreen() + " "
					+ c.getBlue());
			magnifiedColor = c;
			component2.repaint();
		}
	}

	public Color mixColors(Color c0, Color c1, float p) {
		while (p > 1.0f) {
			p -= 1.0f;
		}
		// System.out.println("p = "+p);
		int red = (int) (c0.getRed() * (1.0f - p) + c1.getRed() * p);
		int green = (int) (c0.getGreen() * (1.0f - p) + c1.getGreen() * p);
		int blue = (int) (c0.getBlue() * (1.0f - p) + c1.getBlue() * p);
		// System.out.println("RGB = " + red + " " + green + " " + blue);
		return new Color(red, green, blue);
	}

	public Color mixColors2(Color c0, Color c1, float p) {
		while (p > 1.0f) {
			p -= 1.0f;
		}
		// System.out.println("p = " + p);
		int red, green, blue;
		if (p < 0.5f) {
			red = (int) (c0.getRed() + c1.getRed() * p * 2);
			green = (int) (c0.getGreen() + c1.getGreen() * p * 2);
			blue = (int) (c0.getBlue() + c1.getBlue() * p * 2);
		} else {
			red = (int) (c0.getRed() * (1 - 2 * (p - 0.5f)) + c1.getRed());
			green = (int) (c0.getGreen() * (1 - 2 * (p - 0.5f)) + c1.getGreen());
			blue = (int) (c0.getBlue() * (1 - 2 * (p - 0.5f)) + c1.getBlue());
		}
		// System.out.println("RGB = " + red + " " + green + " " + blue);
		return new Color(red, green, blue);
	}

	public Color getColor(float x) {
		float p = x * 3.0f;
		// System.out.println("x = "+x);
		if (x <= 1.0f / 3.0f)
			return mixColors(Color.red, Color.green, p);
		else if (x <= 2.0f / 3.0f)
			return mixColors(Color.green, Color.blue, p);
		else
			return mixColors(Color.blue, Color.red, p);
	}

	public Color getColor2(float x) {
		float p = x * 3.0f;
		// System.out.println("x = "+x);
		if (x <= 1.0f / 3.0f)
			return mixColors2(Color.red, Color.green, p);
		else if (x <= 2.0f / 3.0f)
			return mixColors2(Color.green, Color.blue, p);
		else
			return mixColors2(Color.blue, Color.red, p);
	}

}
