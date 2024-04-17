/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hvan.qlkh.views;

import hvan.qlkh.models.Product;
import hvan.qlkh.utils.Panel;
import hvan.qlkh.utils.ScrollBar;
import java.awt.Color;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import static javax.swing.ScrollPaneConstants.UPPER_RIGHT_CORNER;

/**
 *
 * @author PC
 */
public class ProductTemplate extends javax.swing.JPanel {

    /**
     * Creates new form ProductTemplate
     */
    
    private Product product;

    @SuppressWarnings("deprecation")
    Locale vn = new Locale("vi", "VN");
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(vn);
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    private void initScroll(){
        scroll.setBorder(null);
        scroll.setVerticalScrollBar(new ScrollBar());
        scroll.getVerticalScrollBar().setBackground(Color.WHITE);
        scroll.getViewport().setBackground(Color.WHITE);
        JPanel p = new JPanel();
        p.setBackground(new Color(246,251,249));
        scroll.setCorner(UPPER_RIGHT_CORNER, p);
    }

    private void setData(){
        if (product != null){
            ProductTemplate__Name.setText("<html><div style=\"width: 140px; text-align: center; color: #32403B; font-size: 18px; font-family: Karla; font-weight: 500; line-height: 20px; word-wrap: break-word\">" + product.getName() +"</div></html>");
            ProductTemplate__ID.setText("<html><div style=\"width: 80px; text-align: left; color:#D43A02; font-size: 12px; font-family: Karla; font-weight: 500; line-height: 15px; word-wrap: break-word\">" + product.getId() + "</div></htm");
            ProductTemplate__Quantity.setText("<html><div style=\"width: 80px; text-align: left; color:#94D82D; font-size: 12px; font-family: Karla; font-weight: 500; line-height: 15px; word-wrap: break-word\">" + product.getQuantity() + "</div></htm");
            ProductTemplate__Price.setText("<html><div style=\"width: 80px; text-align: left; color:orange; font-size: 12px; font-family: Karla; font-weight: 500; line-height: 15px; word-wrap: break-word\">" + currencyFormat.format(product.getPrice()) + "</div></htm");
            ProductTemplate__Expiry.setText("<html><div style=\"width: 80px; text-align: left; color:#CC5DE8; font-size: 12px; font-family: Karla; font-weight: 500; line-height: 15px; word-wrap: break-word\">" + dateFormat.format(product.getExpDate()) + "</div></htm");
            ProductTemplate__Category.setText("<html><div style=\"width: 80px; text-align: left; color:#5C7CFA; font-size: 10px; font-family: Karla; font-weight: 500; line-height: 15px; word-wrap: break-word\">" + product.getCategory() + "</div></htm");
            ProductTemplate__Manafacturer.setText("<html><div style=\"width: 80px; text-align: left; color:#FA5252; font-size: 10px; font-family: Karla; font-weight: 500; line-height: 15px; word-wrap: break-word\">" + product.getManafacturer() + "</div></htm");
            if (product.getThumbnail() != null){
                if (!product.getThumbnail().equals("")){
                    try {
                        ProductTemplate__Thumbnail.setIcon(new ImageIcon(product.getThumbnail()));
                    } catch (Exception e) {
                    }
                }
            }
            ProductTemplate__Description.setText(product.getDescription());
        }
    }
    
