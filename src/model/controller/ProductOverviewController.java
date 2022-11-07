package model.controller;

import gui.ProductOverviewControllerInterface;
import model.modelklasser.Product;
import model.modelklasser.ProductCategory;
import model.modelklasser.Situation;
import model.modelklasser.Unit;

import java.util.List;

public class ProductOverviewController implements ProductOverviewControllerInterface {
    //Fields ---------------------------------------------------------------------------------------------------------
    private StorageInterface storage;

    //Constructors ---------------------------------------------------------------------------------------------------

    /**
     * Creates a new ProductOverviewController object.
     *
     * @param storage the storage this object should use, using the StorageInterface interface.
     */
    public ProductOverviewController(StorageInterface storage) {
        this.storage = storage;
    }

    //Methods - Get, Set & Add ---------------------------------------------------------------------------------------

    /**
     * Bruges til test
     *
     * @param storage
     * @return
     */
    public static ProductOverviewControllerInterface getProductOverviewControllerTest(StorageInterface storage) {
        ProductOverviewControllerInterface unique_ProductOverviewController = new ProductOverviewController(storage);
        return unique_ProductOverviewController;
    }

    //Methods - Other ------------------------------------------------------------------------------------------------

    /**
     * Creates a new ProductCategory
     *
     * @param title       the Title of the new category. Cannot be null
     * @param description a description of the new category
     * @return returns the new ProductCategory
     */
    public ProductCategory createProductCategory(String title, String description) {
        ProductCategory current = new ProductCategory(title, description);
        storage.addProductCategory(current);
        return current;
    }

    /**
     * Creates a new product in the given category
     *
     * @param productCategory the ProductCategory to which the new Product will belong
     * @param name            the name of the product. cannot be null.
     * @param description     the description of the product
     * @return returns the new Product.
     */
    public Product createProductForCategory(ProductCategory productCategory, String name, String description) {
        Product current = productCategory.createProduct(name, description);
        return current;
    }

    /**
     * Returns all current ProductCategories
     *
     * @return a list of all ProductCategories.
     */
    public List<ProductCategory> getProductCategories() {
        return storage.getProductCategories();
    }

    /**
     * Returns a list of all situations
     *
     * @return a List of Situations
     */
    public List<Situation> getSituations() {
        return storage.getSituations();
    }

    /**
     * Creates a situation and adds it to the situations list
     * @param name of the situation
     * @return situation
     */
    public Situation createSituation(String name) {
        Situation situation = new Situation(name);
        storage.addSituation(situation);
        return situation;
    }


