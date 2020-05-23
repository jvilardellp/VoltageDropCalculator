//Package of the class.
package CustomClasses;

//Importing classes required from the API of JAVA.
import javax.swing.*;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.text.View;
import java.awt.*;
import java.util.Arrays;
import java.awt.Font;

public class CustomTabbedPaneUI extends BasicTabbedPaneUI
{

    private Color selectColor;
    private Color deSelectColor;
    private int inclTab = 4;
    private int anchoFocoH = 10;
    private int anchoCarpetas = 20;
    private Polygon shape;
    public  Color colorContentBorder = selectColor;

    @Override
    protected void installDefaults()
    {
        super.installDefaults();
        selectColor = new Color(250, 192, 192);
        deSelectColor = new Color(197, 193, 168);
        tabAreaInsets.right = anchoCarpetas;
    }

    @Override
    protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex)
    {

        if (runCount > 1)
        {
            int lines[] = new int[runCount];
            for (int i = 0; i < runCount; i++)
            {
                lines[i] = rects[tabRuns[i]].y + (tabPlacement == TOP ? maxTabHeight : 0);
            }
            Arrays.sort(lines);
            if (tabPlacement == TOP)
            {
                int fila = runCount;
                for (int i = 0; i < lines.length - 1; i++, fila--)
                {
                    Polygon carp = new Polygon();
                    carp.addPoint(0, lines[i]);
                    carp.addPoint(tabPane.getWidth() - 2 * fila - 2, lines[i]);
                    carp.addPoint(tabPane.getWidth() - 2 * fila, lines[i] + 3);
                    if (i < lines.length - 2)
                    {
                        carp.addPoint(tabPane.getWidth() - 2 * fila, lines[i + 1]);
                        carp.addPoint(0, lines[i + 1]);
                    }
                    else
                    {
                        carp.addPoint(tabPane.getWidth() - 2 * fila, lines[i] + rects[selectedIndex].height);
                        carp.addPoint(0, lines[i] + rects[selectedIndex].height);
                    }
                    carp.addPoint(0, lines[i]);
                    g.setColor(hazAlfa(fila));
                    g.fillPolygon(carp);
                    g.setColor(darkShadow.darker());
                    g.drawPolygon(carp);
                }
            }
            else
            {
                int fila = 0;
                for (int i = 0; i < lines.length - 1; i++, fila++)
                {
                    Polygon carp = new Polygon();
                    carp.addPoint(0, lines[i]);
                    carp.addPoint(tabPane.getWidth() - 2 * fila - 1, lines[i]);
                    carp.addPoint(tabPane.getWidth() - 2 * fila - 1, lines[i + 1] - 3);
                    carp.addPoint(tabPane.getWidth() - 2 * fila - 3, lines[i + 1]);
                    carp.addPoint(0, lines[i + 1]);
                    carp.addPoint(0, lines[i]);
                    g.setColor(hazAlfa(fila + 2));
                    g.fillPolygon(carp);
                    g.setColor(darkShadow.darker());
                    g.drawPolygon(carp);
                }
            }
        }
        super.paintTabArea(g, tabPlacement, selectedIndex);
    }

    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
                                      boolean isSelected)
    {
        Graphics2D g2D = (Graphics2D) g;
        GradientPaint gradientShadow;
        int xp[] = null; // Para la forma
        int yp[] = null;
        switch (tabPlacement)
        {
            case LEFT:
                xp = new int[]{x, x, x + w, x + w, x};
                yp = new int[]{y, y + h - 3, y + h - 3, y, y};
                gradientShadow = new GradientPaint(x, y, new Color(100, 100, 255), x, y + h, new Color(153, 186, 243));
                break;
            case RIGHT:
                xp = new int[]{x, x, x + w - 2, x + w - 2, x};
                yp = new int[]{y, y + h - 3, y + h - 3, y, y};
                gradientShadow = new GradientPaint(x, y, new Color(100, 100, 255), x, y + h, new Color(153, 186, 243));
                break;
            case BOTTOM:
                xp = new int[]{x, x, x + 3, x + w - inclTab - 6, x + w - inclTab - 2, x + w - inclTab, x + w - 3, x};
                yp = new int[]{y, y + h - 3, y + h, y + h, y + h - 1, y + h - 3, y, y};
                gradientShadow = new GradientPaint(x, y, new Color(100, 100, 255), x, y + h, new Color(153, 186, 243));
                break;
            case TOP:
            default:
                xp = new int[]{x, x, x + 0, x + w - inclTab - 0, x + w - inclTab - 0, x + w - inclTab, x + w - inclTab,
                        x};
                yp = new int[]{y + h-3, y + 3, y, y, y + 1, y + 3, y + h-3, y + h-3};
                gradientShadow = new GradientPaint(0, 0, Color.DARK_GRAY, 0, y + h / 2, Color.DARK_GRAY);
                break;
        }
        // ;
        shape = new Polygon(xp, yp, xp.length);
        if (isSelected)
        {
            g2D.setColor(selectColor);
            g2D.setPaint(gradientShadow);
        }
        else
        {
            if (tabPane.isEnabled() && tabPane.isEnabledAt(tabIndex))
            {
                g2D.setColor(deSelectColor);
                GradientPaint gradientShadowTmp = new GradientPaint(0, 0, Color.DARK_GRAY, 0, y + h / 2,
                        Color.DARK_GRAY);
                g2D.setPaint(gradientShadowTmp);
            }
            else
            {
                GradientPaint gradientShadowTmp = new GradientPaint(0, 0, new Color(240, 255, 210), 0, y + 15 + h / 2,
                        new Color(204, 204, 204));
                g2D.setPaint(gradientShadowTmp);
            }
        }
        selectColor = new Color(255, 255, 200);
        deSelectColor = new Color(240, 255, 210);
        g2D.fill(shape);
        if (runCount > 1)
        {
            g2D.setColor(hazAlfa(getRunForTab(tabPane.getTabCount(), tabIndex) - 1));
            g2D.fill(shape);
        }
        g2D.fill(shape);
    }

    @Override
    protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title,
                             Rectangle textRect, boolean isSelected)
    {
        font=new Font("Arial", Font.PLAIN, 14);
        super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
        g.setFont(font);

        View v = getTextViewForTab(tabIndex);
        if (v != null)
        {
            // html
            v.paint(g, textRect);
        }
        else
        {
            // plain text
            int mnemIndex = tabPane.getDisplayedMnemonicIndexAt(tabIndex);

            if (tabPane.isEnabled() && tabPane.isEnabledAt(tabIndex))
            {
                if(isSelected)
                {
                    //g.setColor(tabPane.getForegroundAt(tabIndex));
                    g.setColor(new Color(118, 157, 255));
                    BasicGraphicsUtils.drawStringUnderlineCharAt(g, title, mnemIndex, textRect.x, textRect.y +
                            metrics.getAscent());
                }
                else
                {
                    //g.setColor(tabPane.getForegroundAt(tabIndex));
                    g.setColor(Color.LIGHT_GRAY);
                    BasicGraphicsUtils.drawStringUnderlineCharAt(g, title, mnemIndex, textRect.x, textRect.y +
                            metrics.getAscent());
                }
            }
            else
            { // tab disabled
                g.setColor(Color.BLACK);
                BasicGraphicsUtils.drawStringUnderlineCharAt(g, title, mnemIndex, textRect.x, textRect.y +
                        metrics.getAscent());
                g.setColor(tabPane.getBackgroundAt(tabIndex).darker());
                BasicGraphicsUtils.drawStringUnderlineCharAt(g, title, mnemIndex, textRect.x - 1, textRect.y +
                        metrics.getAscent() - 1);
            }

        }
    }

    @Override
    protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics)
    {
        return 20 + inclTab + super.calculateTabWidth(tabPlacement, tabIndex, metrics);
    }

    @Override
    protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight)
    {
        if (tabPlacement == LEFT || tabPlacement == RIGHT)
        {
            return super.calculateTabHeight(tabPlacement, tabIndex, fontHeight);
        }
        else
            {
            return anchoFocoH + super.calculateTabHeight(tabPlacement, tabIndex, fontHeight);
        }
    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
                                  boolean isSelected)
    {
        Graphics2D g2D = (Graphics2D)g;
        int xp[] = null; // Para la forma
        int yp[] = null;

        switch( tabPlacement )
        {
            case LEFT:
            case RIGHT:
            case BOTTOM:
            /* codigo para estos tabs */
                break;

            case TOP:
            default:
                xp = new int[]
                {
                        x+3,
                        x+3,
                        x+w-3,
                        x+w-3,
                        x+3



                };
                yp = new int[]
                {
                        y+3,
                        y+h-3,
                        y+h-3,
                        y+3,
                        y+3

                };
                break;
        }

        shape = new Polygon( xp, yp, xp.length );

        if ( isSelected )
        {
            g2D.setColor( new Color(60,127,177) );
            g2D.drawPolyline( xp , yp , xp.length);
        }
        else
        {
            g2D.setColor( new Color(137,140,149) );
            g2D.drawPolyline( xp , yp , xp.length );
        }
    }

    @Override
    protected void paintContentBorder(Graphics g,int tabPlacement,int selectedIndex)
    {
        int width = tabPane.getWidth();
        int height = tabPane.getHeight();
        Insets insets = tabPane.getInsets();

        int x = insets.left;
        int y = insets.top;
        int w = width - insets.right - insets.left;
        int h = height - insets.top - insets.bottom;

        switch(tabPlacement)
        {
            case LEFT:
                x += calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
                x -= tabAreaInsets.right;
                w -= (x - insets.left);
                break;
            case RIGHT:
                w -= calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
                w += tabAreaInsets.left;
                break;
            case BOTTOM:
                h -= calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
                h += tabAreaInsets.top;
                break;
            case TOP:
            default:
                y += calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
                y -= tabAreaInsets.bottom;
                h -= (y - insets.top);
        }

        if ( tabPane.getTabCount() > 0  )
        {
            //Color color = UIManager.getColor("TabbedPane.contentAreaColor");
            Color color=Color.DARK_GRAY;
            if (color != null) {
                g.setColor(color);
            }
            else if ( colorContentBorder == null || selectedIndex == -1 ) {
                g.setColor(tabPane.getBackground());
            }
            else {
                g.setColor(colorContentBorder);
            }
            g.fillRect(x,y,w,h);
        }
    }

    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex,
                                       Rectangle iconRect, Rectangle textRect, boolean isSelected) {
        if (tabPane.hasFocus() && isSelected)
        {
            g.setColor(UIManager.getColor("ScrollBar.thumbShadow"));
            g.drawPolygon(shape);
        }
    }

    protected Color hazAlfa(int fila)
    {
        int alfa = 0;
        if (fila >= 0) {
            alfa = 50 + (fila > 7 ? 70 : 10 * fila);
        }
        return new Color(0, 0, 0, alfa);
    }
}