    public ProductTemplate(Product product) {
        this.product = product;
        initComponents();
        initScroll();
        setData();
        ProductTemplate__Description.setCaretPosition(0);
        scroll.getVerticalScrollBar().setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ProductTemplate = new Panel(35);
        ProductTemplate__ID = new javax.swing.JLabel();
        ProductTemplate__Thumbnail = new javax.swing.JLabel();
        ProductTemplate__Price = new javax.swing.JLabel();
        ProductTemplate__Name = new javax.swing.JLabel();
        ProductTemplate__IDTitle = new javax.swing.JLabel();
        ProductTemplate__PriceTitle = new javax.swing.JLabel();
        ProductTemplate__ExpiryTitle = new javax.swing.JLabel();
        ProductTemplate__Expiry = new javax.swing.JLabel();
        ProductTemplate__QuantityTitle = new javax.swing.JLabel();
        ProductTemplate__Quantity = new javax.swing.JLabel();
        scroll = new javax.swing.JScrollPane();
        ProductTemplate__Description = new javax.swing.JTextPane();
        ProductTemplate__ExpiryTitle1 = new javax.swing.JLabel();
        ProductTemplate__ExpiryTitle2 = new javax.swing.JLabel();
        ProductTemplate__Category = new javax.swing.JLabel();
        ProductTemplate__Manafacturer = new javax.swing.JLabel();
        ProductTemplate__ExpiryTitle3 = new javax.swing.JLabel();

        setOpaque(false);

        ProductTemplate.setBackground(new java.awt.Color(255, 255, 255));
        ProductTemplate.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        ProductTemplate.setPreferredSize(new java.awt.Dimension(350, 400));

        ProductTemplate__ID.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        ProductTemplate__ID.setForeground(new java.awt.Color(101, 168, 68));
        ProductTemplate__ID.setText("<html><div style=\"width: 80px; text-align: left; color:#D43A02; font-size: 12px; font-family: Karla; font-weight: 500; line-height: 15px; word-wrap: break-word\">LT01</div></htm");
        ProductTemplate__ID.setPreferredSize(new java.awt.Dimension(100, 20));

        ProductTemplate__Thumbnail.setBackground(new java.awt.Color(255, 255, 255));
        ProductTemplate__Thumbnail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ProductTemplate__Thumbnail.setOpaque(true);
        ProductTemplate__Thumbnail.setPreferredSize(new java.awt.Dimension(140, 180));

        ProductTemplate__Price.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        ProductTemplate__Price.setForeground(new java.awt.Color(252, 140, 25));
        ProductTemplate__Price.setText("<html><div style=\"width: 80px; text-align: left; color:orange; font-size: 12px; font-family: Karla; font-weight: 500; line-height: 15px; word-wrap: break-word\">24.000.000</div></htm");
        ProductTemplate__Price.setPreferredSize(new java.awt.Dimension(100, 20));

        ProductTemplate__Name.setText("<html><div style=\"width: 140px; text-align: center; color: #32403B; font-size: 18px; font-family: Karla; font-weight: 500; line-height: 20px; word-wrap: break-word\">ThinkPad T16</div></html>");
        ProductTemplate__Name.setPreferredSize(new java.awt.Dimension(175, 20));

        ProductTemplate__IDTitle.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        ProductTemplate__IDTitle.setForeground(new Color(0, 0, 0, 100)
        );
        ProductTemplate__IDTitle.setText("<html><div style=\"width: 80px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 8px; font-family: Karla; font-weight: 500; line-height: 15px; word-wrap: break-word\">Mã số:</div></htm");
        ProductTemplate__IDTitle.setPreferredSize(new java.awt.Dimension(70, 20));

        ProductTemplate__PriceTitle.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        ProductTemplate__PriceTitle.setForeground(new Color(0, 0, 0, 100)
        );
        ProductTemplate__PriceTitle.setText("<html><div style=\"width: 80px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 8px; font-family: Karla; font-weight: 500; line-height: 15px; word-wrap: break-word\">Đơn giá:</div></htm");
        ProductTemplate__PriceTitle.setPreferredSize(new java.awt.Dimension(70, 20));

        ProductTemplate__ExpiryTitle.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        ProductTemplate__ExpiryTitle.setForeground(new Color(0, 0, 0, 100)
        );
        ProductTemplate__ExpiryTitle.setText("<html><div style=\"width: 80px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 8px; font-family: Karla; font-weight: 500; line-height: 15px; word-wrap: break-word\">HSD:</div></htm");
        ProductTemplate__ExpiryTitle.setPreferredSize(new java.awt.Dimension(70, 20));

        ProductTemplate__Expiry.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        ProductTemplate__Expiry.setForeground(new java.awt.Color(250, 82, 82));
        ProductTemplate__Expiry.setText("<html><div style=\"width: 80px; text-align: left; color:#CC5DE8; font-size: 12px; font-family: Karla; font-weight: 500; line-height: 15px; word-wrap: break-word\">01/01/2030</div></htm");
        ProductTemplate__Expiry.setPreferredSize(new java.awt.Dimension(100, 20));

        ProductTemplate__QuantityTitle.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        ProductTemplate__QuantityTitle.setForeground(new Color(0, 0, 0, 100)
        );
        ProductTemplate__QuantityTitle.setText("<html><div style=\"width: 80px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 8px; font-family: Karla; font-weight: 500; line-height: 15px; word-wrap: break-word\">SL:</div></htm");
        ProductTemplate__QuantityTitle.setPreferredSize(new java.awt.Dimension(70, 20));

        ProductTemplate__Quantity.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        ProductTemplate__Quantity.setForeground(new java.awt.Color(96, 141, 229));
        ProductTemplate__Quantity.setText("<html><div style=\"width: 80px; text-align: left; color:#94D82D; font-size: 12px; font-family: Karla; font-weight: 500; line-height: 15px; word-wrap: break-word\">25</div></htm");
        ProductTemplate__Quantity.setPreferredSize(new java.awt.Dimension(100, 20));

        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setOpaque(false);
        scroll.setPreferredSize(new java.awt.Dimension(320, 145));

        ProductTemplate__Description.setEditable(false);
        ProductTemplate__Description.setBorder(null);
        ProductTemplate__Description.setText("Hardware\nThe iPhone 13 use an Apple-designed A15 Bionic system on a chip. The iPhone 13 feature a 6-core CPU, 4-core GPU, and 16-core Neural Engine.\n\nDisplay\nThe iPhone 13 features a 6.1-inch (155 mm) display with Super Retina XDR OLED technology at a resolution of 2532×1170 pixels and a pixel density of about 460 PPI with a refresh rate of 60 Hz. The iPhone 13 have the Super Retina XDR OLED display with improved typical brightness up to 800 nits, and max brightness up to 1200 nits.\n\nCameras\nThe iPhone 13 feature the same camera system with three cameras: one front-facing camera (12MP f/2.2) and two back-facing cameras: a wide (12MP f/1.6) and ultra-wide (12MP f/2.4) camera. The back-facing cameras both contain larger sensors for more light-gathering with new sensor shift optical image stabilization (OIS) on the main camera. The camera module on the back is arranged diagonally instead of vertically to engineer the larger sensors.\n\nThe cameras use Apple's latest computational photography engine, called Smart HDR 4. Users can also choose from a range of photographic styles during capture, including rich contrast, vibrant, warm, and cool. Apple clarifies this is different from a filter because it works intelligently with the image processing algorithm during capture to apply local adjustments to an image and the effects will be baked into the photos, unlike filters which can be removed after applying.\n\nThe camera app contains a new mode called Cinematic Mode, which allows users to rack focus between subjects and create (simulate) shallow depth of field using software algorithms. It is supported on wide and front-facing cameras in 1080p at 30 fps.\n\nSoftware\niPhone 13 were originally shipped with iOS 15 at launch. They are compatible with iOS 16, which was released on September 12, 2022. The latest software iOS 17, which was revealed at Apple's WWDC 2023 event, is compatible with the iPhone 13. The next-generation Qi2 wireless charging standard has been added to the iPhone 13 with the update to iOS 17.2.");
        ProductTemplate__Description.setSelectionColor(new java.awt.Color(255, 255, 255));
        scroll.setViewportView(ProductTemplate__Description);

        ProductTemplate__ExpiryTitle1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        ProductTemplate__ExpiryTitle1.setForeground(new Color(0, 0, 0, 100)
        );
        ProductTemplate__ExpiryTitle1.setText("<html><div style=\"width: 80px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 8px; font-family: Karla; font-weight: 500; line-height: 15px; word-wrap: break-word\">Danh mục:</div></htm");
        ProductTemplate__ExpiryTitle1.setPreferredSize(new java.awt.Dimension(70, 20));

        ProductTemplate__ExpiryTitle2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        ProductTemplate__ExpiryTitle2.setForeground(new Color(0, 0, 0, 100)
        );
        ProductTemplate__ExpiryTitle2.setText("<html><div style=\"width: 80px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 8px; font-family: Karla; font-weight: 500; line-height: 15px; word-wrap: break-word\">Nhà sản xuất:</div></htm");
        ProductTemplate__ExpiryTitle2.setPreferredSize(new java.awt.Dimension(70, 20));

        ProductTemplate__Category.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        ProductTemplate__Category.setForeground(new java.awt.Color(101, 168, 68));
        ProductTemplate__Category.setText("<html><div style=\"width: 80px; text-align: left; color:#5C7CFA; font-size: 10px; font-family: Karla; font-weight: 500; line-height: 15px; word-wrap: break-word\">Laptop</div></htm");
        ProductTemplate__Category.setPreferredSize(new java.awt.Dimension(100, 20));

        ProductTemplate__Manafacturer.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        ProductTemplate__Manafacturer.setForeground(new java.awt.Color(101, 168, 68));
        ProductTemplate__Manafacturer.setText("<html><div style=\"width: 80px; text-align: left; color:#FA5252; font-size: 10px; font-family: Karla; font-weight: 500; line-height: 15px; word-wrap: break-word\">Lenovo</div></htm");
        ProductTemplate__Manafacturer.setPreferredSize(new java.awt.Dimension(115, 20));

        ProductTemplate__ExpiryTitle3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        ProductTemplate__ExpiryTitle3.setForeground(new Color(0, 0, 0, 100)
        );
        ProductTemplate__ExpiryTitle3.setText("<html><div style=\"width: 80px; text-align: left; color:rgba(33, 43, 39, 0.8); font-size: 8px; font-family: Karla; font-weight: 500; line-height: 15px; word-wrap: break-word\">Mô tả:</div></htm");
        ProductTemplate__ExpiryTitle3.setPreferredSize(new java.awt.Dimension(70, 20));

        javax.swing.GroupLayout ProductTemplateLayout = new javax.swing.GroupLayout(ProductTemplate);
        ProductTemplate.setLayout(ProductTemplateLayout);
        ProductTemplateLayout.setHorizontalGroup(
            ProductTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProductTemplateLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(ProductTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ProductTemplateLayout.createSequentialGroup()
                        .addComponent(ProductTemplate__Thumbnail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(ProductTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ProductTemplateLayout.createSequentialGroup()
                                .addComponent(ProductTemplate__ExpiryTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(ProductTemplate__Expiry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ProductTemplateLayout.createSequentialGroup()
                                .addComponent(ProductTemplate__ExpiryTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(ProductTemplate__Category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ProductTemplateLayout.createSequentialGroup()
                                .addComponent(ProductTemplate__ExpiryTitle2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(ProductTemplate__Manafacturer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(ProductTemplate__Name, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ProductTemplateLayout.createSequentialGroup()
                                .addComponent(ProductTemplate__IDTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(ProductTemplate__ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ProductTemplateLayout.createSequentialGroup()
                                .addGroup(ProductTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ProductTemplate__PriceTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ProductTemplate__QuantityTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(ProductTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ProductTemplate__Quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ProductTemplate__Price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(ProductTemplateLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(ProductTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ProductTemplate__ExpiryTitle3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        ProductTemplateLayout.setVerticalGroup(
            ProductTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProductTemplateLayout.createSequentialGroup()
                .addGroup(ProductTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ProductTemplateLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(ProductTemplate__Name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(ProductTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ProductTemplate__ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ProductTemplate__IDTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(ProductTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ProductTemplate__QuantityTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ProductTemplate__Quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(ProductTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ProductTemplate__PriceTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ProductTemplate__Price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(ProductTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ProductTemplate__Expiry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ProductTemplate__ExpiryTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(ProductTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ProductTemplate__ExpiryTitle1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ProductTemplate__Category, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(ProductTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ProductTemplate__ExpiryTitle2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ProductTemplate__Manafacturer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ProductTemplateLayout.createSequentialGroup()
                        .addComponent(ProductTemplate__Thumbnail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(ProductTemplate__ExpiryTitle3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5)
                .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ProductTemplate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ProductTemplate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ProductTemplate;
    private javax.swing.JLabel ProductTemplate__Category;
    private javax.swing.JTextPane ProductTemplate__Description;
    private javax.swing.JLabel ProductTemplate__Expiry;
    private javax.swing.JLabel ProductTemplate__ExpiryTitle;
    private javax.swing.JLabel ProductTemplate__ExpiryTitle1;
    private javax.swing.JLabel ProductTemplate__ExpiryTitle2;
    private javax.swing.JLabel ProductTemplate__ExpiryTitle3;
    private javax.swing.JLabel ProductTemplate__ID;
    private javax.swing.JLabel ProductTemplate__IDTitle;
    private javax.swing.JLabel ProductTemplate__Manafacturer;
    private javax.swing.JLabel ProductTemplate__Name;
    private javax.swing.JLabel ProductTemplate__Price;
    private javax.swing.JLabel ProductTemplate__PriceTitle;
    private javax.swing.JLabel ProductTemplate__Quantity;
    private javax.swing.JLabel ProductTemplate__QuantityTitle;
    private javax.swing.JLabel ProductTemplate__Thumbnail;
    private javax.swing.JScrollPane scroll;
    // End of variables declaration//GEN-END:variables
}