    /**
     * Creates initial contents for storage
     */
    public void initContent() {
        Situation sitStand = createSituation("Standard");
        Situation sitFred = createSituation("Fredagsbar");

        ProductCategory pc0 = createProductCategory("Klippekort", "");
        Product P01 = pc0.createProduct("Klippekort", "4 Klip");
        P01.createPrice(130, Unit.DKK, sitStand);
        P01.createPrice(130, Unit.DKK, sitFred);

        ProductCategory pc1 = createProductCategory("Flaskeøl", "");
        Product P101 = pc1.createProduct("Klosterbryg", "");
        P101.createPrice(30, Unit.DKK, sitStand);
        P101.createPrice(70, Unit.DKK, sitFred);
        P101.createPrice(2, Unit.Klip, sitFred);
        Product P102 = pc1.createProduct("Sweet Georgia Brown", "");
        P102.createPrice(30, Unit.DKK, sitStand);
        P102.createPrice(70, Unit.DKK, sitFred);
        P102.createPrice(2, Unit.Klip, sitFred);
        Product P103 = pc1.createProduct("Extra Pilsner", "");
        P103.createPrice(30, Unit.DKK, sitStand);
        P103.createPrice(70, Unit.DKK, sitFred);
        P103.createPrice(2, Unit.Klip, sitFred);
        Product P104 = pc1.createProduct("Celebration", "");
        P104.createPrice(30, Unit.DKK, sitStand);
        P104.createPrice(70, Unit.DKK, sitFred);
        P104.createPrice(2, Unit.Klip, sitFred);
        Product P105 = pc1.createProduct("Blondie", "");
        P105.createPrice(30, Unit.DKK, sitStand);
        P105.createPrice(70, Unit.DKK, sitFred);
        P105.createPrice(2, Unit.Klip, sitFred);
        Product P106 = pc1.createProduct("Forårsbryg", "");
        P106.createPrice(30, Unit.DKK, sitStand);
        P106.createPrice(70, Unit.DKK, sitFred);
        P106.createPrice(2, Unit.Klip, sitFred);
        Product P107 = pc1.createProduct("India Pale Ale", "");
        P107.createPrice(30, Unit.DKK, sitStand);
        P107.createPrice(70, Unit.DKK, sitFred);
        P107.createPrice(2, Unit.Klip, sitFred);
        Product P108 = pc1.createProduct("Julebryg", "");
        P108.createPrice(30, Unit.DKK, sitStand);
        P108.createPrice(70, Unit.DKK, sitFred);
        P108.createPrice(2, Unit.Klip, sitFred);
        Product P109 = pc1.createProduct("Juletønden", "");
        P109.createPrice(30, Unit.DKK, sitStand);
        P109.createPrice(70, Unit.DKK, sitFred);
        P109.createPrice(2, Unit.Klip, sitFred);
        Product P110 = pc1.createProduct("Old Strong Ale", "");
        P110.createPrice(30, Unit.DKK, sitStand);
        P110.createPrice(70, Unit.DKK, sitFred);
        P110.createPrice(2, Unit.Klip, sitFred);
        Product P111 = pc1.createProduct("Fregatten Jylland", "");
        P111.createPrice(30, Unit.DKK, sitStand);
        P111.createPrice(70, Unit.DKK, sitFred);
        P111.createPrice(2, Unit.Klip, sitFred);
        Product P112 = pc1.createProduct("Imperial Stout", "");
        P112.createPrice(30, Unit.DKK, sitStand);
        P112.createPrice(70, Unit.DKK, sitFred);
        P112.createPrice(2, Unit.Klip, sitFred);
        Product P113 = pc1.createProduct("Tribute", "");
        P113.createPrice(30, Unit.DKK, sitStand);
        P113.createPrice(70, Unit.DKK, sitFred);
        P113.createPrice(2, Unit.Klip, sitFred);
        Product P114 = pc1.createProduct("Black Monster", "");
        P114.createPrice(60, Unit.DKK, sitStand);
        P114.createPrice(100, Unit.DKK, sitFred);
        P114.createPrice(3, Unit.Klip, sitFred);

        ProductCategory pc2 = createProductCategory("Fadøl", "40 cl");
        Product P201 = pc2.createProduct("Klosterbryg", "");
        P201.createPrice(38, Unit.DKK, sitFred);
        P201.createPrice(1, Unit.Klip, sitFred);
        Product P202 = pc2.createProduct("Jazz Classic", "");
        P202.createPrice(38, Unit.DKK, sitFred);
        P202.createPrice(1, Unit.Klip, sitFred);
        Product P203 = pc2.createProduct("Extra Pilsner", "");
        P203.createPrice(38, Unit.DKK, sitFred);
        P203.createPrice(1, Unit.Klip, sitFred);
        Product P204 = pc2.createProduct("Celebration", "");
        P204.createPrice(38, Unit.DKK, sitFred);
        P204.createPrice(1, Unit.Klip, sitFred);
        Product P205 = pc2.createProduct("Blondie", "");
        P205.createPrice(38, Unit.DKK, sitFred);
        P205.createPrice(1, Unit.Klip, sitFred);
        Product P206 = pc2.createProduct("Forårsbryg", "");
        P206.createPrice(38, Unit.DKK, sitFred);
        P206.createPrice(1, Unit.Klip, sitFred);
        Product P207 = pc2.createProduct("India Pale Ale", "");
        P207.createPrice(38, Unit.DKK, sitFred);
        P207.createPrice(1, Unit.Klip, sitFred);
        Product P208 = pc2.createProduct("Julebryg", "");
        P208.createPrice(38, Unit.DKK, sitFred);
        P208.createPrice(1, Unit.Klip, sitFred);
        Product P209 = pc2.createProduct("Imperial Stout", "");
        P209.createPrice(38, Unit.DKK, sitFred);
        P209.createPrice(1, Unit.Klip, sitFred);
        Product P210 = pc2.createProduct("Special", "");
        P210.createPrice(38, Unit.DKK, sitFred);
        P210.createPrice(1, Unit.Klip, sitFred);

        ProductCategory pc3 = createProductCategory("Snacks", "");
        Product P301 = pc3.createProduct("Æblebrus", "");
        P301.createPrice(15, Unit.DKK, sitFred);
        Product P302 = pc3.createProduct("Chips", "");
        P302.createPrice(10, Unit.DKK, sitFred);
        Product P303 = pc3.createProduct("Peanuts", "");
        P303.createPrice(15, Unit.DKK, sitFred);
        Product P304 = pc3.createProduct("Cola", "");
        P304.createPrice(15, Unit.DKK, sitFred);
        Product P305 = pc3.createProduct("Nikoline", "");
        P305.createPrice(15, Unit.DKK, sitFred);
        Product P306 = pc3.createProduct("7-Up", "");
        P306.createPrice(15, Unit.DKK, sitFred);
        Product P307 = pc3.createProduct("Vand", "");
        P307.createPrice(10, Unit.DKK, sitFred);
        Product P308 = pc3.createProduct("Ølpølser", "");
        P308.createPrice(30, Unit.DKK, sitFred);
        P308.createPrice(1, Unit.Klip, sitFred);

        ProductCategory pc4 = createProductCategory("Spiritus", "");
        Product P401 = pc4.createProduct("Whiskey 45%", "50cl rør");
        P401.createPrice(599, Unit.DKK, sitStand);
        P401.createPrice(599, Unit.DKK, sitFred);
        Product P402 = pc4.createProduct("Whiskey 45%", "4 cl");
        P402.createPrice(50, Unit.DKK, sitFred);
        Product P403 = pc4.createProduct("Whiskey 43%", "50 cl rør");
        P403.createPrice(499, Unit.DKK, sitStand);
        P403.createPrice(499, Unit.DKK, sitFred);
        Product P404 = pc4.createProduct("Whiskey 43%", " u/ egesplint");
        P404.createPrice(300, Unit.DKK, sitStand);
        P404.createPrice(300, Unit.DKK, sitFred);
        Product P405 = pc4.createProduct("Whiskey 43%", " m/ egesplint");
        P405.createPrice(350, Unit.DKK, sitStand);
        P405.createPrice(350, Unit.DKK, sitFred);
        Product P406 = pc4.createProduct("Whiskeyglas & Brikker", "2 stk");
        P406.createPrice(80, Unit.DKK, sitStand);
        P406.createPrice(80, Unit.DKK, sitFred);
        Product P407 = pc4.createProduct("Liquer of Aarhus", "");
        P407.createPrice(175, Unit.DKK, sitStand);
        P407.createPrice(175, Unit.DKK, sitFred);
        Product P408 = pc4.createProduct("Lyng gin", "50 cl");
        P408.createPrice(350, Unit.DKK, sitStand);
        P408.createPrice(350, Unit.DKK, sitFred);
        Product P409 = pc4.createProduct("Lyng gin", "4 cl");
        P409.createPrice(40, Unit.DKK, sitFred);

        ProductCategory pc5 = createProductCategory("Fustage", "");
        Product P501 = pc5.createProduct("Klosterbryg", "20 l");
        P501.createPrice(775, Unit.DKK, sitStand);
        Product P502 = pc5.createProduct("Jazz Classic", "25 l");
        P502.createPrice(625, Unit.DKK, sitStand);
        Product P503 = pc5.createProduct("Extra Pilsner", "25");
        P503.createPrice(575, Unit.DKK, sitStand);
        Product P504 = pc5.createProduct("Celebration", "20");
        P504.createPrice(775, Unit.DKK, sitStand);
        Product P505 = pc5.createProduct("Blondie", "25");
        P505.createPrice(700, Unit.DKK, sitStand);
        Product P506 = pc5.createProduct("Forårsbryg", "20");
        P506.createPrice(775, Unit.DKK, sitStand);
        Product P507 = pc5.createProduct("India Pale Ale", "20");
        P507.createPrice(775, Unit.DKK, sitStand);
        Product P508 = pc5.createProduct("Julebryg", "20");
        P508.createPrice(775, Unit.DKK, sitStand);
        Product P509 = pc5.createProduct("Imperial Stout", "20");
        P509.createPrice(775, Unit.DKK, sitStand);
         for (Product p : pc5.getProducts()) {
            p.createDeposit(200, Unit.DKK, sitStand);
        }

        ProductCategory pc6 = createProductCategory("Kulsyre", "");
        Product P601 = pc6.createProduct("Kulsyre - 6 kg", "");
        P601.createPrice(400, Unit.DKK, sitStand);
        P601.createPrice(400, Unit.DKK, sitFred);
        P601.createDeposit(1000, Unit.DKK, sitStand);
        Product P602 = pc6.createProduct("Kulsyre - 4 kg", "");
        Product P603 = pc6.createProduct("Kulsyre - 10 kg", "");
        for (Product p : pc6.getProducts()) {
            p.createDeposit(1000, Unit.DKK, sitStand);
        }

        ProductCategory pc7 = createProductCategory("Malt", "");
        Product P701 = pc7.createProduct("Malt", "25 kg");
        P701.createPrice(300, Unit.DKK, sitStand);

        ProductCategory pc8 = createProductCategory("Beklædning", "");
        Product P801 = pc8.createProduct("t-shirt", "");
        P801.createPrice(70, Unit.DKK, sitStand);
        P801.createPrice(70, Unit.DKK, sitFred);
        Product P802 = pc8.createProduct("Polo", "");
        P802.createPrice(100, Unit.DKK, sitStand);
        P802.createPrice(100, Unit.DKK, sitFred);
        Product P803 = pc8.createProduct("Cap", "");
        P803.createPrice(30, Unit.DKK, sitStand);
        P803.createPrice(30, Unit.DKK, sitFred);

        ProductCategory pc9 = createProductCategory("Anlæg", "");
        Product P901 = pc9.createProduct("1 Hane", "");
        P901.createPrice(250, Unit.DKK, sitStand);
        Product P902 = pc9.createProduct("2 Haner", "");
        P902.createPrice(400, Unit.DKK, sitStand);
        Product P903 = pc9.createProduct("Bar med flere haner", "");
        P903.createPrice(500, Unit.DKK, sitStand);
        Product P904 = pc9.createProduct("Anlæg Levering", "");
        P904.createPrice(500, Unit.DKK, sitStand);
        Product P905 = pc9.createProduct("Krus", "");
        P905.createPrice(60, Unit.DKK, sitStand);

        ProductCategory pc10 = createProductCategory("Krus", "");
        Product P1001 = pc10.createProduct("Krus", "Uanset størrelse");
        P1001.createPrice(15, Unit.DKK, sitStand);

        ProductCategory pc11 = createProductCategory("Sampakninger", "");
        Product P1101 = pc11.createProduct("Gaveæske", "2 øl, 2 glas");
        P1101.createPrice(110, Unit.DKK, sitStand);
        P1101.createPrice(110, Unit.DKK, sitFred);
        Product P1102 = pc11.createProduct("Gaveæske", "4 øl");
        P1102.createPrice(140, Unit.DKK, sitStand);
        P1102.createPrice(140, Unit.DKK, sitFred);
        Product P1103 = pc11.createProduct("Trækasse", "6 øl");
        P1103.createPrice(260, Unit.DKK, sitStand);
        P1103.createPrice(260, Unit.DKK, sitFred);
        Product P1104 = pc11.createProduct("Gavekurv", "6 øl, 2 glas");
        P1104.createPrice(260, Unit.DKK, sitStand);
        P1104.createPrice(260, Unit.DKK, sitFred);
        Product P1105 = pc11.createProduct("Trækasse", "6 øl, 6 glas");
        P1105.createPrice(350, Unit.DKK, sitStand);
        P1105.createPrice(350, Unit.DKK, sitFred);
        Product P1106 = pc11.createProduct("Trækasse", "12 øl");
        P1106.createPrice(410, Unit.DKK, sitStand);
        P1106.createPrice(410, Unit.DKK, sitFred);
        Product P1107 = pc11.createProduct("Papkasse", "12 øl");
        P1107.createPrice(370, Unit.DKK, sitStand);
        P1107.createPrice(370, Unit.DKK, sitFred);

        ProductCategory pc12 = createProductCategory("Rundvisning", "");
        Product P1201 = pc12.createProduct("Rundvisning - Standard", "Indenfor arbejdstid");
        P1201.createPrice(100, Unit.DKK, sitStand);
        Product P1202 = pc12.createProduct("Rundvisning - Studerende", "");
        P1202.createPrice(70, Unit.DKK, sitStand);
        Product P1203 = pc12.createProduct("Rundvisning - Aften", "Udenfor arbejdstid");
        P1203.createPrice(150, Unit.DKK, sitStand);
    }

}
